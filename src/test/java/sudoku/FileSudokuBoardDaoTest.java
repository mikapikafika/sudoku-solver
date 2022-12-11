package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest implements AutoCloseable{

    @Test
    void FileSudokuBoardDao_WriteAndReadTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao("file.txt");
        fileSBD.write(sudokuBoard);
        SudokuBoard sudokuBoard1 = fileSBD.read();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudokuBoard1.get(i, j), sudokuBoard.get(i, j));
            }
        }

    }

    @Test
    void FileSudokuBoardDao_CloseTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao("file.txt");
        fileSBD.write(sudokuBoard);

        try {
            fileSBD.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    void FileSudokuBoardDao_WriteExceptionTest() throws Exception {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao(".");
        fileSBD.write(sudokuBoard);
    }
    @Test
    void FileSudokuBoardDao_ReadExceptionTest() throws Exception {
        FileSudokuBoardFactory factory = new FileSudokuBoardFactory();
        Dao<SudokuBoard> fileSBD = factory.getFileDao(".");
        fileSBD.read();
    }

    @Override
    public void close() throws Exception {

    }
}
