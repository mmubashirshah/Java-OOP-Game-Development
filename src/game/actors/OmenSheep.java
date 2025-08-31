package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import game.behaviours.BehaviourSelection;
import game.behaviours.PrioritisedBehaviourSelection;
import game.capabilities.Status;
import game.conditions.Cure;
import game.conditions.ProduceEgg;
import game.grounds.cursed.Blight;
import game.items.eggs.OmenSheepEgg;
import game.trees.Inheritree;

/**
 * The Omen sheep of character 'm' that is curable
 * @author Jared Kosem (Modified by Sanjevan Rajasegar - Version 2)
 */
public class OmenSheep extends PeacefulCharacter implements Cure, ProduceEgg {
    /**
     * The egg counter for OmenSheep Eggs
     */
    private int eggCounter = 7;

    /**
     * Constructor for an OmenSheep with default prioritised behavior selection.
     * Initialises with:
     * - Name: "Omen Sheep"
     * - Display character: 'm'
     * - Hitpoints: 75
     * - CURABLE status flag
     * - Initial countdown: 15 turns
     */
    public OmenSheep() {
        this(new PrioritisedBehaviourSelection());
    }

    /**
     * Constructs an OmenSheep with custom behaviour selection strategy.
     *
     * @param behaviourSelection The behavior selection strategy to use
     */
    public OmenSheep(BehaviourSelection behaviourSelection) {
        super("Omen Sheep", 'm', 75, behaviourSelection);
        this.countdown = 15;
        this.addCapability(Status.CURABLE);
    }

    /**
     * A method that ticks down the life countdown of the Omen sheep
     * @param actions    The collection of possible Actions for this Actor
     * @param lastAction The last action took
     * @param map        The map containing the Actor
     * @param display    The display
     * @return  The parent class playTurn which was the behavior
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        countdown--;
        if (countdown <= 0) {
            map.removeActor(this);
            return new DoNothingAction();
        }
        eggCounter--;
        if (eggCounter == 0) {
            this.onProduceEgg(map, this);
            display.println("Omen Sheep laid an egg!");
            eggCounter = 7;
        }

        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * The changes made to the map when an egg is produced by an OmenSheep
     *
     * @param map: The GameMap
     * @param actor: The actor laying the egg
     */
    @Override
    public void onProduceEgg(GameMap map, Actor actor) {
        Location currentLocation = map.locationOf(this);
        currentLocation.addItem(new OmenSheepEgg());
    }

    /**
     * The method that plants Inheritrees around the Omen sheep around plant
     * @param actor The actor performing the purification
     * @param location The location of the entity
     * @return The outcome of the cure
     */
    @Override
    public String cure(Actor actor, Location location) {
        for (Exit exit: location.getExits()) {
            Location destination = exit.getDestination();
            destination.setGround(new Inheritree(new Blight()));
        }
        return "Around the Omen Sheep, Inheritrees take shape...";
    }

    /**
     * The method for returning the cure cost
     * @return 0
     */
    @Override
    public int getCureCost() {
        return 0;
    }
}