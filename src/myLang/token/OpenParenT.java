package myLang.token;

public class OpenParenT extends Token {
    @Override
    public String toString() {
        return "OpenParenT";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.OPEN_PAREN;
    }
}
