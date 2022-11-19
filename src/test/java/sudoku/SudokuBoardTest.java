package sudoku;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // Methods tests:

    @Test
    void getTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        assertEquals(sudokuBoard.getBoard()[1][1], sudokuBoard.get(1,1));
    }

    @Test
    void setTest() {
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
    void checkBoardTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        try {
            Method privateMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
            privateMethod.setAccessible(true);
            assertTrue((Boolean)privateMethod.invoke(sudokuBoard));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkBoardIncorrectDueToRepetitionInRowTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        try {
            Method privateMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
            privateMethod.setAccessible(true);

            assertTrue((Boolean)privateMethod.invoke(sudokuBoard));

            sudokuBoard.set(0,0,sudokuBoard.get(0,0));
            sudokuBoard.set(0,6,sudokuBoard.get(0,0));

            assertFalse((Boolean)privateMethod.invoke(sudokuBoard));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkBoardIncorrectDueToRepetitionInColTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        try {
            Method privateMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
            privateMethod.setAccessible(true);

            assertTrue((Boolean)privateMethod.invoke(sudokuBoard));

            sudokuBoard.set(0,0,sudokuBoard.get(0,0));
            sudokuBoard.set(6,0,sudokuBoard.get(0,0));

            assertFalse((Boolean)privateMethod.invoke(sudokuBoard));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkBoardIncorrectDueToRepetitionInBoxTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        try {
            Method privateMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
            privateMethod.setAccessible(true);

            assertTrue((Boolean)privateMethod.invoke(sudokuBoard));

            sudokuBoard.set(1,1,sudokuBoard.get(0,3));
            sudokuBoard.set(2,2,sudokuBoard.get(0,3));

            assertFalse((Boolean)privateMethod.invoke(sudokuBoard));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkBoardIncorrectDueToRepetitionInColBoxTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        try {
            Method privateMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
            privateMethod.setAccessible(true);

            assertTrue((Boolean)privateMethod.invoke(sudokuBoard));

            sudokuBoard.set(6,0,sudokuBoard.get(0,3));
            sudokuBoard.set(1,1,sudokuBoard.get(0,3));
            sudokuBoard.set(2,2,sudokuBoard.get(0,3));

            assertFalse((Boolean)privateMethod.invoke(sudokuBoard));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkBoardIncorrectDueToRepetitionInRowBoxTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        try {
            Method privateMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
            privateMethod.setAccessible(true);

            assertTrue((Boolean)privateMethod.invoke(sudokuBoard));

            sudokuBoard.set(1,1,sudokuBoard.get(0,3));
            sudokuBoard.set(2,2,sudokuBoard.get(0,3));
            sudokuBoard.set(0,6,sudokuBoard.get(0,3));

            assertFalse((Boolean)privateMethod.invoke(sudokuBoard));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void checkBoardIncorrectDueToRepetitionInRowColTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        sudokuBoard.solveGame();

        try {
            Method privateMethod = SudokuBoard.class.getDeclaredMethod("checkBoard");
            privateMethod.setAccessible(true);

            assertTrue((Boolean)privateMethod.invoke(sudokuBoard));

            sudokuBoard.set(0,0,sudokuBoard.get(0,3));
            sudokuBoard.set(0,6,sudokuBoard.get(0,3));
            sudokuBoard.set(6,0,sudokuBoard.get(0,3));

            assertFalse((Boolean)privateMethod.invoke(sudokuBoard));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Additional methods tests:

    @Test
    void toStringTest() {
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
}

