package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.recipes.Recipe;

/**
 * The action for a Farmer to craft a recipe
 *
 * @author Jared Kosem
 */
public class CraftAction extends Action {
    private final Recipe recipe;

    /**
     * Constructor
     * @param recipe - The recipe
     */
    public CraftAction(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Method for handling when the action is executed
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return - The outcome of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (!recipe.canCraft(actor)) {
            // Count available Ore and Charm pieces
            int oreCount = 0;
            int charmCount = 0;

            for (Item item : actor.getItemInventory()) {
                String className = item.getClass().getSimpleName();
                if (className.equals("OrePiece")) {
                    oreCount++;
                } else if (className.equals("CharmPiece")) {
                    charmCount++;
                }
            }

            // Calculate how many are still needed
            int oreNeeded = recipe.getRequiredOre() - oreCount;
            int charmNeeded = recipe.getRequiredCharm() - charmCount;

            String message = "Need ";

            if (oreNeeded > 0) {
                message += oreNeeded + " OrePiece";
                if (oreNeeded > 1) {
                    message += "(s)";
                }
            }

            if (oreNeeded > 0 && charmNeeded > 0) {
                message += " and ";
            }

            if (charmNeeded > 0) {
                message += charmNeeded + " CharmPiece";
                if (charmNeeded > 1) {
                    message += "(s)";
                }
            }

            message += " to craft " + recipe.getResultName() + ".";

            return message;
        }

        // Crafting succeeds: create item and add to inventory
        Item craftedItem = recipe.craft(actor);
        if (craftedItem != null) {
            actor.addItemToInventory(craftedItem);
            return "Crafted " + craftedItem;
        }

        return "Failed to craft.";
    }

    /**
     * Method for the description of crafting each item
     * @param actor The actor performing the action.
     * @return - The description
     */
    @Override
    public String menuDescription(Actor actor) {
        String base = "Craft " + recipe.getResultName();
        int oreReq = recipe.getRequiredOre();
        int charmReq = recipe.getRequiredCharm();

        String requirements = " (";

        boolean addedSomething = false;

        // Add ore requirement to display
        if (oreReq > 0) {
            requirements += oreReq + " OrePiece";
            if (oreReq != 1) {
                requirements += "(s)";
            }
            addedSomething = true;
        }

        // Add charm requirement to display
        if (charmReq > 0) {
            if (addedSomething) {
                requirements += ", ";
            }
            requirements += charmReq + " CharmPiece";
            if (charmReq != 1) {
                requirements += "(s)";
            }
        }

        requirements += ")";

        return base + requirements;
    }
}