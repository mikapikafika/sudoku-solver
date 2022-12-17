package sudoku;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;



public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        GuiStageSetup.buildStage(primaryStage, "choosingLevel.fxml", "Sudoku Game");
    }

    public static void main(String[] args) {
        launch();
    }
}
