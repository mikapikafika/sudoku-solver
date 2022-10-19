package sudoku;

/**
 * <h2>Zadanie 3 - Interfejsy.</h2>
 * @author Michalina Wysocka
 * @author Szymon Wydmuch
 */

public class Main {
    public static void main(String[] args) {

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        System.out.println(sudokuBoard);
    }
}





