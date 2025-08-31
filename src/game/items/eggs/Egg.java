package game.items.eggs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.conditions.Consumable;
import game.capabilities.Status;
import game.conditions.HatchCondition;

import java.util.function.Supplier;

/**
 * An abstract class representing an Egg item which can hatch into different entities/consumed by actors
 * The egg will hatch based on certain conditions, a timer and if it's edible
 * @Author Jared Kosem (Modified by: Sanjevan Rajasegar - Version 2)
 */
public abstract class Egg extends Item implements Consumable {
    protected int hatchTimer; // the timer controlling how long it takes for the egg to hatch
    protected final Supplier<Actor> creatureSupplier; // a supplier that will create the creature when the egg hatches
    protected final String eggType; // the type of egg defined (used for descriptions)
    protected final HatchCondition hatchCondition; // the condition that determines how the egg will hatch
    protected Display display = new Display();

    /**
     * Constructor to initalise the egg with its type, hatch timer and hatch conditions
     *
     * @param hatchTimer the initial hatch timer value
     * @param creatureSupplier a supplier that provides the actor to be created when the egg hatches
     * @param eggType the type of egg
     * @param hatchCondition the condition that determines if the egg can hatch
     */
    public Egg(int hatchTimer, Supplier<Actor> creatureSupplier, String eggType, HatchCondition hatchCondition) {
        super("Egg", '0', true);
        this.creatureSupplier = creatureSupplier;
        this.eggType = eggType;
        this.hatchTimer = hatchTimer + 1; // accounts for the turn in which the egg is first laid
        this.hatchCondition = hatchCondition;
        this.addCapability(Status.EATABLE);
    }

    /**
     * Returns a string representation of the egg, including its type and the super class' string representation
     *
     * @return A string representing the egg's type and its base attributes
     */
    @Override
    public String toString() {
        return eggType + " " + super.toString(); // combines the egg type and the base string representation
    }

    /**
     * Tick's the egg's hatch timer and checks if it can hatch
     *
     * @param location The location at which the egg is currently located
     */
    @Override
    public void tick(Location location) {
        if (location != null) {
            if (hatchCondition.isTurnBased()) {
                hatchTimer--;
                if (hatchTimer <= 0 || hatchCondition.canHatch(location)) {
                    hatch(location);
                }
            } else {
                if (hatchCondition.canHatch(location)) {
                    hatch(location);
                }
            }
        }
    }

    /**
     * Tries to hatch the egg depending on its given hatch conditions
     *
     * @param location The location where the egg is located and where hatching is attempted
     */
    public void hatch(Location location) {
        if (hatchCondition.canHatch(location)) { // if the egg can hatch (returning a boolean value)
            if (!location.containsAnActor()) {
                Actor newCreature = creatureSupplier.get(); // get the new creature using the supplier
                GameMap map = location.map();
                map.addActor(newCreature, location);
                location.removeItem(this); // remove the egg from the current location
                display.println(this.eggType + " egg hatched into a new offspring!");
                return;
            }

            // if the current location by another actor is occupied check adjacent tiles for the egg to hatch
            for (Exit exit : location.getExits()) {
                Location adjacent = exit.getDestination();
                if (!adjacent.containsAnActor() && hatchCondition.canHatch(adjacent)) { // check if the adjacent location is empty and hatching is allowed
                    Actor newCreature = creatureSupplier.get();
                    GameMap map = adjacent.map();
                    map.addActor(newCreature, adjacent);
                    location.removeItem(this); // remove the egg from the current location
                    display.println(this.eggType + " egg hatched into a new offspring!");
                    return;
                }
            }

            hatchTimer = 1; // if no valid location was found for this given turn reset the hatch timer to 1

        } else {
            hatchTimer = 1; // if the egg cannot hatch reset the hatch timer to 1
        }
    }

    /**
     * Abstract method to be implemented by subclasses, defining the behaviour of when the egg is consumed by an actor
     *
     * @param consumer The actor consuming the egg
     */
    @Override
    public abstract void onConsume(Actor consumer);

    /**
     * Abstract method to be implemented by subclasses, defining the health added when the egg is consumed
     *
     * @return The health added by consuming the egg
     */
    @Override
    public abstract int getHealthAdded();

    /**
     * Abstract method to be implemented by subclasses, defining the stamina restored when the egg is consumed
     *
     * @return The stamina restored by consuming the egg
     */
    @Override
    public abstract int getStaminaAdded();

    /**
     * Defines the allowable actions for the actor can perform with this egg, including its consumption
     *
     * @param actor the actor that owns the item
     * @param map the map where the actor is performing the action on
     * @return A list of actions available for this egg item
     */
    @Override
    public ActionList allowableActions(Actor actor, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new ConsumeAction(this));
        return actions;
    }
}
