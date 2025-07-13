package code.gui.menus;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.services.Wallet;

/**
 * Controller class for the main menu screen where players can navigate to different areas of the game.
 */
public class MainMenuController extends ScreenController {
    @FXML
    private Label wallet;
    @FXML
    private Label racesLeft;

    /**
     * Constructs a MainMenuController with the given GameManager.
     *
     * @param gameManager The GameManager
     */
    public MainMenuController(GameManager gameManager) {
        super(gameManager);
    }

    /**
     * Returns String filepath to FXML file.
     *
     * @return The FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/mainMenu.fxml";
    }

    /**
     * Returns the title for the main menu window.
     *
     * @return "Golf Cart Legacy - Main Menu"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Main Menu";
    }

    /**
     * Updates the races left label with the number of races left for the player to race.
     */
    private void racesLeftUpdate() {
        racesLeft.setText("Races Left: " + getGameManager().getPlayer().getRacesLeft());
    }

    /**
     * Navigates to the shop screen.
     */
    public void setGoToShop() {
        getGameManager().shopOpen();
    }

    /**
     * Navigates to the upgrades main menu screen.
     */
    public void setGoToUpgradesMainMenu() {
        getGameManager().upgradeShopOpen();
    }

    /**
     * Navigates to the garage screen.
     */
    public void goToGarage() {
        getGameManager().garageOpen();
    }

    /**
     * Attempts to start a race. Shows an error if no vehicle is selected.
     */
    public void goToRace() {
        try {
            if (getGameManager().getGarageService().getCurrentVehicle() == null) {
                throw new NullPointerException("No Vehicle Selected.");
            }
            getGameManager().openTrackSelect();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Golf Cart Legacy - Input Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Exits the game application.
     */
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Initializes the main menu screen by binding wallet label and updating races left.
     */
    @FXML
    public void initialize() {
        wallet.textProperty().unbind();
        Wallet.bindLabel(wallet);
        racesLeftUpdate();
    }
}