package code.models.events;

import code.services.racing.RaceSim;

/**
 * RaceEvent representing a fuel stop.
 * Offers the player a chance to refuel their cart, with a timeout.
 */
public class FuelStopEvent extends RaceEvent {
    /**
     * Constructs a new FuelStopEvent.
     */
    public FuelStopEvent() {
        this.title = "! Fuel Stop !";
        this.description = "A hole is up ahead! Would you like to stop for fuel?";
        this.lButtonAction = "Continue Racing";
        this.rButtonAction = "Stop for Fuel";
    }

    /**
     * Handles the left button + decision timeout.
     * Continue race without refueling. No change to RaceSim.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void lChoice(RaceSim race) {}

    /**
     * Handles the right button.
     * Starts a fuel stop.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void rChoice(RaceSim race) {
        race.startStoppingEvent("fuel");
    }
}
