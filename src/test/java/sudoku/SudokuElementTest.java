package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuElementTest {

    @Test
    void sudokuRulesTestGetRow() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            assertTrue(sudokuBoard.getRow(i).verify());
        }
    }

    // Tests if a value is repeated in a column
    @Test
    void sudokuRulesTestGetColumn() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            assertTrue(sudokuBoard.getColumn(i).verify());
        }
    }

    // Tests if a value is repeated in a 3x3 box
    @Test
    void sudokuRulesTestBox() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(sudokuBoard.getBox(i,j).verify());
            }
        }
    }

    // verify tests:

    @Test
    void sudokuElementVerifyIncorrectDueToRepetitionInRowTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();
        sudokuBoard.set(0,0,sudokuBoard.get(3,0));
        sudokuBoard.set(0,3,sudokuBoard.get(3,0));
        sudokuBoard.set(0,6,sudokuBoard.get(3,0));
        assertFalse(sudokuBoard.getRow(0).verify());

    }

    @Test
    void sudokuElementVerifyIncorrectDueToRepetitionInColTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        sudokuBoard.set(0,0,sudokuBoard.get(3,0));
        sudokuBoard.set(3,0,sudokuBoard.get(3,0));
        sudokuBoard.set(6,0,sudokuBoard.get(3,0));
        assertFalse(sudokuBoard.getColumn(0).verify());

    }

    @Test
    void sudokuElementVerifyIncorrectDueToRepetitionInBoxTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        sudokuBoard.set(0,0,sudokuBoard.get(3,0));
        sudokuBoard.set(1,1,sudokuBoard.get(3,0));
        sudokuBoard.set(2,2,sudokuBoard.get(3,0));
        assertFalse(sudokuBoard.getBox(0,0).verify());
    }

    // equals tests:

    @Test
    void testEqualsSameObj() {
        SudokuElement column = new SudokuColumn();
        assertTrue(column.equals(column));
    }

    @Test
    void testEqualsNull() {
        SudokuElement column = new SudokuColumn();
        assertFalse(column.equals(null));
    }

    @Test
    void equalsTheSameObjectsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertTrue(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getColumn(1)));
    }

    @Test
    void equalsNotTheSameObjectsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        assertFalse(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getColumn(1)));

    }

    @Test
    void equalsDifferentObjectsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertFalse(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getRow(0)));
        assertFalse(sudokuBoard.getColumn(0).equals(sudokuBoard));
    }

    // hashCode tests:

    @Test
    void hashCodeTheSameTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getColumn(1).hashCode());
    }

    @Test
    void hashCodeDifferentTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        assertNotEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getColumn(1).hashCode());
    }

    // equals & hashCode tests - integrity:

    @Test
    void equalsTrueHashCodeMustBeTrueTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertTrue(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getColumn(1)));
        assertEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getColumn(1).hashCode());
    }

    @Test
    void equalsFalseHashCodeMayBeTrueTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertFalse(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getRow(0)));
        assertEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getRow(0).hashCode());
    }

    @Test
    void equalsFalseHashCodeMustBeFalseTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        assertFalse(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getColumn(1)));
        assertNotEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getColumn(1).hashCode());
    }

    @Test
    void hashCodeFalseEqualsMustBeFalseTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        assertNotEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getColumn(1).hashCode());
        assertFalse(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getColumn(1)));
    }

    @Test
    void hashCodeTrueEqualsMayBeTrueTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertEquals(sudokuBoard.getColumn(1).hashCode(),
                sudokuBoard.getColumn(2).hashCode());
        assertTrue(sudokuBoard.getColumn(1)
                .equals(sudokuBoard.getColumn(2)));
    }

    @Test
    void hashCodeTrueEqualsMayBeFalseTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getRow(0).hashCode());
        assertFalse(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getRow(0)));
    }

    //toString tests:

    @Test
    void toStringTest() {
        SudokuElement column = new SudokuColumn();
        String testString = column.toString();
        assertEquals(testString, column.toString());
    }



}
