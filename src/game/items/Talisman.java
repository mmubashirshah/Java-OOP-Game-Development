package game.items;


import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CureAction;
import game.capabilities.Status;
import game.conditions.Cure;

/**
 * A class representing a Talisman that an actor can pick up and drop
 * @author Adrian Kristanto and Jared Kosem
 */
public class Talisman extends Item {

    /**
     * Initialises the Talisman
     */
    public Talisman() {
        super("Talisman", 'o', true);
    }

    /**
     * A method that adds the action that the player can use the talisman on
     * @param actor The actor that owns the item
     * @param map The map where the actor is performing the action on
     * @return A list of actions
     */
    @Override
    public ActionList allowableActions(Actor actor, GameMap map) {
        ActionList actions = new ActionList();
        Location location = map.locationOf(actor);
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getActor() != null && destination.getActor().hasCapability(Status.CURABLE)) {
                actions.add(new CureAction((Cure) destination.getActor(), destination));
            }
        }
        if (location.getGround().hasCapability(Status.CURABLE)) {
            actions.add(new CureAction((Cure) location.getGround(), location));
        }
        return actions;
    }

}
