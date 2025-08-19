package model;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;
import antlr.ExprParser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;


public class StaticTypeChecker extends ExprBaseVisitor<String> {

    private final ScopeStack scopeStack = new ScopeStack();
    private final SemanticErrorReporter semanticErrorReporter;
    private String currentClass;
    private final Map<String, ScopeStack.ScopeFrame> classScopes = new HashMap<>();
    private final Map<String, String> inheritanceMap = new HashMap<>();
    private final List<String> classList =  new ArrayList<>();

    
    // this map is utilized for function
    private String currentFunction = null;

    public StaticTypeChecker(SemanticErrorReporter semanticErrorReporter) {
        this.semanticErrorReporter = semanticErrorReporter;
    }

    private void addError(String message, Token token, String context) {
        semanticErrorReporter.add(
            message,
            token.getLine(),
            token.getCharPositionInLine(),
            context,
            this.currentClass
        );
    }

    // === Program and Class ===

    @Override
    public String visitProg(ProgContext ctx) {
        for (ParseTree child : ctx.children) {
            visit(child);
        }
        return null;
    }
    
    
    @Override
    public String visitClassDecl(ClassDeclContext ctx) {
        this.currentClass = ctx.CLASSNAME(0).getText();
        classList.add(this.currentClass);
        scopeStack.pushScope();

        // Check if there's a parent
        if (ctx.CLASSNAME().size() > 1) {
            String parentName = ctx.CLASSNAME(1).getText();
            if (classScopes.containsKey(parentName)) {
            	this.inheritanceMap.put(this.currentClass, parentName);
                ScopeStack.ScopeFrame inheritedFrame = classScopes.get(parentName);
                scopeStack.mergeIntoCurrent(ScopeStack.cloneFrame(inheritedFrame));
            } else {
                addError("Parent class '" + parentName + "' not found", ctx.CLASSNAME(1).getSymbol(), "ClassDecl");
            }
        }

        for (ParseTree child : ctx.children) {
            if (child instanceof DeclContext) {
                visit((DeclContext) child);
            } else if (child instanceof StmtContext) {
                visit((StmtContext) child);
            } else if (child instanceof FuncDeclContext) {
                visit((FuncDeclContext) child);
            }
        }

        ScopeStack.ScopeFrame classFrame = ScopeStack.cloneFrame(scopeStack.getCurrentFrame());
        classScopes.put(this.currentClass, classFrame);
        
        
//        System.out.println("CLASS LEVEL ["+ this.currentClass +"]\n" + scopeStack.getCurrentFrame());
        semanticErrorReporter.addClassScope(this.currentClass, ScopeStack.cloneFrame(scopeStack.getCurrentFrame()));
        semanticErrorReporter.setInheritanceMap(inheritanceMap);
        semanticErrorReporter.finalizeScopesRemovingInheritedMembers();
        
        scopeStack.popScope();
        return null;
    }

    // === Variable Declarations ===

    @Override
    public String visitPrimitiveInitializedDeclaration(PrimitiveInitializedDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String varType = visit(ctx.type());
        String exprType = visit(ctx.expr());
        
        Token token = ctx.getStart();
        
        if (getBaseType(varType).equals("VOID")) {
        	addError("Type error: " + varType + " is not an acceptable type for variable: " + varName, token, "PrimitiveInitDecl");
        }
        
        if (scopeStack.isDeclaredInCurrentScope(varName)) {
            addError("Variable already declared: " + varName, token, "PrimitiveInitDecl");
        } else {
            scopeStack.declare(varName, exprType);
        }
        if (!isAssignable(exprType, varType)) {
        	addError("Type mismatch: " + exprType + " expected for variable " + varName, token, "PrimitiveInitDecl");
        }
        return null;
    }

    @Override
    public String visitPrimitiveUninitializedDeclaration(PrimitiveUninitializedDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String varType = visit(ctx.type());
        Token token = ctx.getStart();
        if (scopeStack.isDeclaredInCurrentScope(varName)) {
            addError("Variable already declared: " + varName, token, "PrimitiveUninitDecl");
        } else {
            scopeStack.declare(varName, varType);
        }
        return null;
    }
    
    
    @Override
    public String visitBaseType(BaseTypeContext ctx) {
        if (ctx.INT_TYPE() != null) {
            return "NUMBER";
        } else if (ctx.BOOL_TYPE() != null) {
            return "BOOL";
        } else if (ctx.STRING_TYPE() != null) {
            return "STRING";
        } else {
            return "UNKNOWN";
        }
    }
    
    
    
    @Override
    public String visitType(ExprParser.TypeContext ctx) {
        String base;
        if (ctx.INT_TYPE() != null) {
            base = "NUMBER";
        } else if (ctx.BOOL_TYPE() != null) {
            base = "BOOL";
        } else if (ctx.STRING_TYPE() != null) {
            base = "STRING";
        } else if (ctx.VOID_TYPE() != null) {
            base = "VOID";
        } else if (ctx.CLASSNAME() != null) {
            base = ctx.CLASSNAME().getText();
        } else {
            base = "UNKNOWN";
        }

        int dims = ctx.getChildCount() - 1; // all '[]' come after base
        for (int i = 1; i < ctx.getChildCount(); i += 2) { // every pair is '[' and ']'
            base += "[]";
        }

        return base;
    }
    
    
    // Array stuff
    
    
    @Override
    public String visitArrayLiteral(ArrayLiteralContext ctx) {
        List<String> elementTypes = new ArrayList<>();

        if (ctx.exprList() != null) {
            for (ExprContext exprCtx : ctx.exprList().expr()) {
            	String val = visit(exprCtx);
            	if (!val.contains("EMPTY")) {
            		elementTypes.add(val);
            	}
            }
        }
        
//        System.out.println(ctx.getText());
        if (elementTypes.isEmpty()) {
            return "EMPTY_ARRAY";
        }

        String common = elementTypes.get(0);
        for (String t : elementTypes) {
            common = commonSupertype(common, t);
            if (common.equals("ERROR")) {
                addError("Inconsistent element types in array literal", ctx.getStart(), "ArrayLiteral");
                return "ERROR";
            }
        }
        return common + "[]";
    }
    
    
    @Override
    public String visitArrayAccess(ArrayAccessContext ctx) {
        String arrayType = visit(ctx.expr(0));
        String indexType = visit(ctx.expr(1));

        if (!indexType.equals("NUMBER")) {
            addError("Array index must be of type NUMBER, got " + indexType, ctx.expr(1).getStart(), "ArrayAccess");
            return "ERROR";
        }

        if (!isArrayType(arrayType)) {
            addError("Attempting to index a non-array type: " + arrayType, ctx.expr(0).getStart(), "ArrayAccess");
            return "ERROR";
        }

//        if (arrayType.equals("EMPTY_ARRAY")) {
//            addError("Cannot index an EMPTY_ARRAY without knowing its element type", ctx.getStart(), "ArrayAccess");
//            return "ERROR";
//        }

        return arrayType.substring(0, arrayType.length() - 2);
    }



    
    
    
    public static boolean isArrayType(String type) {
        return type.endsWith("[]") || type.equals("EMPTY_ARRAY");
    }

    public static int getArrayDepth(String type) {
        int depth = 0;
        while (type.endsWith("[]")) {
            depth++;
            type = type.substring(0, type.length() - 2);
        }
        return depth;
    }

    public static String getBaseType(String type) {
        while (type.endsWith("[]")) {
            type = type.substring(0, type.length() - 2);
        }
        return type;
    }
    
    private boolean isAssignable(String from, String to) {
        if (from.equals(to)) return true;

        int fromDepth = getArrayDepth(from);
        int toDepth = getArrayDepth(to);
        if (fromDepth != toDepth) return false;

        String fromBase = getBaseType(from);
        String toBase = getBaseType(to);
        return fromBase.equals(toBase) || inheritsFrom(fromBase, toBase);
    }

    
    private String commonSupertype(String a, String b) {
        // Handle empty or error cases first
        if (a.equals("ERROR") || b.equals("ERROR")) return "ERROR";
        if (a.equals(b)) return a;

        // Handle array types: both must be arrays, then recurse on base type
        if (isArrayType(a) && isArrayType(b)) {
            String baseA = getBaseType(a);
            String baseB = getBaseType(b);

            String commonBase = commonSupertype(baseA, baseB);
            if (commonBase.equals("ERROR")) return "ERROR";

            // Return common base with same array depth
            int depth = getArrayDepth(a); // both should have same depth
            StringBuilder sb = new StringBuilder(commonBase);
            for (int i = 0; i < depth; i++) {
                sb.append("[]");
            }
            return sb.toString();
        }

        // If one is array and other is not, no common supertype
        if (isArrayType(a) || isArrayType(b)) {
            return "ERROR";
        }

        // Neither is array - handle class or primitive inheritance
        if (inheritsFrom(a, b)) return b;
        if (inheritsFrom(b, a)) return a;

        return "ERROR";
    }


    
    
    @Override
    public String visitObjectInitializedDeclaration(ObjectInitializedDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String classNameST = ctx.CLASSNAME(0).getText(); // Static type (the declared type)
        String classNameDT = ctx.CLASSNAME(1).getText(); // Dynamic type (the type of the initialized object)
        Token token = ctx.getStart();

        // Check if the variable is already declared in the current scope
        if (scopeStack.isDeclaredInCurrentScope(varName)) {
            addError("Variable already declared: " + varName, token, "StringUninitDecl");
        } else {
            // Check if polymorphism is valid (dynamic type must inherit from static type)
            if (!inheritsFrom(classNameDT, classNameST)) {
                addError("Incompatible types: " + classNameDT + " cannot be assigned to " + classNameST, token, "PolymorphismError");
            } else {
                // Declare the variable with the static type
                scopeStack.declare(varName, classNameDT);
            }
        }
        return null;
    }

    // === Assignment ===

    @Override
    public String visitAssignmentStatement(AssignmentStatementContext ctx) {
        String leftVarName = ctx.ID(0).getText(); // var or obj
        Token token = ctx.getStart();

        // Object field assignment: obj.field = expr
        if (ctx.ID().size() == 2) {
            String fieldName = ctx.ID(1).getText();
            String objectType = scopeStack.lookup(leftVarName);

            if (objectType == null) {
                addError("Undeclared object: " + leftVarName, token, "Assignment");
                return null;
            }

            String fieldType = resolveFieldInClass(objectType, fieldName);
            if (fieldType == null) {
                addError("Field '" + fieldName + "' does not exist in class " + objectType, token, "Assignment");
                return null;
            }
            

            String exprType = visit(ctx.expr());
            if (!fieldType.equals(exprType)) {
//            	System.out.println("HERE");
                addError("Type mismatch: Cannot assign " + exprType + " to field " + fieldName + " of type " + fieldType, token, "Assignment");
            }

            return fieldType;
        }

        // Simple variable assignment (includes arrays)
        String declaredType = scopeStack.lookup(leftVarName);
        if (declaredType == null) {
            addError("Undeclared variable: " + leftVarName, token, "Assignment");
            return null;
        }

        String exprType = visit(ctx.expr());

        if (exprType.equals("EMPTY_ARRAY")) {
            if (!declaredType.endsWith("[]")) {
                addError("Cannot assign empty array to non-array variable: " + leftVarName, token, "Assignment");
            }
            return declaredType;
        }
        
        
//        System.out.println("" + leftVarName + " " + exprType + " " + declaredType);

        if (!isAssignable(exprType, declaredType)) {
            addError("Type mismatch in assignment to " + leftVarName, token, "Assignment");
        }

        return declaredType;
    }


    // Helper method to resolve the field type in the class (with merged class scopes)
    private String resolveFieldInClass(String className, String fieldName) {
        // Look for the field in the current class's scope
        ScopeStack.ScopeFrame classScope = classScopes.get(className);

        if (classScope != null && classScope.variables.containsKey(fieldName)) {
            return classScope.variables.get(fieldName);  // Found in this class
        }

        // If not found, the class scope is already a merge of its parent and itself
        return null;  // If not found in this class or its parent (shouldn't happen as inheritance is merged)
    }
    
    
    @Override
    public String visitArrayAssignment(ArrayAssignmentContext ctx) {
        String arrayType = visit(ctx.expr(0));
        String indexType = visit(ctx.expr(1));
        String valueType = visit(ctx.expr(2));
        Token token = ctx.getStart();

        if (!indexType.equals("NUMBER")) {
            addError("Array index must be of type NUMBER, got " + indexType, token, "ArrayAssign");
            return null;
        }

        if (!isArrayType(arrayType)) {
            addError("Cannot index non-array type: " + arrayType, token, "ArrayAssign");
            return null;
        }

        if (arrayType.equals("EMPTY_ARRAY")) {
            addError("Cannot assign into EMPTY_ARRAY without a declared type", token, "ArrayAssign");
            return null;
        }

        String expectedType = arrayType.substring(0, arrayType.length() - 2);
        if (!isAssignable(valueType, expectedType)) {
            addError("Type mismatch: assigning " + valueType + " to array element of type " + expectedType, token, "ArrayAssign");
        }

        return null;
    }



    // === Expressions ===

    @Override
    public String visitAddition(AdditionContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="NUMBER" || right !="NUMBER") {
            addError("Addition only works for numbers", token, ctx.getClass().getSimpleName());
        }
        return"NUMBER";
    }

    @Override
    public String visitSubtraction(SubtractionContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="NUMBER" || right !="NUMBER") {
            addError("Subtraction only works for numbers", token, ctx.getClass().getSimpleName());
        }
        return"NUMBER";
    }

    @Override
    public String visitMultiplication(MultiplicationContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left != "NUMBER" || right != "NUMBER") {
            addError("Multiplication only works for numbers", token, ctx.getClass().getSimpleName());
        }
        return"NUMBER";
    }

    @Override
    public String visitDivision(DivisionContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        boolean divByZero = ctx.getText().endsWith("0");
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left != "NUMBER" || right != "NUMBER") {
            addError("Division only works for numbers", token, ctx.getClass().getSimpleName());
        } else if (divByZero) {
            addError("Division by zero error", token, ctx.getClass().getSimpleName());
        }
        return"NUMBER";
    }

    @Override
    public String visitModulo(ModuloContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left != "NUMBER" || right != "NUMBER") {
            addError("Modulo only works for numbers", token, ctx.getClass().getSimpleName());
        }
        return"NUMBER";
    }

    @Override
    public String visitPower(PowerContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left != "NUMBER" || right != "NUMBER") {
            addError("Power only works for numbers", token, ctx.getClass().getSimpleName());
        }
        return"NUMBER";
    }
    
    @Override
    public String visitNegation(NegationContext ctx) {
        String exprType = visit(ctx.expr());
        Token token = ctx.getStart();
        if (exprType == "ERROR") {
            addError("Unreachable or invalid variable usage", token, "Negation");
        } else if (exprType !="BOOL") {
            addError("Logical negation requires BOOL", token, "Negation");
        }
        return"BOOL";
    }
    
    @Override
    public String visitConjunction(ConjunctionContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="BOOL" || right !="BOOL") {
            addError("Logical AND requires BOOLs", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitDisjunction(DisjunctionContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="BOOL" || right !="BOOL") {
            addError("Logical OR requires BOOLs", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitImplication(ImplicationContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="BOOL" || right !="BOOL") {
            addError("Implication requires BOOL operands", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitEqual(EqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left != right) {
            addError("Equality operands must be same type", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitNotEqual(NotEqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left != right) {
            addError("Inequality operands must be same type", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitLessThan(LessThanContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left != "NUMBER" || right != "NUMBER") {
            addError("Less than requires numbers", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitLessThanOrEqual(LessThanOrEqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="NUMBER" || right !="NUMBER") {
            addError("Less than or equal requires numbers", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitGreaterThan(GreaterThanContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="NUMBER" || right !="NUMBER") {
            addError("Greater than requires numbers", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }

    @Override
    public String visitGreaterThanOrEqual(GreaterThanOrEqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, ctx.getClass().getSimpleName());
        } else if (left !="NUMBER" || right !="NUMBER") {
            addError("Greater than or equal requires numbers", token, ctx.getClass().getSimpleName());
        }
        return"BOOL";
    }
    
    @Override
    public String visitPrintStatement(PrintStatementContext ctx) {
    	Token token = ctx.getStart();
    	String content = visit(ctx.printStmt().expr());
    	
    	if (content == "ERROR") {
            addError("Unreachable or invalid variable usage", token, "IfStatement");
        } 
    	
    	return null;
    }
    
    @Override
    public String visitAppendStmt(AppendStmtContext ctx) {
        String arrayType = visit(ctx.expr(0));
        String valueType = visit(ctx.expr(1));
        Token token = ctx.getStart();

        if (!arrayType.endsWith("[]")) {
            addError("Cannot append to non-array type: " + arrayType, token, "Append");
            return null;
        }

        String expectedElementType = arrayType.substring(0, arrayType.length() - 2);
        if (!isAssignable(valueType, expectedElementType)) {
            addError("Type mismatch: cannot append " + valueType + " to array of " + expectedElementType, token, "Append");
        }

        return null;
    }
    
    @Override
    public String visitInsertStmt(InsertStmtContext ctx) {
        String arrayType = visit(ctx.expr(0));
        String indexType = visit(ctx.expr(1));
        String valueType = visit(ctx.expr(2));
        Token token = ctx.getStart();

        if (!arrayType.endsWith("[]")) {
            addError("Cannot insert into non-array type: " + arrayType, token, "Insert");
            return null;
        }

        if (!indexType.equals("NUMBER")) {
            addError("Insert index must be of type NUMBER, got " + indexType, token, "Insert");
            return null;
        }

        String expectedElementType = arrayType.substring(0, arrayType.length() - 2);
        if (!isAssignable(valueType, expectedElementType)) {
            addError("Type mismatch: cannot insert " + valueType + " into array of " + expectedElementType, token, "Insert");
        }

        return null;
    }
    
    @Override
    public String visitObjInitializationStmt(ObjInitializationStmtContext ctx) {
        String className = ctx.CLASSNAME().getText();
        if (classList.contains(className)) {
        	return className;
        } else {
            addError("Class '" + className + "' not found", ctx.CLASSNAME().getSymbol(), "ObjInitializationStmt");
        }
        return "ERROR";
        
    }
    

    @Override
    public String visitGrouping(GroupingContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public String visitNumberLiteral(NumberLiteralContext ctx) {
        return"NUMBER";
    }

    @Override
    public String visitBooleanLiteral(BooleanLiteralContext ctx) {
        return"BOOL";
    }

    @Override
    public String visitStringLiteral(StringLiteralContext ctx) {
        return"STRING";
    }

    @Override
    public String visitVariable(ExprParser.VariableContext ctx) {
        Token token = ctx.getStart();

        if (ctx.ID().size() == 1) {
            // Local variable access
            String varName = ctx.ID(0).getText();
            String varType = scopeStack.lookup(varName);
            if (varType == null) {
                addError("Undeclared variable used: " + varName, token, "Variable");
                return "ERROR";
            }
            return varType;

        } else if (ctx.ID().size() == 2) {
            // Object.field access
            String objName = ctx.ID(0).getText();
            String fieldName = ctx.ID(1).getText();

            // Get declared type of the object
            String objType = scopeStack.lookup(objName);
            if (objType == null) {
                addError("Undeclared object used: " + objName, token, "Variable");
                return "ERROR";
            }

            // Check field existence in classScopes (respecting inheritance)
            ScopeStack.ScopeFrame objectScope = getFullClassScope(objType);
            if (!objectScope.variables.containsKey(fieldName)) {
                addError("Field '" + fieldName + "' not found in class '" + objType + "'", token, "Variable");
                return "ERROR";
            }

            return objectScope.variables.get(fieldName);
        }

        return "ERROR"; // shouldn't happen
    }

    @Override
    public String visitConcatenation(ConcatenationContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        Token token = ctx.getStart();
        if (left == "ERROR" || right == "ERROR") {
            addError("Unreachable or invalid variable usage", token, "Concatenation");
        } else if (left !="STRING" && right !="STRING") {
            addError("Concatenation only works for strings", token, "Concatenation");
        }
        return"STRING";
    }

    // === If Statements ===

    @Override
    public String visitIfStatement(IfStatementContext ctx) {
        String condition = visit(ctx.ifStmt().expr());
        Token token = ctx.getStart();
        if (condition == "ERROR") {
            addError("Unreachable or invalid variable usage", token, "IfStatement");
        } else if (condition !="BOOL") {
            addError("If condition must be BOOL", token, "IfStatement");
        }
        visit(ctx.ifStmt());
        return null;
    }

    @Override
    public String visitIfStmt(IfStmtContext ctx) {
        visit(ctx.block(0));
        if (ctx.block().size() > 1) {
            visit(ctx.block(1));
        }
        return null;
    }
    
    // === While Loop Statements ===
    @Override
    public String visitWhileLoopStatement(WhileLoopStatementContext ctx) {
    	String condition = visit(ctx.whileStmt().expr());
        Token token = ctx.getStart();
        if (condition == "ERROR") {
            addError("Unreachable or invalid variable usage", token, "WhileLoopStatement");
        } else if (condition !="BOOL") {
            addError("while condition must be BOOL", token, "WhileLoopStatement");
        }
        visit(ctx.whileStmt());
        return null;
    }
    
    @Override
    public String visitWhileStmt(WhileStmtContext ctx) {
        visit(ctx.block());
        return null;
    }
    
    // === For Loop Statements ===
    
    public String visitForLoopStatement(ForLoopStatementContext ctx) {
    	HashMap<String, String> currentValues = (HashMap<String, String>) scopeStack.getCurrent();
    	//System.out.println(currentValues.toString());
        scopeStack.pushScope();
        //System.out.println(scopeStack.getCurrent().toString());
        scopeStack.pushPrevoiusScope(currentValues);
        //System.out.println(scopeStack.getCurrent().toString());
        Token token = ctx.getStart();
        
        if (ctx.forStmt().forInt() != null) {
        	visit(ctx.forStmt().forInt());
        }
        //System.out.println(scopeStack.getCurrent().toString());
        
        if (ctx.forStmt().forCond() != null) {
        	String condition = visit(ctx.forStmt().forCond().expr());
        	if (condition == "ERROR") {
                addError("Unreachable or invalid variable usage", token, "ForLoopStatement");
            } else if (condition !="BOOL") {
                addError("for loop condition must be BOOL", token, "ForLoopStatement");
            }
        }
        
        if (ctx.forStmt().stmt() != null) {
        	String condition = visit(ctx.forStmt().stmt());
        	if (condition == "ERROR") {
                addError("Unreachable or invalid variable usage", token, "ForLoopStatement");
            } else if (condition !="NUMBER") {
                addError("for loop update must be NUMBER", token, "ForLoopStatement");
            }
        }
        visit(ctx.forStmt());
        scopeStack.popScope();
        return null;
    }
    
    @Override
    public String visitForStmt(ForStmtContext ctx) {
        visit(ctx.block());
        return null;
    }
    
    public String visitForInt(ForIntContext ctx) {
    	if (ctx.decl() != null) {
    		if (ctx.decl() instanceof PrimitiveInitializedDeclarationContext) {
    			visit(ctx.decl());
    		}
    		else if (ctx.decl() instanceof PrimitiveUninitializedDeclarationContext) {
    			visit(ctx.decl());
    		}
    	}
    	else if (ctx.stmt() != null) {
    		if (ctx.stmt() instanceof AssignmentStatementContext) {
    			visit(ctx.stmt());
    		}
    	}
    	return null;
    }
    
    // === Block Statements ===
    
    @Override
    public String visitBlock(BlockContext ctx) {
    	HashMap<String, String> currentValues = (HashMap<String, String>) scopeStack.getCurrent();
    	//System.out.println(currentValues.toString());
        scopeStack.pushScope();
        //System.out.println(scopeStack.getCurrent().toString());
        scopeStack.pushPrevoiusScope(currentValues);

        //System.out.println(scopeStack.getCurrent().toString());
        for (ParseTree child : ctx.children) {
            if (child instanceof DeclContext) {
                visit((DeclContext) child);
            } else if (child instanceof StmtContext) {
                visit((StmtContext) child);
            } else if (child instanceof FuncDeclContext) {
	            visit((FuncDeclContext) child);
	        }
        }
        
        scopeStack.popScope();
        return null;
    }
    
    
    
    
    //function implementation
    
    public static class FunctionInfo {
        String returnType;
        List<String> paramTypes;

        FunctionInfo(String returnType, List<String> paramTypes) {
            this.returnType = returnType;
            this.paramTypes = paramTypes;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < paramTypes.size(); i++) {
                sb.append(paramTypes.get(i));
                if (i < paramTypes.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("): ").append(returnType);
            return sb.toString();
        }
    }
    

    @Override
    public String visitFuncDecl(FuncDeclContext ctx) {
        String funcName = ctx.ID().getText();
        Token token = ctx.getStart();

        if (scopeStack.isFunctionDeclaredInCurrentScope(funcName)) {
            addError("Function already declared: " + funcName, token, "FuncDecl");
            return null;
        }

        // Get return type
        String returnType = getTypeFromTypeContext(ctx.type());

        List<String> paramTypes = new ArrayList<>();

        // Declare function FIRST so it goes into the class-level scope
        scopeStack.declareFunction(funcName, new FunctionInfo(returnType, paramTypes));
        
        // Then push new scope for parameters + body
        scopeStack.pushScope();
        if (ctx.paramList() != null) {
	        for (ParamContext paramCtx : ctx.paramList().param()) {
	            String paramName = paramCtx.ID().getText();
	            String paramType = getTypeFromTypeContext(paramCtx.type());
	
	            if (scopeStack.isDeclaredInCurrentScope(paramName)) {
	                addError("Parameter already declared: " + paramName, paramCtx.getStart(), "FuncDecl");
	            } else {
	                scopeStack.declare(paramName, paramType);
	                paramTypes.add(paramType);
	            }
	        }
        }

        currentFunction = funcName;
        visit(ctx.block());
        currentFunction = null;

        scopeStack.popScope();
        return null;
    }


    
    @Override
    public String visitFuncCall(ExprParser.FuncCallContext ctx) {
        Token token = ctx.getStart();
        List<ExprParser.ExprContext> args = ctx.argList() != null ? ctx.argList().expr() : Collections.emptyList();

        if (ctx.ID().size() == 1) {
            // Local function
            String funcName = ctx.ID(0).getText();
            FunctionInfo funcInfo = scopeStack.lookupFunction(funcName);
            if (funcInfo == null) {
                addError("Call to undeclared function: " + funcName, token, "FuncCall");
                return "ERROR";
            }

            checkArgsMatch(args, funcInfo, funcName, token);
            return funcInfo.returnType;

        } else if (ctx.ID().size() == 2) {
            // Method call: obj.method()
            String objName = ctx.ID(0).getText();
            String methodName = ctx.ID(1).getText();

            String objType = scopeStack.lookup(objName);
            if (objType == null) {
                addError("Undeclared object used: " + objName, token, "FuncCall");
                return "ERROR";
            }

            ScopeStack.ScopeFrame objectScope = getFullClassScope(objType);
            FunctionInfo methodInfo = objectScope.functions.get(methodName);

            if (methodInfo == null) {
                addError("Method '" + methodName + "' not found in class '" + objType + "'", token, "FuncCall");
                return "ERROR";
            }

            checkArgsMatch(args, methodInfo, methodName, token);
            return methodInfo.returnType;
        }

        return "ERROR";
    }

    
    @Override
    public String visitReturnStmt(ReturnStmtContext ctx) {
        if (currentFunction == null) {
            addError("Return statement not inside a function", ctx.getStart(), "ReturnStmt");
            return null;
        }

        FunctionInfo funcInfo = scopeStack.lookupFunction(currentFunction);
        if (funcInfo == null) {
            addError("Cannot resolve current function '" + currentFunction + "'", ctx.getStart(), "ReturnStmt");
            return null;
        }

        String returnType = visit(ctx.expr());
        if (returnType != funcInfo.returnType) {
            addError("Return type mismatch in function " + currentFunction, ctx.getStart(), "ReturnStmt");
        }

        return null;
    }

    
    private String getTypeFromTypeContext(TypeContext ctx) {
    	//helper to convert parser type contexts into your internal
        if (ctx == null) {
            return null; // or "ERROR", depending on your design
        }
        if (ctx.INT_TYPE() != null) {
            return"NUMBER";
        } else if (ctx.BOOL_TYPE() != null) {
            return"BOOL";
        } else if (ctx.STRING_TYPE() != null) {
            return"STRING";
        } else if (ctx.VOID_TYPE() != null) {
            return"VOID";
        }
        return "ERROR"; // fallback for unexpected input
    }
    
    
    public boolean inheritsFrom(String child, String parent) {
        String currentClass = child;
        
        while (currentClass != null) {
            if (currentClass.equals(parent)) {
                return true; // Found the parent in the inheritance chain
            }
            currentClass = inheritanceMap.get(currentClass); // Move up the inheritance chain
        }
        
        return false; // Parent not found in the chain
    }
    
    
    private ScopeStack.ScopeFrame getFullClassScope(String className) {
        ScopeStack.ScopeFrame merged = new ScopeStack.ScopeFrame();

        while (className != null) {
            ScopeStack.ScopeFrame current = classScopes.get(className);
            if (current != null) {
                merged.merge(current);
            }
            className = inheritanceMap.get(className);
        }

        return merged;
    }
    
    
    private void checkArgsMatch(List<ExprParser.ExprContext> args, FunctionInfo funcInfo, String funcName, Token token) {
        if (args.size() != funcInfo.paramTypes.size()) {
            addError("Argument count mismatch in call to " + funcName, token, "FuncCall");
            return;
        }

        for (int i = 0; i < args.size(); i++) {
            String argType = visit(args.get(i));
            String expectedType = funcInfo.paramTypes.get(i);
            if (!argType.equals(expectedType)) {
                addError("Argument type mismatch in call to " + funcName + " at argument " + (i + 1), token, "FuncCall");
            }
        }
    }
    
}
