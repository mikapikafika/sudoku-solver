package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuElementTest {

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
}
