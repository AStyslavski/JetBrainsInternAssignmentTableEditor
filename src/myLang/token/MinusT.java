package myLang.token;

public class MinusT extends Token {
    @Override
    public String toString() {
        return "MinusT";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.MINUS;
    }
}
