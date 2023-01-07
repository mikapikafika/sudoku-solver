package sudoku.repo;

import sudoku.SudokuBoard;

public class SudokuBoardRepository implements Repository<SudokuBoard> {

    private final SudokuBoard sudokuBoard;

    public SudokuBoardRepository(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    @Override
    public SudokuBoard createInstance() throws CloneNotSupportedException {
        return sudokuBoard.clone();
    }
}