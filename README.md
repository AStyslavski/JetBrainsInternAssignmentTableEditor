# _Table editor with formula support_ test assignment
Submission for _Low code Table Data preprocessing_ internship @Jetbrains by Alan Styslavski

## Acknowledgements 
todo

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
ID              = //todo
```

### Supported functions
- `engineersPi()` takes 0 arguments and returns `IntegerValue(3)`
- `abs()` takes 1 argument of type `IntegerValue(n)` and returns `IntegerValue(n')`, where n' = n if n > 0, otherwise n' = -n
- `pow()` takes 2 argument of type `IntegerValue()` and returns `IntegerValue(a^b)`, where a is the value of the first argument and 

### [title todo]
- There is no operator precedence, `a+b*c-d` will be interpreted as `a+(b*(c-d))`
- Reference to an uninitialized memory cell will result in an `InterprerException`
- The expression language supports only Integers
- To most extent, the language is whitespace-agnostic `(12+31-engineersPi()*pow(2,B1))` is equivalent to `  ( 12 +  31 -  engineersPi() * pow( 2 , B1 ) ) `

### Some expressions to try out (make sure cells are initialized)
- `pow(-2, A1 - 3) * (42 + B2)`
- `abs(------------2)`
- `engineersPi() * (Z5)--1`


## Running the program

