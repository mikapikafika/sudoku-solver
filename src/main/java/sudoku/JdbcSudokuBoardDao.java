package sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DbConnectionException;
import sudoku.exceptions.DbCreationException;
import sudoku.lang.BundleManager;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, JdbcDao<SudokuBoard>, AutoCloseable {

    private static Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private String name;
    private Connection connection;

    public JdbcSudokuBoardDao(String name) throws DaoException, SQLException {
        this.name = name;
        create();
    }

    public JdbcSudokuBoardDao() throws DaoException, SQLException {
        create();
    }

    /**
     * Creates a table with sudoku boards.
     * @throws DaoException - exception handling DAO errors
     * @throws SQLException - exception handling database errors
     */

    private void createBoardsTable() throws DaoException, SQLException {
        connect();

        // creates table, auto increments
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE SudokuBoards (" +
                            "board_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY" +
                            "(START WITH 1, INCREMENT BY 1)," +
                            "board_name VARCHAR(40)," +
                            "PRIMARY KEY (board_ID))");
            connection.commit();
        } catch (SQLException e) {
            // if already exists, undo changes
            if (e.getSQLState().equals("X0Y32")) {
                connection.rollback();
                return;
            }

            logger.error(BundleManager
                            .getInstance()
                            .getBundle()
                            .getString("DbCreationException"));
            throw new DbCreationException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DbCreationException"), e);
        }
    }

    // JdbcDao methods:

    @Override
    public void create() throws DaoException, SQLException {
        createBoardsTable();
    }

    @Override
    public int insert(SudokuBoard obj, String name) throws DaoException, SQLException {
        return 0;
    }

    @Override
    public void connect() throws DaoException {
        try {
            connection = DriverManager.getConnection("jdbc:derby:boardsDB;create=true");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            logger.error(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DbConnectionException"));
            throw new DbConnectionException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DbConnectionException"), e);
        }
    }

    @Override
    public void delete(String name) throws DaoException, SQLException {

    }

    @Override
    public SudokuBoard get(String name) throws DaoException, SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAll() throws SQLException {
        return null;
    }

    @Override
    public Boolean isExisting(String name) throws DaoException, SQLException {
        return null;
    }

    // Dao methods:

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public SudokuBoard read() throws DaoException {
        try {
            // to implement
            return null;
        } catch (Exception e) {
            throw new DaoException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DaoException"));
        }
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        try {
            // to implement
        } catch (Exception e) {
            throw new DaoException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DaoException"));
        }
    }
}
