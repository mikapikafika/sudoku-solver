package sudoku;

import java.sql.*;
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
        System.setProperty("derby.language.sequence.preallocator", "1");
        create();
    }

    public JdbcSudokuBoardDao() throws DaoException, SQLException {
        System.setProperty("derby.language.sequence.preallocator", "1");
        create();
    }

    /**
     * Creates a table with board's ID and name.
     * @throws DaoException - exception handling DAO errors
     * @throws SQLException - exception handling database errors
     */

    private void createBoardsTable() throws DaoException, SQLException {
        connect();

        // creates table, auto increments
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE sudoku_boards (" +
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

    /**
     * Creates a table with board's coords and field values.
     * @throws DaoException - exception handling DAO errors
     * @throws SQLException - exception handling database errors
     */
    private void createBoardsValuesTable() throws DaoException, SQLException {
        connect();

        // creates table, auto increments
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE board_values (" +
                            "board_val_id INT REFERENCES sudoku_boards(board_ID), " +
                            "board_X SMALLINT NOT NULL," +
                            "board_Y SMALLINT NOT NULL," +
                            "field_value SMALLINT NOT NULL)");
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

    // TO REVIEW:

    /**
     * Checks if the board already exists in database.
     * @param name - sudoku board's name
     * @return true if exists
     * @throws DaoException - exception handling DAO errors
     * @throws SQLException - exception handling database errors
     */
    private Boolean boardAlreadyExists(String name) throws DaoException, SQLException {
        connect();
        int boardID = -1;
        String query = "SELECT board_ID FROM sudoku_boards WHERE board_name LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                connection.commit();
                while (resultSet.next()) {
                    boardID = resultSet.getInt(1);
                }

                if (boardID == -1) {
                    connection.rollback();
                    return false;
                }

                return true;
            }
        } catch (Exception e) {
            logger.error(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DbCreationException"));
            connection.rollback();
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
        createBoardsValuesTable();
    }

    @Override
    public int insert(SudokuBoard obj, String name) throws DaoException, SQLException {
        // TBC
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
        // TBC
    }

    @Override
    public SudokuBoard get(String name) throws DaoException, SQLException {
        // TBC
        return null;
    }

    @Override
    public ArrayList<String> getAll() throws SQLException {
        // TBC
        return null;
    }

    @Override
    public Boolean alreadyExists(String name) throws DaoException, SQLException {
        return boardAlreadyExists(name);
    }

    // Dao methods:

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public SudokuBoard read() throws DaoException {
        try {
            return get(this.name);
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
            insert(obj,this.name);
        } catch (Exception e) {
            throw new DaoException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DaoException"));
        }
    }
}
