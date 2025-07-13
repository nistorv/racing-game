package code.gui.beginingAndEnding;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import code.main.GameManager;
import code.gui.framework.ScreenController;

/**
 * Controller class responsible for managing the startup lore screen.
 * Class transitions texts and images to display a turning page / presentation style story.
 */
public class LoreController extends ScreenController {

    /**
     * Constructs a new LoreController with the specified game manager.
     *
     * @param gameManager The game manager.
     */
    public LoreController(GameManager gameManager) {
        super(gameManager);
    }

    private static final double FADE_DURATION_TEXT = 5000;
    private static final double FADE_DURATION_IMAGE = 1000;
    private static final double FADE_DURATION_OUT = 1000;

    @FXML private Label loreText;
    @FXML private ImageView loreImage;

    private int currentPage = 0;

    /**
     * Array containing text content for each page of the lore.
     */
    private final String[] lorePages = {
            // Page 1
            "Uncle Bogdan was more than family. He was your mentor and,\n" +
                    "the man who taught you how to play golf. As owner of the great\n" +
                    "Bogdan Pines Golf Club, he let you play free in exchange for\n" +
                    "helping maintain the course and tinkering with golf carts\n" +
                    "in his workshop.",

            // Page 2
            "Everything changed when you got the call...\n\n" +
                    "Bogdan died in a small plane crash en route to play at\n" +
                    "his dream course Pebble Beach.",

            // Page 3
            "The will revealed he'd left you\n" +
                    "the club... along with $100,000 \n" +
                    "of crushing debt which you have no idea how to pay back.",

            // Page 4
            "Victor 'The Vulture' Vaughn, CEO of Vantage Luxury " +
                    "Developments, is circling. He's already bought up all the " +
                    "surrounding land and plans to turn Bogdan's legacy\n" +
                    "into another soulless gated community.",

            // Page 5
            "Your plan? The underground golf cart racing circuit Bogdan always dreamed of.\n\n" +
                    "Vaughn has heard of your idea and has " +
                    "hired pros to stop you!, win as many races as you can, and you might just " +
                    "win enough money to save the club, and prove Bogdan's crazy vision " +
                    "was right all along."
    };

    /**
     * Array containing filepaths to the images for each page of the lore.
     */
    private final String[] imagePaths = {
            "/images/PlayerAndUncle.png",
            "/images/InLovingMemory.png",
            "/images/LawyersOffice.png",
            "/images/VincentPhoto.png",
            "/images/PlayerLookingOut.png"
    };

    /**
     * Goes to next lore slide. If all slides have been shown, transitions to Setup Window.
     */
    @FXML
    public void nextLoreSlide() {

        // Fade out both text
        FadeTransition textFadeOut = new FadeTransition(Duration.millis(FADE_DURATION_OUT), loreText);
        textFadeOut.setFromValue(1.0);
        textFadeOut.setToValue(0.0);
        // Fade out image
        FadeTransition imageFadeOut = new FadeTransition(Duration.millis(FADE_DURATION_OUT), loreImage);
        imageFadeOut.setFromValue(1.0);
        imageFadeOut.setToValue(0.0);

        textFadeOut.setOnFinished(e -> {
            currentPage++;
            if (currentPage < lorePages.length) {
                updateSlide();
                fadeInContent();
            } else {
                skipToMenu();
            }
        });
        textFadeOut.play();
        imageFadeOut.play();
    }

    /**
     * Creates the page transition animation for the image and text UI elements.
     */
    private void fadeInContent() {
        loreText.setOpacity(0);
        loreImage.setOpacity(0);

        //Text fade in
        FadeTransition textFadeIn = new FadeTransition(Duration.millis(FADE_DURATION_TEXT), loreText);
        textFadeIn.setFromValue(0.0);
        textFadeIn.setToValue(1.0);
        //Image fade in
        FadeTransition imageFadeIn = new FadeTransition(Duration.millis(FADE_DURATION_IMAGE), loreImage);
        imageFadeIn.setFromValue(0.0);
        imageFadeIn.setToValue(1.0);

        textFadeIn.play();
        imageFadeIn.play();
    }


    /**
     * Sends user to Setup Window.
     */
    @FXML
    public void skipToMenu() {
        getGameManager().SetupWindowOpen();
    }

    /**
     * Updates the page with its respective lore page and image.
     * */
    @FXML
    private void updateSlide() {
        loreText.setText(lorePages[currentPage]);
        loreImage.setImage(new Image(imagePaths[currentPage]));
    }

    /**
     * JavaFX auto intialisation.
     * Sets up race simulation and UI listeners.
     */
    @FXML
    public void initialize() {
        // Initialize with the first slide
        loreText.setText(lorePages[0]);
        loreImage.setImage(new Image(imagePaths[0], true));
        loreText.setWrapText(true);
        loreText.setOpacity(0);
        loreImage.setOpacity(0.5);
        fadeInContent();
    }

    /**
     * Gets the FXML file.
     *
     * @return String filepath to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/golfcartLegacyLore.fxml";
    }

    /**
     * Gets the window title.
     *
     * @return "Golf Cart Legacy - Lore"
     */
    @Override
    protected String getTitle() {
        return "Golf Cart Legacy - Lore";
    }
}