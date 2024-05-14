import myLang.value.UndefinedValue;
import myLang.value.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.CellUtil;

import static org.junit.jupiter.api.Assertions.*;

class CellUtilTest {

    @Test
    @DisplayName("Test columnToInt with single letter (incl. lowercase)")
    void testColumnToIntSingleLetter() {
        assertEquals(0, CellUtil.columnToInt("A"));
        assertEquals(8, CellUtil.columnToInt("I"));
        assertEquals(8, CellUtil.columnToInt("i"));
        assertEquals(25, CellUtil.columnToInt("Z"));
    }

    @Test
    @DisplayName("Test columnToInt with double letters (incl. lowercase)")
    void testColumnToIntDoubleLetters() {
        assertEquals(26, CellUtil.columnToInt("AA"));
        assertEquals(51, CellUtil.columnToInt("AZ"));
        assertEquals(51, CellUtil.columnToInt("az"));
        assertEquals(701, CellUtil.columnToInt("ZZ"));
    }

    @Test
    @DisplayName("Test columnToInt with invalid inputs (numeric, too long, empty, special char)")
    void testColumnToIntInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> CellUtil.columnToInt("1"));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.columnToInt("AAA"));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.columnToInt(""));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.columnToInt("Ł"));
    }

    @Test
    @DisplayName("Test parseCell with valid inputs")
    void testParseCellValidInputs() {
        assertArrayEquals(new int[]{0, 0}, CellUtil.parseCell("A1"));
        assertArrayEquals(new int[]{27, 99}, CellUtil.parseCell("AB100"));
    }

    @Test
    @DisplayName("Test parseCell with invalid inputs")
    void testParseCellInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> CellUtil.parseCell("A0"));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.parseCell("1A"));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.parseCell("11"));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.parseCell("ABC1"));
    }

    @Test
    @DisplayName("Test isCellReference with valid cell references")
    void testIsCellReferenceValid() {
        assertTrue(CellUtil.isCellReference("A1"));
        assertTrue(CellUtil.isCellReference("Z9"));
        assertTrue(CellUtil.isCellReference("AA10"));
        assertTrue(CellUtil.isCellReference("ZZ100"));
        assertTrue(CellUtil.isCellReference("B2"));
        assertTrue(CellUtil.isCellReference("a1"));
        assertTrue(CellUtil.isCellReference("Aa1"));
        assertTrue(CellUtil.isCellReference("aA1"));
        assertTrue(CellUtil.isCellReference("aa1"));
    }

    @Test
    @DisplayName("Test isCellReference with invalid cell references")
    void testIsCellReferenceInvalid() {
        assertFalse(CellUtil.isCellReference("A"));
        assertFalse(CellUtil.isCellReference("1"));
        assertFalse(CellUtil.isCellReference("1A"));
        assertFalse(CellUtil.isCellReference("AA"));
        assertFalse(CellUtil.isCellReference("A1B"));
        assertFalse(CellUtil.isCellReference("A-1"));
        assertFalse(CellUtil.isCellReference("AA1234Z"));
    }

    @Test
    @DisplayName("Test intToColumnHeader with negative input")
    void testIntToColumnHeaderNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> CellUtil.intToColumnHeader(-1));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.intToColumnHeader(-33));
    }

    @Test
    @DisplayName("Test intToColumnHeader with valid input")
    void testIntToColumnHeaderValidInput() {
        assertEquals("A", CellUtil.intToColumnHeader(0));
        assertEquals("B", CellUtil.intToColumnHeader(1));
        assertEquals("D", CellUtil.intToColumnHeader(3));
        assertEquals("Z", CellUtil.intToColumnHeader(25));
        assertEquals("AB", CellUtil.intToColumnHeader(27));
        assertEquals("CV", CellUtil.intToColumnHeader(99));
        assertEquals("NTP", CellUtil.intToColumnHeader(9999));
    }

    @Test
    @DisplayName("Test initEmpty with valid input")
    void testInitEmpty() {
        Value[][] cells = CellUtil.initEmpty(100, 100);
        assertEquals(100, cells.length);
        assertEquals(100, cells[1].length);
        assertEquals(new UndefinedValue(), cells[99][99]);
        assertEquals(new UndefinedValue(), cells[0][0]);
        assertEquals(new UndefinedValue(), cells[0][99]);
    }

    @Test
    @DisplayName("Test initEmpty with invalid input")
    void testInitEmptyInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> CellUtil.initEmpty(1, 0));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.initEmpty(0, 1));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.initEmpty(1, -1));
        assertThrows(IllegalArgumentException.class, () -> CellUtil.initEmpty(-1, 12));
    }
}