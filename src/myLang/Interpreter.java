package myLang;

import myLang.abstractSyntax.*;
import myLang.exception.InterpreterException;
import myLang.value.IntegerValue;
import myLang.value.Value;

import java.util.List;

public class Interpreter {
    public static Value interpret(AbstractSyntax input, Value[][] cells) {
        return switch (input) {
            case NumExt numExt -> new IntegerValue(numExt.getValue());
            case CellRefExt cellRefExt -> cells[cellRefExt.getColumn()][cellRefExt.getRow()]; // todo: implement cell out of range exception!!!!
            case UnOpExt unOpExt -> {
                Value operand = interpret(unOpExt.getOperand(), cells);
                switch (unOpExt.getOperator()) {
                    case MINUS -> {
                        if (operand instanceof IntegerValue integerValue) {
                            yield new IntegerValue(-integerValue.getValue());
                        }
                        throw new InterpreterException("Minus operator expected IntegerValue, got " + operand.getClass().getSimpleName() + " instead");
                    }
                }
                throw new InterpreterException("Unrecognised unary operator: " + unOpExt.getOperator());
            }
            case BinOpExt binOpExt -> {
                Value leftOperand = interpret(binOpExt.getLeftOperand(), cells);
                Value rightOperand = interpret(binOpExt.getRightOperand(), cells);
                switch (binOpExt.getOperator()) {
                    case PLUS -> {
                        if (leftOperand instanceof IntegerValue leftIntegerValue && rightOperand instanceof IntegerValue rightIntegerValue) {
                            yield new IntegerValue(leftIntegerValue.getValue() + rightIntegerValue.getValue());
                        }
                        throw new InterpreterException("Plus operator expected (IntegerValue,IntegerValue),  got (" + leftOperand.getClass().getSimpleName() + "," + rightOperand.getClass().getSimpleName() + ") instead");
                    }
                    case MINUS -> {
                        if (leftOperand instanceof IntegerValue leftIntegerValue && rightOperand instanceof IntegerValue rightIntegerValue) {
                            yield new IntegerValue(leftIntegerValue.getValue() - rightIntegerValue.getValue());
                        }
                        throw new InterpreterException("Minus operator expected (IntegerValue,IntegerValue),  got (" + leftOperand.getClass().getSimpleName() + "," + rightOperand.getClass().getSimpleName() + ") instead");
                    }
                    case MULT -> {
                        if (leftOperand instanceof IntegerValue leftIntegerValue && rightOperand instanceof IntegerValue rightIntegerValue) {
                            yield new IntegerValue(leftIntegerValue.getValue() * rightIntegerValue.getValue());
                        }
                        throw new InterpreterException("Multiplication operator expected (IntegerValue,IntegerValue),  got (" + leftOperand.getClass().getSimpleName() + "," + rightOperand.getClass().getSimpleName() + ") instead");
                    }
                }
                throw new InterpreterException("Unrecognised binary operator: " + binOpExt.getOperator());
            }
            case FunAppExt funAppExt -> {
                List<Value> values = funAppExt.getParams().stream().map(x -> interpret(x, cells)).toList();
                String functionId = funAppExt.getFunctionId();
                switch (functionId) {
                    case "engineersPi" -> {
                        assertListLength(0, values, functionId);
                        yield new IntegerValue(3);
                    }
                    case "abs" -> {
                        assertListLength(1, values, functionId);
                        if (values.getFirst() instanceof IntegerValue integerValue) {
                            yield new IntegerValue(Math.abs(integerValue.getValue()));
                        }
                        throw new InterpreterException(functionId + "() expected IntegerValue, got " + values.getFirst().getClass().getSimpleName() + " instead");
                    }
                    case "pow" -> {
                        assertListLength(2, values, functionId);
                        if (values.getFirst() instanceof IntegerValue iv0 && values.get(1) instanceof IntegerValue iv1) {
                            yield new IntegerValue((int) Math.pow(iv0.getValue(), iv1.getValue()));
                        }
                        throw new InterpreterException(functionId + "() expected (IntegerValue,IntegerValue),  got (" + values.getFirst().getClass().getSimpleName() + "," + values.get(1).getClass().getSimpleName() + ") instead");
                    }
                    default -> throw new InterpreterException("Unrecognised function: " + functionId);
                }
            }
            default -> throw new InterpreterException("Unrecognised abstract syntax");
        };
    }

    private static void assertListLength(int expectedLength, List<Value> values, String functionName) {
        if (values.size() != expectedLength) {
            throw new InterpreterException(functionName + "() expects " + expectedLength + " arguments, " + values.size() + " provided");
        }
    }
}
