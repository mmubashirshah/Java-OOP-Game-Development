package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.AttackBehaviour;
import game.behaviours.WanderBehaviour;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class the creates a hostile NPC in which they can wander and attack players nearby
 * @author Jared Kosem
 */
public abstract class HostileCharacter extends Actor {
    /**
     * The hashamp denoting behaviour priorities
     */
    protected Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * Initialise the hostile character with a wander behaviour and an attack behaviour
     * @param name The name of the character
     * @param displayChar The character of the character
     * @param hitPoints The hitpoints of the character
     */
    public HostileCharacter(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addBehaviour(10, new WanderBehaviour());
        this.addBehaviour(1, new AttackBehaviour());
    }

    /**
     * A method that executes each behaviour in its list
     * @param actions    The collection of possible Actions for this Actor
     * @param lastAction The last action took
     * @param map        The map containing the Actor
     * @param display    The display
     * @return The action to take
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null) {
                return action;
            }
        }
        return new DoNothingAction();
    }

    /**
     * The method that adds the behaviour to the NPC
     * @param priority The order priority of the behaviour where a behaviour can have higher priority
     * @param behaviour The behaviour to be added
     */
    protected void addBehaviour(int priority, Behaviour behaviour) {
        this.behaviours.put(priority, behaviour);
    }
}
