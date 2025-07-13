package code.services;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import code.models.Player;

/**
 * A utility class for managing and binding a player's wallet to UI components.
 *
 * <p>
 * Binds the wallet to JavaFX.
 * </p>
 */
public class Wallet {
    private static final IntegerProperty money = new SimpleIntegerProperty(0);

    /**
     * Binds the wallet to the player
     *
     * @param player The player whose money property will be bound.
     */
    public static void bindToPlayer(Player player) {
        money.bind(player.moneyProperty());
    }

    /**
     * Binds the specified label to display the wallet's money value without requiring manual refresh.
     *
     * @param label The label to bind to the wallet's money property.
     */
    public static void bindLabel(Label label) {
        label.textProperty().bind(Bindings.concat("Wallet: $", money));
    }
}
