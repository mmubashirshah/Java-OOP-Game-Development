package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.*;

/**
 * An interface for defining behaviour selection strategies used by actors,
 * implementations of this interface determine how an actor selects which
 * behaviour to execute from its determined behaviours
 *
 * Behaviour selection strategies include prioritised selections OR random selections
 * (at this moment in time)
 * @Author Sanjevan Rajasegar (Version 1)
 */
public interface BehaviourSelection {
    /**
     * Selects and returns a behaviour/action from the available behaviours according
     * to the implementation's selection strategy
     * @param actor the actor performing the behaviour
     * @param map the current game map the actor is on
     * @param behaviours a hash map of behaviours representing the actor's given behaviours
     * @return an action to be executed this turn or null if no behaviour is selected
     */
    Action selectBehaviours(Actor actor, GameMap map, Map<Integer, Behaviour> behaviours);
}
