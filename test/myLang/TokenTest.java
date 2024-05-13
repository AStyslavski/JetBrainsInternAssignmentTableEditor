package myLang;

import myLang.token.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testIsBinOpStatic() {
        assertTrue(Token.isBinOp(new PlusT()));
        assertTrue(Token.isBinOp(new MinusT()));
        assertTrue(Token.isBinOp(new MultT()));
        assertFalse(Token.isBinOp(new OpenParenT()));
        assertFalse(Token.isBinOp(new CloseParenT()));
        assertFalse(Token.isBinOp(new CommaT()));
        assertFalse(Token.isBinOp(new NumT(0)));
        assertFalse(Token.isBinOp(new CellRefT("A12")));
        assertFalse(Token.isBinOp(new IdT("abs")));
    }

    @Test
    void testIsBinOp() {
        assertTrue(new PlusT().isBinOp());
        assertTrue(new MinusT().isBinOp());
        assertTrue(new MultT().isBinOp());
        assertFalse(new OpenParenT().isBinOp());
        assertFalse(new CloseParenT().isBinOp());
        assertFalse(new CommaT().isBinOp());
        assertFalse(new NumT(0).isBinOp());
        assertFalse(new CellRefT("A12").isBinOp());
        assertFalse(new IdT("abs").isBinOp());
    }

    @Test
    void testIsUnOpStatic() {
        assertFalse(Token.isUnOp(new PlusT()));
        assertTrue(Token.isUnOp(new MinusT()));
        assertFalse(Token.isUnOp(new MultT()));
        assertFalse(Token.isUnOp(new OpenParenT()));
        assertFalse(Token.isUnOp(new CloseParenT()));
        assertFalse(Token.isUnOp(new CommaT()));
        assertFalse(Token.isUnOp(new NumT(0)));
        assertFalse(Token.isUnOp(new CellRefT("A12")));
        assertFalse(Token.isUnOp(new IdT("abs")));
    }

    @Test
    void testIsUnOp() {
        assertFalse(new PlusT().isUnOp());
        assertTrue(new MinusT().isUnOp());
        assertFalse(new MultT().isUnOp());
        assertFalse(new OpenParenT().isUnOp());
        assertFalse(new CloseParenT().isUnOp());
        assertFalse(new CommaT().isUnOp());
        assertFalse(new NumT(0).isUnOp());
        assertFalse(new CellRefT("A12").isUnOp());
        assertFalse(new IdT("abs").isUnOp());
    }
}