package sudoku;

public class SudokuBoard {

    // A size variable that allows the user to specify the size of our sudoku board
    private final int size = 9;
    private final int[][] board;

    private final SudokuSolver solver;


    public SudokuBoard(SudokuSolver solver) {
        this.board = new int[size][size];
        this.solver = solver;
    }


    // Getters:
    public int[][] getBoard() {
        int[][] copiedBoard = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, copiedBoard[i], 0, size);
        }
        return copiedBoard;
    }

    public int[] getRow(int row) {
        int[] copiedRow = new int[size];
        System.arraycopy(board[row], 0, copiedRow, 0, size);
        return copiedRow;
    }

    public int[] getColumn(int col) {
        int[] copiedColumn = new int[size];
        for (int i = 0; i < size; i++) {
            copiedColumn[i] = board[i][col];
        }
        return copiedColumn;
    }

    public int get(int x, int y) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return -1;
        }
        return board[x][y];
    }

    public void set(int x, int y, int value) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return;
        }
        board[x][y] = value;
    }


    // Methods:

//sprawdzać czy układ liczb na planszy jest prawidłowy

    public void solveGame() {
        solver.solve(this);
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }


}
