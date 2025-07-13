package code.gui.racing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.cart.Vehicle;
import code.models.race.Track;
import code.services.vehicle.GarageService;
import code.services.racing.track.TrackGeneration;

import java.util.ArrayList;

/**
 * Controller for Track Selection Screen.
 * Handles UI elements and user inputs for track selection.
 */
public class TrackSelectorController extends ScreenController {
    @FXML
    private Label leftTrackNameLabel;
    @FXML
    private Label centreTrackNameLabel;
    @FXML
    private Label rightTrackNameLabel;
    @FXML
    private Label leftTrackDescLabel;
    @FXML
    private Label centreTrackDescLabel;
    @FXML
    private Label rightTrackDescLabel;
    @FXML
    private Label leftTrackStatsLabel;
    @FXML
    private Label centreTrackStatsLabel;
    @FXML
    private Label rightTrackStatsLabel;
    @FXML
    private Button leftGoToRace;
    @FXML
    private Button centreGoToRace;
    @FXML
    private Button rightGoToRace;
    @FXML
    private Label selectedCartLabel;
    @FXML
    private ImageView selectedCartImage;
    @FXML
    private Label walletLabel;

    private ArrayList<Track> tracks;

    /**
     * Constructs a new TrackSelectorController
     *
     * @param gameManager The game manager.
     */
    public TrackSelectorController(GameManager gameManager) {
        super(gameManager);
        this.tracks = TrackGeneration.getTracks();
    }

    /**
     * Gets the FXML file.
     *
     * @return String filepath to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/trackSelect.fxml";
    }

    /**
     * Gets the window title.
     *
     * @return "Golf Cart Legacy - Track Selection"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Track Selection";
    }

    /**
     * Generates the statistics label for a track.
     *
     * @param track The track to generate statistics for.
     * @return Formatted string including track statistics.
     */
    private String statsLabel(Track track, int entryCost) {
        int maxPrize = ((track.getPrizeReward() / getGameManager().getPlayer().getDifficulty())
                * (getGameManager().getPlayer().getRacesPlayed() + 1));


        return "Time Limit: " + track.getRaceDuration() + " mins\nEntries: " + track.getEntries() +
                " racers\nMax Prize: $" + maxPrize + "\nEntry Cost: $" + entryCost;
    }

    /**
     * Checks if the player has funds to be able to afford to enter the race.
     *
     * @param track The track to check affordability.
     * @return true if player cannot afford race entry, false otherwise.
     */
    private boolean checkAffordability(Track track, int entryCost) {
        return (getGameManager().getPlayer().getMoney() < entryCost);
    }

    /**
     * Handles track selection and switches to route selection screen.
     *
     * @param track The selected track.
     */
    private void routeSelect(Track track, int entryCost) {
        getGameManager().getPlayer().subMoney(entryCost);
        getGameManager().openRouteSelect(track);
    }

    /**
     * Generates button String for track selection.
     *
     * @param clubName The name of the golf club.
     * @return Formatted button String.
     */
    private String buttonText(String clubName) {
        return "Go to " + clubName;
    }

    /**
     * Displays track information on the UI elements and sets button actions.
     */
    private void displayTracks() {
        Track leftTrack = tracks.getFirst();
        int lEntryCost = ((leftTrack.getEntryCost())) * (getGameManager().getPlayer().getRacesPlayed() + 1);
        leftTrackNameLabel.setText(leftTrack.getTrackName());
        leftTrackDescLabel.setText(leftTrack.getDescription());
        leftTrackStatsLabel.setText(statsLabel(leftTrack, lEntryCost));
        leftGoToRace.setDisable(checkAffordability(leftTrack, lEntryCost));
        leftGoToRace.setOnAction(event -> {
            routeSelect(leftTrack, lEntryCost);
        });
        leftGoToRace.setText(buttonText(leftTrack.getTrackName()));

        Track centreTrack = tracks.get(1);
        int cEntryCost = ((centreTrack.getEntryCost())) * (getGameManager().getPlayer().getRacesPlayed() + 1);
        centreTrackNameLabel.setText(centreTrack.getTrackName());
        centreTrackDescLabel.setText(centreTrack.getDescription());
        centreTrackStatsLabel.setText(statsLabel(centreTrack, cEntryCost));
        centreGoToRace.setOnAction(event -> {
            routeSelect(centreTrack, cEntryCost);
        });
        centreGoToRace.setDisable(checkAffordability(centreTrack, cEntryCost));
        centreGoToRace.setText(buttonText(centreTrack.getTrackName()));

        Track rightTrack = tracks.getLast();
        int rEntryCost = ((leftTrack.getEntryCost())) * (getGameManager().getPlayer().getRacesPlayed() + 1);
        rightTrackNameLabel.setText(rightTrack.getTrackName());
        rightTrackDescLabel.setText(rightTrack.getDescription());
        rightTrackStatsLabel.setText(statsLabel(rightTrack, rEntryCost));
        rightGoToRace.setOnAction(event -> {
            routeSelect(rightTrack, rEntryCost);
        });
        rightGoToRace.setDisable(checkAffordability(rightTrack, rEntryCost));
        rightGoToRace.setText(buttonText(rightTrack.getTrackName()));
    }

    /**
     * Returns to Main Menu screen.
     */
    public void returnToMainMenu() {
        getGameManager().mainMenuOpen();
    }

    /**
     * JavaFX auto intialisation.
     * Sets up race simulation and UI listeners.
     */
    public void initialize() {
        displayTracks();

        walletLabel.setText("Wallet: $" + getGameManager().getPlayer().getMoney());

        GarageService garageService = new GarageService(getGameManager());
        garageService.refreshOwnedVehicles();
        Vehicle vehicle = garageService.getCurrentVehicle();
        selectedCartLabel.setText("Selected Cart:\n" + vehicle.getName());
        selectedCartImage.setImage(new Image(vehicle.getVehicleImage()));
    }
}
