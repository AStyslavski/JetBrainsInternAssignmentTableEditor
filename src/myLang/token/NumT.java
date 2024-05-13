package myLang.token;

public class NumT extends Token {
    private final int value;

    public NumT(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NumT(" + value + ")";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.NUM;
    }
}
