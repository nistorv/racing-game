package code.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the User.
 * Tracks financials, player name, game difficulty, races played and races remaining and owned vehicle and upgrades.
 */
public class Player {
    private static final IntegerProperty money = new SimpleIntegerProperty(20000);
    private String name;
    private int difficulty; // 1: easy, 2: normal, 3: hard
    private int racesLeft;
    private int racesPlayed;

    private List<Vehicle> ownedVehicles = new ArrayList<>();
    private static List<Upgrade> ownedUpgrades = new ArrayList<>();
    private List<Upgrade> equippedUpgrades = new ArrayList<>();
    private Vehicle currentVehicle;

    /**
     * Constructs anew player.
     *
     * @param name Player's name.
     * @param difficulty Game difficulty multiplier. (1: easy, 2: normal, 3: hard)
     * @param season_length Number of races in the game season.
     */
    public Player(String name, int difficulty, int season_length) {
        this.name = name;
        this.difficulty = difficulty;
        this.racesLeft = season_length;
        this.racesPlayed = 0;
        money.set(5000);
    }

    /**
     * Gets the money property to bind.
     *
     * @return IntegerProperty representing the player's wallet.
     */
    public IntegerProperty moneyProperty() {
        return money;
    }

    /**
     * Gets the amount of money.
     *
     * @return money balance.
     */
    public int getMoney() {
        return money.get();
    }

    /**
     * Calculates and returns the net worth (money + cost of vehicles + cost of upgrades).
     *
     * @return Total net worth.
     */
    public int getNetWorth(){
        int netWorth = 0;
        for (Vehicle vehicle : ownedVehicles) {
            netWorth += vehicle.getCost();
        }
        for (Upgrade upgrade : equippedUpgrades) {
            netWorth += upgrade.getCost();
        }
        netWorth += money.get();
        return netWorth;
    }

    /**
     * Adds money to the player's wallet.
     *
     * @param amount Amount of money to add.
     */
    public void addMoney(int amount) {
        money.set(money.get() + amount);
    }

    /**
     * Subtracts money from the player's wallet if player has enough money.
     *
     * @param amount Amount of money to subtract.
     * @return true if transaction successful, false otherwise.
     */
    public boolean subMoney(int amount) {
        if (money.get() >= amount) {
            money.set(money.get() - amount);
            return true;
        }
        return false;
    }

    /**
     * Gets a list of owned vehicles.
     *
     * @return List of owned vehicles.
     */
    // Vehicle management
    public List<Vehicle> getOwnedVehicles() {
        return ownedVehicles;
    }

    /**
     * Sets provided vehicle as the selected vehicle if the player owns the vehicle.
     *
     * @param vehicle Selected vehicle.
     */
    public void setCurrentVehicle(Vehicle vehicle) {
        if (ownedVehicles.contains(vehicle)) {
            currentVehicle = vehicle;
        }
    }

    /**
     * Gets the selected vehicle.
     *
     * @return Selected vehicle.
     */
    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    // Upgrade management

    /**
     * Checks if player owns an upgrade.
     *
     * @param upgrade Upgrade to check.
     * @return true if owned, false otherwise.
     */
    public static boolean ownsUpgrade(Upgrade upgrade) {
        return ownedUpgrades.contains(upgrade);
    }

    /**
     * Gets all owned upgrades.
     *
     * @return Owned upgrades list.
     */
    public List<Upgrade> getOwnedUpgrades() {
        return ownedUpgrades;
    }

    /**
     * Purchases an upgrade if player has sufficient funds and not already owned.
     *
     * @param upgrade Upgrade to purchase.
     * @param cost Upgrade cost.
     * @return true if purchase successful, false otherwise.
     */
    public static boolean purchaseUpgrade(Upgrade upgrade, int cost) {
        if (money.get() >= cost && !ownedUpgrades.contains(upgrade)) {
            money.set(money.get() - cost);
            ownedUpgrades.add(upgrade);
            return true;
        }
        return false;
    }

    /**
     * Removes an upgrade from the upgrade list.
     *
     * @param upgrade Upgrade to be removed.
     */
    public static void removeUpgrade(Upgrade upgrade) {
        ownedUpgrades.remove(upgrade);
    }

    /**
     * Gets the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the game difficuly multiplier.
     *
     * @return The game multiplier. (1: easy, 2: normal, 3: hard)
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Gets remaining left in the game season.
     *
     * @return Number of race left.
     */
    public int getRacesLeft() {
        return racesLeft;
    }

    /**
     * Gets count of races played.
     *
     * @return Number of races played.
     */
    public int getRacesPlayed() {
        return racesPlayed;
    }

    /**
     * Updates race counters upon race completion.
     */
    public void raceFinish() {
        racesLeft -= 1;
        racesPlayed += 1;
    }
}
