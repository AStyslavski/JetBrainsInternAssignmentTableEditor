package myLang.value;

public class UndefinedValue extends Value {
    @Override
    public String toString() {
        return "UndefinedValue";
    }

    @Override
    public String showVal() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UndefinedValue;
    }

}
