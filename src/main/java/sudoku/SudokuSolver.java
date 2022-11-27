package sudoku;

import java.io.Serializable;

interface SudokuSolver extends Serializable {
    void solve(SudokuBoard board);
}
