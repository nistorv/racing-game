package code.services.racing;

/**
 * Listener interface for race timer updates.
 */
public interface RaceTimerListener {
    /**
     * Called when timer updates time.
     *
     * @param timeString Formatted time string. (eg: 5 minutes and 10 seconds as 05:10)
     */
    void TimeUpdate(String timeString);

    /**
     * Called when race time expires.
     */
    void TimeEnd();
}
