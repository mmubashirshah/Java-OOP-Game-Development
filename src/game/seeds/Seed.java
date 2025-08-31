package game.seeds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.PlantAction;

/**
 * The seed class
 * @author Jared Kosem
 */
public abstract class Seed extends Item {
    /**
     * Constructor for the seed class that extends the Item class
     * @param name
     * @param displayChar
     * @param portable
     */
    public Seed(String name, char displayChar, boolean portable) {
        super(name, displayChar, true);
    }

    /**
     * A method for creating the plant
     * @return The seed to be planted on the ground
     */
    public abstract Ground createPlant();

    /**
     * The stamina cost for planting the seed
     * @return
     */
    public abstract int getStaminaCost();

    /**
     * The method that determines if the actor is on soil where they can plant and reduces the actors stamina
     * @param actor the actor that owns the item
     * @param map the map where the actor is performing the action on
     * @return
     */
    @Override
    public ActionList allowableActions(Actor actor, GameMap map) {
        ActionList actions = new ActionList();
        Location location = map.locationOf(actor);
        if (location.getGround().getDisplayChar() == '.' && actor.hasAttribute(BaseActorAttributes.STAMINA)) {
            if (actor.getAttribute(BaseActorAttributes.STAMINA) >= getStaminaCost())
                actions.add(new PlantAction(this));
        }
        return actions;
    }

}
