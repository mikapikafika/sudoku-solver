package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardRepositoryTest {

    @Test
    void cloneBoardTest() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoardRepository sudokuBoardRepository = new SudokuBoardRepository(sudokuBoard);
        SudokuBoard secondSudokuBoard = sudokuBoardRepository.createInstance();

        assertTrue(secondSudokuBoard.equals(sudokuBoard) && sudokuBoard.equals(secondSudokuBoard));

        sudokuBoard.solveGame();

        assertFalse(secondSudokuBoard.equals(sudokuBoard) && sudokuBoard.equals(secondSudokuBoard));

    }
}