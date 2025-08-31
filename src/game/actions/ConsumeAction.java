package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.conditions.Consumable;

/**
 * The action that allows an Actor to consume an consumable character/item
 * Includes consuming things directly from the map (like Golden Beetle) and
 * items in the player's inventory
 *
 * @Author Jared Kosem (Modified by Sanjevan Rajasegar - Version 2)
 */
public class ConsumeAction extends Action {
    /**
     * The consumable item being consumed
     */
    private final Consumable consumable; // the consumable entity (either an actor/item)

    /**
     * The consumable actor being consumed
     */
    private final Actor targetActor; // if the consumable is an actor, this field denotes it

    /**
     * Constructor for consuming an consumable item
     *
     * @param consumable The consumable item to be consumed
     */
    public ConsumeAction(Consumable consumable) {
        this.consumable = consumable;
        this.targetActor = null; // no target actor is initialised
    }

    /**
     * Constructor for consuming an Consumable actor near an Actor
     *
     * @param edibleActor The consumable actor to consume
     */
    public ConsumeAction(Actor edibleActor) {
        this.consumable = (Consumable) edibleActor; // casts the consumable actor to the Consumable
        this.targetActor = edibleActor; // stores the actor to be removed from the map
    }

    /**
     * Executes the consume action by either removing the actor from the map or removing the
     * item from the player's inventory and trigerring the Consumable's onConsume action
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string describing the outcome of the consume action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (targetActor != null) {
            map.removeActor(targetActor);
        } else {
            actor.removeItemFromInventory((Item) consumable);
        }

        consumable.onConsume(actor); // perform the immediate effect of consuming the consumable

        // return a message describing and its provided effect
        return actor + " consumes " + consumable + " and " + consumable.getConsumeDescription() + "!";
    }

    /**
     * Returns a string describing the action to be displayed to the console
     *
     * @param actor The actor performing the action.
     * @return A string to be displayed to the console menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Consume " + consumable;
    }
}