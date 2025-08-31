package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.conditions.Cure;

/**
 * A class representing an action to cure an actor or ground
 * @author Jared Kosem
 */
public class CureAction extends Action {
    /**
     * The specific cure being provided to a given actor
     */
    private final Cure cure;

    /**
     * The location where an entity is being cured
     */
    private final Location location;

    /**
     * A constructor that initialises the entity to be cured and the location
     * @param cure A cure object that is either the ground or an actor meaning type casting has to be used
     * @param location The location of the ground or actor
     */
    public CureAction(Cure cure, Location location) {
        this.location = location;
        this.cure = cure;
    }

    /**
     * The method for when a player attempts to cure something
     * @param actor The actor performing the action
     * @param map The map the actor is on
     * @return A string representation of the outcome of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.getAttribute(BaseActorAttributes.STAMINA) < cure.getCureCost()) {
            return actor + " does not have enough stamina to do this";
        }
        String result = cure.cure(actor, location);
        return actor + " used Talisman. " + result;
    }

    /**
     * A method for determining the menu description
     * @param actor The actor performing the action
     * @return A string representation of the menu description
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " use a talisman on " + cure;
    }
}
