package code.services.racing;

import code.main.GameManager;
import code.models.Player;
import code.models.events.RaceEvent;
import code.models.race.Bot;
import code.models.race.Route;
import code.models.race.Track;
import code.models.cart.Vehicle;
import code.services.vehicle.GarageService;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Base Race Simulation class.
 * Manages race progress, event, vehicle physics, bot AI, race state management and race outcome.
 */
public class RaceSim {
    private final GameManager gameManager;
    private final Player player;
    private final int gameDifficulty;
    private final Vehicle vehicle;
    private final Route route;
    private final RaceEventListener listener;
    private final RaceEventService eventService;
    private final ArrayList<Bot> bots;
    private final Track track;

    private boolean raceRunning;
    private int racePlacement;

    private boolean atStop = false;
    private long eventStartTime;
    private String eventID;
    private RaceEvent currentEvent;
    private long decisionStartTime;
    private boolean awaitDecision = false;
    private double decisionSpeed;

    private boolean slowedDown;
    private long slowDownTime;

    /**
     * Constructs a new Race Simulation.
     *
     * @param track The racetrack.
     * @param route The route on the racetrack.
     * @param listener The event listener for race updates to UI.
     * @param gameManager The game manager.
     */
    public RaceSim(Track track, Route route, RaceEventListener listener, GameManager gameManager) {
        // Base Game stuff
        this.gameManager = gameManager;
        this.player = gameManager.getPlayer();
        this.gameDifficulty = player.getDifficulty();

        // User selectables
        this.track = track;
        this.route = route;
        GarageService garageService = new GarageService(gameManager);
        garageService.refreshOwnedVehicles();
        this.vehicle = garageService.getCurrentVehicle();

        // Required Listeners and Services
        this.listener = listener;
        this.eventService = new RaceEventService(gameManager, track, route);

        // Bots
        this.bots = new ArrayList<>();
        for (int i = 0; i < (track.getEntries() - 1); i++) {
            bots.add(new Bot(vehicle.getTopSpeed()));
        }
    }

    /**
     * Starts the race simulation in a new daemon thread.
     */
    public void startRace() {
        raceRunning = true;
        Thread raceThread = new Thread(this::race);
        raceThread.setDaemon(true);
        raceThread.start();
    }

    /**
     * Ends the race with supplied reason.
     *
     * @param reason Description of why the race ended.
     */
    public void endRace(String reason) {
        raceRunning = false;
        int finalPlacement = racePlacement;
        stopBots();
        gameManager.onRaceEnd();
        listener.racePlacement(finalPlacement);
        listener.raceTerminate(reason);
    }

    /**
     * Prompts a decision for the UI timing how long the decision is.
     */
    private void startDecision() {
        awaitDecision = true;
        decisionStartTime = System.currentTimeMillis();
        listener.promptEvent(currentEvent);
    }

    /**
     * Handles player decision for the current event.
     *
     * @param accepted The players decision.
     */
    public void handleDecision(boolean accepted) {
        awaitDecision = false;
        if (accepted) {
            currentEvent.rChoice(this);
        } else {
            currentEvent.lChoice(this);
        }
        currentEvent = null;
    }

    /**
     * Starts an event that results in a full speed stop.
     * Used for: Fuel Stops, Passenger Pickups and Vehicle Breakdowns.
     *
     * @param id The event ID.
     */
    public void startStoppingEvent(String id) {
        atStop = true;
        eventID = id;
        eventStartTime = System.currentTimeMillis();
    }

    /**
     * Applies random damage risk to vehicle reliability.
     */
    public void damageRisk() {
        double reliability = Math.min(vehicle.getReliability(), Math.random());
        vehicle.setReliability(reliability);
        listener.raceReliability(reliability);
    }

    /**
     * Slows down the vehicle for a duration.
     * Slowdown duration based on game difficulty.
     */
    public void slowDown() {
        slowedDown = true;
        slowDownTime = System.currentTimeMillis() + (5000L * gameDifficulty);
    }

    /**
     * Causes the vehicle to breakdown.
     * Sets the vehicle reliablity 0.
     */
    public void breakdown() {
        vehicle.setReliability(0);
    }

    /**
     * Ends the current full speed stop event.
     */
    private void endStop() {
        eventID = "";
        atStop = false;
    }

    /**
     * Ends passenger pickup event and awards prize.
     */
    private void endPassenger() {
        endStop();
        player.addMoney(2000 / gameDifficulty);
    }

    /**
     * Ends fuel stop event and refills tank.
     */
    private void endFuelStop() {
        endStop();
        vehicle.setFuelTank(1.0);
        listener.raceFuel(1.0);
    }

    /**
     * Updates vehicle degradation and fuel consumption.
     *
     * @param elapsed Time elapsed last update.
     * @param currentSpeed Vehicle speed.
     */
    private void vehicleDegradation(double elapsed, double currentSpeed) {
        double fuelConsumed = (currentSpeed * elapsed) / (vehicle.getFuelEconomy() * 75);
        double fuel = Math.max(0, vehicle.getFuelTank() - fuelConsumed);
        vehicle.setFuelTank(fuel);
        listener.raceFuel(fuel);

        if (((Math.random() / gameDifficulty) < 0.025) && !atStop) {
            double reliability = Math.max(0, vehicle.getReliability() - 0.005);
            vehicle.setReliability(reliability);
            listener.raceReliability(reliability);
        }

        if (vehicle.getFuelTank() <= 0) {
            vehicle.setFuelTank(0);
            listener.raceFuel(0);
            endRace("Your vehicle ran out of fuel!");
        }

        if (vehicle.getReliability() <= 0) {
            vehicle.setReliability(0);
            listener.raceReliability(0);
            endRace("Your vehicle has broken down!");
        }
    }

    /**
     * Update's player's placement position based on progress
     * compared to progress against bots.
     *
     * @param progress Race progress (between 0.0 and 1.0).
     */
    private void updatePlacement(double progress) {
        int passedBots = 0;
        for (Bot bot: bots) {
            if (bot.getProgress() >= progress && bot.isBotRunning()) {
                passedBots++;
            }
        }
        racePlacement = passedBots + 1;

        listener.racePlacement(racePlacement);
    }

    /**
     * Stops all bot racers.
     */
    private void stopBots() {
        for (Bot bot: bots) {
            bot.stopBot();
        }
    }

    /**
     * Race Simulation loop.
     * Handles physics, events, and race state.
     */
    private void race() {
        double passedDistance = 0;
        double distance = route.getDistance();
        double topSpeed = vehicle.getTopSpeed();
        double acceleration = vehicle.getAcceleration();
        double handling = vehicle.getHandling();
        double speed = 0;
        eventID = "";

        // Acceleration Math
        double accelToTop = topSpeed / (acceleration * handling);
        double accelDistance = 0.5 * acceleration * accelToTop * accelToTop;

        long prevTime = System.currentTimeMillis();
        while (raceRunning && passedDistance < distance) {
            // Iteration using Time (independent from CPU clock)
            long time = System.currentTimeMillis();
            double elapsed = (time - prevTime) / 1000.0;
            prevTime = time;

            for (Bot bot : bots) {
                bot.updateProgress(elapsed, distance);
            }

            if ((awaitDecision) && (time - decisionStartTime >= (5000 / gameDifficulty))) {
                listener.clearEvent();
                handleDecision(false);
            }

            double currentSpeed;
            if (awaitDecision) {
                currentSpeed = decisionSpeed;
            } else if (atStop) {
                currentSpeed = 0;
                if ((eventID.equals("fuel")) && (time - eventStartTime >= (3500L * gameDifficulty))) {
                    endFuelStop();
                } else if (eventID.equals("passenger") && (time - eventStartTime >= (2500L * gameDifficulty))) {
                    endPassenger();
                } else if (eventID.equals("breakdown") && (time - eventStartTime >= (5000L * gameDifficulty))) {
                    vehicle.setReliability(vehicle.getReliability() + 0.05);
                    endStop();
                }
            } else if (passedDistance < accelDistance) {
                currentSpeed = Math.min(topSpeed, (speed + accelDistance * elapsed));
            } else {
                currentSpeed = topSpeed;
            }

            if (slowedDown) {
                if (System.currentTimeMillis() >= slowDownTime) {
                    slowedDown = false;
                } else {
                    currentSpeed = Math.min(currentSpeed, topSpeed / 2);
                }
            }

            vehicleDegradation(elapsed, currentSpeed);

            if (!atStop) {
                passedDistance += currentSpeed * elapsed;
                passedDistance = Math.min(passedDistance, distance);
                listener.raceProgress(passedDistance / distance);
            }

            if (!atStop && !awaitDecision) {
                Optional<RaceEvent> event = eventService.checkForEvent(passedDistance, eventID);
                if (event.isPresent()) {
                    currentEvent = event.get();
                    decisionSpeed = currentSpeed;
                    startDecision();
                }
            }

            if (Math.random() < 0.0001) {
                endRace("Adverse Weather has moved in!\nAll racers must stop.");
            }

            updatePlacement(passedDistance / distance);

            // Cart Speed
            if (passedDistance < accelDistance) {
                speed = Math.min(topSpeed, speed + acceleration * elapsed);
            } else {
                speed = topSpeed;
            }

            if (passedDistance >= distance) {
                int prizeMoney = ((track.getPrizeReward() / (racePlacement)) / gameDifficulty)
                        * (gameManager.getPlayer().getRacesPlayed() + 1);
                player.addMoney(prizeMoney);
                endRace("You completed the race!\nPrize: $" + prizeMoney + ".");
            }

            // CPU Overload Prevention
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
