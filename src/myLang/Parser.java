package myLang;

import myLang.abstractSyntax.*;
import myLang.token.*;

import java.util.ArrayList;
import java.util.List;

import myLang.token.TokenType;

public class Parser {
    private static final int MAX_DEPTH = 99;

    private int index = -1;
    private final List<Token> input;
    private final AbstractSyntax output;

    public Parser(List<Token> input) {
        this.input = input;
        this.index = 0;
        this.output = parse(MAX_DEPTH);
    }

    private AbstractSyntax parse(int depthLeft) {
        checkDepth(depthLeft);
        checkIndexNotOutOfRange();
        Token token0 = input.get(index++);

        if (token0.isUnOp()) {
            AbstractSyntax operand = parse(depthLeft - 1);
            return new UnOpExt(token0.toUnOp(), operand);
        }

        AbstractSyntax leftSide = switch (token0) {
            case OpenParenT ignored -> {
                AbstractSyntax returnValue = parse(depthLeft - 1);
                checkIndexNotOutOfRange();
                if (!(input.get(index++).isType(TokenType.CLOSE_PAREN))) {
                    throw new RuntimeException("Missing closing bracket/unexpected token " + token0 + " at index " + (index - 1));
                }
                yield returnValue;
            }
            case IdT idT -> {
                checkIndexNotOutOfRange();
                if (!input.get(index++).isType(TokenType.OPEN_PAREN)) {
                    throw new RuntimeException("Function call not immediately followed by open paren at index " + (index - 1));
                }
                List<AbstractSyntax> body = parseList(depthLeft - 1);
                checkIndexNotOutOfRange();
                if (!(input.get(index++).isType(TokenType.CLOSE_PAREN))) {
                    throw new RuntimeException("Function call param param list not closed with paren (or missing comma) at index " + (index - 1));
                }
                yield new FunAppExt(idT.getValue(), body);
            }
            case NumT numT -> new NumExt(numT.getValue());
            case CellRefT cellRefT -> new CellRefExt(cellRefT.getColumn(), cellRefT.getRow());
            default -> throw new RuntimeException("Unexpected token " + token0 + " at index " + (index - 1));
        };

        if (index < input.size() && input.get(index).isBinOp()) {
            Token operatorToken = input.get(index++);
            AbstractSyntax rightSide = parse(depthLeft - 1);
            return new BinOpExt(operatorToken.toBinOp(), leftSide, rightSide);
        }

        return leftSide;
    }

    private List<AbstractSyntax> parseList(int depthLeft) {
        checkDepth(depthLeft);
        checkIndexNotOutOfRange();
        List<AbstractSyntax> returnList = new ArrayList<>();
        if (!(input.get(index).isType(TokenType.CLOSE_PAREN))) {
            returnList.addLast(parse(depthLeft - 1));
            checkIndexNotOutOfRange();
            while (input.get(index).isType(TokenType.COMMA)) {
                index++;
                returnList.addLast(parse(depthLeft - 1));
                checkIndexNotOutOfRange();
            }
        }
        return returnList;
    }

    public AbstractSyntax getOutput() {
        return this.output;
    }


    private static void checkDepth(int depth_left) {
        if (depth_left < 0) {
            throw new RuntimeException("Parser reached maximum depth");
        }
    }

    private void checkIndexNotOutOfRange() {
        if (index >= input.size()) {
            throw new RuntimeException("Parser reached unexpected end of expression");
        }
    }
}
