package myLang.token;

public class CloseParenT extends Token {
    @Override
    public String toString() {
        return "CloseParenT";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.CLOSE_PAREN;
    }
}
