package code.services.racing;

import code.models.events.RaceEvent;

/**
 * Interface for handling interactions between the Race Simulator and the Race GUI Controller.
 * Implementations used for updating UI elements and handling changes.
 */
public interface RaceEventListener {
    /**
     * Called when the race progress is updated.
     *
     * @param progress Race progress (Double between 0.0 and 1.0)
     */
    void raceProgress(double progress);

    /**
     * Called when the vehicle's fuel level changes.
     *
     * @param fuel Vehicle fuel level (Double between 0.0 and 1.0)
     */
    void raceFuel(double fuel);

    /**
     * Called when the vehicles reliability chanes.
     *
     * @param reliability Vehicle reliability (Double between 0.0 and 1.0)
     */
    void raceReliability(double reliability);

    /**
     * Called when race ends.
     *
     * @param reason Description of why the race ended.
     */
    void raceTerminate(String reason);

    /**
     * Called when an event occurs that needs player input.
     *
     * @param event RaceEvent that needs player input.
     */
    void promptEvent(RaceEvent event);

    /**
     * Called to clear event from GUI.
     */
    void clearEvent();

    /**
     * Called when the player's placement ranking changes.
     *
     * @param placement Race position.
     */
    void racePlacement(int placement);
}
