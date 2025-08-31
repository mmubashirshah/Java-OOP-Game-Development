package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action that allows the Farmer to sell a single OrePiece item
 * to a merchant. Removes the OrePiece from the Farmer's inventory
 * and adds the specified amount of runes to the Farmer's balance.
 * @Author Mubashir Shah
 */
public class SellOreAction extends Action {
    private final Item oreItem;
    private final int price;
    private final String merchantName;

    /**
     * Constructs a new SellOreAction.
     *
     * @param oreItem      The OrePiece (as Item) being sold.
     * @param price        The rune price for one OrePiece.
     * @param merchantName The name of the merchant buying the OrePiece.
     */
    public SellOreAction(Item oreItem, int price, String merchantName) {
        this.oreItem = oreItem;
        this.price = price;
        this.merchantName = merchantName;
    }

    /**
     * Executes the sell action: removes the OrePiece from the actor's inventory
     * and adds the specified price in runes to the actor's balance.
     *
     * @param actor The actor performing the sell (the Farmer).
     * @param map   The GameMap, not used in this action.
     * @return A message indicating that the OrePiece was sold and runes were gained.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.removeItemFromInventory(oreItem);
        actor.addBalance(price);
        return actor + " sold one OrePiece to " + merchantName + " for " + price + " runes.";
    }

    /**
     * Returns a description of this action for the menu.
     *
     * @param actor The actor for whom the menu is being constructed.
     * @return A string describing the sell action and its price.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " sells an OrePiece to " + merchantName + " for " + price + " runes";
    }
}
