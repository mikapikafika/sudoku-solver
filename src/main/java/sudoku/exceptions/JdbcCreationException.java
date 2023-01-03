package sudoku.exceptions;

public class JdbcCreationException extends DaoException {
    public JdbcCreationException(String message) {
        super(message);
    }

    public JdbcCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
