package sudoku.exceptions;

public class JdbcObjInDatabaseException extends DaoException {
    public JdbcObjInDatabaseException(String message) {
        super(message);
    }

    public JdbcObjInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
