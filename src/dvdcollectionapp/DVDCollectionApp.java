package dvdcollectionapp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * DVD Collection App class
 * @author Josh Hanrahan
 */
public class DVDCollectionApp extends Application {

    private Stage primaryStage;

    /**
     *
     * @param primaryStage
     * @throws IOException if FXML file is not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent thisRoot = FXMLLoader.load(getClass().getResource("view/DVDCollectionApp.fxml"));
        Scene scene = new Scene(thisRoot);

        primaryStage.setTitle("DVD Collection");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
