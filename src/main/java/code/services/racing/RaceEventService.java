package code.services.racing;

import code.main.GameManager;
import code.models.events.*;
import code.models.race.Route;
import code.models.race.Track;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Service managing race events.
 * Handles fuel stops based on race progress and random events.
 */
public class RaceEventService {
    private Random random = new Random();
    private Track track;
    private Route route;
    private ArrayList<RaceEvent> randomEvents = new ArrayList<>();

    private int nextFuelStop = 1;
    private int fuelStopInterval;
    private boolean fuelStopStarted = false;

    /**
     * Constructs a RaceEventService.
     *
     * @param gameManager The game manager.
     * @param track The race track.
     * @param route The race route
     */
    public RaceEventService(GameManager gameManager, Track track, Route route) {
        this.track = track;
        this.route = route;
        this.fuelStopInterval = route.getDistance() / (route.getFuelStops() + 1);
        randomEvents.add(new PickupPassengerEvent());
        randomEvents.add(new BumpEvent());
        randomEvents.add(new BreakdownEvent(gameManager));
    }

    /**
     * Checks if an event should trigger.
     *
     * @param currentDistance Distance travelled in the race.
     * @param currentEvent ID of active event.
     * @return Optional containing a RaceEvent if an event is triggered, empty otherwise.
     */
    public Optional<RaceEvent> checkForEvent(double currentDistance, String currentEvent) {
        if (!currentEvent.isEmpty()) {
            return Optional.empty();
        }
        if (nextFuelStop <= route.getFuelStops()) {
            double nextStop = fuelStopInterval * nextFuelStop;

            if (currentDistance >= nextStop && !fuelStopStarted) {
                fuelStopStarted = true;
                nextFuelStop++;
                return Optional.of(new FuelStopEvent());
            }

            if (currentDistance < nextStop) {
                fuelStopStarted = false;
            }
        }

        if (Math.random() < 0.001) {
            return Optional.of(randomEvents.get(random.nextInt(randomEvents.size())));
        }

        return Optional.empty();
    }
}
