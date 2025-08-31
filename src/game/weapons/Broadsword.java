package game.weapons;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.actors.Actor;

/**
 * REQ4: Broadsword - Deals 30 damage with 50% hit accuracy.
 *
 * All purchases heal the player by 10 HP.
 *
 * Additional effects depend on the seller:
 * - From Sellen: Increases the player's maximum health by 20
 * - From Kale: Increases the player's maximum stamina by 30
 *
 * Implements applyPurchaseEffect from PurchasableWeapon to encapsulate its side-effects.
 *
 * @author Khanh Le
 */

public class Broadsword extends WeaponItem {

    /**
     * Constructor
     */
    public Broadsword() {
        super("Broadsword", 'b', 30, "slashes", 50);
    }

    /**
     * Applies the effects granted upon purchasing the Broadsword.
     *
     * @param player   The player purchasing the weapon
     * @param sellerId The name of the merchant (e.g., "Sellen", "Kale")
     * @param map      The game map (not used in this case)
     */
    @Override
    public void applyPurchaseEffect(Actor player, String sellerId, GameMap map) {
        player.heal(10);
        if (sellerId.equals("Sellen")) {
            player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 20);
        } else if (sellerId.equals("Kale")) {
            player.modifyAttributeMaximum(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 30);
        }
    }
}