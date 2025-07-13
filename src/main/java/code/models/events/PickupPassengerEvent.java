package code.models.events;

import code.services.racing.RaceSim;

/**
 * RaceEvent for picking up a passenger along your race.
 * Player may choose to stop to pickup a passenger for a reward or continue racing.
 */
public class PickupPassengerEvent extends RaceEvent {
    /**
     * Constructs a new PickupPassengerEvent.
     */
    public PickupPassengerEvent() {
        this.title = "! Pickup Passenger !";
        this.description = "A stray golfer is stranded. Would you like to pick up a passenger?";
        this.lButtonAction = "Continue Racing";
        this.rButtonAction = "Pickup Stranded Passenger";
    }

    /**
     * Handles the left button + decision timeout.
     * Continue race without picking up passenger. No change to RaceSim.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void lChoice(RaceSim race) {}

    /**
     * Handles the right button.
     * Stops to pickup a passenger to earn money.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void rChoice(RaceSim race) {
        race.startStoppingEvent("passenger");
    }
}
