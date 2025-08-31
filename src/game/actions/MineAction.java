package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.ShimmeringGlyph;

/**
 * An action that allows a Farmer to mine an adjacent ShimmeringGlyph (ore) tile.
 * The action consumes 10 stamina and requires the actor to have a Pickaxe.
 * @Author Mubashir Shah
 */
public class MineAction extends Action {
    private final ShimmeringGlyph shimmeringGlyph;
    private final Location oreLocation;
    private final String direction;

    /**
     * Constructs a new MineAction.
     *
     * @param shimmeringGlyph The ShimmeringGlyph (ore) to be mined.
     * @param oreLocation     The location of the ore on the map.
     * @param direction       The direction from the actor to the ore (e.g., "north").
     */
    public MineAction(ShimmeringGlyph shimmeringGlyph, Location oreLocation, String direction) {
        this.shimmeringGlyph = shimmeringGlyph;
        this.oreLocation = oreLocation;
        this.direction = direction;
    }

    /**
     * Executes the mining action: deducts 10 stamina from the actor and
     * calls breakOre on the ShimmeringGlyph.
     *
     * @param actor The actor performing the mining.
     * @param map   The GameMap containing the ore.
     * @return A string describing the mining event, including the direction.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 10);
        shimmeringGlyph.breakOre(actor, oreLocation);
        return actor + " mines the rock to the " + direction + ".";
    }

    /**
     * Returns a menu description for this action.
     *
     * @param actor The actor for whom the menu is being generated.
     * @return A string describing the action in the menu, including the direction.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses pickaxe to mine to the " + direction;
    }
}
