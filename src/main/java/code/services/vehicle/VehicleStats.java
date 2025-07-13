package code.services.vehicle;

import code.models.cart.Vehicle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Creates, stores and provides access to all vehicles in the game.
 *
 * <p>
 * Constructor for initialising, accessing and managing vehicles.
 * </p>
 */
public class VehicleStats {
    private static final List<Vehicle> ALL_VEHICLES = new ArrayList<>();
    private static final List<Vehicle> ALL_DEFAULT_CARTS = new ArrayList<>();

    static {
        ALL_VEHICLES.add(new Vehicle(
               38, 33, 30, 1, 44, 2000, "Blue Cart", "default_cart"
        ));

        ALL_VEHICLES.add(new Vehicle(
                42, 36, 32, 1, 34, 2000, "Green Cart", "default_cart2"
        ));

        ALL_VEHICLES.add(new Vehicle(
                34, 45, 31, 1, 33, 2000, "Purple Cart", "default_cart3"
        ));

        ALL_VEHICLES.add(new Vehicle(
                "scooter_cart", 55, 65, 65, 0.6, 40, 6000, "Scooter Cart"
        ));

        ALL_VEHICLES.add(new Vehicle(
                "sports_cart", 80, 75, 70, 0.65, 30, 10000, "Sport Cart"
        ));

        ALL_VEHICLES.add(new Vehicle(
                "hover_cart", 70, 80, 75, 0.7, 35, 14000, "Hover Cart"
        ));

        ALL_VEHICLES.add(new Vehicle(
                "segway", 78, 88, 85, 0.8, 45, 18000, "Ultra Segway"
        ));

        for (Vehicle vehicle : ALL_VEHICLES) {
            if (Objects.equals(vehicle.getVehicleType(), "default_cart")) {
                ALL_DEFAULT_CARTS.add(vehicle);
            }
        }
    }

    /**
     * Gets a list of all the vehicles in the game.
     *
     * @return List containing all Vehicle objects.
     */
    public static List<Vehicle> getAllVehicles() {
        return new ArrayList<>(ALL_VEHICLES); // Return copy
    }

    /**
     * Gets a list of all the default vehicles in the game.
     *
     * @return List containing all Vehicle objects.
     */
    public static List<Vehicle> getAllDefaultCarts() {
        return new ArrayList<>(ALL_DEFAULT_CARTS); // Return copy
    }
}