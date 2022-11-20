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

    // SudokuColumn needed for further tests:
    SudokuColumn column = new SudokuColumn();
    SudokuField f1 = new SudokuField();
    SudokuField f2 = new SudokuField();
    SudokuField f3 = new SudokuField();
    SudokuField f4 = new SudokuField();
    SudokuField f5 = new SudokuField();
    SudokuField f6 = new SudokuField();
    SudokuField f7 = new SudokuField();
    SudokuField f8 = new SudokuField();
    SudokuField f9 = new SudokuField();

    @BeforeEach
    void init() {
        f1.setFieldValue(1);
        f2.setFieldValue(1);
        f3.setFieldValue(1);
        f4.setFieldValue(1);
        f5.setFieldValue(1);
        f6.setFieldValue(1);
        f7.setFieldValue(1);
        f8.setFieldValue(1);
        f9.setFieldValue(1);
        column.setFieldInElement(0,f1);
        column.setFieldInElement(1,f2);
        column.setFieldInElement(2,f3);
        column.setFieldInElement(3,f4);
        column.setFieldInElement(4,f5);
        column.setFieldInElement(5,f6);
        column.setFieldInElement(6,f7);
        column.setFieldInElement(7,f8);
        column.setFieldInElement(8,f9);
    }

    // equals tests:

    @Test
    void equalsExactlyTheSameObjectTest() {
//        pls czy to może działać jakoś tak mniej skomplikowanie
//                bez powyższych inicjalizacji
//        SudokuSolver solver = new BacktrackingSudokuSolver();
//        SudokuBoard sudokuBoard = new SudokuBoard(solver);
//
//        assertTrue(sudokuBoard.getColumn(0)
//                .equals(sudokuBoard.getColumn(0)));

        assertTrue(column.equals(column));
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

    @Test
    void equalsFalseHashCodeFalseTest() {
        // no tak ewidentnie nic nie daje
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        assertNotEquals(sudokuBoard.getColumn(0).hashCode(),
                sudokuBoard.getColumn(1).hashCode());
        assertFalse(sudokuBoard.getColumn(0)
                .equals(sudokuBoard.getColumn(1)));
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

    //toString tests:

    @Test
    void toStringTest() {
        String testString = column.toString();

        assertEquals(testString, column.toString());
    }



}
