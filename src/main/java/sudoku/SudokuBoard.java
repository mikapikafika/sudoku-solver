package sudoku;

public class SudokuBoard {
    
    private final int[][] board;
    private final SudokuSolver solver;


    public SudokuBoard(SudokuSolver solver) {
        this.board = new int[9][9];
        this.solver = solver;
    }


    // Getters:
    public int[][] getBoard() {
        int[][] copiedBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, copiedBoard[i], 0, 9);
        }
        return copiedBoard;
    }

    public int[] getRow(int row) {
        int[] copiedRow = new int[9];
        System.arraycopy(board[row], 0, copiedRow, 0, 9);
        return copiedRow;
    }

    public int[] getColumn(int col) {
        int[] copiedColumn = new int[9];
        System.arraycopy(board[col],0,copiedColumn,0,9);
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

    public void solveGame() {
        solver.solve(this);
    }

    /*
    @Override
    public String toString() {
        StringBuilder stringBoard = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBoard.append(board[i][j]);
                stringBoard.append("\t");
            }
            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }

     */
}
