package sudoku;

public class BacktrackingSudokuSolver implements SudokuSolver {
    @Override
    public void solve(SudokuBoard board) {
        fillSquares(board);
        fillEmptyCells(board);
    }

    /**
     * Fills three 3x3 grids diagonally (from upper left to lower right) with random numbers.
     * Creates a "base" to solve the rest of the board.
     */
    private void fillSquares(SudokuBoard board) {
        int randomNumber;
        for (int z = 0; z < 9; z += 3) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int row = i + z;
                    int col = j + z;
                    do {
                        randomNumber = (int)(Math.random() * 9 + 1);
                    } while (!checkAssignment(board, randomNumber, row, col));
                    board.set(row,col,randomNumber);
                }
            }
        }
    }

    /**
     * Fills the remaining empty cells recursively, one by one.
     * Uses backtracking if a wrong number is assigned to a cell.
     * @return false if no empty cells are found
     */
    private boolean fillEmptyCells(SudokuBoard board) {
        int emptyRow = 0;
        int emptyCol = 0;
        if (checkBoard(board)) {
            return true;
        }

        // Finds an empty cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i,j) == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }

        // Tries assigning a number to a cell.
        // If nothing fits, zeroes the cell and goes back to the previous one.
        for (int i = 1; i <= 9; i++) {
            if (checkAssignment(board, i, emptyRow, emptyCol)) {
                board.set(emptyRow,emptyCol,i);
                if (fillEmptyCells(board)) {
                    return true;
                }
                board.set(emptyRow,emptyCol,0);
            }
        }

        return false;
    }

    /**
     * Checks if the board is already solved (looks for zeroes).
     * @param board sudoku board
     * @return true if solved
     */
    boolean checkBoard(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i,j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if it's OK to assign a number to a cell.
     * @param board sudoku board
     * @param num number to assign
     * @param row row of the board
     * @param col column of the board
     * @return true if the assignment is possible
     */
    private boolean checkAssignment(SudokuBoard board, int num, int row, int col) {
        // Checks in a...
        // ... row
        for (int i = 0; i < 9; i++) {
            if (board.get(row,i) == num) {
                return false;
            }
        }

        // ... column
        for (int i = 0; i < 9; i++) {
            if (board.get(i, col) == num) {
                return false;
            }
        }

        // ... 3x3 grid
        int sqrt = 3;
        int rowStart = row - row % sqrt;
        int colStart = col - col % sqrt;

        for (int i = 0; i < sqrt; i++) {
            for (int j = 0; j < sqrt; j++) {
                if (board.get(i + rowStart,j + colStart) == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
