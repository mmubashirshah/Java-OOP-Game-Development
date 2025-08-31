package game.actors;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

import game.behaviours.BehaviourSelection;
import game.behaviours.PrioritisedBehaviourSelection;
import game.behaviours.WanderBehaviour;

import java.util.HashMap;
import java.util.Map;

/**
 * A peaceful character assuming peaceful characters rot
 * @author Jared Kosem (Modified by Sanjevan Rajasegar - Version 2)
 */
public abstract class PeacefulCharacter extends Actor {
    protected Map<Integer, Behaviour> behaviours = new HashMap<>();
    protected int countdown;
    protected BehaviourSelection behaviourSelection;

    /**
     * Initialises the peaceful character within the valley game map with default prioritised behavior
     * @param name The name
     * @param displayChar The display character
     * @param hitPoints The hitpoints
     */
    public PeacefulCharacter(String name, char displayChar, int hitPoints) {
        this(name, displayChar, hitPoints, new PrioritisedBehaviourSelection());
    }

    /**
     * Initialises the peaceful character with custom behavior selection strategy
     * @param name The name
     * @param displayChar The display character
     * @param hitPoints The hitpoints
     * @param behaviourSelection The behavior selection strategy to use
     */
    public PeacefulCharacter(String name, char displayChar, int hitPoints, BehaviourSelection behaviourSelection) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(999, new WanderBehaviour());
        this.behaviourSelection = behaviourSelection;
    }

    /**
     * The method that executes a prioritised behaviour
     * @param actions    The collection of possible Actions for this Actor
     * @param lastAction The last action took
     * @param map        The map containing the Actor
     * @param display    The display
     * @return  The parent class playTurn which was the behaviour
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        Action selectedAction = behaviourSelection.selectBehaviours(this, map, behaviours);

        if (selectedAction != null) {
            return selectedAction;
        } else {
            return new DoNothingAction();
        }
    }

    /**
     * A method for adding new behaviours for peaceful characters
     * @param priority The priortiy of the behaviour
     * @param behaviour The type of behaviour being added for ALL peacefulcharacters
     */
    protected void addBehaviour(int priority, Behaviour behaviour) {
        this.behaviours.put(priority, behaviour);
    }

    @Override
    public String toString() {
        return super.toString() + (countdown > 0 ? " rots after " + countdown + " turns," : "");
    }
}
