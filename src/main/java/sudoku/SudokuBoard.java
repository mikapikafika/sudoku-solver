package sudoku;

public class SudokuBoard {

    // A size variable that allows the user to specify the size of our sudoku board
    private final int size;
    private final int[][] board;

    public SudokuBoard() {
        size = 9;
        board = new int[size][size];
    }


    // Getters:
    public int[][] getBoard() {
        int[][] copiedBoard = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copiedBoard[i][j] = board[i][j];
            }
        }
        return copiedBoard;
    }

    public int[] getRow(int row) {
        int[] copiedRow = new int[size];
        for (int i = 0; i < size; i++) {
                copiedRow[i] = board[row][i];
        }
        return copiedRow;
    }

    public int[] getColumn(int col) {
        int[] copiedColumn = new int[size];
        for (int i = 0; i < size; i++) {
            copiedColumn[i] = board[i][col];
        }
        return copiedColumn;
    }

    public int getCell(int x, int y) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return -1;
        }
        return board[x][y];
    }


    // Methods:

    /**
     * Checks if the board is already solved (looks for zeroes).
     * @param board sudoku board
     * @return true if solved
     */
    boolean checkBoard(int[][] board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Fills the board according to the rules of sudoku.
     */
    public void fillBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
            }
        }
        fillSquares();
        fillEmptyCells();
    }

    /**
     * Fills three 3x3 grids diagonally (from upper left to lower right) with random numbers.
     * Creates a "base" to solve the rest of the board.
     */
    void fillSquares() {
        int randomNumber;
        for (int z = 0; z < size; z += (int)(Math.sqrt(size))) {
            for (int i = 0; i < Math.sqrt(size); i++) {
                for (int j = 0; j < Math.sqrt(size); j++) {
                    int row = i + z;
                    int col = j + z;
                    do {
                        randomNumber = (int)(Math.random() * size + 1);
                    } while (!checkAssignment(board, randomNumber, row, col));
                    board[row][col] = randomNumber;
                }
            }
        }
    }

    /**
     * Fills the remaining empty cells recursively, one by one.
     * Uses backtracking if a wrong number is assigned to a cell.
     * @return false if no empty cells are found
     */
    boolean fillEmptyCells() {
        int emptyRow = 0;
        int emptyCol = 0;
        if (checkBoard(board)) {
            return true;
        }

        // Finds an empty cell
        boolean found = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        // Tries assigning a number to a cell.
        // If nothing fits, zeroes the cell and goes back to the previous one.
        for (int i = 1; i <= size; i++) {
            if (checkAssignment(board, i, emptyRow, emptyCol)) {
                board[emptyRow][emptyCol] = i;
                if (fillEmptyCells()) {
                    return true;
                }
                board[emptyRow][emptyCol] = 0;
            }
        }

        return false;
    }

    /**
     * Checks if it's OK to assign a number to a cell.
     * @param board sudoku board
     * @param num number to assign
     * @param row row of the board
     * @param col column of the board
     * @return true if the assignment is possible
     */
    boolean checkAssignment(int[][] board, int num, int row, int col) {
        // Checks in a...
        // ... row
        for (int i = 0; i < size; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // ... column
        for (int i = 0; i < size; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // ... 3x3 grid
        int sqrt = (int)(Math.sqrt(size));
        int rowStart = row - row % sqrt;
        int colStart = col - col % sqrt;

        for (int i = 0; i < sqrt; i++) {
            for (int j = 0; j < sqrt; j++) {
                if (board[i + rowStart][j + colStart] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
