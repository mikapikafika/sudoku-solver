package sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    public SudokuBoardTest() {
    }

    
    // Tests if two sudoku boards are different.
    @Test
    void differentBoardsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoardTest1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoardTest2 = new SudokuBoard(solver);

        sudokuBoardTest1.solveGame();
        sudokuBoardTest2.solveGame();

        assertFalse(Arrays.deepEquals(sudokuBoardTest1.getBoard(), sudokuBoardTest2.getBoard()));
    }

    // Tests to see if the sudoku board solution is valid:

    // Tests if the numbers are in range.
    @Test
    void sudokuRulesTestRange() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertFalse(sudokuBoard.getBoard()[i][j] <= 0 && sudokuBoard.getBoard()[i][j] > 9);
            }
        }
    }

    // Tests if a value is repeated in a row
    @Test
    void sudokuRulesTestRow() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        HashSet<Integer> setRow = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            int [] testRow = sudokuBoard.getRow(i);
           for (int j = 0; j < 9; j++) {
               setRow.add(sudokuBoard.getRow(i)[j]);
               assertEquals(9,sudokuBoard.getRow(i).length);
               assertEquals(sudokuBoard.get(i,j),testRow[j]);
           }
        }

        assertEquals(9,setRow.size());
        setRow.clear();
    }

    // Tests if a value is repeated in a column
    @Test
    void sudokuRulesTestColumn() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        HashSet<Integer> setColumn = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            int [] testColumn = sudokuBoard.getColumn(i);
            for (int j = 0; j < 9; j++) {
                setColumn.add(sudokuBoard.getColumn(i)[j]);
                assertEquals(9,sudokuBoard.getColumn(i).length);
                assertEquals(sudokuBoard.get(i,j),testColumn[j]);
            }
        }

        assertEquals(9,setColumn.size());
        setColumn.clear();
    }

    // Tests if a value is repeated in a 3x3 grid
    @Test
    void sudokuRulesTestGrid() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        HashSet<Integer> setGrid = new HashSet<>();

        for (int i = 0; i <= 6; i += 3) {
            for (int j = 0; j <= 6; j += 3) {
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        setGrid.add(sudokuBoard.get(k, l));
                    }
                }
                assertEquals(9,setGrid.size());
                setGrid.clear();
            }
        }
    }

    @Test
    void sudokuRulesTestGetCell() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        assertEquals(sudokuBoard.getBoard()[1][1], sudokuBoard.get(1,1));


    }
    @Test
    void sudokuRulesTestToString() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

    }

}

