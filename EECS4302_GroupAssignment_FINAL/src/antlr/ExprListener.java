// Generated from Expr.g4 by ANTLR 4.13.2

    package antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void enterClassDecl(ExprParser.ClassDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void exitClassDecl(ExprParser.ClassDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjectInitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterObjectInitializedDeclaration(ExprParser.ObjectInitializedDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjectInitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitObjectInitializedDeclaration(ExprParser.ObjectInitializedDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimitiveInitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveInitializedDeclaration(ExprParser.PrimitiveInitializedDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimitiveInitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveInitializedDeclaration(ExprParser.PrimitiveInitializedDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimitiveUninitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveUninitializedDeclaration(ExprParser.PrimitiveUninitializedDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimitiveUninitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveUninitializedDeclaration(ExprParser.PrimitiveUninitializedDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterBaseType(ExprParser.BaseTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitBaseType(ExprParser.BaseTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(ExprParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(ExprParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAssignment}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterArrayAssignment(ExprParser.ArrayAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAssignment}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitArrayAssignment(ExprParser.ArrayAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ExprParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ExprParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileLoopStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoopStatement(ExprParser.WhileLoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileLoopStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoopStatement(ExprParser.WhileLoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForLoopStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterForLoopStatement(ExprParser.ForLoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForLoopStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitForLoopStatement(ExprParser.ForLoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(ExprParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(ExprParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AppendStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterAppendStatement(ExprParser.AppendStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AppendStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitAppendStatement(ExprParser.AppendStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InsertStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterInsertStatement(ExprParser.InsertStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InsertStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitInsertStatement(ExprParser.InsertStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ExprParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ExprParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallStatement(ExprParser.FunctionCallStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallStatement(ExprParser.FunctionCallStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(ExprParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(ExprParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(ExprParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(ExprParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(ExprParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(ExprParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void enterFuncDecl(ExprParser.FuncDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void exitFuncDecl(ExprParser.FuncDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(ExprParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(ExprParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(ExprParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(ExprParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ExprParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ExprParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(ExprParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(ExprParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#appendStmt}.
	 * @param ctx the parse tree
	 */
	void enterAppendStmt(ExprParser.AppendStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#appendStmt}.
	 * @param ctx the parse tree
	 */
	void exitAppendStmt(ExprParser.AppendStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#insertStmt}.
	 * @param ctx the parse tree
	 */
	void enterInsertStmt(ExprParser.InsertStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#insertStmt}.
	 * @param ctx the parse tree
	 */
	void exitInsertStmt(ExprParser.InsertStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(ExprParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(ExprParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(ExprParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(ExprParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(ExprParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(ExprParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#forInt}.
	 * @param ctx the parse tree
	 */
	void enterForInt(ExprParser.ForIntContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#forInt}.
	 * @param ctx the parse tree
	 */
	void exitForInt(ExprParser.ForIntContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#forCond}.
	 * @param ctx the parse tree
	 */
	void enterForCond(ExprParser.ForCondContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#forCond}.
	 * @param ctx the parse tree
	 */
	void exitForCond(ExprParser.ForCondContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ExprParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ExprParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(ExprParser.ExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#exprList}.
	 * @param ctx the parse tree
	 */
	void exitExprList(ExprParser.ExprListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Multiplication}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplication(ExprParser.MultiplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Multiplication}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplication(ExprParser.MultiplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ExprParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ExprParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNegation(ExprParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNegation(ExprParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessThanOrEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLessThanOrEqual(ExprParser.LessThanOrEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanOrEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLessThanOrEqual(ExprParser.LessThanOrEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(ExprParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(ExprParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterThanOrEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanOrEqual(ExprParser.GreaterThanOrEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanOrEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanOrEqual(ExprParser.GreaterThanOrEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Implication}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterImplication(ExprParser.ImplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Implication}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitImplication(ExprParser.ImplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessThan}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLessThan(ExprParser.LessThanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThan}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLessThan(ExprParser.LessThanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAccess}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(ExprParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAccess}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(ExprParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjInitializationStmt}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterObjInitializationStmt(ExprParser.ObjInitializationStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjInitializationStmt}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitObjInitializationStmt(ExprParser.ObjInitializationStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqual(ExprParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqual(ExprParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterThan}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThan(ExprParser.GreaterThanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThan}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThan(ExprParser.GreaterThanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Conjunction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(ExprParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Conjunction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(ExprParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Division}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDivision(ExprParser.DivisionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Division}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDivision(ExprParser.DivisionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Addition}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddition(ExprParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Addition}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddition(ExprParser.AdditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotEqual(ExprParser.NotEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotEqual(ExprParser.NotEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Modulo}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterModulo(ExprParser.ModuloContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Modulo}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitModulo(ExprParser.ModuloContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Grouping}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGrouping(ExprParser.GroupingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Grouping}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGrouping(ExprParser.GroupingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(ExprParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(ExprParser.ArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Disjunction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(ExprParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Disjunction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(ExprParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpr(ExprParser.FunctionCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpr(ExprParser.FunctionCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Subtraction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSubtraction(ExprParser.SubtractionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Subtraction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSubtraction(ExprParser.SubtractionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Concatenation}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConcatenation(ExprParser.ConcatenationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Concatenation}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConcatenation(ExprParser.ConcatenationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(ExprParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(ExprParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(ExprParser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(ExprParser.NumberLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Power}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPower(ExprParser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Power}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPower(ExprParser.PowerContext ctx);
}