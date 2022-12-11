package sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class GuiSudokuGridController {

    @FXML
    private GridPane sudokuGrid = new GridPane();
    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private EmptyFieldsDependingOnLevel emptyFieldsDependingOnLevel = new EmptyFieldsDependingOnLevel();

    public void initialize() {
        sudokuBoard.solveGame();
        emptyFieldsDependingOnLevel.readyBoard(sudokuBoard, GuiChooseLevelController.getLevel());
        fill();
    }

    public void fill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                if (sudokuBoard.get(i,j) != 0) {
                    textField.setDisable(true);
                    textField.setText(String.valueOf(sudokuBoard.get(i, j)));
                }
                textField.setMinSize(50, 50);
                textField.setFont(Font.font(18));
                sudokuGrid.add(textField, j, i);
            }
        }
    }

}
