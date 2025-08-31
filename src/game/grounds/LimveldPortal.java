package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.capabilities.Status;
import game.utils.SurroundingUtils;

/**
 * A special ground type that allows player teleportation to a linked destination.
 * They appear as the display character 'A' and can only be used by actors with the
 * IS_PLAYER STATUS
 * @Author Sanjevan Rajasegar
 */
public class LimveldPortal extends Ground {
    /**
     * The destination location this portal teleports actors to
     */
    private final Location destination;

    /**
     * Constructor for a portal with a linked destination location
     * @param destination the location actors will be teleported to
     */
    public LimveldPortal(Location destination) {
        super('A', "LimveldPortal");
        this.destination = destination;
        this.addCapability(Status.TELEPORTABLE);
    }

    /**
     * Returns a list of allowable actions for actors standing on this portal,
     * adding a Teleport Action if the actor is a player
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return a array of allowable actions for selection by the user
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        // retrieves the allowable actions Action list
        ActionList actions = super.allowableActions(actor, location, direction);

        // checks if the ground currently contains an Actor who has the IS_PLAYER status
        if (actor.hasCapability(Status.IS_PLAYER)) {
            actions.add(new TeleportAction(destination));
        }
        
        return actions;
    }

    /**
     * A method that marks the portal as a ground which can only be entered through (you can't
     * walk through the portal, you must enter through it as your next action)
     * @param actor The actor
     * @return A boolean value on whether the actor can enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
