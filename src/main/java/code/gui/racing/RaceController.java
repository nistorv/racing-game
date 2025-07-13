package code.gui.racing;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import code.main.GameManager;
import code.gui.framework.ScreenController;
import code.models.Player;
import code.models.events.RaceEvent;
import code.models.race.Route;
import code.models.race.Track;
import code.models.cart.Vehicle;
import code.services.vehicle.GarageService;
import code.services.racing.track.TrackGeneration;
import code.services.racing.RaceEventListener;
import code.services.racing.RaceSim;
import code.services.racing.RaceTimer;
import code.services.racing.RaceTimerListener;

import static javafx.scene.text.FontWeight.BOLD;

/**
 * Controller for the race UI.
 */
public class RaceController extends ScreenController {
    @FXML
    private Label raceLabel;
    @FXML
    private Label raceTimerLabel;
    @FXML
    private ProgressBar raceProgressBar;
    @FXML
    private ImageView cartThumbnail;
    @FXML
    private Label vehicleNameLabel;
    @FXML
    private ProgressBar fuelBar;
    @FXML
    private Label fuelBarPercentLabel;
    @FXML
    private ProgressBar reliabilityBar;
    @FXML
    private Label reliabilityBarPercentLabel;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private Label eventDescLabel;
    @FXML
    private Button leftChoiceButton;
    @FXML
    private Button rightChoiceButton;
    @FXML
    private Label racePlacementLabel;
    @FXML
    private Label racerAmountLabel;

    private final Player player;
    private final Track track;
    private final Route route;
    private final Vehicle vehicle;

    private RaceSim raceSim;
    private RaceTimer raceTimer;

    /**
     * Constructs a new RaceController.
     *
     * @param gameManager the game manager instance.
     * @param track The racetrack.
     * @param route The route on the racetrack.
     */
    public RaceController(GameManager gameManager, Track track, Route route) {
        super(gameManager);
        this.track = track;
        this.route = route;
        this.player = getGameManager().getPlayer();
        GarageService garageService = new GarageService(gameManager);
        garageService.refreshOwnedVehicles();
        this.vehicle = garageService.getCurrentVehicle();
    }

    /**
     * Gets the FXML file.
     *
     * @return String filepath to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/racingMenu.fxml";
    }

    /**
     * Gets the window title.
     *
     * @return "Golf Cart Legacy - Race"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Race";
    }

    /**
     * Updates a progress bar and its percentage label.
     * Usage: For updating fuel and reliability displays.
     *
     * @param bar The ProgressBar to update.
     * @param percentLabel The Label showing the percentage representation.
     * @param percent Value to update progress bar and percentage label. (Between 0.0 to 1.0)
     */
    @FXML
    private void setBar(ProgressBar bar, Label percentLabel, double percent) {
        bar.setProgress(percent);
        percentLabel.setText(String.format("%.0f%%", percent * 100));
    }

    /**
     * Sets UI elements.
     */
    @FXML
    private void initSetters() {
        raceLabel.setText("Race #" + (player.getRacesPlayed() + 1) + " - " + track.getTrackName());

        cartThumbnail.setImage(new Image(vehicle.getVehicleImage(), true));
        vehicleNameLabel.setText(vehicle.getName());
        setBar(fuelBar, fuelBarPercentLabel, vehicle.getFuelTank());
        setBar(reliabilityBar, reliabilityBarPercentLabel, vehicle.getReliability());

        setPlacement(track.getEntries());
        racerAmountLabel.setText("out of " + track.getEntries());
    }

    /**
     * Handles race ending.
     * @param endDesc Description of race end reason.
     */
    @FXML
    private void raceEnd(String endDesc) {
        eventTitleLabel.setText("Race Over!");
        eventDescLabel.setText(endDesc);

        leftChoiceButton.setDisable(false);
        leftChoiceButton.setText("Go Home");
        leftChoiceButton.setOnAction(event -> {
           getGameManager().mainMenuOpen();
        });

        if ((vehicle.getFuelTank() <= 0) || (vehicle.getReliability() <= 0)) {
            rightChoiceButton.setDisable(true);
        } else {
            rightChoiceButton.setDisable(false);
        }
        rightChoiceButton.setText("Go to Next Race");
        rightChoiceButton.setOnAction(event -> {
            TrackGeneration.generateTracks();
            getGameManager().openTrackSelect();
        });

        raceTimer.stop();
    }

    /**
     * Clears event UI elements.
     */
    @FXML
    private void clearEventUI() {
        eventTitleLabel.setText("");
        eventDescLabel.setText("");
        leftChoiceButton.setText("");
        leftChoiceButton.setDisable(true);
        rightChoiceButton.setText("");
        rightChoiceButton.setDisable(true);
    }

    /**
     * Handles players decision on events.
     *
     * @param choice The player's choice (true for right button, left for left button)
     */
    private void eventChoice(boolean choice) {
        clearEventUI();
        raceSim.handleDecision(choice);
    }

    /**
     * Updates race timer label.
     *
     * @param timeString Formatted time string. (eg: 5 minutes & 10 seconds as 05:10)
     */
    @FXML
    private void setRaceTimerLabel(String timeString) {
        raceTimerLabel.setText("Time left: " + timeString);
    }

    /**
     * Updates race progress bar.
     *
     * @param progress progress (between 0.0 and 1.0)
     */
    @FXML
    private void setProgress(double progress) {
        raceProgressBar.setProgress(progress);
    }

    /**
     * Updates placement label with suffix.
     *
     * @param placement Race position.
     */
    @FXML
    private void setPlacement(int placement) {
        if (placement >= 10) {
            racePlacementLabel.setFont(Font.font("System", BOLD,30));
        } else {
            racePlacementLabel.setFont(Font.font("System", BOLD, 34));
        }

        if (placement == 1) {
            racePlacementLabel.setText("1st");
        } else if (placement == 2) {
            racePlacementLabel.setText("2nd");
        } else if (placement == 3) {
            racePlacementLabel.setText("3rd");
        } else {
            racePlacementLabel.setText(placement + "th");
        }
    }

    /**
     * JavaFX auto intialisation.
     * Sets up race simulation and UI listeners.
     */
    public void initialize() {
        initSetters();

        this.raceTimer = new RaceTimer(track.getRaceDuration(), new RaceTimerListener() {
            /**
             * Updates the timer label.
             *
             * @param timeString Formatted time string. (eg: 5 minutes & 10 seconds as 05:10)
             */
            @Override
            public void TimeUpdate(String timeString) {
                Platform.runLater(() -> setRaceTimerLabel(timeString));
            }

            /**
             * Calls race end function upon timer expiration.
             */
            public void TimeEnd() {
                raceEnd("Time's Up!");
            }
        });
        raceTimer.start();

        this.raceSim = new RaceSim(track, route, new RaceEventListener() {
            /**
             * Updates the progress bar of the race based on passed distance of the
             * player's vehicle.
             *
             * @param progress Race progress (Double between 0.0 and 1.0)
             */
            @Override
            public void raceProgress(double progress) {
                setProgress(progress);
            }

            /**
             * Updates fuel tank UI elements.
             *
             * @param fuel Vehicle fuel level (Double between 0.0 and 1.0)
             */
            @Override
            public void raceFuel(double fuel) {
                Platform.runLater(() -> setBar(fuelBar, fuelBarPercentLabel, fuel));
            }

            /**
             * Updates reliability UI elements.
             *
             * @param reliability Vehicle reliability (Double between 0.0 and 1.0)
             */
            @Override
            public void raceReliability(double reliability) {
                Platform.runLater(() -> setBar(reliabilityBar, reliabilityBarPercentLabel, reliability));
            }

            /**
             * Handles race termination, passes reason to race end function.
             *
             * @param reason Description of why the race ended.
             */
            @Override
            public void raceTerminate(String reason) {
                Platform.runLater(() -> raceEnd(reason));
            }

            /**
             * Displays event prompt with choices.
             *
             * @param event RaceEvent that needs player input.
             */
            @Override
            public void promptEvent(RaceEvent event) {
                Platform.runLater(() -> {
                    eventTitleLabel.setText(event.getTitle());
                    eventDescLabel.setText(event.getDescription());

                    leftChoiceButton.setDisable(false);
                    leftChoiceButton.setText(event.getLeftChoice());
                    leftChoiceButton.setOnAction(e -> eventChoice(false));

                    rightChoiceButton.setDisable(false);
                    rightChoiceButton.setText(event.getRightChoice());
                    rightChoiceButton.setOnAction(e -> eventChoice(true));
                });
            }

            /**
             * Clears event UI elements.
             */
            @Override
            public void clearEvent() {
                Platform.runLater(() -> clearEventUI());
            }

            /**
             * Updates player placement.
             *
             * @param placement Race position.
             */
            @Override
            public void racePlacement(int placement) {
                Platform.runLater(() -> setPlacement(placement));
            }
        }, getGameManager());
        raceSim.startRace();
    }
}
