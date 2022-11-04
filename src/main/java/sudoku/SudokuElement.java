package sudoku;

import java.util.HashSet;

public abstract class SudokuElement {

    private final SudokuField[] field;
    public SudokuElement() {
        this.field = new SudokuField[9];
    }

    // Methods:
    /**
     * Verifies if the sudoku element is correct
     *
     * @return true if there's no repetitions - the HashSet's size is 9
     */
    public boolean verify() {
        HashSet<Integer> setField = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            setField.add(field[i].getFieldValue());
        }

        return setField.size() == 9;
    }
}

