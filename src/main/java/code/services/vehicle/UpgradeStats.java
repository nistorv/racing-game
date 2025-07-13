package code.services.vehicle;

import code.models.cart.Upgrade;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Creates, stores and provides access to all upgrades in the game.
 *
 * <p>
 * Constructor for initialising, accessing and managing vehicle upgrades.
 * </p>
 */
public class UpgradeStats {
    private static final List<Upgrade> ALL_UPGRADES = createAllUpgrades();

    /**
     * Creates all upgrades in the game.
     *
     * @return ArrayList containing all created upgrade objects.
     */
    private static List<Upgrade> createAllUpgrades() {
        List<Upgrade> upgrades = new ArrayList<>();

        // Handling Upgrades
        upgrades.add(new Upgrade("Racing Steering Wheel", 3, 0, 3, 0, 0, 0, 100));
        upgrades.add(new Upgrade("Performance Suspension Kit", 1, 0, 8, 0.05, 0, 0, 400));
        upgrades.add(new Upgrade("Performance Tires", 2, 0, 10, 0.20, 2, 0, 800));

        // Engine Upgrades
        upgrades.add(new Upgrade("Air Filter", 3, 4, 1, 0.03, 0, 0, 100));
        upgrades.add(new Upgrade("High-Compression Pistons", 8, 5, 0, -0.03, 0, 0, 400));
        upgrades.add(new Upgrade("Gold Exhaust", 10, 3, 0, 0.10, 0, 0, 800));

        // Fuel Tank Extensions
        upgrades.add(new Upgrade("2L Fuel Tank", 0, 0, 0, 0, 2, 0, 100));
        upgrades.add(new Upgrade("5L Fuel Tank", 0, 0, 0, 0, 5, 0, 400));
        upgrades.add(new Upgrade("10L Fuel Tank", 0, 0, 0, 0, 10, 0, 800));

        return upgrades;
    }

    /**
     * Returns a queried upgrade.
     *
     * @param upgradeName Name of queried upgrade.
     * @return Queried upgrade object.
     */
    public static Optional<Upgrade> getUpgrade(String upgradeName) {
        return ALL_UPGRADES.stream()
                .filter(upgrade -> upgrade.getName().equals(upgradeName))
                .findFirst();
    }
}
