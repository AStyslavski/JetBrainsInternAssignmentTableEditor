package myLang.token;

public class CommaT extends Token {
    @Override
    public String toString() {
        return "CommaT";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.COMMA;
    }
}
