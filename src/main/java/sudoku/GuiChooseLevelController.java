package sudoku;

import java.io.IOException;

public class GuiChooseLevelController {

    private static Level level;

    public static Level getLevel() {
        return level;
    }


    public void pressedEasyButton() throws IOException {
        level = Level.EASY;
        GuiStageSetup.buildStage("sudokuGrid.fxml");
    }

    public void pressedMediumButton() throws IOException {
        level = Level.MEDIUM;
        GuiStageSetup.buildStage("sudokuGrid.fxml");
    }

    public void pressedHardButton() throws IOException {
        level = Level.HARD;
        GuiStageSetup.buildStage("sudokuGrid.fxml");
    }

}

