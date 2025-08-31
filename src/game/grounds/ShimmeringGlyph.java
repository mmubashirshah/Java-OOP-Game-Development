package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.MineAction;
import game.actors.OmenSheep;
import game.actors.SpiritGoat;
import game.capabilities.Status;
import game.items.CharmPiece;
import game.items.OrePiece;

import java.util.Random;

/**
 * A mineable ore tile represented by '⤱'. Each ShimmeringGlyph has a random
 * durability between 1 and 3. Each mining strike reduces durability by one.
 * When durability reaches zero, the ore breaks and produces a random reward:
 * - 50% chance: add an OrePiece to the farmer's inventory.
 * - 40% chance: add a CharmPiece to the farmer's inventory.
 * - 10% chance: spawn either an OmenSheep or a SpiritGoat at this location.
 * Finally, the tile is replaced with Dirt.
 * @Author Mubashir Shah
 */
public class ShimmeringGlyph extends Ground {
    private int durability;

    /**
     * Constructs a new ShimmeringGlyph with random durability from 1 to 3.
     */
    public ShimmeringGlyph() {
        super('⤱', "ShimmeringGlyph");
        this.durability = new Random().nextInt(3) + 1; // 1, 2, or 3
    }

    /**
     * Returns the actions available to an actor at this location. Only an actor
     * with Status.IS_PLAYER and a Pickaxe in inventory will receive a MineAction.
     *
     * @param actor     The actor approaching this ground.
     * @param location  The location of this ground.
     * @param direction The direction from the actor to this ground.
     * @return An ActionList that may include MineAction if conditions are met.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);

        if (actor.hasCapability(Status.IS_PLAYER)) {
            // Check inventory for a Pickaxe by name
            boolean hasPickaxe = actor.getItemInventory().stream()
                    .anyMatch(item -> item.getClass().getSimpleName().equals("Pickaxe"));

            if (hasPickaxe) {
                actions.add(new MineAction(this, location, direction));
            }
        }
        return actions;
    }

    /**
     * Applies one strike of mining. Reduces durability by one. When durability
     * reaches zero, the ore breaks and grants one of three random rewards:
     * - 10% chance to spawn a fossil (OmenSheep or SpiritGoat).
     * - 40% chance to give the Farmer a CharmPiece.
     * - 50% chance to give the Farmer an OrePiece.
     * After granting the reward, this tile is replaced with Dirt.
     *
     * @param actor    The actor who mined the rock.
     * @param location The location of this ShimmeringGlyph.
     */
    public void breakOre(Actor actor, Location location) {
        durability--;
        if (durability > 0) {
            return;
        }

        // 10-point roll: 1 = fossil, 2–5 = CharmPiece, 6–10 = OrePiece
        int roll = new Random().nextInt(10) + 1; // 1..10
        if (roll == 1) {
            Actor fossil = new Random().nextBoolean() ? new OmenSheep() : new SpiritGoat();
            GameMap map = location.map();
            map.addActor(fossil, location);
        }
        else if (roll <= 5) {
            actor.addItemToInventory(new CharmPiece());
        }
        else {
            actor.addItemToInventory(new OrePiece());
        }

        location.setGround(new Soil());
    }
}
