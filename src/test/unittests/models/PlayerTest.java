package code.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import code.models.Player;
import code.models.cart.Upgrade;
import code.models.cart.Vehicle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private Vehicle vehicle;
    private List<Vehicle> ownedVehicles;

    @BeforeEach
    public void init() {
        player = new Player("Player", 1, 10);
        ownedVehicles = player.getOwnedVehicles();
    }

    @Test
    public void testGetNetWorthOnlyWallet() {

        int netWorth = player.getNetWorth();

        assertEquals(5000, netWorth);
    }

    @Test
    public void testGetNetWorthVehiclesOnly() {
        vehicle = new Vehicle(1, 1, 1, 1, 1, 15000, "Vehicle", "");
        ownedVehicles.add(vehicle);

        int netWorth = player.getNetWorth();

        assertEquals(20000, netWorth);
    }

    @Test
    public void testGetNetWorthWithVehiclesAndUpgrades() {
        vehicle = new Vehicle(1, 1, 1, 1, 1, 15000, "Vehicle", "");
        Upgrade upgrade = new Upgrade("Upgrade", 1, 1, 1, 1, 1, 1, 5);

        ownedVehicles.add(vehicle);
        List<Upgrade> equippedUpgrades = player.getOwnedUpgrades();
        equippedUpgrades.add(upgrade);
        int netWorth = player.getNetWorth();

        assertEquals(20000, netWorth);
    }

    @Test
    public void subMoneyWithMoney() {
        boolean result = player.subMoney(5000);

        assertEquals(true, result);
        assertEquals(0, player.getMoney());
    }

    @Test
    public void subMoneyWithInsufficientMoney() {
        boolean result = player.subMoney(25000);

        assertEquals(false, result);
        assertEquals(5000, player.getMoney());
    }

    @Test
    public void purchaseUpgradeWithMoney() {
        Upgrade upgrade = new Upgrade("Upgrade", 10, 5, 3, 95.0, 10, 50.0, 5000);

        boolean result = Player.purchaseUpgrade(upgrade, 5000);

        assertTrue(result);
        assertEquals(0, player.getMoney());
        assertTrue(Player.ownsUpgrade(upgrade));
    }

    @Test
    public void purchaseUpgradeWithoutMoney() {
        Upgrade upgrade = new Upgrade("Upgrade", 10, 5, 3, 95.0, 10, 50.0, 25000);

        boolean result = Player.purchaseUpgrade(upgrade, 25000);

        assertFalse(result);
        assertEquals(5000, player.getMoney());
        assertFalse(Player.ownsUpgrade(upgrade));
    }

    @Test
    public void purchaseSameUpgradeTwice() {
        Upgrade upgrade = new Upgrade("Upgrade", 10, 5, 3, 95.0, 10, 50.0, 5000);
        player.addMoney(10000); // player starts with default $5000.

        boolean firstPurchase = Player.purchaseUpgrade(upgrade, 5000);
        boolean secondPurchase = Player.purchaseUpgrade(upgrade, 5000);

        assertTrue(firstPurchase);
        assertFalse(secondPurchase);
        assertEquals(10000, player.getMoney());
    }
}