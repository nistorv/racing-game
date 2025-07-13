package code.gui.beginingAndEnding;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.Player;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;

/**
 * Controller for the End UI when the player has won the game.
 */
public class WinningEndingController extends ScreenController {
    @FXML
    private Label finishingNetworth;

    /**
     * Constructs a new WinningEndingController
     *
     * @param gameManager The game manager.
     */
    public WinningEndingController(GameManager gameManager) {
        super(gameManager);
    }

    /**
     * Updates the finishing net worth label with the provided net worth value.
     *
     * @param networth The final net worth value to be displayed on the UI.
     */
    public void setFinishingNetworth(int networth) {
        finishingNetworth.setText("Your finishing networth was: $" + networth);
    }

    /**
     * Closes the program.
     */
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Gets the FXML file.
     *
     * @return String filepath to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/winnerEnding.fxml";
    }

    /**
     * Gets the window title.
     *
     * @return "Golf Cart Legacy - Winning Ending"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Winning Ending";
    }

    /**
     * JavaFX auto intialisation.
     * Sets up race simulation and UI listeners.
     */
    @FXML
    public void initialize() {
        setFinishingNetworth(getGameManager().getPlayer().getNetWorth());}

}
