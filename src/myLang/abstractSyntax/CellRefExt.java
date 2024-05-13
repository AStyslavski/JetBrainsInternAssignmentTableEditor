package myLang.abstractSyntax;

import util.CellUtil;

public class CellRefExt extends AbstractSyntax {
    private final int column;
    private final int row;

    public CellRefExt(String input) {
        int[] coords = CellUtil.parseCell(input);
        this.column = coords[0];
        this.row = coords[1];
    }

    public CellRefExt(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "CellRefExt(c:" + column + ", r:" + row + ")";
    }
}
