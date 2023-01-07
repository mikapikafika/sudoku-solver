package sudoku;

import java.sql.SQLException;
import java.util.ArrayList;
import sudoku.exceptions.DaoException;

public interface JdbcDao<T> extends AutoCloseable {
    void create() throws DaoException, SQLException;

    int insert(T obj, String name) throws DaoException, SQLException;

    void connect() throws DaoException;

    void delete(String name) throws DaoException, SQLException;


    T get(String name) throws DaoException, SQLException;

    ArrayList<String> getAll() throws SQLException, DaoException;

    Boolean alreadyExists(String name) throws DaoException, SQLException;

}