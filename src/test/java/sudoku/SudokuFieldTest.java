package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    // equals tests:

    @Test
    void testEqualsSameObj() {
        SudokuField field = new SudokuField();
        assertTrue(field.equals(field));
    }

    @Test
    void testEqualsNull() {
        SudokuField field = new SudokuField();
        assertFalse(field.equals(null));
    }

    @Test
    void equalsNotTheSameObjectsTest() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(1);
        field2.setFieldValue(2);

        assertFalse(field1.equals(field2));
    }

    @Test
    void equalsDifferentObjectsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        SudokuField field1 = new SudokuField();

        assertFalse(field1.equals(sudokuBoard));
    }
}
