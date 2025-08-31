package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import game.actors.GoldenBeetle;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * REQ4: Dragonslayer Greatsword - Deals 70 damage with 75% hit accuracy.
 *
 * Upon purchase, this weapon grants the player +15 maximum HP regardless of the seller.
 *
 * Additional effects depend on the seller:
 * - From Sellen: Spawns a Golden Beetle at the player’s location.
 * - From Merchant Kale: Restores 20 stamina immediately.
 *
 * Implements applyPurchaseEffect from PurchasableWeapon to encapsulate merchant-specific purchase effects.
 *
 * @author Khanh Le
 */

public class DragonslayerGreatsword extends WeaponItem {

    /**
     * Constructor
     */
    public DragonslayerGreatsword() {
        super("Dragonslayer Greatsword", 'D', 70, "smashes", 75);
    }

    /**
     * Applies side effects triggered upon purchasing this weapon.
     *
     * @param player   The player purchasing the weapon
     * @param sellerId The name of the merchant (e.g., "Sellen", "Kale")
     * @param map      The game map used for spawning entities (Golden Beetle in this case)
     */
    @Override
    public void applyPurchaseEffect(Actor player, String sellerId, GameMap map) {
        player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);
        if (sellerId.equals("Sellen")) {
            Location spawn = map.locationOf(player);
            map.addActor(new GoldenBeetle(), spawn);
        } else if (sellerId.equals("Kale")) {
            player.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 20);
        }
    }
}