package myLang.abstractSyntax;

public class NumExt extends AbstractSyntax {
    private final int value;

    public NumExt(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "NumExt(" + value + ")";
    }
}
