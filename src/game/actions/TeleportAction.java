package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

/**
 * Class which allows the farmer to teleport to different game maps
 * Only actors with the IS_PLAYER status are capable of executing this action
 * @Author Sanjevan Rajasegar (Version 1)
 */
public class TeleportAction extends Action {
    /**
     * The location the farmer will be teleported to
     */
    private final Location destination;

    /**
     * Constructor for the teleport action
     * @param destination the location to which the actor will be teleported
     */
    public TeleportAction(Location destination) {
        this.destination = destination;
    }

    /**
     * Executes the teleport action by moving the actor to a different GameMap
     * checking that only actors with the IS_PLAYER status are capable of doing so
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (!actor.hasCapability(Status.IS_PLAYER)) {
            return actor + " couldn't teleport to " + destination;
        }

        map.moveActor(actor, destination);
        return actor + " teleports to " + destination.map();
    }

    /**
     * Returns a descriptive string describing the action for selection by the farmer
     * @param actor The actor performing the action.
     * @return a string denoting the teleport option to the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Step into the " + destination.map();
    }
}
