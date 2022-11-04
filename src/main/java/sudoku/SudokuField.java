package sudoku;

public class SudokuField {
    private int value;
    private int rowLoc;
    private int colLoc;
    private int boxLoc;

    public SudokuField(int rowLoc, int colLoc) {
        this.value = 0;
        this.rowLoc = rowLoc;
        this.colLoc = colLoc;
        this.boxLoc = getLocationInBox(rowLoc, colLoc);
    }

    public int getRowLoc() {
        return rowLoc;
    }

    public int getColLoc() {
        return colLoc;
    }

    public int getBoxLoc() {
        return boxLoc;
    }

    public SudokuField getField() {
        return this;
    }

    // Methods:

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int valueToSet) {
        value = valueToSet;
    }

    //Additional:
    //powtarza się
    private int getLocationInBox(int x, int y) {
        int rowStart = x - x % 3;
        int colStart = y - y % 3;

        int rowLoc = x - rowStart;
        int colLoc = y - colStart;

        return 3 * colLoc + rowLoc;
    }
}
