// Generated from Expr.g4 by ANTLR 4.13.2

    package antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ExprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#classDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(ExprParser.ClassDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjectInitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectInitializedDeclaration(ExprParser.ObjectInitializedDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimitiveInitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveInitializedDeclaration(ExprParser.PrimitiveInitializedDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimitiveUninitializedDeclaration}
	 * labeled alternative in {@link ExprParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveUninitializedDeclaration(ExprParser.PrimitiveUninitializedDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(ExprParser.BaseTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(ExprParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAssignment}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAssignment(ExprParser.ArrayAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ExprParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileLoopStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoopStatement(ExprParser.WhileLoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForLoopStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoopStatement(ExprParser.ForLoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(ExprParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AppendStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAppendStatement(ExprParser.AppendStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InsertStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(ExprParser.InsertStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ExprParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCallStatement}
	 * labeled alternative in {@link ExprParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallStatement(ExprParser.FunctionCallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(ExprParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(ExprParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(ExprParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#funcDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDecl(ExprParser.FuncDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(ExprParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(ExprParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ExprParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(ExprParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#appendStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAppendStmt(ExprParser.AppendStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#insertStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStmt(ExprParser.InsertStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(ExprParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(ExprParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(ExprParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#forInt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInt(ExprParser.ForIntContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#forCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCond(ExprParser.ForCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ExprParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(ExprParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Multiplication}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplication(ExprParser.MultiplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(ExprParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegation(ExprParser.NegationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessThanOrEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanOrEqual(ExprParser.LessThanOrEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(ExprParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterThanOrEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanOrEqual(ExprParser.GreaterThanOrEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Implication}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplication(ExprParser.ImplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessThan}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThan(ExprParser.LessThanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAccess}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(ExprParser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjInitializationStmt}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjInitializationStmt(ExprParser.ObjInitializationStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(ExprParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterThan}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThan(ExprParser.GreaterThanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Conjunction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunction(ExprParser.ConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Division}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivision(ExprParser.DivisionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Addition}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddition(ExprParser.AdditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotEqual}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEqual(ExprParser.NotEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Modulo}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModulo(ExprParser.ModuloContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Grouping}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrouping(ExprParser.GroupingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiteral(ExprParser.ArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Disjunction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisjunction(ExprParser.DisjunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpr(ExprParser.FunctionCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Subtraction}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtraction(ExprParser.SubtractionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Concatenation}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatenation(ExprParser.ConcatenationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(ExprParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(ExprParser.NumberLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Power}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(ExprParser.PowerContext ctx);
}