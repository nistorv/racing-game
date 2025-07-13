package code.gui.menus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import code.main.GameManager;
import code.gui.framework.ScreenController;

/**
 * Controller for the Upgrade Shop's Main Menu.
 */
public class UpgradesMainMenuController extends ScreenController {
    @FXML private Label wallet;

    /**
     * Constructs a new UpgradesMainMenuController.
     *
     * @param gameManager The game manager.
     */
    public UpgradesMainMenuController(GameManager gameManager) {
        super(gameManager);
    }

    /**
     * Gets the FXML file.
     *
     * @return String filepath to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/upgradeShopMenu.fxml";
    }

    /**
     * Gets the window title.
     *
     * @return "Golf Cart Legacy - Upgrades Main Menu Selection"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Upgrades Main Menu";
    }

    /**
     * Sends user to the game's Main Menu screen.
     */
    public void setGoToMainMenu() {
        getGameManager().mainMenuOpen();
    }

    /**
     * Sends user to the Garage.
     */
    public void goToGarage() {
        getGameManager().garageOpen();
    }

    /**
     * Sends user to the Handling section of the Upgrade Shop.
     */
    public void setGoToHandlingShop(){
        getGameManager().handlingShopOpen();
    }

    /**
     * Sends user to the Engine section of the Upgrade Shop.
     */
    public void setGoToEngineShop(){
        getGameManager().engineShopOpen();
    }

    /**
     * Sends user to the Fuel Tank section of the Upgrade Shop.
     */
    public void setGoToFuelShop(){
        getGameManager().fuelShopOpen();
    }

    /**
     * JavaFX auto intialisation.
     * Sets up race simulation and UI listeners.
     */
    @FXML
    public void initialize() {
        wallet.setText("Wallet: $" + getGameManager().getPlayer().getMoney());
    }

}
