package myLang.token;

public class MultT extends Token {
    @Override
    public String toString() {
        return "MultT";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.MULT;
    }
}
