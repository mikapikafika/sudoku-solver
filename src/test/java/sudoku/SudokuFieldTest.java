package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    SudokuField f1 = new SudokuField();
    SudokuField f2 = new SudokuField();
    SudokuField f3 = new SudokuField();
    SudokuField f4 = new SudokuField();

    @BeforeEach
    void init() {
        f1.setFieldValue(1);
        f2.setFieldValue(1);
        f3.setFieldValue(2);
    }

    // equals tests:

    @Test
    void equalsExactlyTheSameObjectTest() {
        assertTrue(f1.equals(f1));
    }

    @Test
    void equalsTheSameObjectsTest() {
        assertTrue(f1.equals(f2));
    }

    @Test
    void equalsNotTheSameObjectsTest() {
        assertFalse(f1.equals(f3));
    }

    @Test
    void equalsDifferentObjectsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertFalse(f1.equals(sudokuBoard));
    }

    @Test
    void equalsFalseHashCodeFalseTest() {
        //?????????????????????????????
        assertFalse(f1.equals(f3));
        assertNotEquals(f1.hashCode(),f3.hashCode());
    }


}
