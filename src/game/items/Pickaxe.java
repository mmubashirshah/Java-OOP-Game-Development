package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * A Pickaxe used for mining. Has unlimited durability.
 * The player must have one in inventory to perform MineAction.
 * @Author Mubashir Shah
 */
public class Pickaxe extends Item {
    public Pickaxe() {
        super("Pickaxe", 'p', true);
    }
}