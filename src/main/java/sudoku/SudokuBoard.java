package sudoku;

public class SudokuBoard {
    
    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver solver;
    private final SudokuRow[] rows = new SudokuRow[9];
    private final SudokuColumn[] cols = new SudokuColumn[9];
    private final SudokuBox[] boxes = new SudokuBox[9];

    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;

        for (int i = 0; i < 9; i++) {
            rows[i] = new SudokuRow();
            cols[i] = new SudokuColumn();
            boxes[i] = new SudokuBox();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField(i, j);
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

        rows[x].setFieldInElement(y, board[x][y].getField());
        cols[y].setFieldInElement(x, board[x][y].getField());
        boxes[getBoxCoords(x,y)].setFieldInElement(getLocationInBox(x,y),
                board[x][y].getField());

        board[x][y].setFieldValue(value);

        return true;
    }

    /**
     * Check's if the board is correctly solved.
     * @return true if the board is correct
     */
    private boolean checkBoard() {

        for (int i = 0; i < 9; i++) {
            if (!rows[i].verify() || !cols[i].verify() || !boxes[i].verify()) {
                return false;
            }
        }

        return true;
    }

    public SudokuRow getRow(int y) {
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            row.setFieldInElement(i, rows[y].getFields()[i]);
        }

        return row;
    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn col = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            col.setFieldInElement(i, cols[x].getFields()[i]);
        }

        return col;
    }

    public SudokuBox getBox(int x, int y) {
        SudokuBox box = new SudokuBox();
        for (int i = 0; i < 9; i++) {
            box.setFieldInElement(boxes[getBoxCoords(x,y)].getFields()[i].getBoxLoc(),
                    boxes[getBoxCoords(x,y)].getFields()[i]);
        }

        return box;
    }

    /**
     * Solves the sudoku with the chosen algorithm.
     */
    public void solveGame() {
        solver.solve(this);
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

    private int getLocationInBox(int x, int y) {
        int rowStart = x - x % 3;
        int colStart = y - y % 3;

        int rowLoc = x - rowStart;
        int colLoc = y - colStart;

        return 3 * colLoc + rowLoc;
    }

    private int getBoxCoords(int x, int y) {
        int rowStart = x - x % 3;
        int colStart = y - y % 3;

        return (3 * rowStart + colStart) / 3;
    }

}
