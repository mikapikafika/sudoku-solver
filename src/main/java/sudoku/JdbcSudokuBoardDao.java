package sudoku;

import java.sql.*;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.*;
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
     * @throws DaoException exception handling DAO errors
     * @throws SQLException exception handling database errors
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
            // saves all the modifications
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
                            .getString("JdbcCreationException"));
            throw new JdbcCreationException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcCreationException"), e);
        }
    }

    /**
     * Creates a table with board's coords and field values.
     * @throws DaoException exception handling DAO errors
     * @throws SQLException exception handling database errors
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
            // saves all the modifications
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
                    .getString("JdbcCreationException"));
            throw new JdbcCreationException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcCreationException"), e);
        }
    }

    /**
     * Checks if the board already exists in database.
     * @param name sudoku board's name
     * @return true if exists
     * @throws DaoException exception handling DAO errors
     * @throws SQLException exception handling database errors
     */
    private Boolean boardAlreadyExists(String name) throws DaoException, SQLException {
        connect();
        int boardID = -1;

        // PreparedStatements are often used with parameters ("?")
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT board_ID FROM sudoku_boards WHERE board_name LIKE ?")) {
            // adds parameter
            preparedStatement.setString(1,name);

            // ResultSet contains the result of the query above
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                connection.commit();
                // next() moves to the next row
                while (resultSet.next()) {
                    boardID = resultSet.getInt(1);
                }

                // no duplicate board found
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
                    .getString("JdbcObjAlreadyExistsException"));
            connection.rollback();
            throw new JdbcObjInDatabaseException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjAlreadyExistsException"), e);
        }
    }

    /**
     * Getter for the board_ID.
     * @param name sudoku board's name
     * @return board_ID
     * @throws DaoException exception handling DAO errors
     * @throws SQLException exception handling database errors
     */
    private int getBoardID(String name) throws DaoException, SQLException {
        connect();
        int boardID = -1;

        // PreparedStatements are often used with parameters ("?")
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT board_ID FROM sudoku_boards WHERE board_name LIKE ?")) {
            // adds parameter
            preparedStatement.setString(1, name);

            // ResultSet contains the result of the query above
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                connection.commit();
                // next() moves to the next row
                while (resultSet.next()) {
                    boardID = resultSet.getInt(1);
                }

                // no board found
                if (boardID == -1) {
                    throw new JdbcObjInDatabaseException(BundleManager
                            .getInstance()
                            .getBundle()
                            .getString("JdbcObjectDoesntExistException"));
                }

                return boardID;
            }
        } catch (JdbcObjInDatabaseException e) {
            logger.error(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjectDoesntExistException"));
            connection.rollback();
            throw new JdbcObjInDatabaseException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjectDoesntExistException"));
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
        connect();

        try {
            if (!boardAlreadyExists(name)) {
                // not sure about the 2nd connect
                connect();
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO sudoku_boards (board_name) VALUES (?)")) {
                    preparedStatement.setString(1, name);
                    preparedStatement.executeUpdate();
                    connection.commit();
                }

                int boardID = getBoardID(name);
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                                "INSERT INTO board_values " +
                                "(board_val_ID, board_X, board_Y, field_value) " +
                                "VALUES (?, ?, ?, ?)")) {
                            preparedStatement.setInt(1,boardID);
                            preparedStatement.setInt(2,i);
                            preparedStatement.setInt(3,j);
                            preparedStatement.setInt(4,obj.get(i,j));
                            preparedStatement.executeUpdate();
                        }
                    }
                }

                connection.commit();
                return boardID;
            }

            connection.rollback();
            throw new JdbcObjInDatabaseException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjAlreadyExistsException"));
        } catch (JdbcObjInDatabaseException | GetValueException e) {
            logger.error(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjAlreadyExistsException"));
            connection.rollback();
            throw new JdbcObjInDatabaseException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjAlreadyExistsException"),e);
        }
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
                    .getString("JdbcConnectionException"));
            throw new JdbcErrorException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcConnectionException"), e);
        }
    }

    @Override
    public void delete(String name) throws DaoException, SQLException {
        connect();
        int boardID = getBoardID(name);

        try (PreparedStatement preparedStatement1 = connection.prepareStatement("" +
                "DELETE FROM sudoku_boards WHERE board_ID = ?");
            PreparedStatement preparedStatement2 = connection.prepareStatement("" +
                    "DELETE FROM board_values WHERE board_val_ID = ?")) {
            preparedStatement1.setInt(1,boardID);
            preparedStatement2.setInt(1,boardID);
            preparedStatement2.executeUpdate();
            preparedStatement1.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            logger.error(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjectDoesntExistException"));
            connection.rollback();
            throw new JdbcObjInDatabaseException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcObjectDoesntExistException"),e);
        }
    }

    /**
     * Getter for sudoku board in database.
     * @param name sudoku board's name
     * @return sudoku board specified by the name parameter
     * @throws DaoException exception handling DAO errors
     * @throws SQLException exception handling database errors
     */
    @Override
    public SudokuBoard get(String name) throws DaoException, SQLException {
        // or make a separate getter? ACID ????
        connect();
        int boardID = getBoardID(name);

        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM board_values WHERE board_val_ID = ?")) {
            preparedStatement.setInt(1,boardID);

            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                connection.commit();
                resultSet.next();

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        sudokuBoard.set(resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4));
                        resultSet.next();
                    }
                }

                return sudokuBoard;
            } catch (Exception e) {
                logger.error(BundleManager
                        .getInstance()
                        .getBundle()
                        .getString("JdbcObjectDoesntExistException"));
                connection.rollback();
                throw new JdbcObjInDatabaseException(BundleManager
                        .getInstance()
                        .getBundle()
                        .getString("JdbcObjectDoesntExistException"));
            }
        }
    }

    /**
     * Getter for all the sudoku board names in database.
     * @return an ArrayList of boards' names
     * @throws SQLException exception handling DAO errors
     * @throws DaoException exception handling database errors
     */
    @Override
    public ArrayList<String> getAll() throws SQLException, DaoException {
        connect();
        ArrayList<String> boardNames = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT board_name FROM sudoku_boards")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                connection.commit();
                while (resultSet.next()) {
                    boardNames.add(resultSet.getString(1));
                }

                return boardNames;
            }
        } catch (Exception e) {
            logger.error(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcErrorException"),e);
            connection.rollback();
            throw new JdbcErrorException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("JdbcErrorException"));
        }
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
