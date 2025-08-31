package game.recipes;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.items.Talisman;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;

import java.util.ArrayList;
import java.util.List;

/**
 * A Recipe for the crafting of each item
 * @author Jared Kosem
 */
public class Recipe {
    private final String resultName;
    private final int requiredOre;
    private final int requiredCharm;

    /**
     * Recipe constructor for the item to be crafted
     * @param resultName - Name of existing item recipe
     * @param requiredOre - Ore cost
     * @param requiredCharm - Charm cost
     */
    public Recipe(String resultName, int requiredOre, int requiredCharm) {
        this.resultName = resultName;
        this.requiredOre = requiredOre;
        this.requiredCharm = requiredCharm;
    }

    /**
     * A method to check if the recipe can be crafted
     * @param actor - Player
     * @return - True if the recipe can be crafted
     */
    public boolean canCraft(Actor actor) {
        int oreCount = 0;
        int charmCount = 0;

        // Count resources in inventory
        for (Item item : actor.getItemInventory()) {
            String className = item.getClass().getSimpleName();
            if (className.equals("OrePiece")) {
                oreCount++;
            } else if (className.equals("CharmPiece")) {
                charmCount++;
            }
        }
        return oreCount >= requiredOre && charmCount >= requiredCharm;
    }

    /**
     * A method to craft the item
     * @param actor
     * @return
     */
    public Item craft(Actor actor) {
        List<Item> toRemove = new ArrayList<>();
        int oresLeft = requiredOre;
        int charmsLeft = requiredCharm;

        // Gather items to remove from inventory
        for (Item item : actor.getItemInventory()) {
            if (oresLeft > 0 && item.getClass().getSimpleName().equals("OrePiece")) {
                toRemove.add(item);
                oresLeft--;
            }
            if (charmsLeft > 0 && item.getClass().getSimpleName().equals("CharmPiece")) {
                toRemove.add(item);
                charmsLeft--;
            }
            if (oresLeft == 0 && charmsLeft == 0) break;
        }

        // Remove resources from inventory
        for (Item item : toRemove) {
            actor.removeItemFromInventory(item);
        }

        /**
         * Create the crafted item based on recipe name.
         * DeepSeek was used to create this switch case
         */
        return switch (resultName) {
            case "Broadsword" -> new Broadsword();
            case "Talisman" -> new Talisman();
            case "Dragonslayer Greatsword" -> new DragonslayerGreatsword();
            default -> null;
        };
    }

    /**
     * Get the result name
     * @return - result name
     */
    public String getResultName() {
        return resultName;
    }

    /**
     * Get the required ores
     * @return - required ores
     */
    public int getRequiredOre() {
        return requiredOre;
    }

    /**
     * Get the required charms
     * @return - required charms
     */
    public int getRequiredCharm() {
        return requiredCharm;
    }

}