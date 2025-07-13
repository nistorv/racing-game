package code.services.vehicle;

import code.models.cart.Vehicle;

import java.util.Collections;
import java.util.List;

/**
 * Manages the vehicles owned by the player.
 * Has functionality to set, get and cycle through owned vehicles.
 */
public class OwnedVehiclesManager {
    private static OwnedVehiclesManager instance;
    private Vehicle currentVehicle = null;

    private List<Vehicle> ownedVehicles = Collections.emptyList();
    private int currentIndex = 0;

    /**
     * Constructs a new OwnedVehiclesManager.
     */
    private OwnedVehiclesManager() {
    }

    /**
     * Retrieves a new OwnedVehicleManager instance.
     *
     * @return New OwnedVehiclesManager instance.
     */
    public static OwnedVehiclesManager getInstance() {
        if (instance == null) {
            instance = new OwnedVehiclesManager();
        }
        return instance;
    }

    /**
     * Sets owned vehicles.
     *
     * @param vehicles List of owned vehicles. If null, an empty list will be set.
     */
    public void setOwnedVehicles(List<Vehicle> vehicles) {
        if (vehicles == null) {
            this.ownedVehicles = Collections.emptyList();
        } else {
            this.ownedVehicles = vehicles;
        }
        this.currentIndex = 0;
    }

    /**
     * Retrieves owned vehicles.
     *
     * @return An unmodifiable list of owned vehicles.
     */
    public List<Vehicle> getOwnedVehicles() {
        return Collections.unmodifiableList(ownedVehicles);
    }

    /**
     * Gets the selected vehicle.
     *
     * @return The selected vehicle, or null if no vehicles are owned.
     */
    public Vehicle getCurrent() {
        if (ownedVehicles.isEmpty()) {
            return null;
        }
        return currentVehicle;
    }

    /**
     * Moves to the next vehicle in the list, returning back to the start if at the end.
     *
     * @return The next vehicle, or null if no vehicles are owned.
     */
    public Vehicle next() {
        if (ownedVehicles.isEmpty()) return null;
        currentIndex = (currentIndex + 1) % ownedVehicles.size();
        return ownedVehicles.get(currentIndex);
    }

    /**
     * Moves to the previous vehicle in the list, returning to the end if at the start.
     *
     * @return The previous vehicle, or null if no vehicles are owned.
     */
    public Vehicle previous() {
        if (ownedVehicles.isEmpty()) return null;
        currentIndex = (currentIndex - 1 + ownedVehicles.size()) % ownedVehicles.size();
        return ownedVehicles.get(currentIndex);
    }
}