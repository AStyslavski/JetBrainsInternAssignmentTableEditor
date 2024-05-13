package myLang.abstractSyntax;

import myLang.opTypes.BinOpType;

public class BinOpExt extends AbstractSyntax {
    private final BinOpType operator;
    private final AbstractSyntax leftOperand;
    private final AbstractSyntax rightOperand;

    public BinOpExt(BinOpType operator, AbstractSyntax leftOperand, AbstractSyntax rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public BinOpType getOperator() {
        return operator;
    }

    public AbstractSyntax getLeftOperand() {
        return leftOperand;
    }

    public AbstractSyntax getRightOperand() {
        return rightOperand;
    }

    @Override
    public String toString() {
        return "BinOpExt(" + operator + ", " + leftOperand + ", " + rightOperand + ")";
    }
}
