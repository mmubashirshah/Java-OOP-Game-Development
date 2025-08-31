package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.*;

/**
 * A behaviour selection strategy that chooses actions randomly from an available set of behaviours
 * in the hash map by converting it into an array and selecting among one of the available behaviours
 * at random
 * @Author Sanjevan Rajasegar (Version 1)
 */
public class RandomBehaviourSelection implements BehaviourSelection {
    /**
     * Random number generator for the selection of behaviours
     */
    private Random random = new Random(); // generates random numbers to select a behaviour

    /**
     * Selects an action by randomly choosing among available behaviours,
     * all behaviours have equal probability of being selected
     *
     * @param actor the actor performing the behaviour
     * @param map the current game map the actor is on
     * @param behaviours a hash map of behaviours representing the actor's given behaviours
     * @return An Action from a randomly selected behaviour, or null if:
     *         - The behaviours map is empty
     *         - The selected behaviour returns null
     */
    @Override
    public Action selectBehaviours(Actor actor, GameMap map, Map<Integer, Behaviour> behaviours) {
        // if the behaviours hash map is empty return null
        if (behaviours.isEmpty()) { // checks if the behaviour map is empty, and if so returns null
            return null;
        }

        // converts the hashmap collection of values into an array for random access
        Behaviour[] behaviourArray = behaviours.values().toArray(new Behaviour[0]);

        // randomly selects one behaviour from the array
        Behaviour selectedBehaviour = behaviourArray[random.nextInt(behaviourArray.length)];

        // returns the action to be performed by the NPC
        return selectedBehaviour.getAction(actor, map);
    }
}
