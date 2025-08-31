package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Status;
import game.weapons.WeaponItem;

/**
 * REQ4: BuyWeaponAction - Allows the Farmer to buy a weapon directly from a merchant.
 *
 * Deducts runes, adds the weapon to inventory, and applies merchant-specific effects
 * via the PurchasableWeapon interface.
 *
 * @author Khanh Le
 */
public class BuyWeaponAction extends Action {
    /**
     * The weapon item to be purchased
     */
    private final WeaponItem weapon;

    /**
     * The price of the weapon
     */
    private final int price;

    /**
     * The actor/entity selling the given weapon
     */
    private final String sellerName;

    /**
     * Constructor for creating a buy action.
     *
     * @param weapon     Weapon to be purchased
     * @param price      Rune cost
     * @param sellerName Name of the seller
     */
    public BuyWeaponAction(WeaponItem weapon, int price, String sellerName) {
        this.weapon = weapon;
        this.price = price;
        this.sellerName = sellerName;
    }

    /**
     * Executes the purchase: deducts runes, adds weapon, applies effects.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        if (actor.hasCapability(Status.IS_PLAYER)) {
            if (actor.getBalance() < price)
                return "Not enough runes.";

            actor.deductBalance(price);
            actor.addItemToInventory( weapon);
            weapon.applyPurchaseEffect(actor, sellerName, map);

        }

        return actor + " bought " + weapon + " from " + sellerName + ".";
    }

    /**
     * Returns a string describing the action to be displayed to the console
     *
     * @param actor The actor performing the action.
     * @return A string to be displayed to the console menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + weapon + " from " + sellerName + " for " + price + " runes";
    }
}
