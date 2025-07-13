package code.unittests.models.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {
    @Test
    public void applyValidUpgradeEffectToVehicle() {
        Vehicle vehicle = new Vehicle(50, 60, 70, 0.8, 40, 10000, "Car", "car_image");
        Upgrade upgrade = new Upgrade("Upgrade", 10, 5, 0, 0.05, 0, 0.1, 500);

        vehicle.applyUpgradeEffects(upgrade);

        assertEquals(60, vehicle.getTopSpeed());
        assertEquals(65, vehicle.getAcceleration());
        assertEquals(70, vehicle.getHandling());
        assertEquals(0.85, vehicle.getReliability(), 0.001);
        assertEquals(40, vehicle.getFuelEconomy());
        assertEquals(1.0, vehicle.getFuelTank(), 0.001);
        assertEquals(1, vehicle.getAppliedUpgrades().size());
        assertTrue(vehicle.getAppliedUpgrades().contains(upgrade));
    }

    @Test
    public void removeUpgradeEffectFromVehicle() {
        Vehicle vehicle = new Vehicle(50, 60, 70, 0.8, 40, 10000, "Car", "car_image");
        Upgrade upgrade = new Upgrade("Upgrade", 10, 5, -10, 0.05, -5, 0, 500);

        vehicle.applyUpgradeEffects(upgrade);
        vehicle.removeUpgradeEffects(upgrade);

        assertEquals(50, vehicle.getTopSpeed());
        assertEquals(60, vehicle.getAcceleration());
        assertEquals(70, vehicle.getHandling());
        assertEquals(0.8, vehicle.getReliability(), 0.001);
        assertEquals(40, vehicle.getFuelEconomy());
        assertEquals(1.0, vehicle.getFuelTank(), 0.001);
        assertEquals(0, vehicle.getAppliedUpgrades().size());
    }

    @Test
    public void removeOneUpgradeEffectOutOfMultiple() {
        Vehicle vehicle = new Vehicle(50, 60, 70, 0.8, 40, 10000, "Car", "car_image");
        Upgrade upgrade1 = new Upgrade("Upgrade1", 10, 5, 0, 0.05, 0, 0, 500);
        Upgrade upgrade2 = new Upgrade("Upgrade2", 5, 3, -5, 0.02, 2, 0, 300);

        vehicle.applyUpgradeEffects(upgrade1);
        vehicle.applyUpgradeEffects(upgrade2);
        vehicle.removeUpgradeEffects(upgrade1);

        assertEquals(55, vehicle.getTopSpeed());
        assertEquals(63, vehicle.getAcceleration());
        assertEquals(65, vehicle.getHandling());
        assertEquals(0.82, vehicle.getReliability(), 0.001);
        assertEquals(42, vehicle.getFuelEconomy());
        assertEquals(1.0, vehicle.getFuelTank(), 0.001);
        assertEquals(1, vehicle.getAppliedUpgrades().size());
        assertTrue(vehicle.getAppliedUpgrades().contains(upgrade2));
    }

    @Test
    public void applyUpgradeEffectMaxClamp() {
        Vehicle vehicle = new Vehicle(95, 95, 95, 0.95, 95, 20000, "Hyper Car", "hyper_car_image");
        Upgrade upgrade = new Upgrade("Upgrade", 10, 10, 10, 0.1, 10, 0.1, 3000);

        vehicle.applyUpgradeEffects(upgrade);

        assertEquals(100, vehicle.getTopSpeed());
        assertEquals(100, vehicle.getAcceleration());
        assertEquals(100, vehicle.getHandling());
        assertEquals(1.0, vehicle.getReliability(), 0.001);
        assertEquals(100, vehicle.getFuelEconomy());
        assertEquals(1.0, vehicle.getFuelTank(), 0.001);
    }
}