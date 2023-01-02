package sudoku.exceptions;

public class DbCreationException extends DaoException {
    public DbCreationException(String message) {
        super(message);
    }

    public DbCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
