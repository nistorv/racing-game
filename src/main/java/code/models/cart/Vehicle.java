package code.models.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vehicle with customizable stats and upgrades.
 * Provides functionality to manage vehicle properties like speed, handling, reliability etc.
 * and track upgrades applied to the vehicle.
 */
public class Vehicle {
    private String type;
    private int topSpeed;
    private int acceleration;
    private int handling;
    private double reliability;
    private int fuelEconomy;
    private double fuelTank;
    private int cost;
    private final String name;
    private final String img;

    /**
     * Minimum value for integer stats
     */
    private static final int MIN_STAT = 0;
    /**
     * Maximum value for top speed stat
     */
    private static final int MAX_TOP_SPEED = 100;
    /**
     * Maximum value for acceleration stat
     */
    private static final int MAX_ACCELERATION = 100;
    /**
     * Maximum value for handling stat
     */
    private static final int MAX_HANDLING = 100;
    /**
     * Maximum value for fuel economy stat
     */
    private static final int MAX_FUEL_ECONOMY = 100;

    /**
     * Minimum value for reliability (0.0)
     */
    private static final double MIN_RELIABILITY = 0.0;
    /**
     * Maximum value for reliability (1.0)
     */
    private static final double MAX_RELIABILITY = 1.0;
    /**
     * Minimum value for fuel tank level (0.0)
     */
    private static final double MIN_FUEL_TANK = 0.0;
    /**
     * Maximum value for fuel tank level (1.0)
     */
    private static final double MAX_FUEL_TANK = 1.0;

    /**
     * List tracking all upgrades currently applied to the vehicle
     */
    private final List<Upgrade> appliedUpgrades = new ArrayList<>();

    /**
     * Gets a defensive copy of all upgrades currently applied to this vehicle.
     *
     * @return A new ArrayList containing all applied upgrades
     */
    public List<Upgrade> getAppliedUpgrades() {
        return new ArrayList<>(appliedUpgrades);
    }

    /**
     * Constructs a new default cart vehicle with specified stats.
     *
     * @param topSpeed     Maximum speed stat of the vehicle
     * @param acceleration Rate of speed increase stat
     * @param handling     Vehicle maneuverability stat
     * @param reliability  Vehicle durability stat between 0.0 and 1.0
     * @param fuelEconomy  Fuel usage efficiency stat
     * @param cost         Purchase price of the vehicle
     * @param name         Display name of the vehicle
     */
    public Vehicle(int topSpeed, int acceleration,
                   int handling, double reliability,
                   int fuelEconomy,
                   int cost, String name, String imgName) {
        this.type = "default_cart";
        this.topSpeed = topSpeed;
        this.acceleration = acceleration;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.fuelTank = 1.0;
        this.cost = cost;
        this.name = name;
        this.img = "/images/" + imgName + ".png";
    }

    /**
     * Constructs a new vehicle of specified type with custom stats.
     *
     * @param type         The vehicle type/category
     * @param topSpeed     Maximum speed stat in metres
     * @param acceleration Rate of speed increase in metres/second^2
     * @param handling     Vehicle maneuverability stat
     * @param reliability  Vehicle durability stat between 0.0 and 1.0
     * @param fuelEconomy  Fuel usage efficiency stat
     * @param cost         Purchase price of the vehicle
     * @param name         Display name of the vehicle
     */
    public Vehicle(String type, int topSpeed,
                   int acceleration, int handling,
                   double reliability, int fuelEconomy,
                   int cost, String name) {
        this.type = type;
        this.topSpeed = topSpeed;
        this.acceleration = acceleration;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.fuelTank = 1.0;
        this.cost = cost;
        this.name = name;
        this.img = "/images/" + type + ".png";
    }

    /**
     * Gets the vehicle type/category.
     *
     * @return The vehicle type string
     */
    public String getVehicleType() {
        return type;
    }

    /**
     * Gets the vehicle's maximum speed stat.
     *
     * @return Top speed value
     */
    public int getTopSpeed() {
        return topSpeed;
    }

    /**
     * Gets the vehicle's acceleration rate stat.
     *
     * @return Acceleration value
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the vehicle's acceleration rate.
     * Value is given in metres/second^2.
     *
     * @param acceleration The new acceleration value to set
     */
    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Gets the vehicle's handling/maneuverability stat.
     *
     * @return Handling value
     */
    public int getHandling() {
        return handling;
    }

    /**
     * Sets the vehicle's handling/maneuverability stat.
     *
     * @param handling The new handling value to set
     */
    public void setHandling(int handling) {
        this.handling = handling;
    }

    /**
     * Gets the vehicle's reliability/durability stat.
     *
     * @return Reliability value between 0.0 and 1.0
     */
    public double getReliability() {
        return reliability;
    }

    /**
     * Sets the vehicle's reliability/durability stat.
     *
     * @param reliability The new reliability value between 0.0 and 1.0
     */
    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    /**
     * Gets the vehicle's fuel economy/efficiency stat.
     *
     * @return Fuel economy value
     */
    public int getFuelEconomy() {
        return fuelEconomy;
    }

    /**
     * Gets the current fuel tank level as a percentage.
     *
     * @return Fuel tank level between 0.0 and 1.0
     */
    public double getFuelTank() {
        return fuelTank;
    }

    /**
     * Sets the fuel tank level as a percentage.
     *
     * @param fuelTank The new fuel level between 0.0 and 1.0
     */
    public void setFuelTank(double fuelTank) {
        this.fuelTank = fuelTank;
    }

    /**
     * Gets the vehicle's purchase price.
     *
     * @return Cost value in game currency
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the vehicle's display name.
     *
     * @return Vehicle name string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the filepath to this vehicle's image asset.
     *
     * @return Image filepath string
     */
    public String getVehicleImage() {
        return img;
    }

    /**
     * Applies an upgrade's effects to the vehicle's stats.
     * Adds the upgrade to the applied upgrades list and modifies vehicle stats
     * according to the upgrade's bonuses, ensuring values stay within valid ranges.
     *
     * @param upgrade The upgrade to apply
     */
    public void applyUpgradeEffects(Upgrade upgrade) {
        topSpeed = clamp(topSpeed + upgrade.getTopSpeed(), MIN_STAT, MAX_TOP_SPEED);
        acceleration = clamp(acceleration + upgrade.getAcceleration(), MIN_STAT, MAX_ACCELERATION);
        handling = clamp(handling + upgrade.getHandling(), MIN_STAT, MAX_HANDLING);
        reliability = clamp(reliability + upgrade.getReliability(), MIN_RELIABILITY, MAX_RELIABILITY);
        fuelTank = clamp(fuelTank + upgrade.getFuelTank(), MIN_FUEL_TANK, MAX_FUEL_TANK);
        fuelEconomy = clamp(fuelEconomy + upgrade.getFuelEconomy(), MIN_STAT, MAX_FUEL_ECONOMY);
        appliedUpgrades.add(upgrade);
    }

    /**
     * Removes an upgrade's effects from the vehicle's stats.
     * Removes the upgrade from applied upgrades and reverses stat modifications,
     * ensuring values stay within valid ranges.
     *
     * @param upgrade The upgrade to remove
     */
    public void removeUpgradeEffects(Upgrade upgrade) {
        topSpeed = clamp(topSpeed - upgrade.getTopSpeed(), MIN_STAT, MAX_TOP_SPEED);
        acceleration = clamp(acceleration - upgrade.getAcceleration(), MIN_STAT, MAX_ACCELERATION);
        handling = clamp(handling - upgrade.getHandling(), MIN_STAT, MAX_HANDLING);
        reliability = clamp(reliability - upgrade.getReliability(), MIN_RELIABILITY, MAX_RELIABILITY);
        fuelTank = clamp(fuelTank - upgrade.getFuelTank(), MIN_FUEL_TANK, MAX_FUEL_TANK);
        fuelEconomy = clamp(fuelEconomy - upgrade.getFuelEconomy(), MIN_STAT, MAX_FUEL_ECONOMY);
        appliedUpgrades.remove(upgrade);
    }

    /**
     * Ensures an integer value stays within specified bounds.
     *
     * @param value The value to clamp
     * @param min   The minimum allowed value
     * @param max   The maximum allowed value
     * @return The clamped value between min and max
     */
    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Ensures a double value stays within specified bounds.
     *
     * @param value The value to clamp
     * @param min   The minimum allowed value
     * @param max   The maximum allowed value
     * @return The clamped value between min and max
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}