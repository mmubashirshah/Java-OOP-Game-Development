package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.BehaviourSelection;
import game.behaviours.FollowBehaviour;
import game.behaviours.PrioritisedBehaviourSelection;
import game.capabilities.Ability;
import game.capabilities.Status;
import game.conditions.Consumable;
import game.conditions.ProduceEgg;
import game.items.eggs.GoldenBeetleEgg;

/**
 * The GoldenBeetle is a peaceful actor in the game that lays GoldenBeetle Eggs and follows
 * the Player, and can be consumed to boost health and grant runes
 * @Author by Sanjevan Rajasegar (Version 2)
 */
public class GoldenBeetle extends PeacefulCharacter implements ProduceEgg, Consumable {
    /**
     * The given egg counter for goldenbeetleeggs
     */
    private int eggCounter = 5; // counter to keep track of when the GoldenBeetle can lay its next egg

    /**
     * The actor currently being followed by the GoldenBeetle
     */
    private Actor followTarget = null; // The actor the beetle will follow

    /**
     * Constructor for a GoldenBeetle with default prioritised behavior selection
     * Initialises with:
     * - Name: "Golden Beetle"
     * - Display character: 'b'
     * - Hitpoints: 25
     * - EATABLE status flag
     */
    public GoldenBeetle() {
        this(new PrioritisedBehaviourSelection());
    }

    /**
     * Constructor for a GoldenBeetle with custom behavior selection strategy
     *
     * @param behaviourSelection The behavior selection strategy to use
     */
    public GoldenBeetle(BehaviourSelection behaviourSelection) {
        super("Golden Beetle", 'b', 25, behaviourSelection);
        this.addCapability(Status.EATABLE);
    }

    /**
     * Executes a single turn for the Golden Beetle, handling its egg production
     * and behavior to follow the Player if it's nearby
     *
     * @param actions    The collection of possible Actions for this Actor
     * @param lastAction The last action took
     * @param map        The map containing the Actor
     * @param display    The display
     * @return  The action the beetle will perform for this given turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        eggCounter--; // decrease the egg counter with each turn

        if (eggCounter <= 0) {
            this.onProduceEgg(map, this); // produce an egg
            display.println("Golden Beetle laid a Golden Egg!");
            eggCounter = 5; // reset the egg counter
        }

        handleFollowBehavior(map); // check for and apply the follow behavior if needed

        return super.playTurn(actions, lastAction, map, display); // delegate the superclass to display other behaviors
    }

    /**
     * Handles the logic to follow a nearby actor with the CAN_BE_FOLLOWED status, and adding
     * and/or removing the given FollowBehaviour if applicable
     * @param map The game map
     */
    private void handleFollowBehavior(GameMap map) {
        if (followTarget != null && (!map.contains(followTarget) || !map.contains(this))) {
            behaviours.remove(998); // remove the follow behavior
            followTarget = null; // clear the follow target
            return;
        }

        if (followTarget == null) {
            followTarget = findFollowableActor(map); // look for a new target to follow
            if (followTarget != null) {
                behaviours.put(998, new FollowBehaviour(followTarget)); // add a new follow behaviour with priority 998
            }
        }
    }

    /**
     * Searches adjacent locations for an actor that can be followed
     *
     * @param map The game map
     * @return The first possible followable actor or null if not applicable
     */
    private Actor findFollowableActor(GameMap map) {
        Location currentLocation = map.locationOf(this); // get the current location

        if (currentLocation == null) {
            return null;
        }

        for (Exit exit : currentLocation.getExits()) { // check all adjacent tiles/locations to the GoldenBeetle
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) { // check if any given adjacent location contains an actor
                Actor actor = destination.getActor();
                if (actor.hasCapability(Status.CAN_BE_FOLLOWED)) {
                    return actor; // if the actor can be followed return this actor
                }
            }
        }
        return null; // no followable actor is found
    }

    /**
     * Called when the GoldenBeetle lays an egg, adding a GoldenBeetleEgg to the current
     * location
     * @param map The current game map where the egg should be laid
     * @param actor The beetle laying the egg
     */
    @Override
    public void onProduceEgg(GameMap map, Actor actor) {
        Location currentLocation = map.locationOf(this); // get the beetle's current location
        currentLocation.addItem(new GoldenBeetleEgg()); // drop a new GoldenBeetleEgg at the location
    }

    /**
     * Amount of health restored when consumed
     *
     * @return 15
     */
    @Override
    public int getHealthAdded() {
        return 15;  // Heals 15 HP for the player
    }

    /**
     * Amount of stamina restored when consumed
     *
     * @return 0
     */
    @Override
    public int getStaminaAdded() {
        return 0;  // No stamina bonus
    }

    /**
     * Amount of runes granted when consumed
     *
     * @return 1000
     */
    @Override
    public int getRunesGranted() {
        return 1000;
    }

    /**
     * Defines what happens when this GoldenBeetle is consumed from the map by an actor, healing the actor
     * and granting them 1000 runes if they're the player
     *
     * @param consumer The actor consuming the beetle
     */
    @Override
    public void onConsume(Actor consumer) {
        consumer.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);

        if (consumer.hasCapability(Status.IS_PLAYER) && consumer.hasCapability(Ability.CAN_RECEIVE_RUNES))  { // checks if the actor has the status IS_PLAYER
            consumer.addBalance(getRunesGranted());
        }
    }

    /**
     * A description of the effects of consuming this beetle
     *
     * @return A string describing the benefits of consumption of the GoldenBeetle
     */
    @Override
    public String getConsumeDescription() {
        return "gains 15 HP and 1000 runes";
    }
}