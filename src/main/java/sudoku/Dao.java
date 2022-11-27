package sudoku;

import java.io.Serializable;

public interface Dao<T> extends AutoCloseable {
    public T read();

    public void write(T obj);
}
