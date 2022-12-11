package sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SudokuGridWindow {

    @FXML
    private GridPane sudokuGrid = new GridPane();
    private SudokuBoard sudokuBoard;

    public void initializeStartingGrid() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        fill();
    }

    public void fill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setMinSize(50, 50);
                textField.setFont(Font.font(18));
                textField.setText(String.valueOf(sudokuBoard.get(i, j)));
                sudokuGrid.add(textField, j, i);
            }
        }
    }

}
