package myLang.token;

public class IdT extends Token {
    private final String value;

    public IdT(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IdT(\"" + value + "\")";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.ID;
    }
}
