package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.seeds.Seed;

/**
 * A class for the action of planting a seed that decreases the actor stamina by the staminacost of that seed
 * @author Jared Kosem
 */
public class PlantAction extends Action {
    /**
     * The seed being planted to the map
     */
    private final Seed seed;

    /**
     * Initialise the plantaction with the seed to be planted
     * @param seed The seed
     */
    public PlantAction(Seed seed) {
        this.seed = seed;
    }

    /**
     * A method for the execution of planting a seed where the seed is removed and the actors stamina is decreased
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return The outcome of the plant action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        actor.removeItemFromInventory(seed);
        Ground plant = seed.createPlant();
        location.setGround(plant);
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, seed.getStaminaCost());
        return actor + " plants a " + plant.toString();
    }

    /**
     * The menu description representation of the action
     * @param actor The actor performing the action
     * @return The string representation of the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " plant a " + seed.toString();
    }
}
