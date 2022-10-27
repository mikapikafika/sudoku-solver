package sudoku;


import java.util.HashSet;

public class SudokuBoard {
    
    private final SudokuField[][] board;
    private final SudokuSolver solver;


    public SudokuBoard(SudokuSolver solver) {
        this.board = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
        this.solver = solver;
    }


    // Getters:
    public int[][] getBoard() {
        int[][] copiedBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copiedBoard[i][j] = board[i][j].getFieldValue();
            }
        }
        return copiedBoard;
    }

    public int[] getRow(int row) {
        int[] copiedRow = new int[9];
        for (int i = 0; i < 9; i++) {
            copiedRow[i] = board[i][row].getFieldValue();
        }
        return copiedRow;
    }

    public int[] getColumn(int col) {
        int[] copiedColumn = new int[9];
        for (int i = 0; i < 9; i++) {
            copiedColumn[i] = board[col][i].getFieldValue();
        }
        return copiedColumn;
    }

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

    // Methods:

    public void solveGame() {
        solver.solve(this);
    }

    public boolean checkIsBoardCorrect() {

        HashSet<Integer> setSquare = new HashSet<>();

        // Checking if there is more than one repetition of the number in a square
        for (int i = 0; i <= 6; i += 3) {
            for (int j = 0; j <= 6; j += 3) {
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        setSquare.add(this.get(k, l));
                    }
                }
                if (setSquare.size() != 9) {
                    return false;
                }
                setSquare.clear();
            }
        }


        return true;
    }


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
