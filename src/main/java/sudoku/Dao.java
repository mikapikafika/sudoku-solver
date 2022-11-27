package sudoku;

import java.io.Serializable;

public interface Dao<T> extends AutoCloseable {
    T read();

    void write(T obj);
}
