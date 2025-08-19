
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
    NUMBER value = 99;

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

    NUMBER val = obj.value;
    NUMBER doubled = obj.double(10);

    for (NUMBER i = 0; i < 5; i = i + 1;) {
        val = val + i;
    }

    while (val < 200) {
        val = val + 10;
    }

    // wrong type in function call
    NUMBER bad = obj.double("string");

    // field doesn't exist
    BOOL nope = obj.fakeField;

    // method doesn't exist
    obj.notAFunction();

    // === BASIC TYPE CHECKING STUFF ===

    // undeclared variable access
    NUMBER a = undeclaredVar;

    // variable redeclaration
    NUMBER z = 5;
    NUMBER z = 10;

    // type mismatch assignment
    NUMBER notANumber = true;

    // bad expression: adding bool to number
    NUMBER x = 4 + false;

    // bad expression: or with number
    BOOL logicFail = 1 or true;

    // bad if condition type
    if ("string") {
        val = 5;
    }

    // bad return type
    fun returnMismatch(): NUMBER {
        return "hello";
    }

    // too many arguments
    NUMBER tooMany = double(1, 2);

    // too few arguments
    BOOL missingArg = isBig();
}



+++++++++++++++++++++++++++++++++++++++++++
OUTPUT
+++++++++++++++++++++++++++++++++++++++++++

Semantic errors found:
Error [FuncCall] in class 'Main' at line 34, char 17: Argument type mismatch in call to double at argument 1
Error [Variable] in class 'Main' at line 37, char 16: Field 'fakeField' not found in class 'A'
Error [BooleanInitDecl] in class 'Main' at line 37, char 4: Type mismatch: BOOL expected for variable nope
Error [FuncCall] in class 'Main' at line 40, char 4: Method 'notAFunction' not found in class 'A'
Error [Variable] in class 'Main' at line 45, char 15: Undeclared variable used: undeclaredVar
Error [NumberInitDecl] in class 'Main' at line 45, char 4: Type mismatch: NUMBER expected for variable a
Error [NumberInitDecl] in class 'Main' at line 49, char 4: Variable already declared: z
Error [NumberInitDecl] in class 'Main' at line 52, char 4: Type mismatch: NUMBER expected for variable notANumber
Error [AdditionContext] in class 'Main' at line 55, char 15: Addition only works for numbers
Error [DisjunctionContext] in class 'Main' at line 58, char 21: Logical OR requires BOOLs
Error [IfStatement] in class 'Main' at line 61, char 4: If condition must be BOOL
Error [ReturnStmt] in class 'Main' at line 67, char 8: Return type mismatch in function returnMismatch
Error [FuncCall] in class 'Main' at line 71, char 21: Argument count mismatch in call to double
Error [FuncCall] in class 'Main' at line 74, char 22: Argument count mismatch in call to isBig
