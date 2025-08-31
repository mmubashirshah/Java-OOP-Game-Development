package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.capabilities.Status;

/**
 * A ground tile that represents a glowing sinkhole. When an actor is on this tile,
 * they can choose to teleport to a predefined destination location.
 * @Author Mubashir Shah
 */
public class GlowingSinkhole extends Ground {

    /** The destination location to which actors will be teleported. */
    private Location destination;

    /**
     * Creates a GlowingSinkhole that teleports actors to the given destination.
     *
     * @param destination the location where actors will be sent
     */
    public GlowingSinkhole(Location destination) {
        super('ʘ', "GlowingSinkhole");
        this.destination = destination;
        addCapability(Status.TELEPORTABLE);
    }

    /**
     * Returns the actions available to an actor at this location. Always includes
     * a TeleportAction that moves the actor to the stored destination.
     *
     * @param actor     the actor considering available actions
     * @param location  this sinkhole’s location on the map
     * @param direction the direction from the actor to this sinkhole
     * @return a list of actions, including TeleportAction
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        actions.add(new TeleportAction(destination));
        return actions;
    }

    /**
     * A method that marks the sinkhole as a ground which can only be entered through (you can't
     * walk over the sinkhole, you must enter jump through it as your next action)
     * @param actor The actor
     * @return A boolean value on whether the actor can enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
