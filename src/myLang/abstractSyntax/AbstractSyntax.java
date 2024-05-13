package myLang.abstractSyntax;

import myLang.exception.ParserException;
import myLang.token.Token;
import myLang.opTypes.BinOpType;
import myLang.opTypes.UnOpType;

public abstract class AbstractSyntax {

    @Override
    public String toString() {
        return "undefined abstract syntax";
    }

    public static UnOpType unOpFromToken(Token token) {
        return switch (token.tokenType()) {
            case MINUS -> UnOpType.MINUS;
            default -> throw new ParserException(token.tokenType() + " is not an unary operator");
        };
    }

    public static BinOpType binOpFromToken(Token token) {
        return switch (token.tokenType()) {
            case PLUS -> BinOpType.PLUS;
            case MINUS -> BinOpType.MINUS;
            case MULT -> BinOpType.MULT;
            default -> throw new ParserException(token.tokenType() + " is not a binary operator");
        };
    }
}

