package code.main;

import code.gui.framework.ScreenNavigator;
import code.models.Player;
import code.models.cart.Vehicle;
import code.models.race.Route;
import code.models.race.Track;
import code.services.vehicle.GarageService;
import code.services.racing.track.TrackGeneration;
import code.services.Wallet;

import java.util.ArrayList;
import java.util.List;

/**
 * GameManager is the primary controller.
 * Used for initialising different components, handling game logic, and maintaining game state.
 */
public class GameManager {
    private final ScreenLoader screenLoader;
    private Player player;

    /**
     * Constructs a new GameManager.
     * @param navigator ScreenNavigator.
     */
    public GameManager(ScreenNavigator navigator) {
        this.screenLoader = new ScreenLoader(navigator, this);
        screenLoader.loreOpen(); //first screen you see when the game starts
    }

    /**
     * Creates the player, binds wallet to player and generates track for track selection.
     *
     * @param name Player name.
     * @param diff Game difficulty (1: easy, 2: normal, 3: hard).
     * @param season_len Number of races in a game season.
     */
    public void onSetupComplete(String name, int diff, int season_len) {
        player = new Player(name, diff, season_len);
        Wallet.bindToPlayer(player);
        TrackGeneration.generateTracks();
        initialCartOpen();
    }

    /**
     * Calls the player to update race count information then generates new tracks for the Track Selection Menu.
     */
    public void onRaceEnd() {
        player.raceFinish();
        TrackGeneration.generateTracks();
    }

    /**
     * Gets the player.
     * @return Player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Calls the ScreenLoader to load the Lore Introduction.
     * Used to restart the game after a race season has been completed.
     */
    public void playAgain() {
        screenLoader.loreOpen();
    }

    /**
     * Gets the ScreenLoader.
     * @return ScreenLoader.
     */
    public ScreenLoader getScreenLoader() {
        return screenLoader;
    }

    /**
     * Gets a new GarageService instance, then refreshes the owned vehicles.
     * @return new GarageService instance.
     */
    public GarageService getGarageService() {
        GarageService garageService = new GarageService(this);
        garageService.refreshOwnedVehicles();
        return garageService;
    }

    /**
     * Checks to see whether the game is playable depending on vehicle on how much money the player has left compared
     * to repairing their cheapest repair vehicle.
     * @return true if player can still afford vehicle repairs, and/or player vehicles have reliability, false otherwise.
     */
    public boolean isGamePlayable() {
        List<Vehicle> vehicles = getGarageService().getOwnedVehicles();
        List<Vehicle> runningVehicles = new ArrayList<>();
        if (!vehicles.isEmpty()) {
            Vehicle firstVehicle = vehicles.getFirst();
            double lowestRepairCost = Math.min(firstVehicle.getCost(), (100 / firstVehicle.getReliability()));
            for (Vehicle vehicle : vehicles) {
                double repairCost = Math.min(vehicle.getCost(), (100 / vehicle.getReliability()));
                if (repairCost < lowestRepairCost) {
                    lowestRepairCost = repairCost;
                }

                if (vehicle.getReliability() != 0) {
                    runningVehicles.add(vehicle);
                }
            }

            if ((lowestRepairCost > player.getMoney()) && (runningVehicles.isEmpty())) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * Checks to see whether the player has completed all races of the season.
     * @return true if player has not finished all races of season, false otherwise.
     */
    public boolean gameEndCheck() {
        if (player.getRacesLeft() <= 0 || !isGamePlayable()) {
            if (player.getNetWorth() >= 100000 && isGamePlayable()) {
                screenLoader.winnerEnding();
                return false;
            } else {
                screenLoader.losingEnding();
                return false;
            }
        }
        return true;
    }

    /**
     * Calls the Screen Loader to load the Setup Window.
     */
    public void SetupWindowOpen() { screenLoader.SetupWindowOpen(); }

    /**
     * Calls the Screen Loader to load the Cart Shop.
     */
    public void shopOpen() { screenLoader.shopOpen(); }

    /**
     * Calls the Screen Loader to load the Initial Cart.
     */
    public void initialCartOpen() { screenLoader.initialCartOpen(); }

    /**
     * Calls the Screen Loader to load the Upgrade Shop.
     */
    public void upgradeShopOpen() { screenLoader.upgradeShopOpen(); }

    /**
     * Checks whether player has finished their races or not, and if not calls the screen loader to load the
     * track selection screen.
     */
    public void openTrackSelect() {
        if (gameEndCheck()) {
            screenLoader.openTrackSelect();
        }
    }

    /**
     * Calls the Screen Loader to load the Route Selection Screen.
     * @param track The parent racetrack of the routes.
     */
    public void openRouteSelect(Track track) { screenLoader.openRouteSelect(track); }

    /**
     * Calls the Screen Loader to load the Racing Screen.
     * @param track The selected racetrack.
     * @param route The selected route on the racetrack.
     */
    public void raceStart(Track track, Route route) { screenLoader.raceStart(track, route); }

    /**
     * Calls the Screen Loader to load the Garage Screen.
     */
    public void garageOpen() { screenLoader.garageOpen(); }

    /**
     * Calls the Screen Loader to load the Main Menu Screen.
     */
    public void mainMenuOpen() {
        if (gameEndCheck()) {
            screenLoader.mainMenuOpen();
        }
    }

    /**
     * Calls the Screen Loader to load the Handling Section of the Upgrades Shop.
     */
    public void handlingShopOpen() { screenLoader.handlingShopOpen(); }

    /**
     * Calls the Screen Loader to load the Engine Section of the Upgrades Shop.
     */
    public void engineShopOpen() { screenLoader.engineShopOpen(); }

    /**
     * Calls the Screen Loader to load the Fuel Tank Section of the Upgrades Shop.
     */
    public void fuelShopOpen() { screenLoader.fuelShopOpen(); }
}