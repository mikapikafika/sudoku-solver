package sudoku.exceptions;

public class JdbcErrorException extends DaoException {
    public JdbcErrorException(String message) {
        super(message);
    }

    public JdbcErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
