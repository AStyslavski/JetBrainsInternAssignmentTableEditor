package myLang.token;

import myLang.abstractSyntax.AbstractSyntax;
import myLang.opTypes.BinOpType;
import myLang.opTypes.UnOpType;
import util.CellUtil;

public abstract class Token {
    public static Token fromString(String str) {
        switch (str) {
            case "+":
                return new PlusT();
            case "-":
                return new MinusT();
            case "*":
                return new MultT();
            case "(":
                return new OpenParenT();
            case ")":
                return new CloseParenT();
            case ",":
                return new CommaT();
            default:
                try {
                    return new NumT(Integer.parseInt(str));
                } catch (NumberFormatException _) {
                    if (CellUtil.isCellReference(str)) {
                        return new CellRefT(str);
                    }
                    return new IdT(str);
                }
        }
    }


    public TokenType tokenType() {
        return TokenType.UNDEFINED;
    }

    public boolean isType(TokenType type) {
        return tokenType() == type;
    }

    public boolean isBinOp() {
        return isBinOp(this);
    }

    public boolean isUnOp() {
        return isUnOp(this);
    }

    public UnOpType toUnOp() {
        return AbstractSyntax.unOpFromToken(this);
    }

    public BinOpType toBinOp() {
        return AbstractSyntax.binOpFromToken(this);
    }

    public static boolean isBinOp(Token t) {
        return t instanceof PlusT || t instanceof MinusT || t instanceof MultT;
    }

    public static boolean isUnOp(Token t) {
        return t instanceof MinusT;
    }
}
