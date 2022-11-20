package sudoku;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard {
    
    private final List<List<SudokuField>> board;
    private final SudokuSolver solver;

    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;

        board = new ArrayList<>(9);
        for (int i = 0; i < 9; ++i) {
            board.add(new ArrayList<>(9));
            for (int j = 0; j < 9; j++) {
                board.get(i).add(j, new SudokuField());
            }
        }
}

    // Methods:

    public int get(int x, int y) {
        return board.get(x).get(y).getFieldValue();
    }

    public boolean set(int x, int y, int value) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return false;
        }
        board.get(x).get(y).setFieldValue(value);

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
            field.setFieldValue(board.get(i).get(y).getFieldValue());
            row.setFieldInElement(i,field);
        }
        return row;
    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn col = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            field.setFieldValue(board.get(x).get(i).getFieldValue());
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
                field.setFieldValue(board.get(i).get(j).getFieldValue());
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

    public int[][] getBoard() {
        int[][] copiedBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copiedBoard[i][j] = board.get(i).get(j).getFieldValue();
            }
        }
        return copiedBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard that)) {
            return false;
        }

        return new EqualsBuilder()
                .append(getBoard(), that.getBoard())
                .append(solver, that.solver)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getBoard())
                .append(solver)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", board)
                .append("solver", solver)
                .toString();
    }
}
