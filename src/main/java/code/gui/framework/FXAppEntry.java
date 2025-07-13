package code.gui.framework;

import javafx.application.Application;
import javafx.stage.Stage;
import code.main.GameManager;

/**
 * Class that starts the JavaFX application thread.
 * Initializes Game Manager and Screen Navigator
 *
 * <p>
 * Initializes the FXAppEntry class.
 * </p>
 */
public class FXAppEntry extends Application {

    /**
     * Creates the GameManager with a ScreenNavigator for the given Stage.
     * @param primaryStage The current fxml stage, handled by this JavaFX Application class.
     */
    @Override
    public void start(Stage primaryStage) {
        // Load the game manager and pass it the navigator
        ScreenNavigator navigator = new ScreenNavigator(primaryStage);
        GameManager gameManager = new GameManager(navigator);
    }
}