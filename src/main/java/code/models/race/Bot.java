package code.models.race;

/**
 * Represents bot in a race.
 * Racing Stats: Top Speed (topSpeed), Reliability (reliability), Race Progress (progress).
 * Random Bot Events (different from regular Race Events) occur that affects the bots stats.
 */
public class Bot {
    private double topSpeed;
    private double reliability;
    private double progress;
    private boolean botRunning = true;

    /**
     * Constructs a bot.
     * Bot's top speed based on the player's vehicle top speed minus 5% or more.
     * Bot's reliability starts at 1.0 (100%).
     * Bot's race progress starts at 0.0.
     *
     * @param playerTopSpeed Player's vehicle top speed, used to calculate the Bot's top speed.
     */
    public Bot(double playerTopSpeed) {
        this.topSpeed = playerTopSpeed * Math.min(0.95, Math.random());
        this.reliability = 1.0;
        this.progress = 0.0;
    }

    /**
     * Gets the progress of the bot in the race.
     * Progress is a value between 0.0 (start) and 1.0 (finish)
     *
     * @return The bot's current progress in the race as a double.
     */
    public double getProgress() {
        return progress;
    }

    /**
     * Checks to see if the bot is still racing.
     *
     * @return true if bot is still in the race, false if it has broken down or finished.
     */
    public boolean isBotRunning() {
        return botRunning;
    }

    /**
     * Update's the bot's progress in the race based on the elapsed time and route distance.
     * During a bot update, there is a 1% of a bot event occuring.
     *
     * @param elapsed The time elapsed since the last update. Used for bot distance calculation
     * @param distance The route distance. Used for bot distance calculation.
     */
    public void updateProgress(double elapsed, double distance) {
        if (!isBotRunning()) { return; }
        double passedDistance = topSpeed * elapsed;
        progress += passedDistance / distance;
        progress = Math.min(progress, 1.0);

        if (passedDistance >= distance) {
            stopBot();
        }

        if (Math.random() < 0.01) {
            botEvent();
        }
    }

    /**
     * Handles a bot event occurring that affects the bot's stats.
     * 30% chance for a 5% top speed reduction.
     * 5% chance for a 10% top speed boost.
     * 25% chance for a 10% reliability decrease.
     * 40% chance for no event occuring.
     * Only one event may happen per call.
     * The bot will break down when reliability reaches 0.0.
     */
    public void botEvent() {
        double randdbl = Math.random();
        if (randdbl < 0.3) {
            topSpeed *= 0.95;
        } else if (randdbl < 0.35) {
            topSpeed *= 1.1;
        } else if (randdbl < 0.6) {
            reliability -= 0.1;
            if (reliability <= 0.0) {
                reliability = 0.0;
                stopBot();
            }
        }
    }

    /**
     * Stops the bot from running in the race.
     * This is called when a bot breaks down, or the race finishes.
     */
    public void stopBot() {
        botRunning = false;
    }
}
