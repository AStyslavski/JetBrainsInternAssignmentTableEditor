package myLang;

import myLang.exception.InterpreterException;
import myLang.value.IntegerValue;
import myLang.value.UndefinedValue;
import myLang.value.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.CellUtil;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    private Value[][] cells;

    @BeforeEach
    void beforeEach() {
        cells = CellUtil.initEmpty(100, 100);
    }

    @Test
    void testInterpret() {
        assertEquals(new IntegerValue(21), interpretFromString("20 + 1", cells));
        assertEquals(new IntegerValue(21), interpretFromString("22 - (-1 * -1)", null));
        assertEquals(new UndefinedValue(), interpretFromString("", null));
    }

    @Test
    void testCellAccess() {
        assertThrows(InterpreterException.class, () -> interpretFromString("A1", cells));
        cells[0][0] = new IntegerValue(910);
        assertEquals(new IntegerValue(910), interpretFromString("A1", cells));
        assertThrows(InterpreterException.class, () -> interpretFromString("A12", cells));
        assertThrows(InterpreterException.class, () -> interpretFromString("B1", cells));
    }

    @Test
    void testMisc() {
        assertEquals(new IntegerValue(2), interpretFromString("abs(----------2)", cells));
        assertEquals(new IntegerValue(2), interpretFromString("(((((    abs(-----------2))) ) )   )", cells));
        cells[1][1] = new IntegerValue(-2);
        cells[0][0] = new IntegerValue(4);
        assertEquals(new IntegerValue(-80), interpretFromString("pow(-2, A1 - 3) * (42 + B2)", cells));
        cells[4][25] = new IntegerValue(4);
        assertEquals(new IntegerValue(15), interpretFromString("engineersPi() * (Z5)--1", cells));
    }

    @Test
    void testEmptyExpressionReturnsUndefinedValue() {
        assertEquals(new UndefinedValue(), interpretFromString("", cells));
    }

    @Test
    void testWhitespaceAgnostic() {
        cells[0][1] = new IntegerValue(3);
        assertEquals(interpretFromString("(12+31-engineersPi()*pow(2,B1))", cells), interpretFromString(" ( 12 +  31 -  engineersPi() * pow( 2 , B1 ) )  ", cells));
    }

    private static Value interpretFromString(String input, Value[][] cells) {
        Parser p = new Parser(Tokenizer.tokenize(input));
        return Interpreter.interpret(p.getOutput(), cells);
    }

}