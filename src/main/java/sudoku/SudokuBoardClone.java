package sudoku;

public class SudokuBoardClone {
    public SudokuBoard cloneBoard(SudokuBoard board) throws CloneNotSupportedException {
        return (SudokuBoard) board.clone();
    }
}
