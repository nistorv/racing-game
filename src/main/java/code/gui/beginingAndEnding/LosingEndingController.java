package code.gui.beginingAndEnding;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.Player;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;

/**
 * Controller class for the losing ending screen shown when player fails to hit the $100,000 goal.
 */
public class LosingEndingController extends ScreenController {

    @FXML
    private Label finishingNetworth;

    /**
     * Constructs a LosingEndingController with the given GameManager.
     *
     * @param gameManager The GameManager
     */
    public LosingEndingController(GameManager gameManager) {
        super(gameManager);
    }

    /**
     * Sets the finishing net worth text displayed to the player.
     *
     * @param networth The final net worth value to display
     */
    public void setFinishingNetworth(int networth) {
        finishingNetworth.setText("Your finishing networth was: $" + networth);
    }

    /**
     * Exits the game.
     */
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Returns the FXML file path for this controller.
     *
     * @return The FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/losingEnding.fxml";
    }

    /**
     * Returns the title for the losing ending window.
     *
     * @return "Golf Cart Legacy - Losing Ending"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Losing Ending";
    }

    /**
     * Initializes the losing ending screen by setting the final net worth.
     */
    @FXML
    public void initialize() {
        setFinishingNetworth(getGameManager().getPlayer().getNetWorth());
    }

}