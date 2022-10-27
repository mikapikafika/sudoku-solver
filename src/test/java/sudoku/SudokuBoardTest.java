package sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {


    // Tests if two sudoku boards are different.
    @Test
    void differentBoardsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoardTest1 = new SudokuBoard(solver);
        int [][] savedBoardTest = sudokuBoardTest1.getBoard();

        sudokuBoardTest1.solveGame();

        assertFalse(Arrays.deepEquals(sudokuBoardTest1.getBoard(), savedBoardTest));
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
    void sudokuRulesTestGetRow() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        HashSet<Integer> setRow = new HashSet<>();


        for (int i = 0; i < 9; i++) {
           for (int j = 0; j < 9; j++) {
               setRow.add(sudokuBoard.getRow(i)[j]);
               assertEquals(9,sudokuBoard.getRow(i).length);
           }
        }

        assertEquals(9,setRow.size());
        setRow.clear();
    }

    // Tests if a value is repeated in a column
    @Test
    void sudokuRulesTestGetColumn() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        HashSet<Integer> setColumn = new HashSet<>();

        for (int i = 0; i < 9 ; i++){
            int [] testingColumn = new int[9];
            for (int j = 0; j < 9; j++){
                testingColumn[j] = sudokuBoard.get(i,j);
            }
            assertArrayEquals(testingColumn, sudokuBoard.getColumn(i));
        }


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setColumn.add(sudokuBoard.getColumn(i)[j]);
                assertEquals(9,sudokuBoard.getColumn(i).length);
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
    void sudokuRulesTestSetCell() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();
        sudokuBoard.set(1,1,0);

        assertEquals(sudokuBoard.get(1,1),0);

        assertFalse(sudokuBoard.set(9,1,9));
        assertFalse(sudokuBoard.set(-1,1,9));

        assertFalse(sudokuBoard.set(1,9,9));
        assertFalse(sudokuBoard.set(1,-1,9));

        assertFalse(sudokuBoard.set(9,9,9));
        assertFalse(sudokuBoard.set(-1,-1,9));

        assertTrue(sudokuBoard.set(1,1,9));
        assertEquals(sudokuBoard.get(1,1),9);
    }

    @Test
    void sudokuRulesTestToString() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();
        String testingToStringSudoku = sudokuBoard.toString();
        StringBuilder expectedToStringSudoku = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                expectedToStringSudoku.append(sudokuBoard.get(i, j));
                expectedToStringSudoku.append("\t");
            }
            expectedToStringSudoku.append("\n");
        }
        assertEquals(expectedToStringSudoku.toString(), testingToStringSudoku);
    }

    @Test
    void sudokuRulesTestCheckIsBoardCorrect() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        assertTrue(sudokuBoard.checkIsBoardCorrect());

        sudokuBoard.set(8,8,0);
    }

    @Test
    void sudokuRulesTestCheckIsBoardInCorrectDueToRepetitionInRow() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();
        sudokuBoard.set(0,0,1);
        sudokuBoard.set(0,3,1);
        sudokuBoard.set(0,6,1);
        assertFalse(sudokuBoard.checkIsBoardCorrect());

    }

    @Test
    void sudokuRulesTestCheckIsBoardInCorrectDueToRepetitionInColumn() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        sudokuBoard.set(0,0,1);
        sudokuBoard.set(3,0,1);
        sudokuBoard.set(6,0,1);
        assertFalse(sudokuBoard.checkIsBoardCorrect());

    }
    @Test
    void sudokuRulesTestCheckIsBoardInCorrectDueToRepetitionInSquare() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        sudokuBoard.set(0,0,1);
        sudokuBoard.set(1,1,1);
        sudokuBoard.set(2,2,1);
        assertFalse(sudokuBoard.checkIsBoardCorrect());
    }

}

