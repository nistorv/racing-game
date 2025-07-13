package code.gui.racing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.cart.Vehicle;
import code.models.race.Route;
import code.models.race.Track;
import code.services.vehicle.GarageService;

/**
 * Controller for Route Selection Screen.
 * Handles UI elements and user inputs for route selection.
 */
public class RouteSelectorController extends ScreenController {
    @FXML
    private Label golfClubLabel;
    @FXML
    private Label leftRouteDesc;
    @FXML
    private Label rightRouteDesc;
    @FXML
    private Label leftRouteStats;
    @FXML
    private Label rightRouteStats;
    @FXML
    private Button leftRaceButton;
    @FXML
    private Button rightRaceButton;
    @FXML
    private Label selectedCartLabel;
    @FXML
    private ImageView selectedCartImage;

    private final Track track;

    /**
     * Constructs a new RouteSelectorController
     *
     * @param gameManager The game manager.
     * @param track The selected track.
     */
    public RouteSelectorController(GameManager gameManager, Track track) {
        super(gameManager);
        this.track = track;
    }

    /**
     * Gets the FXML file.
     *
     * @return String filepath to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/routeSelect.fxml";
    }

    /**
     * Gets the window title.
     *
     * @return "Golf Cart Legacy - Track Selection"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Route Selector";
    }

    /**
     * Returns to track selection screen.
     */
    public void returnToTracks() {
        getGameManager().openTrackSelect();
    }

    /**
     * Generates the statistics label for a track.
     *
     * @param route The route to generate statistics for.
     * @return Formatted string including route statistics.
     */
    public String statsString(Route route) {
        return "Difficulty: " + route.getRouteDifficulty() + "\nFuel Stops: " + route.getFuelStops() +
                "\nDistance: " + route.getDistance() + "m";
    }

    /**
     * Calls the raceStart function with the selected track and route.
     *
     * @param track The selected track.
     * @param route The selected route.
     */
    public void startRace(Track track, Route route) {
        getGameManager().raceStart(track, route);
    }

    /**
     * Displays route information on the UI elements and sets button actions.
     */
    public void showRoutes() {
        Route leftRoute = track.getRoutes().getFirst();
        Route rightRoute = track.getRoutes().getLast();

        leftRouteDesc.setText(leftRoute.getRouteDesc());
        leftRouteStats.setText(statsString(leftRoute));
        leftRaceButton.setOnAction(event -> {
            startRace(track, leftRoute);
        });

        rightRouteDesc.setText(rightRoute.getRouteDesc());
        rightRouteStats.setText(statsString(rightRoute));
        rightRaceButton.setOnAction(event -> {
            startRace(track, rightRoute);
        });
    }

    /**
     * JavaFX auto intialisation.
     * Sets up race simulation and UI listeners.
     */
    public void initialize() {
        golfClubLabel.setText(track.getTrackName());

        showRoutes();

        GarageService garageService = new GarageService(getGameManager());
        garageService.refreshOwnedVehicles();
        Vehicle vehicle = garageService.getCurrentVehicle();
        selectedCartLabel.setText("Selected Cart:\n" + vehicle.getName());
        selectedCartImage.setImage(new Image(vehicle.getVehicleImage()));
    }
}
