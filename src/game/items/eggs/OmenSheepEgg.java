package game.items.eggs;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import game.conditions.NonConstraintHatch;
import game.actors.OmenSheep;

/**
 * The OmenSheepEgg representing an Egg which hatches into an OmenSheep
 * This egg increasing the actor's maximum health when consumed
 */
public class OmenSheepEgg extends Egg {
    /**
     * Constructor for initialising the OmenSheepEgg with specific attributes (hatch timer of 4 turns, creating a new OmenSheep
     * containing the NonConstraint Condition
     */
    public OmenSheepEgg() {
        super(4, OmenSheep::new, "Omen Sheep", new NonConstraintHatch());
    }

    /**
     * This method is called when the egg is consumed by an actor
     * In this case, it increases the actor's maximum health by 10
     *
     * @param consumer The actor consuming the egg
     */
    @Override
    public void onConsume(Actor consumer) {
        consumer.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 10);
    }

    /**
     * Returns the health added when this egg is consumed.
     *
     * @return The health added (10 for this egg)
     */
    @Override
    public int getHealthAdded() {
        return 10;
    }

    /**
     * Returns the stamina added when this egg is consumed.
     *
     * @return The stamina added (0 for this egg)
     */
    @Override
    public int getStaminaAdded() {
        return 0;
    }

    /**
     * Provides a description of the effect of consuming this egg.
     *
     * @return A description of the consumption effect ("gains 10 HP")
     */
    @Override
    public String getConsumeDescription() {
        return "gains 10 HP";
    }
}
