package game.items.eggs;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import game.conditions.CursedProximityHatch;
import game.actors.GoldenBeetle;

/**
 * The GoldenBeetleEgg represents a Golden Beetle egg that can hatch into a Golden Beetle.
 * It provides a unique consumption effect, increasing the actor's stamina when consumed.
 * @Author Sanjevan Rajasegar (Version 1)
 */
public class GoldenBeetleEgg extends Egg {
    /**
     * Constructor for initialising the GoldenBeetleEgg with specific attributes (hatch timer of 1 turn, creating a new Golden Beetle
     * containing the CursedProximityHatch Condition
     */
    public GoldenBeetleEgg() {
        super(1, GoldenBeetle::new, "Golden Beetle", new CursedProximityHatch());
    }

    /**
     * This method is called when the egg is consumed by an actor
     * In this case, it increases the actor's stamina by 20
     *
     * @param consumer The actor consuming the egg
     */
    @Override
    public void onConsume(Actor consumer) {
        consumer.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 20);
    }

    /**
     * Returns the health added when this egg is consumed
     *
     * @return The health added for consuming the egg (0)
     */
    @Override
    public int getHealthAdded() {
        return 0;
    }

    /**
     * Returns the stamina added when this egg is consumed
     *
     * @return The stamina added (20 for this egg)
     */
    @Override
    public int getStaminaAdded() {
        return 20;
    }

    /**
     * Provides a description of the effect of consuming this egg
     *
     * @return A description of the consumption effect ("gains 20 Stamina")
     */
    @Override
    public String getConsumeDescription() {
        return "gains 20 Stamina";
    }
}