package code.gui.shopsAndGarage;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ProgressBar;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.cart.Vehicle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import code.services.vehicle.VehicleStats;

/**
 * Controller class for the Cart Shop screen where players can purchase vehicles
 */
public class CartShopController extends ScreenController {
    private static final String DEFAULT_IMAGE = "/images/default_cart.png";
    private static final String IMAGE_PATH_PREFIX = "/images/";

    // Labels
    @FXML private Label golfCartNameWithPrice, fuelCapacityNumValue, fuelEconomyNumValue,
            topSpeedNumValue, accelerationNumValue, handlingNumValue,
            reliabilityNumValue, wallet;

    // Buttons
    @FXML private Button nextCart, prevCart, goToPreviousPage, goToUpgradeShop,
            goToGarage, buyCart;

    // ProgressBars
    @FXML private ProgressBar fuelCapacityProgBar, fuelEconomyProgBar, topSpeedProgBar,
            accelerationProgBar, handlingProgBar, reliabilityProgBar;

    // ImageViews
    @FXML private ImageView cartImageView;


    private List<Vehicle> availableVehicles = new ArrayList<>();
    private int currentVehicleIndex = 0;

    /**
     * Creates a new CartShopController with the game manager and initializes available vehicles.
     * Gets all available vehicles and their stats from VehicleStats.
     *
     * @param gameManager The game manager to handle state and navigation
     */
    public CartShopController(GameManager gameManager) {
        super(gameManager);
        this.availableVehicles = VehicleStats.getAllVehicles();
    }

    /**
     * Displays the next available vehicle in the shop
     */
    @FXML
    public void nextCart() {
        currentVehicleIndex = (currentVehicleIndex + 1) % availableVehicles.size();
        updateVehicleDisplay();
    }

    /**
     * Displays the previous vehicle in the shop
     */
    @FXML
    public void prevCart() {
        currentVehicleIndex = (currentVehicleIndex - 1 + availableVehicles.size()) % availableVehicles.size();
        updateVehicleDisplay();
    }

    /**
     * Updates the display with current vehicle information for the shop interface.
     * Loads and displays the vehicle's image from resources, sets the name and either
     * the cost or "OWNED" status text based on whether the player already owns it.
     * Also refreshes all vehicle stat displays via updateStats().
     */
    private void updateVehicleDisplay() {
        Vehicle currentVehicle = availableVehicles.get(currentVehicleIndex);

        String imagePath = currentVehicle.getVehicleImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        cartImageView.setImage(new Image(imageStream));

        boolean isOwned = getGameManager().getPlayer().getOwnedVehicles().contains(currentVehicle);
        String statusText = isOwned ? "OWNED" : "$" + currentVehicle.getCost();
        golfCartNameWithPrice.setText(currentVehicle.getName() + " - " + statusText);

        updateStats(currentVehicle);
    }

    /**
     * Updates the display of vehicle stats
     *
     * @param vehicle The current vehicles stats should be displayed
     */
    private void updateStats(Vehicle vehicle) {
        fuelCapacityNumValue.setText(String.format(("Fuel: %.0f / 100"), vehicle.getFuelTank() * 100));
        fuelCapacityProgBar.setProgress(vehicle.getFuelTank());

        fuelEconomyNumValue.setText("Fuel Economy : " + vehicle.getFuelEconomy() + "/100");
        fuelEconomyProgBar.setProgress(vehicle.getFuelEconomy() / 100.0);

        topSpeedNumValue.setText("Top Speed: " + vehicle.getTopSpeed() + " m/s");
        topSpeedProgBar.setProgress(vehicle.getTopSpeed() / 100.0);

        accelerationNumValue.setText(String.valueOf("Acceleration: " + vehicle.getAcceleration() + "m/s^2"));
        accelerationProgBar.setProgress(vehicle.getAcceleration() / 100.0);

        handlingNumValue.setText(String.valueOf("Handling: " + vehicle.getHandling() + "/100"));
        handlingProgBar.setProgress(vehicle.getHandling() / 100.0);

        reliabilityNumValue.setText("Reliability: " + vehicle.getReliability() * 100 + " / 100");
        reliabilityProgBar.setProgress(vehicle.getReliability());
    }

    /**
     * Handles the purchase of vehicles by checking if the player has enough money and doesn't already own the cart.
     * If the player has enough money and doesn't own the cart, deducts the cost from their wallet,
     * adds the vehicle to their collection, and sets it as their current vehicle.
     * Shows an error alert if the purchase fails due to insufficient funds or existing ownership.
     */
    @FXML
    public void buyCart() {
        Vehicle currentVehicle = availableVehicles.get(currentVehicleIndex);
        try {
            if (currentVehicle.getCost() > getGameManager().getPlayer().getMoney()) {
                throw new IllegalStateException(("Not enough money to buy this cart!"));
            }
            if (getGameManager().getPlayer().getOwnedVehicles().contains(currentVehicle)) {
                throw new IllegalStateException(("You already own this cart!"));
            } else {
                getGameManager().getPlayer().subMoney((currentVehicle.getCost()));
                getGameManager().getPlayer().getOwnedVehicles().add(currentVehicle);
                getGameManager().getPlayer().setCurrentVehicle(currentVehicle);
                updateVehicleDisplay();
            }
        } catch (IllegalStateException e) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Purchase Failed");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            updateVehicleDisplay();
        }
    }

    /**
     * Initializes the cart shop screen, updating the display and wallet amount
     */
    @FXML
    public void initialize() {
        updateVehicleDisplay();
        wallet.textProperty().bind(
                Bindings.concat("Wallet: $", getGameManager().getPlayer().moneyProperty())
        );
    }

    /**
     * Returns a string path to FXML file for this screen
     *
     * @return The FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/cartShop.fxml";
    }

    /**
     * Returns the title for this screen
     *
     * @return "Golf Cart Legacy - Cart Shop"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Cart Shop";
    }

    /**
     * Returns to the main menu
     */
    public void setGoToMainMenu() {
        getGameManager().mainMenuOpen();
    }

    /**
     * Opens the main upgrade menu
     */
    public void setGoToUpgradesMainMenu() {
        getGameManager().upgradeShopOpen();
    }

    /**
     * Opens the garage screen
     */
    public void goToGarage() {
        getGameManager().garageOpen();
    }
}