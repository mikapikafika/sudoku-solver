package sudoku.exceptions;

public class DbConnectionException extends DaoException {
    public DbConnectionException(String message) {
        super(message);
    }

    public DbConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
