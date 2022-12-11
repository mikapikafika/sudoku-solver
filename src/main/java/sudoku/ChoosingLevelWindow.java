package sudoku;

import java.io.IOException;
import javafx.fxml.FXML;

public class ChoosingLevelWindow {

    private Level level;

    public Level getLevel() {
        return level;
    }


    public void pressedEasyButton() throws IOException {
        level = Level.EASY;
        SetupStage.build("sudukoGrid.fxml");
    }

    public void pressedMediumButton() throws IOException {
        level = Level.MEDIUM;
        SetupStage.build("sudukoGrid.fxml");
    }

    public void pressedHardButton() throws IOException {
        level = Level.HARD;
        SetupStage.build("sudukoGrid.fxml");
    }
}

