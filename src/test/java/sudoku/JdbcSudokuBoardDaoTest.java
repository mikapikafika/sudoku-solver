package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.JdbcErrorException;
import sudoku.exceptions.JdbcObjInDatabaseException;
import sudoku.lang.BundleManager;

public class JdbcSudokuBoardDaoTest implements AutoCloseable{

    SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void objectInDatabaseTest() throws Exception {
        try (JdbcDao<SudokuBoard> jdbcDao = FileSudokuBoardFactory.getJdbcDao()) {
            sudokuBoard.solveGame();

            if (!jdbcDao.alreadyExists("Sudoku")) {
                jdbcDao.insert(sudokuBoard, "Sudoku");
            }
            Assertions.assertTrue(jdbcDao.alreadyExists("Sudoku"));
            jdbcDao.delete("Sudoku");
            Assertions.assertFalse(jdbcDao.alreadyExists("Sudoku"));
        } catch (Exception e) {
            throw new JdbcErrorException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcErrorException"),e);
        }
    }

    @Test
    public void objectNotInDatabaseTest() throws Exception {
        try (JdbcDao<SudokuBoard> jdbcDao = FileSudokuBoardFactory.getJdbcDao()) {
            sudokuBoard.solveGame();

            if (jdbcDao.alreadyExists("Sudoku")) {
                jdbcDao.delete("Sudoku");
            }

            if (!jdbcDao.alreadyExists("Sudoku2")) {
                jdbcDao.insert(sudokuBoard, "Sudoku2");
            }

            Assertions.assertThrows(JdbcObjInDatabaseException.class, () -> jdbcDao.get("Sudoku"));
        } catch (Exception e) {
            throw new JdbcObjInDatabaseException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjectDoesntExistException"),e);
        }
    }

    @Test
    public void writeReadObjectTest() throws Exception {
        try (Dao<SudokuBoard> jdbcBoardDao = FileSudokuBoardFactory.getJdbcSudokuBoardDao("Sudoku")) {
            sudokuBoard.solveGame();

            jdbcBoardDao.write(sudokuBoard);
            SudokuBoard jdbcSudokuBoard = jdbcBoardDao.read();
            Assertions.assertEquals(sudokuBoard, jdbcSudokuBoard);
        } catch (Exception e) {
            throw new JdbcErrorException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcErrorException"),e);
        }
    }

    @Test
    public void sameObjectsInDatabaseTest() throws Exception {
        try (JdbcDao<SudokuBoard> jdbcDao = FileSudokuBoardFactory.getJdbcDao()) {
            sudokuBoard.solveGame();

            if (!jdbcDao.alreadyExists("Sudoku")) {
                jdbcDao.insert(sudokuBoard, "Sudoku");
            }

            Assertions.assertThrows(JdbcObjInDatabaseException.class, () -> jdbcDao.insert(sudokuBoard,"Sudoku"));
        } catch (Exception e) {
            throw new JdbcErrorException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcErrorException"),e);
        }
    }

    @Override
    public void close() throws Exception {
    }
}
