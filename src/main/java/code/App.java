package code;

import code.gui.framework.FXAppEntry;

/**
 * Launches Application.
 * Default entry point class.
 *
 * <p>
 * This constructor initializes the App class, setting it as the entry point 
 * for launching the JavaFX application.
 * </p>
 */
public class App {
    /**
     * Entry point which runs the javaFX application.
     * Launch program using FXAppEntry.launch().
     * @param args program arguments from command line.
     */
    public static void main(String[] args) {
        FXAppEntry.launch(FXAppEntry.class, args);
    }
}
