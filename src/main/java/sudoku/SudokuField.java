package sudoku;

public class SudokuField {
    private int value;

    public SudokuField() {
        this.value = 0;
    }

    // Methods:

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int valueToSet) {
        value = valueToSet;
    }

}
