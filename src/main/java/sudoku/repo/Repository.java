package sudoku.repo;

public interface Repository<T> {
    T createInstance() throws CloneNotSupportedException;

}
