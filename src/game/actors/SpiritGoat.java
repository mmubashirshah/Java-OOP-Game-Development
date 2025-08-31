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
import game.conditions.Reproduce;
import game.conditions.Cure;
import game.capabilities.Status;
import game.utils.SurroundingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A spirit goat class
 * @author Jared Kosem (Modified by Sanjevan Rajasegar = Version 2)
 */
public class SpiritGoat extends PeacefulCharacter implements Cure, Reproduce {
    /**
     * Flag to indicate whether the Spirit Goat has already reproduced.
     */
    private boolean hasReproduced = false;

    /**
     * Random instance used to select a location for offspring placement
     */
    private final Random random = new Random();

    /**
     * Display instance used to print game messages
     */
    private Display display = new Display();

    /**
     * The current cooldown counter for reproduction
     */
    private int reproduceCooldown = 0;

    /**
     * Constant defining the cooldown duration after reproduction
     */
    private static final int COOLDOWN = 15;

    /**
     * Constructor for a SpiritGoat with default prioritised behavior selection.
     * Initialises with:
     * - Name: "Spirit Goat"
     * - Display character: 'y'
     * - Hitpoints: 50
     * - CURABLE status flag
     * - Initial lifespan: 10 turns
     */
    public SpiritGoat() {
        this(new PrioritisedBehaviourSelection());
    }

    /**
     * Constructs a SpiritGoat with custom behavior selection strategy.
     *
     * @param behaviourSelection The behavior selection strategy to use
     */
    public SpiritGoat(BehaviourSelection behaviourSelection) {
        super("Spirit Goat", 'y', 50, behaviourSelection);
        this.countdown = 10;
        this.addCapability(Status.CURABLE);
    }

    /**
     * Constructor for Spirit Goat with a custom initial reproduction cooldown.
     * Used when spawning new Spirit Goat offspring
     *
     * @param initialCooldown The initial cooldown before the goat can reproduce again
     */
    private SpiritGoat(int initialCooldown) {
        this(); // Call default constructor
        this.reproduceCooldown = initialCooldown;
    }

    /**
     * A method that decrements the lifespan every tick
     * @param actions    The collection of possible Actions for this Actor
     * @param lastAction The last action took
     * @param map        The map containing the Actor
     * @param display    The display
     * @return  The parent class playTurn which was the behavior
     */
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        countdown--;
        if (countdown <= 0) {
            map.removeActor(this);
            return new DoNothingAction();
        }

        if (reproduceCooldown > 0) {
            reproduceCooldown--;
        }

        if (!hasReproduced && reproduceCooldown == 0) {
            Location currentLocation = map.locationOf(this);

            if (SurroundingUtils.hasSurroundingCapability(currentLocation, Status.HAS_GRACE)) {
                this.onReproduce(map, this);
                hasReproduced = true; // Mark as reproduced
                reproduceCooldown = COOLDOWN;
            }
        }

        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * The method for curing the goat which sets its lifespan back to 1
     * @param actor The actor performing the purification
     * @param location The location of the entity
     * @return The outcome of the cure
     */
    @Override
    public String cure(Actor actor, Location location) {
        this.countdown = 10;
        return "Spirit Goat has been cured... for now.";
    }

    /**
     * A method for returning the cost of curing
     * @return An integer representation of the cost
     */
    @Override
    public int getCureCost() {
        return 0;
    }

    /**
     * Spawns a new Spirit Goat in a random adjacent location if possible
     * @param map   The game map
     * @param actor The parent Spirit Goat
     */
    @Override
    public void onReproduce(GameMap map, Actor actor) {
        List<Location> possibleLocations = new ArrayList<>();
        Location currentLocation = map.locationOf(actor);

        for (Exit exit : currentLocation.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) { // Check if a new goat can spawn here
                possibleLocations.add(destination);
            }
        }

        if (!possibleLocations.isEmpty()) {
            Location spawnLocation = possibleLocations.get(random.nextInt(possibleLocations.size()));
            SpiritGoat newGoat = new SpiritGoat(COOLDOWN);
            map.addActor(newGoat, spawnLocation);
            display.println("SpiritGoat produced a new offspring due to the effects of grace!");
        }
    }
}