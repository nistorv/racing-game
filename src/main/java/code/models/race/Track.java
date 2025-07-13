package code.models.race;

import java.util.ArrayList;

/**
 * Represents a racing track.
 * Tracks contain: The track's name (name), its race duration (duration), a list of routes (routes),
 * the race's entries (entries), the base prize money (prize), the entry cost (entryCost) and a
 * description of the track (description).
 */
public class Track {
    private String name;
    private int duration;
    private ArrayList<Route> routes = new ArrayList<>();
    private int entries;
    private int prize;
    private int entryCost;
    private String description;


    /**
     * Constructs the Track Object
     *
     * @param name The name of the track.
     * @param description A description of the track.
     * @param duration The time limit for races in minutes.
     * @param entries The number of racers in a race on this track.
     * @param prize The base reward for a race on this track.
     * @param entryCost The cost required to enter a race on this track.
     * */
    public Track(String name, String description,
                 int duration, int entries,
                 int prize, int entryCost) {
        this.name = name;
        this.duration = duration;
        this.entries = entries;
        this.prize = prize;
        this.description = description;
        this.entryCost = entryCost;
    }

    /**
     * Gets the name of the track.
     *
     * @return The track's name.
     */
    public String getTrackName() {
        return name;
    }

    /**
     * Gets the duration (time limit) for races on this track.
     *
     * @return The duration in minutes.
     */
    public int getRaceDuration() {
        return duration;
    }

    /**
     * Gets all routes available on this track.
     *
     * @return An ArrayList containing the route objects in this track.
     */
    public ArrayList<Route> getRoutes() {
        return routes;
    }

    /**
     * Gets the number of racers in a race on this track.
     *
     * @return The number of racers.
     */
    public int getEntries() {
        return entries;
    }

    /**
     * Returns the base prize money for completing a race on this track.
     *
     * @return The prize amount.
     */
    public int getPrizeReward() {
        return prize;
    }

    /**
     * Adds a route object to this track.
     *
     * @param route The route object to add to routes ArrayList.
     */
    public void addRoutes(Route route) {
        routes.add(route);
    }

    /**
     * Gets the description for this track.
     *
     * @return The track description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the cost required to enter a race on this track.
     *
     * @return The entry cost.
     */
    public int getEntryCost() {
        return entryCost;
    }
}
