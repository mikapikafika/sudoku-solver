package sudoku;

import sudoku.exceptions.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface JdbcDao<T> extends AutoCloseable {
    void create() throws DaoException, SQLException;
    int insert(T obj, String name) throws DaoException, SQLException;
    void connect() throws DaoException;
    void delete(String name) throws DaoException, SQLException;

    T get(String name) throws DaoException, SQLException;
    ArrayList<String> getAll() throws SQLException;
    Boolean isExisting(String name) throws DaoException, SQLException;
}
