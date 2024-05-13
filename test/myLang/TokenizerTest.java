package myLang;

import myLang.token.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void tokenize() {
        assertEquals(
                List.of(new IdT("hello"), new OpenParenT(), new CellRefT("A12"), new PlusT(), new OpenParenT(),
                        new NumT(2), new MinusT(), new IdT("abs"), new OpenParenT(), new MinusT(),
                        new NumT(21), new CloseParenT(), new CloseParenT(), new CloseParenT()).toString(),
                Tokenizer.tokenize("hello(A12 + (2 - abs(-21)))").toString());
    }
}