package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardCloneTest {

    @Test
    void cloneBoardTest() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoardClone sudokuBoardClone = new SudokuBoardClone();
        SudokuBoard secondSudokuBoard = sudokuBoardClone.cloneBoard(sudokuBoard);

        assertTrue(secondSudokuBoard.equals(sudokuBoard) && sudokuBoard.equals(secondSudokuBoard));

        sudokuBoard.solveGame();

        assertFalse(secondSudokuBoard.equals(sudokuBoard) && sudokuBoard.equals(secondSudokuBoard));

    }
}