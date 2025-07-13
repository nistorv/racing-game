package code.unittests.models.race;

import org.junit.jupiter.api.Test;
import code.models.race.Bot;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    @Test
    void botRunningIncreasingRaceProgress() {
        double topSpeed = 100.0;
        double elapsed = 2.0;
        double distance = 1000.0;
        Bot bot = new Bot(topSpeed);

        bot.updateProgress(elapsed, distance);

        assertTrue(bot.getProgress() > 0.0);
        assertTrue(bot.getProgress() <= 1.0);
    }

    @Test
    void raceProgressMax1() {
        double topSpeed = 1000.0;
        double elapsed = 10.0;
        double distance = 1000.0;
        Bot bot = new Bot(topSpeed);

        bot.updateProgress(elapsed, distance);

        assertTrue(bot.getProgress() <= 1.0);
        assertFalse(bot.isBotRunning());
    }

    @Test
    void ifBotBrokenProgressHalted() {
        double topSpeed = 100.0;
        double elapsed = 2.0;
        double distance = 1000.0;
        Bot bot = new Bot(topSpeed);
        bot.stopBot();

        bot.updateProgress(elapsed, distance);

        assertEquals(0.0, bot.getProgress());
    }

    @Test
    void botEventSpeedReduction() {
        double topSpeed = 100.0;
        double elapsed = 2.0;
        double distance = 1000.0;
        Bot bot = new Bot(topSpeed);

        for (int i = 0; i < 1000; i++) {
            bot.updateProgress(elapsed, distance);
        }

        assertTrue(bot.getProgress() <= 1.0);
    }
}