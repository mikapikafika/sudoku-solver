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
        SudokuBoard sudokuBoardTest1 = new SudokuBoard();
        sudokuBoardTest1.fillBoard();

        assertTrue(sudokuBoardTest1.checkBoard(sudokuBoardTest1.getBoard()));

        int [][]savedArray = sudokuBoardTest1.getBoard();
        sudokuBoardTest1.fillBoard();

        assertTrue(sudokuBoardTest1.checkBoard(sudokuBoardTest1.getBoard()));
        assertFalse(Arrays.deepEquals(sudokuBoardTest1.getBoard(), savedArray));
    }

    // Tests to see if the sudoku board solution is valid:

    // Tests if the numbers are in range.
    @Test
    void sudokuRulesTestRange() {
        SudokuBoard sudokuBoardTest = new SudokuBoard();
        sudokuBoardTest.fillBoard();

        assertTrue(sudokuBoardTest.checkBoard(sudokuBoardTest.getBoard()));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertFalse(sudokuBoardTest.getBoard()[i][j] <= 0 && sudokuBoardTest.getBoard()[i][j] > 9);
            }
        }
    }

    // Tests if a value is repeated in a row
    @Test
    void sudokuRulesTestRow() {
        SudokuBoard sudokuBoardTest = new SudokuBoard();
        sudokuBoardTest.fillBoard();

        assertTrue(sudokuBoardTest.checkBoard(sudokuBoardTest.getBoard()));

        HashSet<Integer> setRow = new HashSet<>();

        for (int i = 0; i < 9; i++) {
           for (int j = 0; j < 9; j++) {
               setRow.add(sudokuBoardTest.getRow(i)[j]);
           }
        }

        assertEquals(9,setRow.size());
        setRow.clear();
    }

    // Tests if a value is repeated in a column
    @Test
    void sudokuRulesTestColumn() {
        SudokuBoard sudokuBoardTest = new SudokuBoard();
        sudokuBoardTest.fillBoard();

        assertTrue(sudokuBoardTest.checkBoard(sudokuBoardTest.getBoard()));

        HashSet<Integer> setColumn = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setColumn.add(sudokuBoardTest.getColumn(i)[j]);
            }
        }

        assertEquals(9,setColumn.size());
        setColumn.clear();
    }

    // Tests if a value is repeated in a 3x3 grid
    @Test
    void sudokuRulesTestGrid() {
        SudokuBoard sudokuBoardTest = new SudokuBoard();
        sudokuBoardTest.fillBoard();

        assertTrue(sudokuBoardTest.checkBoard(sudokuBoardTest.getBoard()));

        HashSet<Integer> setGrid = new HashSet<>();

        for (int i = 0; i <= 6; i += 3) {
            for (int j = 0; j <= 6; j += 3) {
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        setGrid.add(sudokuBoardTest.get(k, l));
                    }
                }
                assertEquals(9,setGrid.size());
                setGrid.clear();
            }
        }
    }


}