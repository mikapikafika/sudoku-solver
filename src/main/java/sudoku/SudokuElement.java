package sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class SudokuElement {

    private List<SudokuField> fields = Arrays.asList(new SudokuField[9]);

    public SudokuElement() {
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
            setField.add(fields.get(i).getFieldValue());
        }

        return setField.size() == 9;
    }

    //Additional:

    public void setFieldInElement(int loc, SudokuField field) {
        this.fields.set(loc,field);
    }
}

