package myLang.value;

public class IntegerValue extends Value {
    private final int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IntegerValue(" + value + ")";
    }

    @Override
    public String showVal() {
        return Integer.toString(value);
    }
}
