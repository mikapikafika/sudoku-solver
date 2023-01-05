package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.GetValueException;
import sudoku.lang.BundleManager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest implements AutoCloseable {

    @Test
    void FileSudokuBoardDao_WriteAndReadTest() throws DaoException, SQLException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao("file.txt");
        fileSBD.write(sudokuBoard);
        SudokuBoard sudokuBoard1 = fileSBD.read();

        assertTrue(sudokuBoard.equals(sudokuBoard1));

    }

    @Test
    void FileSudokuBoardDao_CloseTest() throws DaoException, SQLException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao("file.txt");
        fileSBD.write(sudokuBoard);

        try {
            fileSBD.close();
        } catch (Exception e) {
            throw new DaoException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DaoException"),e);
        }

    }

    @Test
    void FileSudokuBoardDao_WriteExceptionTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao(".");
        assertThrows(DaoException.class, () -> fileSBD.write(sudokuBoard));
    }
    @Test
    void FileSudokuBoardDao_ReadExceptionTest() {
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao(".");
        assertThrows(DaoException.class, () -> fileSBD.read());
    }

    @Override
    public void close() {

    }
}
