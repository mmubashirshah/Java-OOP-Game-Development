package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for weapons that trigger purchase-time effects when bought from a merchant.
 * @Author Khanh Le
 */
public interface Purchasable {
    /**
     * Apply merchant-specific side effects to the player when the weapon is purchased.
     *
     * @param player   the player purchasing the weapon
     * @param sellerId the ID of the seller (e.g., "Sellen", "Kale")
     * @param map      the game map for spawning if needed
     */
    void applyPurchaseEffect(Actor player, String sellerId, GameMap map);
}