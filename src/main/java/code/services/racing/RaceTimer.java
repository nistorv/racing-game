package code.services.racing;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer service for race countdown.
 */
public class RaceTimer {
    private Timer timer;
    private long startTime;
    private boolean isRunning;
    private long raceDuration;
    private RaceTimerListener listener;

    /**
     * Constructs a new RaceTimer
     *
     * @param raceDuration Race Duration in minutes.
     * @param listener Listener for timer updates.
     */
    public RaceTimer(long raceDuration, RaceTimerListener listener) {
        this.raceDuration = raceDuration * 60000;
        this.listener = listener;
    }

    /**
     * Start the race timer.
     */
    public void start() {
        if (isRunning) return;
        isRunning = true;
        startTime = System.currentTimeMillis();
        timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        }, 0, 100);
    }

    /**
     * Updates the timer and sends listener
     */
    private void updateTime() {
        long elapsed = System.currentTimeMillis() - startTime;
        long remaining = raceDuration - elapsed;

        if (remaining <= 0) {
            stop();
            listener.TimeEnd();
            return;
        }

        long totalSec = remaining / 1000;
        long mins = totalSec / 60;
        long secs = totalSec % 60;

        listener.TimeUpdate(String.format("%02d:%02d", mins, secs));
    }

    /**
     * Stops the race timer.
     */
    public void stop() {
        isRunning = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
