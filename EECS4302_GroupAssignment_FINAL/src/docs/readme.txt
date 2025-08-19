
java -jar my-compiler.jar src/tests/test1.txt --html --console
(assuming the jar is in the base directory of the project)

===========================================
           COMPILER USAGE OVERVIEW
===========================================

1. Command-Line Flags
The compiler accepts optional flags to control output behavior:

  --html     Generates an HTML semantic error report.
  --console  Prints semantic errors directly to the console.

These flags can be used independently or together.  
If no flags are provided, the compiler runs silently unless errors are encountered.

2. Multi-File Input Support
The compiler supports multiple input files in a single run.

- All specified files are read in the order listed.
- Their contents are concatenated as one input unit.
- Parsing and type checking are performed on the combined input.

Example:
java -jar my-compiler.jar file1.txt file2.txt --html --console

The output will be:
- A single HTML document in the base project directory summarizing all semantic errors.
- Optional console output of the same errors if `--console` is used.

SINGLE INPUT OR MULTIPLE FILES => ONE HTML REPORT (if requested)


===========================================
      CUSTOM LANGUAGE SYNTAX OVERVIEW
===========================================

This language supports class-based OOP, strong typing, 
basic control flow, expressions, and arrays.

-----------------------------
1. VARIABLE DECLARATIONS
-----------------------------
NUMBER x = 5;
BOOL flag = true;
STRING name = "John";
MyClass obj = new MyClass;

Uninitialized:
NUMBER x;
BOOL flag;
STRING name;

Array Declarations:
NUMBER[] nums = [1, 2, 3];
BOOL[] bools = [true, false];
STRING[] strs = ["a", "b"];
NUMBER[][] matrix = [[1, 2], [3, 4]];
MyClass[] objs = [new MyClass, new MyClass];
MyClass[][] grid = [[new MyClass], [new MyClass]];

Empty or uninitialized arrays:
NUMBER[] empty;
BOOL[] flags = [true, false, true];

-----------------------------
2. FUNCTION DECLARATIONS
-----------------------------
fun add(a: NUMBER, b: NUMBER): NUMBER {
    return a + b;
}

fun printHello(): VOID {
    print("Hello");
}

-----------------------------
3. CLASS DECLARATIONS
-----------------------------
class Animal {
    STRING name;
    fun speak(): VOID {
        print("generic sound");
    }
}

class Dog inherits Animal {
    fun speak(): VOID {
        print("woof");
    }
}

-----------------------------
4. OBJECT CREATION & ACCESS
-----------------------------
Dog d = new Dog;
d.name = "Fido";
d.speak();

-----------------------------
5. FUNCTION CALLS
-----------------------------
add(2, 3);
printHello();
d.speak();

-----------------------------
6. CONTROL FLOW
-----------------------------
IF:
if (x > 5) {
    print("big");
} else {
    print("small");
}

WHILE:
while (x < 10) {
    x = x + 1;
}

FOR:
for (NUMBER i = 0; i < 5; i = i + 1;) {
    print(i);
}

-----------------------------
7. EXPRESSIONS
-----------------------------
Arithmetic:
x + 1, y - 2, a * b, n / 3, x % 2, 2 ^ 3

Boolean:
true, false, not flag, flag and x > 3, flag or x < 10

Comparison:
x == 10, y != 3, a >= b, c < d

Implication:
a -> b

String Concatenation:
"Hi" & name

Parentheses:
(x + 2) * 3

-----------------------------
8. STATEMENTS
-----------------------------
Assignments:
x = 5;
d.name = "Rex";

Returns:
return x;

Function calls as statements:
print("hi");
d.speak();

Array indexing and assignment:
nums[0] = 10;
matrix[1][1] = 7;
objs[0] = new MyClass;

Insert/append:
insert(nums, 1, 99);
append(strs, "c");

-----------------------------
9. TYPES
-----------------------------
Built-in:
- NUMBER
- BOOL
- STRING
- VOID (for functions only)

Custom:
- Any class name (e.g., Dog, Animal, MyClass)

Arrays:
- Any valid type followed by `[]`, including multi-dimensional arrays:
  - NUMBER[]
  - STRING[][]
  - MyClass[][]



+++++++++++++++++++++++++++++++++++++++++++
SAMPLE INPUT:
+++++++++++++++++++++++++++++++++++++++++++

class A {
    NUMBER value = 42;

    fun double(x: NUMBER): NUMBER {
        return x * 2;
    }

    fun isBig(n: NUMBER): BOOL {
        return n > 100;
    }
}

class B inherits A {
    NUMBER twice = double(value);
    BOOL check = isBig(twice);
}

class Main inherits B {
    A obj = new A;
    B sub = new B;

    NUMBER val = obj.value;
    NUMBER doubled = obj.double(10);

    // === VALID ARRAY DECLARATIONS ===
    NUMBER[] nums = [1,2,3];
    BOOL[] bools = [true, false];
    STRING[] strs = ["a", "b"];
    NUMBER[][] matrix = [[1, 2], [3, 4]];
    A[] polyArr = [new A, new B, sub];
    A[][] poly2D = [[new A, new B], [obj, sub]];
    NUMBER[] empty;
    BOOL[] flags = [true, false, true];

    // === ARRAY INDEXING / ASSIGNMENTS ===
    nums[0] = 5;
    matrix[1][1] = 9;
    polyArr[1] = new B;
    poly2D[0] = [new A, new B];

    // === INSERT / APPEND: VALID CASES ===
    insert(nums, 1, 99);
    insert(matrix, 0, [7, 8]);
    insert(poly2D, 2, [obj, sub]);
    insert(flags, 0, true);
    append(nums, 10);
    append(strs, "z");
    append(bools, false);
    append(polyArr, new B);

    // === CONTROL STRUCTURES ===
    for (NUMBER i = 0; i < 5; i = i + 1;) {
        val = val + i;
    }

    while (val < 200) {
        val = val + 10;
    }

    if (isBig(val)) {
        print("Big number");
    } else {
        print("Small number");
    }

    // === POLYMORPHISM ===
    A baseRef = new B;
    print(baseRef.double(3));
    print(baseRef.value);

    // === ERROR CASES ===

    // ERROR: wrong arg type
    NUMBER err1 = obj.double("string");  // ERROR: STRING passed to NUMBER param

    // ERROR: no such field
    BOOL err2 = obj.fakeField;  // ERROR: field doesn't exist

    // ERROR: no such method
    obj.notAFunction();  // ERROR: method doesn't exist

    // ERROR: undeclared variable
    NUMBER err3 = undeclaredVar;  // ERROR: undeclaredVar not declared

    // ERROR: variable redeclaration
    NUMBER z = 5;
    NUMBER z = 10;  // ERROR: z already declared

    // ERROR: type mismatch assignment
    NUMBER err4 = true;  // ERROR: BOOL assigned to NUMBER

    // ERROR: invalid operation
    NUMBER err5 = 4 + false;  // ERROR: NUMBER + BOOL not valid

    // ERROR: bad logic op
    BOOL err6 = 1 or true;  // ERROR: NUMBER in logical op

    // ERROR: bad conditional type
    if ("string") { val = 5; }  // ERROR: STRING in condition

    // ERROR: return mismatch
    fun badReturn(): NUMBER {
        return "hello";  // ERROR: STRING returned to NUMBER function
    }

    // ERROR: too many args
    NUMBER err7 = double(1, 2);  // ERROR: extra argument

    // ERROR: too few args
    BOOL err8 = isBig();  // ERROR: missing argument

    // === BAD INSERT / APPEND CASES ===

    // ERROR: insert wrong type
    insert(nums, 0, "bad");  // ERROR: STRING into NUMBER[]

    // ERROR: insert into wrong target
    insert(val, 0, 1);  // ERROR: cannot insert into scalar

    // ERROR: append wrong type
    append(bools, 1);  // ERROR: NUMBER into BOOL[]

    // ERROR: append to non-array
    append(val, 99);  // ERROR: cannot append to scalar

    // ERROR: insert into 2D array with wrong shape
    insert(matrix, 1, 9);  // ERROR: expected NUMBER[] not NUMBER

    // ERROR: append wrong object type
    append(polyArr, "string");  // ERROR: STRING into A[]

    // ERROR: invalid array literal
    NUMBER[] badArr = [1, true, 3];  // ERROR: BOOL in NUMBER[]

    // ERROR: bad matrix nesting
    NUMBER[][] badMat = [[1, 2], ["oops"]];  // ERROR: STRING in NUMBER[][]

    // ERROR: indexing non-array
    NUMBER nonArr = 5;
    nonArr[0] = 1;  // ERROR: can't index non-array

    // ERROR: assigning empty array to scalar
    BOOL badAssign = [true, false];  // ERROR: array not assignable to BOOL
}




+++++++++++++++++++++++++++++++++++++++++++
OUTPUT
+++++++++++++++++++++++++++++++++++++++++++

Semantic errors found:
Error [FuncCall] in class 'Main' at line 74, char 18: Argument type mismatch in call to double at argument 1
Error [Variable] in class 'Main' at line 77, char 16: Field 'fakeField' not found in class 'A'
Error [PrimitiveInitDecl] in class 'Main' at line 77, char 4: Type mismatch: ERROR expected for variable err2
Error [FuncCall] in class 'Main' at line 80, char 4: Method 'notAFunction' not found in class 'A'
Error [Variable] in class 'Main' at line 83, char 18: Undeclared variable used: undeclaredVar
Error [PrimitiveInitDecl] in class 'Main' at line 83, char 4: Type mismatch: ERROR expected for variable err3
Error [PrimitiveInitDecl] in class 'Main' at line 87, char 4: Variable already declared: z
Error [PrimitiveInitDecl] in class 'Main' at line 90, char 4: Type mismatch: BOOL expected for variable err4
Error [AdditionContext] in class 'Main' at line 93, char 18: Addition only works for numbers
Error [DisjunctionContext] in class 'Main' at line 96, char 16: Logical OR requires BOOLs
Error [IfStatement] in class 'Main' at line 99, char 4: If condition must be BOOL
Error [ReturnStmt] in class 'Main' at line 103, char 8: Return type mismatch in function badReturn
Error [FuncCall] in class 'Main' at line 107, char 18: Argument count mismatch in call to double
Error [FuncCall] in class 'Main' at line 110, char 16: Argument count mismatch in call to isBig
Error [Insert] in class 'Main' at line 115, char 4: Type mismatch: cannot insert STRING into array of NUMBER
Error [Insert] in class 'Main' at line 118, char 4: Cannot insert into non-array type: NUMBER
Error [Append] in class 'Main' at line 121, char 4: Type mismatch: cannot append NUMBER to array of BOOL
Error [Append] in class 'Main' at line 124, char 4: Cannot append to non-array type: NUMBER
Error [Insert] in class 'Main' at line 127, char 4: Type mismatch: cannot insert NUMBER into array of NUMBER[]
Error [Append] in class 'Main' at line 130, char 4: Type mismatch: cannot append STRING to array of A
Error [ArrayLiteral] in class 'Main' at line 133, char 22: Inconsistent element types in array literal
Error [PrimitiveInitDecl] in class 'Main' at line 133, char 4: Type mismatch: ERROR expected for variable badArr
Error [ArrayLiteral] in class 'Main' at line 136, char 24: Inconsistent element types in array literal
Error [PrimitiveInitDecl] in class 'Main' at line 136, char 4: Type mismatch: ERROR expected for variable badMat
Error [ArrayAssign] in class 'Main' at line 140, char 4: Cannot index non-array type: NUMBER
Error [PrimitiveInitDecl] in class 'Main' at line 143, char 4: Type mismatch: BOOL[] expected for variable badAssign
