package code.main;

import code.gui.beginingAndEnding.*;
import code.gui.framework.ScreenNavigator;
import code.gui.menus.MainMenuController;
import code.gui.racing.RaceController;
import code.gui.racing.RouteSelectorController;
import code.gui.racing.TrackSelectorController;
import code.gui.shopsAndGarage.*;
import code.gui.menus.UpgradesMainMenuController;
import code.models.race.Route;
import code.models.race.Track;

/**
 * Class that points to different ScreenControllers to be loaded by ScreenNavigator.
 */
public class ScreenLoader {
    private final ScreenNavigator navigator;
    private final GameManager gameManager;

    /**
     * Constructs the ScreenLoader.
     *
     * @param navigator The ScreenNavigator that loads the screen.
     * @param gameManager The GameManager.
     */
    public ScreenLoader(ScreenNavigator navigator, GameManager gameManager) {
        this.navigator = navigator;
        this.gameManager = gameManager;
    }

    /**
     * Launches the Setup Window.
     */
    public void SetupWindowOpen() {
        navigator.loadScreen(new SetupWindow(gameManager));
    }

    /**
     * Launches the Cart Shop.
     */
    public void shopOpen() {
        navigator.loadScreen(new CartShopController(gameManager));
    }

    /**
     * Launches the Upgrade Shop.
     */
    public void upgradeShopOpen() {
        navigator.loadScreen(new UpgradesMainMenuController(gameManager));
    }

    /**
     * Launches the Race.
     * @param track The racetrack.
     * @param route The route chosen on the racetrack.
     */
    public void raceStart(Track track, Route route) {
        navigator.loadScreen(new RaceController(gameManager, track, route));
    }

    /**
     * Launches the Garage.
     */
    public void garageOpen() {
        navigator.loadScreen(new GarageController(gameManager));
    }

    /**
     * Launches the Main Menu.
     */
    public void mainMenuOpen() {
        navigator.loadScreen(new MainMenuController(gameManager));
    }

    /**
     * Launches the Handling Shop.
     */
    public void handlingShopOpen() {
        navigator.loadScreen(new HandlingShopController(gameManager));
    }

    /**
     * Launches the Initial Cart.
     */
    public void initialCartOpen(){navigator.loadScreen(new InitialCartController(gameManager));}

    /**
     * Launches the Engine Shop.
     */
    public void engineShopOpen() {
        navigator.loadScreen(new EngineShopController(gameManager));
    }

    /**
     * Launches the Fuel Tank Shop.
     */
    public void fuelShopOpen() {
        navigator.loadScreen(new FuelShopController(gameManager));
    }

    /**
     * Launches the startup game lore.
     */
    public void loreOpen() {
        navigator.loadScreen(new LoreController(gameManager));
    }

    /**
     * Launches the winner end lore.
     */
    public void winnerEnding() {
        navigator.loadScreen(new WinningEndingController(gameManager));
    }

    /**
     * Launches the losing end lore.
     */
    public void losingEnding() {
        navigator.loadScreen(new LosingEndingController(gameManager));
    }

    /**
     * Opens race track selection.
     */
    public void openTrackSelect() { navigator.loadScreen(new TrackSelectorController(gameManager)); }

    /**
     * Opens racetrack route selection.
     * @param track The racetrack.
     */
    public void openRouteSelect(Track track) {
        navigator.loadScreen(new RouteSelectorController(gameManager, track));
    }
}