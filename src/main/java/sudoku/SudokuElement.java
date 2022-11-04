package sudoku;

import java.util.HashSet;

public abstract class SudokuElement {

    private SudokuField[] fields;

    public SudokuElement() {
        this.fields = new SudokuField[9];
    }

    public SudokuField[] getFields() {
        SudokuField[] newFields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            newFields[i] = new SudokuField(fields[i].getRowLoc(), fields[i].getColLoc());
            newFields[i].setFieldValue(fields[i].getFieldValue());
        }
        return newFields;
    }

    // Methods:

    /**
     * Verifies if the sudoku element is correct.
     *
     * @return true if there's no repetitions - the HashSet's size is 9
     */
    public boolean verify() {
        HashSet<Integer> setField = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            setField.add(fields[i].getFieldValue());
        }

        return setField.size() == 9;
    }

    //Additional:

    public void setFieldInElement(int loc, SudokuField field) {
        this.fields[loc] = field;
    }
}

