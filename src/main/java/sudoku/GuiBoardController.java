package sudoku;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.GetElementException;
import sudoku.exceptions.GetValueException;
import sudoku.exceptions.GuiBuilderException;
import sudoku.exceptions.NullException;
import sudoku.exceptions.SetValueException;
import sudoku.lang.BundleManager;

public class GuiBoardController {
    @FXML
    private GridPane sudokuGrid = new GridPane();
    @FXML
    private Label label = new Label();
    @FXML
    private Label SaveToDatabase = new Label();
    @FXML
    private TextField mySave = new TextField();
    private SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard sudokuBoard = new SudokuBoard(solver);
    private SudokuBoard originalBoard;
    private SudokuBoard loadedSudokuBoard;
    private LevelEmptyFields levelEmptyFields = new LevelEmptyFields();

    private Dao<SudokuBoard> fileSudokuBoardDao;
    private Dao<SudokuBoard> jdbcSudokuBoardDao;
    private ResourceBundle bundle = ResourceBundle.getBundle("Gui");

    public void initialize() throws SetValueException, GetElementException,
            GetValueException, CloneNotSupportedException {
        if (GuiMenuController.getLoadedSudokuBoard() != null) {
            loadedSudokuBoard = GuiMenuController.getLoadedSudokuBoard();
            originalBoard = loadedSudokuBoard.clone();
            sudokuBoard = loadedSudokuBoard;
        } else {
            sudokuBoard.solveGame();
            originalBoard = sudokuBoard.clone();
            levelEmptyFields.readyBoard(sudokuBoard, GuiMenuController.getLevel());
        }
        fill();
        mySave.setPromptText("Enter Save Name");
    }

    public void fill() throws GetValueException {
        label.setText(bundle.getString("Board_Invalid"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setText("");

                if (sudokuBoard.get(i, j) != 0) {
                    textField.setDisable(true);
                    textField.setText(String.valueOf(sudokuBoard.get(i, j)));
                } else {
                    int writtenI = i;
                    int writtenJ = j;
                    textField.textProperty().addListener((observable, oldVal, newVal) -> {
                        if (newVal.matches("[1-9]")) {
                            try {
                                sudokuBoard.set(writtenI, writtenJ, Integer.parseInt(newVal));
                            } catch (SetValueException e) {
                                throw new NullException(BundleManager
                                        .getInstance()
                                        .getBundle()
                                        .getString("SetElementException"));
                            }
                            if (isBoardValid()) {
                                try {
                                    if (sudokuBoard.getColumn(writtenI).verify()
                                            && sudokuBoard.getRow(writtenJ).verify()
                                            && sudokuBoard.getBox(writtenI, writtenJ).verify()) {
                                        label.setText(bundle.getString("Board_Check_Correct"));
                                    } else {
                                        label.setText(bundle.getString("Board_Check_Incorrect"));
                                    }
                                } catch (GetElementException e) {
                                    throw new NullException(BundleManager
                                            .getInstance()
                                            .getBundle()
                                            .getString("SetElementException"));
                                }
                            } else {
                                label.setText(bundle.getString("Board_Invalid"));
                            }

                        } else if (newVal.matches("")) {
                            textField.setText(newVal);
                            label.setText(bundle.getString("Board_Invalid"));
                            try {
                                sudokuBoard.set(writtenI, writtenJ, 0);
                            } catch (SetValueException e) {
                                throw new NullException(BundleManager
                                        .getInstance()
                                        .getBundle()
                                        .getString("SetElementException"));
                            }
                        } else {
                            textField.setText(oldVal);
                        }
                    });
                }


                textField.setMinSize(50, 50);
                textField.setFont(Font.font(18));
                sudokuGrid.add(textField, j, i);
            }
        }
    }

    public void pressedBackButton() throws IOException, GuiBuilderException {
        GuiStageSetup.buildStage("choosingLevel.fxml", bundle);
    }

    private void updateBoard() throws SetValueException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String sudokuFieldValue = ((TextField)
                        sudokuGrid.getChildren().get((i * 9) + j)).getText();
                if (!sudokuFieldValue.equals("")) {
                    sudokuBoard.set(i, j, Integer.parseInt(sudokuFieldValue));
                } else {
                    sudokuBoard.set(i, j, 0);
                }
            }
        }
    }

    private boolean isBoardValid() {
        boolean isValid = true;
        for (int i = 0; i < 81; i++) {
            String sudokuFieldValue = ((TextField)
                    sudokuGrid.getChildren().get(i)).getText();
            if (sudokuFieldValue.equals("") || !(sudokuFieldValue.matches("[1-9]"))) {
                isValid = false;
            }
        }
        return isValid;
    }

    public void pressedSaveToFileButton() throws SetValueException, DaoException {
        updateBoard();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "Text Files", "*.txt"));
        File startingDirectory = new File("D:/KOMPO_STUDIA/mka_pn_1400_02");
        fileChooser.setInitialDirectory(startingDirectory);

        try {
            File file = fileChooser.showSaveDialog(GuiStageSetup.getStage());
            fileSudokuBoardDao = FileSudokuBoardFactory.getFileDao(file.getAbsolutePath());
            fileSudokuBoardDao.write(sudokuBoard);
        } catch (NullPointerException e) {
            throw new NullException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("NullException"));
        } catch (SQLException e) {
            throw new DaoException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DaoException"));
        }
    }

    public void pressedSaveToDatabaseButton() throws SetValueException, DaoException {
        updateBoard();

        try {
            String regex = "^[a-zA-Z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            String saveName;
            saveName = mySave.getText();
            Matcher matcher = pattern.matcher(saveName);
            if(FileSudokuBoardFactory.getJdbcDao().alreadyExists(saveName) ){
                PopOutWindow popOutWindow = new PopOutWindow();
                popOutWindow.messageBox("Wrong Save Name",
                        "Save already exist", Alert.AlertType.INFORMATION);
            } else if (!matcher.matches()){
                PopOutWindow popOutWindow = new PopOutWindow();
                popOutWindow.messageBox("Wrong Save Name",
                        "Wrong characters in the file name", Alert.AlertType.INFORMATION);
            } else {
                jdbcSudokuBoardDao = FileSudokuBoardFactory.getJdbcSudokuBoardDao(saveName);
                jdbcSudokuBoardDao.write(sudokuBoard);
            }


        } catch (SQLException e) {
            throw new DaoException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("DaoException"));
        }

    }
}