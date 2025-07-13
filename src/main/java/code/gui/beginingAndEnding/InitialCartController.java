package code.gui.beginingAndEnding;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.cart.Vehicle;
import code.services.vehicle.VehicleStats;

import java.util.List;

public class InitialCartController extends ScreenController {

    @FXML private Label wallet;

    @FXML private ProgressBar topSpeedProgBar, accelerationProgBar, handlingProgBar, fuelEconomyProgBar;
    @FXML private ProgressBar topSpeedProgBar1, accelerationProgBar1, handlingProgBar1, fuelEconomyProgBar1;
    @FXML private ProgressBar topSpeedProgBar2, accelerationProgBar2, handlingProgBar2, fuelEconomyProgBar2;

    @FXML private Label topSpeedNumValue, accelerationNumValue, handlingNumValue, fuelEconomyNumValue;
    @FXML private Label topSpeedNumValue1, accelerationNumValue1, handlingNumValue1, fuelEconomyNumValue1;
    @FXML private Label topSpeedNumValue2, accelerationNumValue2, handlingNumValue2, fuelEconomyNumValue2;

    @FXML private ImageView cart1img, cart2img, cart3img;

    private List<Vehicle> defaultCarts;

    public InitialCartController(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/initialCart.fxml";
    }

    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Buy First Cart";
    }

    @FXML
    private void initialize() {
        // Load default carts
        defaultCarts = VehicleStats.getAllDefaultCarts();

        loadCartStats(defaultCarts.get(0), 0);
        loadCartStats(defaultCarts.get(1), 1);
        loadCartStats(defaultCarts.get(2), 2);

        wallet.setText("Wallet: $" + getGameManager().getPlayer().getMoney());
    }

    private void loadCartStats(Vehicle cart, int index) {
        double topSpeed = cart.getTopSpeed();
        double acceleration = cart.getAcceleration();
        double handling = cart.getHandling();
        double fuelEconomy = cart.getFuelEconomy();

        // Normalize stats for progress bars (adjust this if your max stat is higher)
        double maxStat = 100.0;

        switch (index) {
            case 0 -> {
                topSpeedProgBar.setProgress(topSpeed / maxStat);
                accelerationProgBar.setProgress(acceleration / maxStat);
                handlingProgBar.setProgress(handling / maxStat);
                fuelEconomyProgBar.setProgress(fuelEconomy / maxStat);

                topSpeedNumValue.setText("Top Speed: " + topSpeed);
                accelerationNumValue.setText("Acceleration: " + acceleration);
                handlingNumValue.setText("Handling: " + handling);
                fuelEconomyNumValue.setText("Fuel Economy: " + fuelEconomy);

                cart1img.setImage(new Image(cart.getVehicleImage()));
            }
            case 1 -> {
                topSpeedProgBar1.setProgress(topSpeed / maxStat);
                accelerationProgBar1.setProgress(acceleration / maxStat);
                handlingProgBar1.setProgress(handling / maxStat);
                fuelEconomyProgBar1.setProgress(fuelEconomy / maxStat);

                topSpeedNumValue1.setText("Top Speed: " + topSpeed);
                accelerationNumValue1.setText("Acceleration: " + acceleration);
                handlingNumValue1.setText("Handling: " + handling);
                fuelEconomyNumValue1.setText("Fuel Economy: " + fuelEconomy);

                cart2img.setImage(new Image(cart.getVehicleImage()));
            }
            case 2 -> {
                topSpeedProgBar2.setProgress(topSpeed / maxStat);
                accelerationProgBar2.setProgress(acceleration / maxStat);
                handlingProgBar2.setProgress(handling / maxStat);
                fuelEconomyProgBar2.setProgress(fuelEconomy / maxStat);

                topSpeedNumValue2.setText("Top Speed: " + topSpeed);
                accelerationNumValue2.setText("Acceleration: " + acceleration);
                handlingNumValue2.setText("Handling: " + handling);
                fuelEconomyNumValue2.setText("Fuel Economy: " + fuelEconomy);

                cart3img.setImage(new Image(cart.getVehicleImage()));
            }
        }
    }

    @FXML
    private void buyCartOne() {
        chooseCart(0);
    }

    @FXML
    private void buyCartTwo() {
        chooseCart(1);
    }

    @FXML
    private void buyCartThree() {
        chooseCart(2);
    }

    private void chooseCart(int index) {
        Vehicle currentVehicle = defaultCarts.get(index);
        getGameManager().getPlayer().subMoney((currentVehicle.getCost()));
        getGameManager().getPlayer().getOwnedVehicles().add(currentVehicle);
        getGameManager().getPlayer().setCurrentVehicle(currentVehicle);

        getGameManager().mainMenuOpen();
    }
}
