package game.trees;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.conditions.CursedGround;
import game.grounds.Soil;

/**
 * The inheritree that can heel those surrounding the inheritree as well as increase the stamina
 * @author Jared Kosem
 */
public class Inheritree extends Ground {
    private final CursedGround cursedGround;
    private boolean initial = true;
    private int INHERITREE_HEAL_AMOUNT = 5;

    /**
     * Initialise an inheritree that can cure a specific type of cursed ground
     * @param cursedGround The cursed ground that the inheritree can cure
     */
    public Inheritree(CursedGround cursedGround) {
        super('t', "Inheritree");
        this.cursedGround = cursedGround;
        this.addCapability(Status.HAS_GRACE);
    }

    /**
     * A method that ensures the Inheritree convert its surrounding cursed soil type into soil and heal actors in its surrounding or increase their stamina every tick
     * @param location The location of the ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (initial) {
            effectConvertSurrounding(location);
            initial = false;
        }
        effectSurrounding(location);
    }

    /**
     * A method that marks the tree as a wall
     * @param actor The actor
     * @return A boolean value on whether the actor can enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * A method that converts the surrounding cursed ground type into soil
     * @param location
     */
    private void effectConvertSurrounding(Location location) {
        for (Exit exit: location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround().getDisplayChar() == cursedGround.getCursedChar()) {
                destination.setGround(new Soil());
            }
        }
    }

    /**
     * A method that heals the surrounding actors and increases stamina if it exists
     * @param location The location of the actor
     */
    private void effectSurrounding(Location location) {

        // Heal Effect and Stamina And gives grace
        for (Exit exit: location.getExits()) {
            Location destination = exit.getDestination();

            // Only tree has GRACE right? not the grounds surrounding it
            destination.getGround().addCapability(Status.HAS_GRACE);

            if (destination.containsAnActor()) {
                Actor actor = destination.getActor();
                actor.heal(5);
                if (actor.hasAttribute(BaseActorAttributes.STAMINA)) {
                    actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, INHERITREE_HEAL_AMOUNT);
                }
            }
        }
    }
}
