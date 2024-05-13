package myLang.token;

public class PlusT extends Token {
    @Override
    public String toString() {
        return "PlusT";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.PLUS;
    }
}
