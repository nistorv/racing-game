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

import java.io.InputStream;

import code.services.vehicle.GarageService;
import code.services.vehicle.OwnedVehiclesManager;
import code.services.vehicle.UpgradeStats;
import code.models.Player;

/**
 * Controller class for the engine shop screen where players can purchase engine upgrades.
 */
public class EngineShopController extends ScreenController {
    @FXML
    private Label wallet, selectedCartName, fuelCapacityNumValue, fuelEconomyNumValue,
            topSpeedNumValue, accelerationNumValue, handlingNumValue, reliabilityNumValue;
    @FXML
    private Button nextCart, prevCart, buyAirFilter, buyHCP, buyExhaust;
    @FXML
    private ProgressBar fuelCapacityProgBar, fuelEconomyProgBar, topSpeedProgBar,
            accelerationProgBar, handlingProgBar, reliabilityProgBar;
    @FXML
    private ImageView cartImageView;

    private final OwnedVehiclesManager vehicleManager;
    private GarageService garageService;

    /**
     * Constructs an EngineShopController with the GameManager.
     *
     * @param gameManager The GameManager
     */
    public EngineShopController(GameManager gameManager) {
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
        garageService.refreshOwnedVehicles();
        refreshBuyButtons();
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
     * Displays a message when no vehicles are owned and hides relevant controls.
     */
    private void showNoVehiclesMessage() {
        cartImageView.setImage(null);
        selectedCartName.setText("No vehicles in your garage!");
        selectedCartName.setStyle("-fx-text-fill: red; -fx-font-style: italic;");
        nextCart.setVisible(false);
        prevCart.setVisible(false);
        clearAllStats();
    }

    /**
     * Clears all vehicle stats in progress bars and removes the text above them.
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
     * Switches to the next vehicle in the garage by calling the garage service next vehicle method
     * This also updates the display to show the user the new currently selected vehicle
     */
    @FXML
    public void nextCart() {
        Vehicle next = garageService.nextVehicle();
        getGameManager().getPlayer().setCurrentVehicle(next);
        updateVehicleDisplay();
    }

    /**
     * Switches to the previous vehicle in the garage.
     */
    @FXML
    public void prevCart() {
        Vehicle prev = garageService.previousVehicle();
        getGameManager().getPlayer().setCurrentVehicle(prev);
        updateVehicleDisplay();
    }

    /**
     * Updates the display with the current vehicle's stats and image. Sets the next and previous buttons
     * to be able to be used
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
     * Updates all vehicle progress bars and the text above with the given vehicle's stats.
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
     * Handles the purchase of the Air Filter upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual conformation of the purchase.
     */
    @FXML
    private void buyAirFilter() {
        Upgrade airFilter = UpgradeStats.getUpgrade("Air Filter").orElse(null);

        if (airFilter != null) {
            boolean success = Player.purchaseUpgrade(airFilter, airFilter.getCost());
            if (success) {
                buyAirFilter.setDisable(true);
                buyAirFilter.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Handles the purchase of the High-Compression Pistons upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual conformation of the purchase.
     */
    @FXML
    private void buyHCP() {
        Upgrade HighCompressionPistons = UpgradeStats.getUpgrade("High-Compression Pistons").orElse(null);

        if (HighCompressionPistons != null) {
            boolean success = Player.purchaseUpgrade(HighCompressionPistons, HighCompressionPistons.getCost());
            if (success) {
                buyHCP.setDisable(true);
                buyHCP.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * Handles the purchase of the Gold Exhaust upgrade and disables the buy button.
     * Sets the button text to "Purchased" to give visual conformation of the purchase.
     */
    @FXML
    private void buyExhaust() {
        Upgrade exhaust = UpgradeStats.getUpgrade("Gold Exhaust").orElse(null);

        if (exhaust != null) {
            boolean success = Player.purchaseUpgrade(exhaust, exhaust.getCost());
            if (success) {
                buyExhaust.setDisable(true);
                buyExhaust.setText("Purchased");
            } else {
                System.out.println("Not enough money or already owned.");
            }
        }
    }

    /**
     * checks if upgrade is already owned and if it is then sets the buttons to disable and changes the text to "Purchased"
     */

    private void refreshBuyButtons(){
        Upgrade exhaust = UpgradeStats.getUpgrade("Gold Exhaust").orElse(null);
        Upgrade HighCompressionPistons = UpgradeStats.getUpgrade("High-Compression Pistons").orElse(null);
        Upgrade airFilter = UpgradeStats.getUpgrade("Air Filter").orElse(null);

        if (getGameManager().getPlayer().getOwnedUpgrades().contains(exhaust)){
            buyExhaust.setDisable(true);
            buyExhaust.setText("Purchased");
        }
        if (getGameManager().getPlayer().getOwnedUpgrades().contains(HighCompressionPistons)){
            buyHCP.setDisable(true);
            buyHCP.setText("Purchased");
        }
        if (getGameManager().getPlayer().getOwnedUpgrades().contains(airFilter)){
            buyAirFilter.setDisable(true);
            buyAirFilter.setText("Purchased");
        }
    }

    /**
     * Returns String filepath to FXML file.
     *
     * @return The FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/engineShop.fxml";
    }

    /**
     * Returns the title for the engine shop window.
     *
     * @return "Golf Cart Legacy - Engine Shop"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Engine Shop";
    }

    /**
     * Navigates back to the main upgrade shop menu.
     */
    @FXML
    public void setGoToUpgradesMainMenu() {
        getGameManager().upgradeShopOpen();
    }
}