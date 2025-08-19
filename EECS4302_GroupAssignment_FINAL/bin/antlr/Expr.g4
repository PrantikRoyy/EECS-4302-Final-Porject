grammar Expr;

@header {
    package antlr;
}

// === PROGRAM ===
prog
    : (classDecl | funcDecl | decl | stmt)+ EOF
    ;

// === CLASS ===
classDecl
    : 'class' CLASSNAME ('inherits' CLASSNAME)? '{' (decl | funcDecl | stmt)* '}'
    ;

// === DECLARATIONS ===
decl
    : 
    CLASSNAME ID '=' 'new' CLASSNAME ';'			# ObjectInitializedDeclaration
    | type ID '=' expr ';'        					# PrimitiveInitializedDeclaration
    | type ID ';'                 					# PrimitiveUninitializedDeclaration
    ;


    
baseType
    : INT_TYPE | BOOL_TYPE | STRING_TYPE | CLASSNAME
    ;


// === STATEMENTS ===
stmt
    : (ID | ID '.' ID) '=' expr ';'   	# AssignmentStatement
    | expr '[' expr ']' '=' expr ';'  	# ArrayAssignment
    | ifStmt                          	# IfStatement
    | whileStmt						  	# WhileLoopStatement
    | forStmt						  	# ForLoopStatement
    | printStmt						  	# PrintStatement
    | appendStmt						# AppendStatement
    | insertStmt						# InsertStatement
    | returnStmt                  	  	# ReturnStatement
    | funcCall ';'                	  	# FunctionCallStatement
    ;

returnStmt
    : 'return' expr ';'
    ;

funcCall
    : ( ID | ID '.' ID ) '(' argList? ')'
    ;


argList
    : expr (',' expr)*
    ;

// === FUNCTION DECLARATION ===
funcDecl
    : 'fun' ID '(' paramList? ')' ':' type block
    ;

paramList
    : param (',' param)*
    ;

param
    : ID ':' type
    ;

type
    : (INT_TYPE | BOOL_TYPE | STRING_TYPE | VOID_TYPE | CLASSNAME) ('[' ']')*
    ;


// === PRINT ===
printStmt: 'print' '(' expr ')'  ';';

// === APPEND/INSERT ===
appendStmt: 'append' '(' expr ',' expr ')' ';';
insertStmt: 'insert' '(' expr ',' expr ',' expr ')' ';';

// === IF ===
ifStmt
    : 'if' '(' expr ')' block ('else' block)?
    ;

// === WHILE ===
whileStmt: 'while' '(' expr ')' block  
    ;

forStmt: 'for' '(' forInt? forCond? stmt? ')' block 
    ;

forInt: (decl | stmt) 
    ;

forCond: expr ';'
    ;

// === BLOCK ===
block
    : '{' (decl | stmt)* '}'
    ;

// === ARRAYS ===
exprList
    : expr (',' expr)*
    ;

// === EXPRESSIONS ===
expr
    : '(' expr ')'                    # Grouping
    | expr '^' expr                   # Power
    | expr '%' expr                   # Modulo
    | expr '*' expr                   # Multiplication
    | expr '/' expr                   # Division
    | expr '+' expr                   # Addition
    | expr '-' expr                   # Subtraction
    | expr '==' expr                  # Equal
    | expr '!=' expr                  # NotEqual
    | expr '<' expr                   # LessThan
    | expr '>' expr                   # GreaterThan
    | expr '<=' expr                  # LessThanOrEqual
    | expr '>=' expr                  # GreaterThanOrEqual
    | 'not' expr                      # Negation
    | expr 'and' expr                 # Conjunction
    | expr 'or' expr                  # Disjunction
    | expr '->' expr                  # Implication
    | expr '&' expr                   # Concatenation
    | 'new' CLASSNAME				  # ObjInitializationStmt
    
    | expr '[' expr ']'               # ArrayAccess
    | '[' exprList? ']'    	 		  # ArrayLiteral
    
    | BOOL_LITERAL                    # BooleanLiteral
    | (ID | ID '.' ID)                # Variable
    | NUM                             # NumberLiteral
    | STRING_LITERAL                  # StringLiteral
    | funcCall                        # FunctionCallExpr
    ;

// === LEXER ===
BOOL_LITERAL : 'true' | 'false';
INT_TYPE : 'NUMBER';
BOOL_TYPE : 'BOOL';
STRING_TYPE : 'STRING';
VOID_TYPE : 'VOID';

CLASSNAME : [A-Z][a-zA-Z0-9_]*;
ID        : [a-z][a-zA-Z0-9_]*;

NUM       : '0' | '-'?[1-9][0-9]*;
STRING_LITERAL : '"' ( ~["\\\r\n] | '\\' . )* '"';

COMMENT : ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;
WS : [ \t\r\n]+ -> skip;
