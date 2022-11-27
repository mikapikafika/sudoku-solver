package sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard board = null;

        try (FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            board = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException  | ClassNotFoundException e) {
            System.out.println("Exception");
        }
        return board;
    }

    @Override
    public void write(SudokuBoard board) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(board);
        } catch (IOException e) {
            System.out.println("Exception");
        }
    }

    @Override
    public void close() {
        System.out.println("Closed AutoClosable Resources");
    }
}
