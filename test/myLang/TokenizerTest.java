package myLang;

import myLang.exception.TokenizerException;
import myLang.token.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void testTokenize() {
        assertEquals(
                List.of(new IdT("hello"), new OpenParenT(), new CellRefT("A12"), new PlusT(), new OpenParenT(),
                        new NumT(2), new MinusT(), new IdT("abs"), new OpenParenT(), new MinusT(),
                        new NumT(21), new CloseParenT(), new CloseParenT(), new CloseParenT()).toString(),
                Tokenizer.tokenize("hello(A12 + (2 - abs(-21)))").toString());
        assertEquals(
                Tokenizer.tokenize("hello(A12+(2-abs(-21)))").toString(),
                Tokenizer.tokenize("hello( A12   +  ( 2   -  abs(  -21 ) ) )").toString());
        assertEquals(
                List.of(new IdT("pow"), new OpenParenT(), new MinusT(), new NumT(2), new CommaT(), new CellRefT("A1"),
                        new MinusT(), new NumT(3), new CloseParenT(), new MultT(), new OpenParenT(), new NumT(42),
                        new PlusT(), new CellRefT("B2"), new CloseParenT()).toString(),
                Tokenizer.tokenize("pow(-2, A1 - 3) * (42 + B2)").toString());
        assertEquals(List.of().toString(), Tokenizer.tokenize(" ").toString());
    }

    @Test
    void testTokenizeTrowsOnInvalidInput() {
        for (String input : List.of("1+1=2")) {
            assertThrows(TokenizerException.class,  () -> Tokenizer.tokenize(input));
        }
    }
}
