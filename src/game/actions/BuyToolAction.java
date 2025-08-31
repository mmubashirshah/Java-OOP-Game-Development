package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Status;

/**
 * An action that allows a Farmer to purchase a tool item from a merchant.
 * If the Farmer has enough runes (balance), the tool is added to their inventory
 * and the corresponding cost is deducted from their balance.
 * @Author Mubashir Shah
 */
public class BuyToolAction extends Action {
    private Item tool;
    private int price;

    /**
     * Constructs a BuyToolAction.
     *
     * @param tool  The tool item to be purchased.
     * @param price The rune cost of the tool.
     */
    public BuyToolAction(Item tool, int price) {
        this.tool = tool;
        this.price = price;
    }

    /**
     * Executes the purchase: checks if the actor is a Farmer with sufficient runes,
     * deducts the price from their balance, and adds the tool to their inventory.
     *
     * @param actor The actor attempting to buy the tool (must be a Farmer).
     * @param map   The GameMap (not used in this action).
     * @return A message indicating success or failure of the purchase.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        if (actor.hasCapability(Status.IS_PLAYER)) {
            if (actor.getBalance() < price) {
                return "Not enough runes.";
            }
            actor.deductBalance(price);
            actor.addItemToInventory(tool);
        }
        return actor + " bought " + tool + ".";
    }

    /**
     * Returns a description of this action for the menu.
     *
     * @param actor The actor for whom the menu is generated.
     * @return A string describing the purchase and its cost.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + tool + " for " + price + " runes";
    }
}
