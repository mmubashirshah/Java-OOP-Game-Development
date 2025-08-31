package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import game.actors.OmenSheep;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * REQ4: Katana - Deals 50 damage with 60% hit accuracy.
 *
 * This weapon is only available from Sellen. Upon purchase, it inflicts 25 damage to the player,
 * but also provides several benefits:
 * - Restores 10 HP
 * - Increases maximum stamina by 30
 * - Spawns an Omen Sheep at the player's location
 *
 * Implements applyPurchaseEffect from PurchasableWeapon to apply seller-specific effects at the time of purchase.
 *
 * @author Khanh Le
 */
public class Katana extends WeaponItem {

    /**
     * Constructor
     */
    public Katana() {
        super("Katana", 'j', 50, "slashes", 60);
    }

    /**
     * Applies purchase effects if bought from Sellen.
     *
     * @param player   The player purchasing the weapon
     * @param sellerId The name of the merchant (expected to be "Sellen")
     * @param map      The game map used for spawning entities (Omen Sheep in this case)
     */
    @Override
    public void applyPurchaseEffect(Actor player, String sellerId, GameMap map) {
        if (sellerId.equals("Sellen")) {
            player.hurt(25);
            player.heal(10);
            player.modifyAttributeMaximum(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 30);
            Location spawn = map.locationOf(player);
            map.addActor(new OmenSheep(), spawn);
        }
    }
}
