package code.models.events;

import code.main.GameManager;
import code.services.racing.RaceSim;

/**
 * RaceEvent for a vehicle breakdown.
 * Offers the player a chance to repair their cart for a price or forfeit the race.
 */
public class BreakdownEvent extends RaceEvent {
    final GameManager gameManager;

    /**
     * Constructs a new BreakdownEvent.
     *
     * @param gameManager The game manager. Used for accessing wallet.
     */
    public BreakdownEvent(GameManager gameManager) {
        this.gameManager = gameManager;
        this.title = "! Minor Vehicle Breakdown !";
        this.description = "Your vehicle is breaking down!";
        this.lButtonAction = "Forfeit Race";
        this.rButtonAction = "On-site Repair ($500)";
    }

    /**
     * Handles the left button + decision timeout.
     * Forces vehicle breakdown.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void lChoice(RaceSim race) {
        race.breakdown();
    }

    /**
     * Handles the right button.
     * Subtracts $500 from the player if they have the necessary money, otherwise forces a vehicle breakdown.
     *
     * @param race The Race Simulator instance.
     */
    @Override
    public void rChoice(RaceSim race) {
        if (gameManager.getPlayer().subMoney(500)) {
            race.startStoppingEvent("breakdown");
        } else {
            lChoice(race);
        }
    }
}
