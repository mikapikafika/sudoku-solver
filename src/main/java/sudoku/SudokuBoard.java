package sudoku;

import java.util.HashSet;

public class SudokuBoard {
    
    private final SudokuField[][] board;
    private final SudokuSolver solver;
    private final SudokuElement[] rows;
    private final SudokuElement[] cols;
    private final SudokuElement[] boxes;

    public SudokuBoard(SudokuSolver solver) {
        this.board = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }

        this.solver = solver;

        this.rows = new SudokuRow[9];
        this.cols = new SudokuColumn[9];
        this.boxes = new SudokuBox[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new SudokuRow();
            cols[i] = new SudokuColumn();
            boxes[i] = new SudokuBox();
        }
    }


    // Methods:

    public int get(int x, int y) {
        return board[x][y].getFieldValue();
    }

    public boolean set(int x, int y, int value) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return false;
        }
        board[x][y].setFieldValue(value);
        return true;
    }

    /**
     * Check's if the board is correctly solved
     * @return true if the board is correct
     */
    private boolean checkBoard() {

        HashSet<Integer> setBox = new HashSet<>();

        // Checking if there is more than one repetition of the number in a box
        for (int i = 0; i <= 6; i += 3) {
            for (int j = 0; j <= 6; j += 3) {
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        setBox.add(this.get(k, l));
                    }
                }
                if (setBox.size() != 9) {
                    return false;
                }
                setBox.clear();
            }
        }

        return true;
    }

    public SudokuElement getRow(int y) {
        return rows[y];
    }

    public SudokuElement getColumn(int x) {
        return cols[x];
    }

    public SudokuElement getBox(int x, int y) {
        int sqrt = 3;
        int rowStart = x - x % sqrt;
        int colStart = y - y % sqrt;
        int boxNumber = (3 * rowStart + colStart) / 3;

        return boxes[boxNumber];
    }

    public int[][] getBoard() {
        int[][] copiedBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copiedBoard[i][j] = board[i][j].getFieldValue();
            }
        }
        return copiedBoard;
    }

    /**
     * Solves the sudoku with the chosen algorithm
     */
    public void solveGame() {
        solver.solve(this);
    }

    /**
     * Prints the board using overrided toString() method
     * @return a string - sudoku board
     */
    @Override
    public String toString() {
        StringBuilder stringBoard = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBoard.append(board[i][j].getFieldValue());
                stringBoard.append("\t");
            }
            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }

}
