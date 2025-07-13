package code.models.race;

/**
 * Represents a track route.
 * Route Stats: Description (routeDesc), Difficulty (routeDifficulty),
 * Number of Fuel Stops (fuelStops), Route Distance (distance).
 * Routes are created in a Track Array along with their parent Tracks, and are
 * immutable.
 *
 * @see Track
 */
public class Route {
    private final String routeDesc;
    private final String routeDifficulty;
    private final int fuelStops;
    private final int distance;

    /**
     * Consturct a Route.
     *
     * @param description The description of the route.
     * @param difficulty The difficulty of the route (Easy, Medium or Hard),
     *                   based on the amount of distance between fuel stops.
     * @param fuelStops The number of fuel stops to be placed along a route
     *                  Calculated by dividing distance by the difficulty multiplier.
     * @param distance The length of the route in metres.
     */
    public Route(String description,
                 String difficulty, int fuelStops, int distance) {
        this.routeDesc = description;
        this.routeDifficulty = difficulty;
        this.fuelStops = fuelStops; // fuelStops = distance / (easy: 750, medium: 1000, hard: 1500)
        this.distance = distance; // distance in metres
    }

    /**
     * Gets the route description.
     *
     * @return The route description.
     */
    public String getRouteDesc() {
        return routeDesc;
    }

    /**
     * Gets the route difficulty.
     *
     * @return The route difficulty (Easy, Medium or Hard)
     */
    public String getRouteDifficulty() {
        return routeDifficulty;
    }

    /**
     * Gets the number of fuel stops along the route.
     *
     * @return The number of fuel stops along the route.
     */
    public int getFuelStops() {
        return fuelStops;
    }

    /**
     * Gets the total distance of the route.
     *
     * @return The route distance in metres.
     */
    public int getDistance() {
        return distance;
    }
}
