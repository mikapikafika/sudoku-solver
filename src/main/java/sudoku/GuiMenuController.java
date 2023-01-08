package sudoku;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.GuiBuilderException;
import sudoku.exceptions.GuiException;
import sudoku.exceptions.NullException;
import sudoku.lang.Authors_EN;
import sudoku.lang.Authors_PL;
import sudoku.lang.BundleManager;

public class GuiMenuController {

    private static Level level;
    private ResourceBundle bundle = ResourceBundle.getBundle("Gui");
    private Dao<SudokuBoard> fileSudokuBoardDao;
    private Dao<SudokuBoard> jdbcSudokuBoardDao;
    private static SudokuBoard loadedSudokuBoard = null;
    @FXML
    private ComboBox<String> comboBox;

    public static Level getLevel() {
        return level;
    }

    public static SudokuBoard getLoadedSudokuBoard() {
        return loadedSudokuBoard;
    }

    public void pressedEasyButton() throws GuiException {
        try {
            level = Level.EASY;
            GuiStageSetup.buildStage("sudokuGrid.fxml", bundle);
        } catch (IOException | GuiBuilderException e) {
            throw new GuiException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("GuiException"));
        }

    }

    public void pressedMediumButton() throws GuiException {
        try {
            level = Level.MEDIUM;
            GuiStageSetup.buildStage("sudokuGrid.fxml", bundle);
        } catch (IOException | GuiBuilderException e) {
            throw new GuiException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("GuiException"));
        }
    }

    public void pressedHardButton() throws GuiException {
        try {
            level = Level.HARD;
            GuiStageSetup.buildStage("sudokuGrid.fxml", bundle);
        } catch (IOException | GuiBuilderException e) {
            throw new GuiException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("GuiException"));
        }
    }

    public void pressedPolishLanguageButton() throws GuiException {
        try {
            Locale.setDefault(new Locale("pl"));
            bundle = ResourceBundle.getBundle("Gui");
            GuiStageSetup.buildStage("choosingLevel.fxml", bundle);
        } catch (IOException | GuiBuilderException e) {
            throw new GuiException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("GuiException"));
        }
    }

    public void pressedEnglishLanguageButton() throws GuiException {
        try {
            Locale.setDefault(new Locale("en"));
            bundle = ResourceBundle.getBundle("Gui");
            GuiStageSetup.buildStage("choosingLevel.fxml", bundle);
        } catch (IOException | GuiBuilderException e) {
            throw new GuiException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("GuiException"));
        }
    }

    public void pressedLoadFromFileButton() throws GuiException {
        try {
            String fileName;
            FileChooser fileChooser = new FileChooser();
            File startingDirectory = new File("D:/KOMPO_STUDIA/mka_pn_1400_02");
            fileChooser.setInitialDirectory(startingDirectory);
            fileName = fileChooser.showOpenDialog(GuiStageSetup.getStage()).getAbsolutePath();

            fileSudokuBoardDao = FileSudokuBoardFactory.getFileDao(fileName);
            loadedSudokuBoard = fileSudokuBoardDao.read();

            GuiStageSetup.buildStage("sudokuGrid.fxml", bundle);
        } catch (IOException | GuiBuilderException | DaoException e) {
            throw new GuiException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("GuiException"));
        }
    }

    public void pressedAuthorsButton() {
        if (bundle.getString("Button_Authors").equals("Autorzy")) {
            Authors_PL authorsPL = new Authors_PL();
            PopOutWindow popOutWindow = new PopOutWindow();
            popOutWindow.messageBox("Autorzy", (authorsPL.getObject("1")
                            + "\n" + (authorsPL.getObject("2"))),
                    Alert.AlertType.INFORMATION);
        } else {
            Authors_EN authorsEN = new Authors_EN();
            PopOutWindow popOutWindow = new PopOutWindow();
            popOutWindow.messageBox("Authors", (authorsEN.getObject("1")
                            + "\n" + (authorsEN.getObject("2"))),
                    Alert.AlertType.INFORMATION);
        }
    }
    public void pressedLoadFromDatabaseButton() throws GuiException, SQLException {

        try {
            String [] items = FileSudokuBoardFactory.getJdbcDao().getAll().toArray(new String[0]);
            comboBox.getItems().addAll(items);

            comboBox.setOnAction(event ->{
                String dbChoice = comboBox.getSelectionModel().getSelectedItem().toString();

                try {
                    jdbcSudokuBoardDao = FileSudokuBoardFactory.getJdbcSudokuBoardDao(dbChoice);
                    loadedSudokuBoard = jdbcSudokuBoardDao.read();
                    GuiStageSetup.buildStage("sudokuGrid.fxml", bundle);
                    loadedSudokuBoard = null;
                } catch (DaoException | SQLException | IOException | GuiBuilderException e) {
                    throw new RuntimeException(e);
                }

            });

        } catch (DaoException e) {
            throw new GuiException(BundleManager
                    .getInstance()
                    .getBundle()
                    .getString("GuiException"));
        }

    }

}

