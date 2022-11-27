package sudoku;

public class FileSudokuBoardFactory {

    public Dao<SudokuBoard> getFileDao(String fileName) {

        Dao<SudokuBoard> dao = new FileSudokuBoardDao(fileName);
        return dao;
    }

}
