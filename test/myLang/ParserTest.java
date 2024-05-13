package myLang;

import myLang.abstractSyntax.*;
import myLang.exception.ParserException;
import myLang.opTypes.BinOpType;
import myLang.opTypes.UnOpType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    void TestSimpleParsing() {
        Parser parser = new Parser(Tokenizer.tokenize("pow(-2, A1 - 3) * (42 + B2)"));
        assertEquals(
                new BinOpExt(BinOpType.MULT, new FunAppExt("pow", List.of(
                        new UnOpExt(UnOpType.MINUS, new NumExt(2)),
                        new BinOpExt(BinOpType.MINUS, new CellRefExt(0, 0), new NumExt(3)))),
                        new BinOpExt(BinOpType.PLUS, new NumExt(42), new CellRefExt("B2"))).toString(),
                parser.getOutput().toString()
        );

        parser = new Parser(Tokenizer.tokenize("hello(A12 + (2 - abs(-21)))"));
        assertEquals(
                new FunAppExt("hello", List.of(new BinOpExt(BinOpType.PLUS, new CellRefExt(0, 11),
                        new BinOpExt(BinOpType.MINUS, new NumExt(2), new FunAppExt("abs",
                                List.of(new UnOpExt(UnOpType.MINUS, new NumExt(21)))))))).toString(),
                parser.getOutput().toString()
        );

        parser = new Parser(Tokenizer.tokenize("engineersPi()"));
        assertEquals(new FunAppExt("engineersPi", List.of()).toString(), parser.getOutput().toString());

        parser = new Parser(Tokenizer.tokenize("1+2+3"));
        assertEquals(
                new BinOpExt(BinOpType.PLUS, new NumExt(1), new BinOpExt(BinOpType.PLUS, new NumExt(2), new NumExt(3))).toString(),
                parser.getOutput().toString()
        );
    }

    @Test
    void testParseErrors() {
        for (String input : List.of("engineersPi", "+1", ")", "1+2 is 4", "pow(1 2)", "abs(1, )")) {
            assertThrows(ParserException.class, () -> new Parser(Tokenizer.tokenize(input)));
        }
    }

}