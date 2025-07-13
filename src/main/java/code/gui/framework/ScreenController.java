package code.gui.framework;


import code.main.GameManager;
import code.main.ScreenLoader;

/**
 * Abstract parent class for all GameManager UI controller classes.
 */
public abstract class ScreenController {

    private final GameManager gameManager;

    /**
     * Creates an instance of a ScreenController with the given GameManager.
     * @param gameManager The rocket manager used by this ScreenController.
     */
    protected ScreenController(final GameManager gameManager) {
        this.gameManager = gameManager;
        ScreenLoader screenLoader = gameManager.getScreenLoader();
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The file path to the FXML file for this controller
     */
    protected abstract String getFxmlFile();

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen
     */
    protected abstract String getTitle();

    /**
     * Gets the rocket manager associated with this screen controller.
     * @return The rocket manager for this controller
     */
    protected GameManager getGameManager() {return gameManager;}

}
