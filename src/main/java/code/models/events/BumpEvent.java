package code.models.events;

import code.services.racing.RaceSim;

/**
 * RaceEvent for a Speed Bump.
 * Offers the player the choice between keeping their speed with a damage risk to their vehicle, or slowing down to half speed.
 */
public class BumpEvent extends RaceEvent {

    /**
     * Constructs a new BumpEvent.
     */
    public BumpEvent() {
        this.title = "! Speed Bump Ahead !";
        this.description = "A speed bump is ahead.";
        this.lButtonAction = "Continue at current speed";
        this.rButtonAction = "Slow down";
    }

    /**
     * Handles the left button + decision timeout.
     * Risks vehicle damage, continuing at their current speed.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void lChoice(RaceSim race) {
        race.damageRisk();
    }

    /**
     * Handles the right button.
     * Reduces speed to 50%, no risk of vehicle damage.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void rChoice(RaceSim race) {
        race.slowDown();
    }
}
