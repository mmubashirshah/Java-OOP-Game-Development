package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An interface cure to ensure that a cure and getcurecost method is implemented for each entity that can be cured
 * @author Jared Kosem
 */
public interface Cure {

    /**
     * A method for curing
     * @param actor The actor performing the purification
     * @param location The location of the entity
     * @return A string return to show that the entity has been cured
     */
    String cure(Actor actor, Location location);

    /**
     * A method for returning the cost of curing
     * @return
     */
    int getCureCost();
}
