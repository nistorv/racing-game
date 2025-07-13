package code.services.vehicle;

import javafx.scene.control.Button;
import code.models.Player;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;
import code.main.GameManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service class representing the garage, managing vehicles and upgrades.
 */
public class GarageService {

    private final Player player;
    private final OwnedVehiclesManager vehicleManager;

    /**
     * Constructs a new GarageService.
     *
     * @param gameManager The game manager.
     */
    public GarageService(GameManager gameManager) {
        this.player = gameManager.getPlayer();
        this.vehicleManager = OwnedVehiclesManager.getInstance();
        refreshOwnedVehicles();
    }

    /**
     * Refreshes the list of owned vehicles in the vehicle manager.
     */
    public void refreshOwnedVehicles() {
        List<Vehicle> ownedVehicles = new ArrayList<>(player.getOwnedVehicles());
        vehicleManager.setOwnedVehicles(ownedVehicles);
    }

    /**
     * Gets the player's selected vehicle.
     *
     * @return The selected vehicle.
     */
    public Vehicle getCurrentVehicle() {
        return player.getCurrentVehicle();
    }

    /**
     * Selects the next vehicle in the rotation of owned vehicles.
     *
     * @return The next vehicle in the list.
     */
    public Vehicle nextVehicle() {
        return vehicleManager.next();
    }

    /**
     * Selects the previous vehicle in the rotation of owned vehicles.
     *
     * @return The previous vehicle in the list.
     */
    public Vehicle previousVehicle() {
        return vehicleManager.previous();
    }

    /**
     * Retrieves a list of all vehicles owned by the player.
     *
     * @return A list of owned vehicles.
     */
    public List<Vehicle> getOwnedVehicles() {
        return vehicleManager.getOwnedVehicles();
    }

    /**
     * Retrieves a list of all upgrades owned by the player.
     *
     * @return An unmodifiable list of owned upgrades.
     */
    public List<Upgrade> getOwnedUpgrades() {
        return Collections.unmodifiableList(player.getOwnedUpgrades());
    }

    /**
     * Finds an owned upgrade by its name.
     *
     * @param upgradeName The name of the upgrade to find.
     * @return An Optional containing the found upgrade if it exists, or empty if not.
     */
    public Optional<Upgrade> findOwnedUpgradeByName(String upgradeName) {
        return getOwnedUpgrades().stream()
                .filter(upgrade -> upgrade.getName().equalsIgnoreCase(upgradeName))
                .findFirst();
    }

    /**
     * Checks if the player owns an upgrade by name.
     *
     * @param upgradeName The name of the upgrade to check.
     * @return true if the player owns the upgrade, false otherwise.
     */
    public boolean ownsUpgrade(String upgradeName) {
        return findOwnedUpgradeByName(upgradeName).isPresent();
    }

    /**
     * Toggles an upgrade between equipped and unequipped states for all vehicles.
     *
     * @param upgradeName The name of the upgrade to toggle.
     * @param button      The button object indicating the current state ("Equip" or "Unequip").
     */
    public void toggleUpgrade(String upgradeName, Button button) {
        if ("Equip".equals(button.getText())) {
            applyUpgradeToAllVehicles(upgradeName);
            button.setText("Unequip");
        } else {
            removeUpgradeToAllVehicles(upgradeName);
            button.setText("Equip");
        }
        refreshOwnedVehicles();
    }

    /**
     * Equips an upgrade to all vehicles owned by the player.
     *
     * @param upgradeName The name of the upgrade to equip.
     */
    private void applyUpgradeToAllVehicles(String upgradeName) {
        Upgrade upgrade = UpgradeStats.getUpgrade(upgradeName).orElse(null);
        if (upgrade == null || !player.ownsUpgrade(upgrade)) return;

        for (Vehicle vehicle : player.getOwnedVehicles()) {
            vehicle.applyUpgradeEffects(upgrade);

            if (!vehicle.getAppliedUpgrades().contains(upgrade)) {
                vehicle.getAppliedUpgrades().add(upgrade);
            }
        }
    }

    /**
     * Removes an upgrade from all vehicles owned by the player.
     *
     * @param upgradeName The name of the upgrade to remove.
     */
    private void removeUpgradeToAllVehicles(String upgradeName) {
        Upgrade upgrade = UpgradeStats.getUpgrade(upgradeName).orElse(null);
        if (upgrade == null || !player.ownsUpgrade(upgrade)) return;

        for (Vehicle vehicle : player.getOwnedVehicles()) {
            vehicle.removeUpgradeEffects(upgrade);

            if (vehicle.getAppliedUpgrades().contains(upgrade)) {
                vehicle.getAppliedUpgrades().remove(upgrade);
            }
        }
    }


}