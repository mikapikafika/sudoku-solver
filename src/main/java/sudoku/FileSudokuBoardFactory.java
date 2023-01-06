package sudoku;

import java.sql.SQLException;
import sudoku.exceptions.DaoException;

public class FileSudokuBoardFactory {

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static JdbcDao<SudokuBoard> getJdbcDao() throws SQLException, DaoException {
        return new JdbcSudokuBoardDao();
    }

    public static Dao<SudokuBoard> getJdbcSudokuBoardDao(String name)
            throws SQLException, DaoException {
        return new JdbcSudokuBoardDao(name);
    }

}
