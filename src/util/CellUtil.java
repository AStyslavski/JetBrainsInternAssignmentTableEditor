package util;

import myLang.value.UndefinedValue;
import myLang.value.Value;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellUtil {

    public static final int ALPHABET_SIZE = 'Z' - 'A' + 1;
    public static final String CELL_REFERENCE_REGEX = "([A-Z][A-Z]?)([0-9]+)";

    /**
     * Converts a column reference (e.g., A, AB) into a zero-based column index.
     * Single letter ranges from A to Z, double letters from AA to ZZ.
     *
     * @param column the column reference as a string.
     * @return the zero-based column index corresponding to the column reference.
     * @throws IllegalArgumentException if the input string is not a valid column reference (or is too long).
     */
    public static int columnToInt(String column) {
        column = column.toUpperCase(Locale.ROOT);
        if (!column.matches("[A-Z][A-Z]?")) {
            throw new IllegalArgumentException("Sting " + column + " cannot be converted to column");
        }
        int returnValue = column.charAt(0) - 'A';
        if (column.length() > 1) {
            returnValue += 1;
            returnValue *= ALPHABET_SIZE;
            returnValue += column.charAt(1) - 'A';
        }
        return returnValue;
    }

    /**
     * Parses a cell reference (e.g., A1, AB32) into an array containing zero-based column and row indices.
     *
     * @param input the cell reference as a string.
     * @return an array of two integers: the first is the zero-based column index, the second is the zero-based row index.
     * @throws IllegalArgumentException if the input is not in a valid cell reference format or if the row number is less than 1.
     */
    public static int[] parseCell(String input) {
        input = input.toUpperCase(Locale.ROOT);
        if (!isCellReference(input)) {
            throw new IllegalArgumentException("Invalid cell reference format");
        }

        Matcher matcher = Pattern.compile(CELL_REFERENCE_REGEX).matcher(input);
        if (!matcher.find()) {
            throw new RuntimeException("Undefined pattern matcher error when parsing cell");
        }

        int column = columnToInt(matcher.group(1));
        int row = Integer.parseInt(matcher.group(2));
        if (row < 1) {
            throw new IllegalArgumentException("Rows are 1-indexed");
        }

        row--;

        return new int[]{column, row};
    }

    /**
     * Checks if the provided string is a valid cell reference.
     * A valid cell reference consists of one or two letters followed by one or more digits.
     * The letter(s) represent the column while the digits represent the row in a spreadsheet.
     * Valid examples: "A1", "AB32", "Z999".
     * Invalid examples: "A", "1A", "AAA1".
     *
     * @param input the string to be checked for validity as a cell reference.
     * @return true if the input string matches the cell reference format, false otherwise.
     */
    public static boolean isCellReference(String input) {
        input = input.toUpperCase(Locale.ROOT);
        return input.matches(CELL_REFERENCE_REGEX);
    }

    /**
     * DOCS TODO
     * @param column
     * @return
     */
    public static String intToColumnHeader(int column) {
        if (column < 0) {
            throw new IllegalArgumentException("Column number cannot be negative");
        }
        final StringBuilder sb = new StringBuilder();
        do {
            sb.append((char) ('A' + column % ALPHABET_SIZE));
            column /= ALPHABET_SIZE;
        } while (column-- > 0);
        return sb.reverse().toString();
    }

    /**
     * Docs todo
     * @param columns
     * @param rows
     * @return
     */
    public static Value[][] initEmpty(int columns, int rows) {
        Value[][] retVal = new Value[columns][rows];
        for (int i = 0; i < columns; i++) {
            retVal[i] = new Value[rows];
            for (int j = 0; j < rows; j++) {
                retVal[i][j] = new UndefinedValue();
            }
        }
        return retVal;
    }

}
