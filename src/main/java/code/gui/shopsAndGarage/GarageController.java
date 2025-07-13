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
import code.models.cart.Vehicle;
import code.services.vehicle.GarageService;

import java.io.InputStream;

/**
 * Controller class for the Garage screen where players can maintain vehicles and apply upgrades
 */
public class GarageController extends ScreenController {

    @FXML
    private Button nextCart, prevCart;
    @FXML
    private ImageView cartImageView;
    @FXML
    private Button PSKButton, RSWButton, tireButton, airFilterButton, HCPButton, exhaustButton, twoLButton, fiveLButton, tenLButton, repairCart, fuelCart;
    @FXML
    private Label fuelCapacityNumValue, fuelEconomyNumValue, topSpeedNumValue, wallet,
            accelerationNumValue, handlingNumValue, reliabilityNumValue, cartName;
    @FXML
    private ProgressBar fuelCapacityProgBar, fuelEconomyProgBar, topSpeedProgBar,
            accelerationProgBar, handlingProgBar, reliabilityProgBar;

    private final GarageService garageService;
    private Vehicle currentVehicle;

    /**
     * Creates a new GarageController with the given game manager
     *
     * @param gameManager The game manager to handle state and navigation
     */
    public GarageController(GameManager gameManager) {
        super(gameManager);
        this.garageService = new GarageService(gameManager);
    }

    /**
     * Initializes the garage screen, updating owned vehicles,
     * displays no vehicle message or the current vehicle,
     * updates upgrade display, repair button, current cart, fuel button and wallet amount 
     */
    @FXML
    public void initialize() {
        garageService.refreshOwnedVehicles();
        if (garageService.getOwnedVehicles().isEmpty()) {

        } else {
            currentVehicle = getGameManager().getPlayer().getCurrentVehicle();
            updateVehicleDisplay(currentVehicle);
        }
        updateUpgradeDisplay();
        updateRepairButtonState();

        updateFuelButtonState();

        wallet.textProperty().bind(
                Bindings.concat("Wallet: $", getGameManager().getPlayer().moneyProperty())
        );
    }

    /**
     * Switches to display the next owned vehicle using garage service
     */
    @FXML
    public void nextCart() {
        Vehicle next = garageService.nextVehicle();
        if (next != null) {
            currentVehicle = next;
            getGameManager().getPlayer().setCurrentVehicle(currentVehicle);
            updateVehicleDisplay(currentVehicle);
            updateUpgradeDisplay();
            if (currentVehicle.getReliability() >= 1.0) {
                repairCart.setDisable(true);
            } else {
                repairCart.setDisable(false);
            }
            updateRepairButtonState();
            updateFuelButtonState();
        }
    }

    /**
     * Switches to display the previously owned vehicle using garage service
     */
    @FXML
    public void prevCart() {
        Vehicle prev = garageService.previousVehicle();
        if (prev != null) {
            currentVehicle = prev;
            getGameManager().getPlayer().setCurrentVehicle(currentVehicle);
            updateVehicleDisplay(currentVehicle);
            updateUpgradeDisplay();
            updateRepairButtonState();
            updateFuelButtonState();
        }
    }

    /**
     * Updates the displayed vehicle image and stats
     *
     * @param vehicle Vehicle to display
     */
    private void updateVehicleDisplay(Vehicle vehicle) {
        try (InputStream imageStream = getClass().getResourceAsStream(vehicle.getVehicleImage())) {
            cartImageView.setImage(new Image(imageStream));
        } catch (Exception e) {
            cartImageView.setImage(null);
        }
        cartName.setText("Cart name: " + vehicle.getName());
        updateStats(vehicle);
        nextCart.setDisable(false);
        prevCart.setDisable(false);
    }

    /**
     * Updates the displayed vehicle stats
     *
     * @param vehicle Vehicle whose stats should be displayed
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
     * Updates the upgrade buttons display
     */
    private void updateUpgradeDisplay() {
        updateUpgradeButton("Performance Suspension Kit", PSKButton);
        updateUpgradeButton("Racing Steering Wheel", RSWButton);
        updateUpgradeButton("Performance Tires", tireButton);
        updateUpgradeButton("Air Filter", airFilterButton);
        updateUpgradeButton("High-Compression Pistons", HCPButton);
        updateUpgradeButton("Gold Exhaust", exhaustButton);
        updateUpgradeButton("2L Fuel Tank", twoLButton);
        updateUpgradeButton("5L Fuel Tank", fiveLButton);
        updateUpgradeButton("10L Fuel Tank", tenLButton);
    }

    /**
     * Updates a single upgrade button's visibility and text
     * If upgrade is not owned, the button will be hidden
     * If upgrade is equipped, the button will show "Unequip", otherwise "Equip"
     *
     * @param upgradeName Name of the upgrade to check
     * @param button      Button to update visibility and text
     */
    private void updateUpgradeButton(String upgradeName, Button button) {
        boolean owned = garageService.ownsUpgrade(upgradeName);
        button.setVisible(owned);

        if (!owned || currentVehicle == null) {
            return;
        }

        boolean equipped = currentVehicle.getAppliedUpgrades().stream()
                .anyMatch(upgrade -> upgrade.getName().equalsIgnoreCase(upgradeName));
            
        button.setText(equipped ? "Unequip" : "Equip");
    }

    /**
     * Applies or removes the Performance Suspension Kit upgrade from all owned vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void PSKButtonClicked() {
        garageService.toggleUpgrade("Performance Suspension Kit", PSKButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the Racing-Steering-Wheel upgrade from all owned vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void RSWButtonClicked() {
        garageService.toggleUpgrade("Racing Steering Wheel", RSWButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the Performance Tires upgrade from all vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void tireButtonClicked() {
        garageService.toggleUpgrade("Performance Tires", tireButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the Air Filter upgrade from all vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void airFilterButtonClicked() {
        garageService.toggleUpgrade("Air Filter", airFilterButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the High-Compression Pistons upgrade from all vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void HCPButtonClicked() {
        garageService.toggleUpgrade("High-Compression Pistons", HCPButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the Gold Exhaust upgrade from all vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void exhaustButtonClicked() {
        garageService.toggleUpgrade("Gold Exhaust", exhaustButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the 2L Fuel Tank upgrade from all vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void twoLButtonClicked() {
        garageService.toggleUpgrade("2L Fuel Tank", twoLButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the 5L Fuel Tank upgrade from all vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void fiveLButtonClicked() {
        garageService.toggleUpgrade("5L Fuel Tank", fiveLButton);
        updateStats(currentVehicle);
    }

    /**
     * Applies or removes the 10L Fuel Tank upgrade from all vehicles.
     * Updates vehicle stats after applying/removing the upgrade.
     */
    @FXML
    public void tenLButtonClicked() {
        garageService.toggleUpgrade("10L Fuel Tank", tenLButton);
        updateStats(currentVehicle);
    }

    /**
     * Repairs the current vehicle for a cost, then updates the stats and the repair button
     */
    @FXML
    private void repairCart() {
        if (currentVehicle == null) return;

        if (currentVehicle.getReliability() >= 1.0) {
            repairCart.setDisable(true);
            updateRepairButtonState();
            return;
        }
        double repairCost = Math.min(currentVehicle.getCost(), (100 / currentVehicle.getReliability()));
        currentVehicle.setReliability(1.0);
        Player player = getGameManager().getPlayer();
        player.subMoney((int) repairCost);

        updateStats(currentVehicle);
        updateRepairButtonState();
        repairCart.setDisable(true);
    }

    /**
     * Updates repair button state and sets cost as the text of the button
     * repair cannot be more than the vehicle worth
     */
    private void updateRepairButtonState() {
        if (currentVehicle == null || currentVehicle.getReliability() >= 1.0) {
            repairCart.setDisable(true);
            repairCart.setText("Repair Cart");
        } else {
            double repairCost = Math.min(currentVehicle.getCost(), (100 / currentVehicle.getReliability()));
            repairCart.setText("Repair Cart $" + (int) repairCost);
            if (getGameManager().getPlayer().getMoney() < repairCost) {
                repairCart.setDisable(true);
            } else {
                repairCart.setDisable(false);
            }
        }
    }


    /**
     * Refuels the current vehicle for a cost
     * updates the stats and the fuel button
     */
    @FXML
    public void fuelCart() {
        System.out.println("Fuel cart");
        if (currentVehicle == null) return;

        if (currentVehicle.getFuelTank() >= 1.0) {
            fuelCart.setDisable(true);
            updateFuelButtonState();
            return;
        }
        double fuelCost = 20 / Math.max(0.1, currentVehicle.getFuelTank());
        currentVehicle.setFuelTank(1.0);
        Player player = getGameManager().getPlayer();
        player.subMoney((int) fuelCost);

        updateStats(currentVehicle);
        updateFuelButtonState();
        fuelCart.setDisable(true);
    }

    /**
     * Updates fuel button state and cost
     * sets text to the amount of money needed to fuel up
     */
    private void updateFuelButtonState() {
        if (currentVehicle == null || currentVehicle.getFuelTank() >= 1.0) {
            fuelCart.setDisable(true);
            fuelCart.setText("Fuel Cart");
        } else {
            double fuelCost = 20 / Math.max(0.1, currentVehicle.getFuelTank());
            fuelCart.setText("Fuel Cart $" + (int) fuelCost);
            if (getGameManager().getPlayer().getMoney() < fuelCost) {
                fuelCart.setDisable(true);
            } else {
            fuelCart.setDisable(false);
            }
        }
    }

    /**
     * Returns to the main menu
     */
    @FXML
    public void setGoToMainMenu() {
        getGameManager().mainMenuOpen();
    }

    /**
     * Gets the FXML file path for this screen
     *
     * @return The FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/garage.fxml";
    }

    /**
     * Gets the title for this screen
     *
     * @return "Golf Cart Legacy - Garage"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Garage";
    }
}