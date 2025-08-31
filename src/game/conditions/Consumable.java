package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface defining an object which can be consumed by an Actor,
 * implementing classes must define effects such as health and stamina gain upon consumption
 * @author Jared Kosem (Modified by Sanjevan Rajasegar - Version 2)
 */
public interface Consumable {
    /**
     * Returns the amount of health restored when consumed
     * @return An integer representing the amount of health gained by the consumer
     */
    int getHealthAdded();

    /**
     * Returns the amount of stamina restored for the consumer
     *
     * @return An integer representing stamina points added to the consumer
     */
    int getStaminaAdded();

    /**
     * Returns the number of runes granted by consuming the edible, defaulting to 0 and can be
     * overriden if required
     * @return
     */
    default int getRunesGranted() { return 0; }

    /**
     * Applies the immediate effect of consuming the edible to the actor
     *
     * @param consumer The actor consuming the edible
     */
    void onConsume(Actor consumer);

    /**
     * Provides a description of the consumption action
     *
     * @return A string denoting the action/effects of consuming the edible
     */
    String getConsumeDescription();
}
