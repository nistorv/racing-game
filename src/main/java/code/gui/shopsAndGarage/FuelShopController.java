package code.gui.shopsAndGarage;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;
import code.services.vehicle.GarageService;
import code.services.vehicle.OwnedVehiclesManager;
import code.services.vehicle.UpgradeStats;

import java.io.InputStream;

/**
 * Controller class for the Fuel Shop screen where players can purchase fuel economy upgrades
 */
public class FuelShopController extends ScreenController {
    @FXML 
    private Label wallet, selectedCartName, fuelCapacityNumValue, fuelEconomyNumValue,
            topSpeedNumValue, accelerationNumValue, handlingNumValue, reliabilityNumValue;
    @FXML 
    private Button nextCart, prevCart, buyTwoL, buyFiveL, buyTenL;
    @FXML 
    private ProgressBar fuelCapacityProgBar, fuelEconomyProgBar, topSpeedProgBar,
            accelerationProgBar, handlingProgBar, reliabilityProgBar;
    @FXML 
    private ImageView cartImageView;


    private final OwnedVehiclesManager vehicleManager;
    private final GarageService garageService;

    /**
     * Creates a new FuelShopController with the game manager
     *
     * @param gameManager The game manager
     */
    public FuelShopController(GameManager gameManager) {
        super(gameManager);
        this.vehicleManager = OwnedVehiclesManager.getInstance();
        this.garageService = new GarageService(gameManager);
    }

    /**
     * Initializes the engine shop screen, refreshing owned vehicles to check for any new purchases
     * and updating the display. Also checking the current wallet amount and displaying it
     */
    @FXML
    public void initialize() {
        vehicleManager.setOwnedVehicles(getGameManager().getPlayer().getOwnedVehicles());
        checkOwnedUpgrade();

        if (vehicleManager.getOwnedVehicles().isEmpty()) {
            showNoVehiclesMessage();
        } else {
            updateVehicleDisplay();
        }

        wallet.textProperty().bind(
                Bindings.concat("Wallet: $", getGameManager().getPlayer().moneyProperty())
        );
    }

    /**
     * checks if upgrade is already owned and if it is then sets the buttons to disable and changes the text to "Purchased"
     */
    private void checkOwnedUpgrade() {
        Upgrade TwoL = UpgradeStats.getUpgrade("2L Fuel Tank").orElse(null);
        Upgrade FiveL = UpgradeStats.getUpgrade("5L Fuel Tank").orElse(null);
        Upgrade TenL = UpgradeStats.getUpgrade("10L Fuel Tank").orElse(null);

        if (getGameManager().getPlayer().getOwnedUpgrades().contains(TwoL)) {
            buyTwoL.setDisable(true);
            buyTwoL.setText("Purchased");
        }
        if (getGameManager().getPlayer().getOwnedUpgrades().contains(FiveL)) {
            buyFiveL.setDisable(true);
            buyFiveL.setText("Purchased");
        }
        if (getGameManager().getPlayer().getOwnedUpgrades().contains(TenL)) {
            buyTenL.setDisable(true);
            buyTenL.setText("Purchased");
        }
    }

    /**
     * Displays a message when the player has no owned vehicles while hiding the controls for
     * changing the cart
     */
    private void showNoVehiclesMessage() {
        cartImageView.setImage(null);
        selectedCartName.setText("No vehicles in your garage!");
        selectedCartName.setStyle("-fx-text-fill: #ff5555; -fx-font-style: italic;");
        nextCart.setVisible(false);
        prevCart.setVisible(false);
        clearAllStats();
    }

    /**
     * Clears all vehicle stats for progress bars and names
     */
    private void clearAllStats() {
        fuelCapacityNumValue.setText("");
        fuelCapacityProgBar.setProgress(0);
        fuelEconomyNumValue.setText("");
        fuelEconomyProgBar.setProgress(0);
        topSpeedNumValue.setText("");
        topSpeedProgBar.setProgress(0);
        accelerationNumValue.setText("");
        accelerationProgBar.setProgress(0);
        handlingNumValue.setText("");
        handlingProgBar.setProgress(0);
        reliabilityNumValue.setText("");
        reliabilityProgBar.setProgress(0);
    }

    /**
     * Displays next vehicle in owned vehicles using the garage service call next vehicle
     */
    @FXML
    public void nextCart() {
        Vehicle next = garageService.nextVehicle();
        getGameManager().getPlayer().setCurrentVehicle(next);
        updateVehicleDisplay();
    }

    /**
     * Displays previous vehicle in owned vehicles using the garage service call previous vehicle
     */
    @FXML
    public void prevCart() {
        Vehicle prev = garageService.previousVehicle();
        getGameManager().getPlayer().setCurrentVehicle(prev);
        updateVehicleDisplay();
    }

    /**
     * Updates the display with current vehicle information and enables the next cart and previous cart
     */
    private void updateVehicleDisplay() {
        Vehicle currentVehicle = getGameManager().getPlayer().getCurrentVehicle();

        try {
            InputStream imageStream = getClass().getResourceAsStream(currentVehicle.getVehicleImage());
            cartImageView.setImage(new Image(imageStream));
        } catch (Exception e) {
            cartImageView.setImage(null);
        }
        selectedCartName.setText(currentVehicle.getName());
        selectedCartName.setStyle("");
        updateStats(currentVehicle);
        nextCart.setDisable(false);
        prevCart.setDisable(false);
    }

    /**
     * Updates the display of vehicle stats
     *
     * @param vehicle The current vehicle stats should be displayed
     */
    private void updateStats(Vehicle vehicle) {
        fuelCapacityNumValue.setText(String.format(("Fuel: %.0f%%"), vehicle.getFuelTank() * 100));
        fuelCapacityProgBar.setProgress(vehicle.getFuelTank());

        fuelEconomyNumValue.setText("Fuel Economy : " + vehicle.getFuelEconomy() + "/100");
        fuelEconomyProgBar.setProgress(vehicle.getFuelEconomy() / 100.0);

        topSpeedNumValue.setText("Top Speed: " + vehicle.getTopSpeed() + " m/s");
        topSpeedProgBar.setProgress(vehicle.getTopSpeed() / 100.0);

        accelerationNumValue.setText(String.valueOf("Acceleration: " + vehicle.getAcceleration() + "m/s^2"));
        accelerationProgBar.setProgress(vehicle.getAcceleration() / 100.0);

        handlingNumValue.setText(String.valueOf("Handling: " + vehicle.getHandling() + "/100"));
        handlingProgBar.setProgress(vehicle.getHandling() / 100.0);

        reliabilityNumValue.setText(String.format("Reliability: %.0f%%", vehicle.getReliability() * 100));
        reliabilityProgBar.setProgress(vehicle.getReliability());
    }

    /**
     * Handles the purchase of the 2L Fuel Tank upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual confirmation of the purchase.
     */
    @FXML
    private void buyTwoL() {
        Upgrade TwoL = UpgradeStats.getUpgrade("2L Fuel Tank").orElse(null);

        if (TwoL != null) {
            boolean success = getGameManager().getPlayer().purchaseUpgrade(TwoL, TwoL.getCost());
            if (success) {
                buyTwoL.setDisable(true);
                buyTwoL.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Handles the purchase of the 5L Fuel Tank upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual confirmation of the purchase.
     */
    @FXML
    private void buyFiveL() {
        Upgrade FiveL = UpgradeStats.getUpgrade("5L Fuel Tank").orElse(null);

        if (FiveL != null) {
            boolean success = getGameManager().getPlayer().purchaseUpgrade(FiveL, FiveL.getCost());
            if (success) {
                buyFiveL.setDisable(true);
                buyFiveL.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Handles the purchase of the 10L Fuel Tank upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual confirmation of the purchase.
     */
    @FXML
    private void buyTenL() {
        Upgrade TenL = UpgradeStats.getUpgrade("10L Fuel Tank").orElse(null);
        if (TenL != null) {
            boolean success = getGameManager().getPlayer().purchaseUpgrade(TenL, TenL.getCost());
            if (success) {
                buyTenL.setDisable(true);
                buyTenL.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Returns String filepath to FXML file.
     * @return The FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/fuelShop.fxml";
    }

    /**
     * Returns the title for this screen
     * "Golf Cart Legacy - Fuel Shop"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Fuel Shop";
    }

    /**
     * Returns to the main upgrades menu
     */
    @FXML
    public void setGoToUpgradesMainMenu() {
        getGameManager().upgradeShopOpen();
    }
}