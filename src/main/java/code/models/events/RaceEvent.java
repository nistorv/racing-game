package code.models.events;

import code.services.racing.RaceSim;

/**
 * Abstract class that acts as a base for race events in the game.
 * Sets up necessary functions for Race Events.
 *
 * <p>
 * This is the constructor for the abstract RaceEvent class. It initializes the base attributes for race events.
 * </p>
 */
public abstract class RaceEvent {

    /**
     * The title of the race event.
     */
    protected String title;

    /**
     * The description of the race event.
     */
    protected String description;

    /**
     * Represents the action that will be taken if the user uses the left choice button or has an
     * automatic decision timeout as a String.
     */
    protected String lButtonAction;

    /**
     * Represents the action that will be taken if the user uses the right choice button as a String.
     */
    protected String rButtonAction;

    /**
     * Handles the left button / automatic decision timeout action.
     *
     * @param race The Race Simulator instance.
     */
    public abstract void lChoice(RaceSim race);

    /**
     * Handles the right button action.
     *
     * @param race The Race Simulator instance.
     */
    public abstract void rChoice(RaceSim race);

    /**
     * Gets the event title.
     *
     * @return The event title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the event description.
     *
     * @return The event description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the String for the left choice button.
     *
     * @return The left button String.
     */
    public String getLeftChoice() {
        return lButtonAction;
    }

    /**
     * Gets the String for teh right choice button.
     *
     * @return The right button String.
     */
    public String getRightChoice() {
        return rButtonAction;
    }
}
