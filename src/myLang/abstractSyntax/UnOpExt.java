package myLang.abstractSyntax;

import myLang.opTypes.UnOpType;

public class UnOpExt extends AbstractSyntax {
    private final UnOpType operator;
    private final AbstractSyntax operand;

    public UnOpExt(UnOpType operator, AbstractSyntax operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "UnOpExt(" + operator + ", " + operand + ")";
    }
}
