package code.gui.beginingAndEnding;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import code.main.GameManager;
import code.gui.framework.ScreenController;

import java.util.List;

/**
 * Controller for Setup Window Screen.
 * Handles UI elements and user inputs for setting up the game.
 */
public class SetupWindow extends ScreenController {
    @FXML
    private TextField nameTextField;
    @FXML
    private Slider sliderDragLocation;
    @FXML
    private Label raceSeasonLabel;
    @FXML
    private Button diffButtonEasy;
    @FXML
    private Button diffButtonNormal;
    @FXML
    private Button diffButtonHard;
    @FXML
    private Button startGame;


    private int seasonLength = 5;
    private int difficulty = -1;

    /**
     * Constructs a new SetupWindow.
     *
     * @param gameManager The Game Manager.
     */
    public SetupWindow(GameManager gameManager) {
        super(gameManager);
    }

    /**
     * Links FXML file to the SetupWindow.
     *
     * @return Filepath to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/start.fxml";
    }

    /**
     * Sets window title.
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Setup";
    }

    /**
     * Sets the startGame button to be disabled until the player has selected a difficulty.
     */

    public void quitGame() {
        System.exit(0);
    }


    /**
     * Selects difficulty the player selects and updates the GUI element.
     */
    @FXML
    private void difficultySelect(List<Button> buttonList, int buttonIndex) {
        for (Button button : buttonList) {
            if (button.isDisabled()) {
                button.setDisable(false);
            }
            if (button == buttonList.get(buttonIndex)) {
                difficulty = buttonIndex + 1; // 1 = easy, 2 = normal, 3 = hard.
                button.setDisable(true);
            }
        }
    }

    /**
     * Sets the Season Length and updates the Season Length Label.
     */
    @FXML
    private void seasonLengthModifier(int length) {
        seasonLength = length;
        raceSeasonLabel.setText(length + " Race Season");
    }

    /**
     * Continues to the next window, updates GameManager.
     */
    @FXML
    private void continueSetup() {
        try {
            if (nameTextField.getText().isEmpty()) {
                throw new IllegalArgumentException("No name entered. Please enter a name for your character");
            }
            if (nameTextField.getText().length() < 3) {
                throw new IllegalStateException("Name too short. Please enter a name between 3 - 15 characters long");
            }
            if (nameTextField.getText().length() > 15) {
                throw new IllegalStateException("Name too long. Please enter a name between 3 - 15 characters long");
            }
            if (difficulty == -1) {
                throw new IllegalStateException("Difficulty not selected. Please select a game difficulty.");
            }
            getGameManager().onSetupComplete(nameTextField.getText(), difficulty, seasonLength);
        } catch (IllegalStateException | IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Golf Cart Legacy - Input Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Automatically loaded by JavaFX, used for listeners, etc.
     */
    public void initialize() {
        /**
         * Gets difficulty buttons and assigns their individual action events based on their
         * button index.
         */
        List<Button> diffButtons = List.of(
                diffButtonEasy,
                diffButtonNormal,
                diffButtonHard);
        for (int i = 0; i < diffButtons.size(); i++) {
            int buttonIndex = i;
            diffButtons.get(i).setOnAction(event -> difficultySelect(diffButtons, buttonIndex));
        }

        /**
         * Listens to sliderDragLocation and calls on seasonLengthModifier when a change
         * occurs.
         */
        sliderDragLocation.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        seasonLengthModifier(newValue.intValue()));
    }
    
}
