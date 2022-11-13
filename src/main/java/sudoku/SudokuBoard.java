package sudoku;

public class SudokuBoard {
    
    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver solver;

    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
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
     * Check's if the board is correctly solved.
     * @return true if the board is correct
     */
    private boolean checkBoard() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                SudokuBox box = getBox(i,j);
                SudokuColumn col = getColumn(i);
                SudokuRow row = getRow(i);

                if (!box.verify() || !row.verify() || !col.verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public SudokuRow getRow(int y) {
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            field.setFieldValue(board[i][y].getFieldValue());
            row.setFieldInElement(i,field);
        }
        return row;
    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn col = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            field.setFieldValue(board[x][i].getFieldValue());
            col.setFieldInElement(i,field);
        }
        return col;
    }

    public SudokuBox getBox(int x, int y) {
        SudokuBox box = new SudokuBox();
        int rowStart = x - x % 3;
        int colStart = y - y % 3;
        int index = 0;
        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                SudokuField field = new SudokuField();
                field.setFieldValue(board[i][j].getFieldValue());
                box.setFieldInElement(index,field);
                index++;
            }
        }
        return box;
    }

    /**
     * Solves the sudoku with the chosen algorithm.
     */
    public void solveGame() {
        solver.solve(this);
        checkBoard();
    }


    //Additional methods:

    /**
     * Prints the board using overrided toString() method.
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

    public int[][] getBoard() {
        int[][] copiedBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copiedBoard[i][j] = board[i][j].getFieldValue();
            }
        }
        return copiedBoard;
    }

}
