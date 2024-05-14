# _Table editor with formula support_ test assignment
Submission for _Low code Table Data preprocessing_ internship @Jetbrains by Alan Styslavski

## The Expression Language

### Grammar (Context-free)
```
Expr.NumExt     = INT
Expr.CellRefExt = CREF
Expr.GroupExt   = [([Expr])]
Expr.UnOpExt    = [[UnOp] [Expr]]
Expr.BinOpExt   = [[Expr] [BinOp] [Expr]] 
Expr.FunCallExt = [[ID]([[Expr] [,[Expr]]*]?)]

UnOp.Minus      = [-]

BinOp.Plus      = [+]
BinOp.Minus     = [-]
BinOp.Mult      = [*]

INT             = [0-9]+
CREF            = [a-zA-Z]{1, 2}
ID              = //any srting not containing special characters/numbers, that is not a CREF 
```

### Supported functions
- `engineersPi()` takes 0 arguments and returns `IntegerValue(3)`
- `abs()` takes 1 argument of type `IntegerValue(n)` and returns `IntegerValue(n')`, where n' = n if n > 0, otherwise n' = -n
- `pow()` takes 2 argument of type `IntegerValue()` and returns `IntegerValue(a^b)`, where a is the value of the first argument and b is the value of the second argument

### Misc. parsing and interpreting rules
- There is no operator precedence, `a+b*c-d` will be interpreted as `a+(b*(c-d))`
- Reference to an uninitialized memory cell will result in an `InterprerException`
- The expression language supports only Integers
- To most extent, the language is whitespace-agnostic `(12+31-engineersPi()*pow(2,B1))` is equivalent to `  ( 12 +  31 -  engineersPi() * pow( 2 , B1 ) ) `
- Tokenizer recognises rows up to `ZZ` in cell reference.

### Some expressions to try out (make sure cells are initialized)
- `pow(-2, A1 - 3) * (42 + B2)`
- `abs(------------2)`
- `engineersPi() * (Z5)--1`


## Running the program
This code was written with Java version 22.
1. Navigate to the `src` directory.
2. Compile Main: `javac Main.java`
3. Run Main: `java Main`

### The spreadsheet behaviour
- All cells from `A1` to `CV100` are initialized with `UndefinedValue()`
- All formulas are evaluated once.
  - If you reference a cell in a formula, the value will not change after the referenced cell changes
- Any exceptions will trigger a popup and make the invalid cell revert ot its previous state.


## Test coverage
| package | Line coverage | Branch coverage |
|---------|---------------|-----------------|
| myLang  | 88%           | 79%             |
| util    | 97%           | 95%             |
