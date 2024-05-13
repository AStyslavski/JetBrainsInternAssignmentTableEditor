package myLang;

import myLang.abstractSyntax.*;
import myLang.exception.ParserException;
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
        if (index < input.size()) {
            throw new ParserException("Leftover " + input.subList(index, input.size()));
        }
    }

    public AbstractSyntax getOutput() {
        return this.output;
    }

    private AbstractSyntax parse(int depthLeft) {
        if(input == null || input.isEmpty()) {
            return new UndefinedExt();
        }
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
                    throw new ParserException("Missing closing bracket/unexpected token " + token0, (index - 1));
                }
                yield returnValue;
            }
            case IdT idT -> {
                checkIndexNotOutOfRange();
                if (!input.get(index++).isType(TokenType.OPEN_PAREN)) {
                    throw new ParserException("Function call not immediately followed by open paren", (index - 1));
                }
                List<AbstractSyntax> body = parseList(depthLeft - 1);
                checkIndexNotOutOfRange();
                if (!(input.get(index++).isType(TokenType.CLOSE_PAREN))) {
                    throw new ParserException("Function call param param list not closed with paren (or missing comma)", (index - 1));
                }
                yield new FunAppExt(idT.getValue(), body);
            }
            case NumT numT -> new NumExt(numT.getValue());
            case CellRefT cellRefT -> new CellRefExt(cellRefT.getColumn(), cellRefT.getRow());
            default -> throw new ParserException("Unexpected token " + token0, (index - 1));
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

    private static void checkDepth(int depth_left) {
        if (depth_left < 0) {
            throw new ParserException("Parser reached maximum depth");
        }
    }

    private void checkIndexNotOutOfRange() {
        if (index >= input.size()) {
            throw new ParserException("Parser reached unexpected end of expression");
        }
    }
}
