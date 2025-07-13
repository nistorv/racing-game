package code.gui.framework;

import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Class that handles navigation between various ScreenController's. This navigator
 * uses a BorderPane layout for the root pane. A launched screen is placed in the
 * center area of the border pane, replacing the previous screen if any.
 */
public class ScreenNavigator {
    private final Stage stage;
    private final BorderPane rootPane;

    /**
     * Creates a ScreenNavigator with the given stage.
     *
     * @param stage The JavaFX stage
     */
    public ScreenNavigator(Stage stage) {
        this.stage = stage;
        this.rootPane = new BorderPane();
        this.rootPane.setPrefHeight(400);
        this.rootPane.setPrefWidth(600);
        this.stage.setScene(new Scene(rootPane));
        this.stage.show();
    }

    /**
     * Replaces the root border pane's center component with the screen defined by the given
     * ScreenController.
     *
     * @param controller The JavaFX screen controller for the screen to be launched
     */
    public void loadScreen(ScreenController controller) {
        try {
            // Load the FXML file and set the controller for the screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource(controller.getFxmlFile()));
            loader.setControllerFactory(param -> controller);
            Parent screenParent = loader.load();
            rootPane.setCenter(screenParent);
            stage.setTitle(controller.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}