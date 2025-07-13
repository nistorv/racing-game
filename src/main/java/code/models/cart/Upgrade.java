package code.models.cart;

/**
 * Represents a vehicle upgrade.
 * Provides statistics that can be added to a vehicle's stats at a cost.
 */
public class Upgrade {
    private final String name;
    private final int topSpeed;
    private final int acceleration;
    private final int handling;
    private final double reliability;
    private final int fuelEconomy;
    private final double fuelTank;
    private final int cost;

    /**
     * Constructs a new Upgrade.
     *
     * @param name Upgrade name.
     * @param topSpeed Speed increase (in metres).
     * @param acceleration Acceleration increase (in metres^2).
     * @param handling Handling.
     * @param reliability Reliability (0.0 to 1.0).
     * @param fuelEconomy Fuel economy improvement.
     * @param fuelTank Fuel capacity increase (0.0 to 1.0).
     * @param cost Upgrade cost price.
     */
    public Upgrade(String name, int topSpeed, int acceleration,
                   int handling, double reliability,
                   int fuelEconomy, double fuelTank,
                   int cost) {
        this.name = name;
        this.topSpeed = topSpeed;
        this.acceleration = acceleration;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.fuelTank = fuelTank;
        this.cost = cost;
    }

    /**
     * Gets the upgrade name.
     * @return Upgrade name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the top speed.
     *
     * @return Top speed stat.
     */
    public int getTopSpeed() {
        return topSpeed;
    }

    /**
     * Gets the acceleration.
     *
     * @return Acceleration stat.
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Gets the handling.
     *
     * @return Handling stat.
     */
    public int getHandling() {
        return handling;
    }

    /**
     * Gets the reliability.
     *
     * @return Reliability stat (Double between 0.0 and 1.0).
     */
    public double getReliability() {
        return reliability;
    }

    /**
     * Gets the fuel economy.
     *
     * @return Fuel economy stat.
     */
    public int getFuelEconomy() {
        return fuelEconomy;
    }

    /**
     * Gets the fuel tank level as a percentage.
     *
     * @return Fuel tank level (Double between 0.0 and 1.0).
     */
    public double getFuelTank() {
        return fuelTank;
    }

    /**
     * Gets upgrade cost.
     *
     * @return Upgrade cost.
     */
    public int getCost() {
        return cost;
    }
}