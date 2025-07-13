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
import code.models.Player;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;
import code.services.vehicle.GarageService;
import code.services.vehicle.OwnedVehiclesManager;
import code.services.vehicle.UpgradeStats;

import java.io.InputStream;

/**
 * Controller class for the Handling Shop screen where players can purchase handling upgrades
 */
public class HandlingShopController extends ScreenController {
    // FXML components
    @FXML
    private Label wallet;
    @FXML
    private Button nextCart;
    @FXML
    private Button prevCart;
    @FXML
    private Label selectedCartName;
    @FXML
    private ImageView cartImageView;
    @FXML
    private Label fuelCapacityNumValue;
    @FXML
    private ProgressBar fuelCapacityProgBar;
    @FXML
    private Label fuelEconomyNumValue;
    @FXML
    private ProgressBar fuelEconomyProgBar;
    @FXML
    private ProgressBar topSpeedProgBar;
    @FXML
    private Label topSpeedNumValue;
    @FXML
    private ProgressBar accelerationProgBar;
    @FXML
    private Label accelerationNumValue;
    @FXML
    private ProgressBar handlingProgBar;
    @FXML
    private Label handlingNumValue;
    @FXML
    private ProgressBar reliabilityProgBar;
    @FXML
    private Label reliabilityNumValue;
    @FXML
    private Button buyRSW, buyPSK, buyTire;

    private final OwnedVehiclesManager vehicleManager;
    private final GarageService garageService;

    /**
     * Creates a new HandlingShopController with the game manager
     *
     * @param gameManager The game manager
     */
    public HandlingShopController(GameManager gameManager) {
        super(gameManager);
        this.vehicleManager = OwnedVehiclesManager.getInstance();
        this.garageService = new GarageService(gameManager);
    }

    /**
     * Initializes the handling shop screen, refreshing owned vehicles to check for any new purchases
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
        Upgrade Tire = UpgradeStats.getUpgrade("Performance Tires").orElse(null);
        Upgrade RSW = UpgradeStats.getUpgrade("Racing Steering Wheel").orElse(null);
        Upgrade PSK = UpgradeStats.getUpgrade("Performance Suspension Kit").orElse(null);

        if (getGameManager().getPlayer().getOwnedUpgrades().contains(Tire)) {
            buyTire.setText("Purchased");
            buyTire.setDisable(true);
        }
        if (getGameManager().getPlayer().getOwnedUpgrades().contains(RSW)) {
            buyRSW.setText("Purchased");
            buyRSW.setDisable(true);
        }
        if (getGameManager().getPlayer().getOwnedUpgrades().contains(PSK)) {
            buyPSK.setText("Purchased");
            buyPSK.setDisable(true);
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

        nextCart.setDisable(true);
        prevCart.setDisable(true);

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
     * Handles the purchase of the Racing Steering-Wheel upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual confirmation of the purchase.
     */
    @FXML
    private void buyRSW() {
        Upgrade RSW = UpgradeStats.getUpgrade("Racing Steering Wheel").orElse(null);

        if (RSW != null) {
            boolean success = Player.purchaseUpgrade(RSW, RSW.getCost());
            if (success) {
                buyRSW.setDisable(true);
                buyRSW.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Handles the purchase of the Performance Suspension Kit upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual confirmation of the purchase.
     */
    @FXML
    private void buyPSK() {
        Upgrade PSK = UpgradeStats.getUpgrade("Performance Suspension Kit").orElse(null);

        if (PSK != null) {
            boolean success = Player.purchaseUpgrade(PSK, PSK.getCost());
            if (success) {
                buyPSK.setDisable(true);
                buyPSK.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Handles the purchase of the Performance Tires upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual confirmation of the purchase.
     */
    @FXML
    private void buyTire() {
        Upgrade Tire = UpgradeStats.getUpgrade("Performance Tires").orElse(null);

        if (Tire != null) {
            boolean success = Player.purchaseUpgrade(Tire, Tire.getCost());
            if (success) {
                buyTire.setDisable(true);
                buyTire.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Returns String filepath to FXML file.
     *
     * @return The FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/handlingShop.fxml";
    }

    /**
     * Returns the title for this screen
     * "Golf Cart Legacy - Handling Shop"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Handling Shop";
    }

    /**
     * Returns to the main upgrades menu
     */
    @FXML
    public void setGoToUpgradesMainMenu() {
        getGameManager().upgradeShopOpen();
    }
}