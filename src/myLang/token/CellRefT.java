package myLang.token;

import util.CellUtil;

public class CellRefT extends Token {
    private final int column;
    private final int row;

    public CellRefT(String input) {
        int[] coords = CellUtil.parseCell(input);
        this.column = coords[0];
        this.row = coords[1];
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "CellRefT(c:" + column + ", r:" + row + ")";
    }

    @Override
    public TokenType tokenType() {
        return TokenType.CELL_REF;
    }
}
