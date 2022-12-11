package sudoku;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SetupStage {
    private static Stage stage;

    private static void setStage(Stage stage) {
        SetupStage.stage = stage;
    }

    private static Parent loadFxml(String fxml) throws IOException {
        return new FXMLLoader(SetupStage.class.getResource(fxml)).load();
    }

    public static void build(String pathToFile) throws IOException {
        Scene scene = new Scene(loadFxml(pathToFile));
        stage.setScene(scene);
        stage.show();
    }

    public static void buildStarting(Stage stage, String pathToFile, String title) throws IOException {
        setStage(stage);
        stage.setTitle(title);
        Scene scene = new Scene(loadFxml(pathToFile));
        stage.setScene(scene);
        stage.show();
    }


}
