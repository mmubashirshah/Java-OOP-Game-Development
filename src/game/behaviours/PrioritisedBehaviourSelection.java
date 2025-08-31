package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.*;

/**
 * A behaviour selection strategy that chooses actions based on priority order in the hash map,
 * and executing the first valid action it finds
 * @Author Sanjevan Rajasegar (Version 1)
 */
public class PrioritisedBehaviourSelection implements BehaviourSelection {
    /**
     * Selects an action by evaluating behaviours in priority order, by iterating through the behaviours map
     * from highest to lowest priority and returning the FIRST non null action/behaviour found
     *
     * @param actor the actor performing the behaviour
     * @param map the current game map the actor is on
     * @param behaviours a hash map of behaviours representing the actor's given behaviours
     * @return the first valid action found, or null if no behaviours produce an action
     */
    @Override
    public Action selectBehaviours(Actor actor, GameMap map, Map<Integer, Behaviour> behaviours) {
        for (Behaviour behaviour : behaviours.values()) { // iterate through the behaviours in the hash map

            Action action = behaviour.getAction(actor, map); // check the action which should be taken

            if (action != null) { // if the behaviour is a non-null action return and perform it immediately
                return action;
            }
        }

        return null;
    }
}
