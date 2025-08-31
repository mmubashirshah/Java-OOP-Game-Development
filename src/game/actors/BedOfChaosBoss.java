package game.actors;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.components.BossComponent;
import game.components.Branch;
import game.components.Leaf;

/**
 * The Bed of Chaos boss that remains stationary and either attacks the player if nearby,
 * or grows new branches and leaves to increase its power over time.
 * Growth only starts after the player has been seen nearby at least once.
 *
 * @author Jared Kosem
 */
public class BedOfChaosBoss extends Actor {
    private boolean hasPlayerEverBeenNearby = false;
    private static final int BASE_DAMAGE = 25;
    private final List<BossComponent> parts = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Constructs the boss with 1000 HP and character T.
     */
    public BedOfChaosBoss() {
        super("Bed of Chaos", 'T', 1000);
    }

    /**
     * Returns the boss's shared random generator.
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Adds a new component (Branch or Leaf) to the boss's internal structure.
     */
    public void addPart(BossComponent part) {
        parts.add(part);
    }

    /**
     * Calculates total attack power based on base and all grown components.
     */
    private int getTotalDamage() {
        int total = BASE_DAMAGE;
        for (BossComponent part : parts) {
            total += part.getDamageContribution();
        }
        return total;
    }

    /**
     * Sums healing from all leaves currently part of the tree.
     */
    private int getHealing() {
        int healing = 0;
        for (BossComponent part : parts) {
            healing += part.getHealingAmount();
        }
        return healing;
    }

    /**
     * Determines what the boss should do on its turn:
     * - If the player is nearby, it attacks.
     * - Otherwise, it grows branches or leaves if the player has ever been nearby.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        Actor target = null;

        // Search for a player in adjacent tiles
        for (Exit exit : location.getExits()) {
            Location dest = exit.getDestination();
            Actor actor = dest.getActor();
            if (actor != null && actor.hasCapability(Status.IS_PLAYER)) {
                target = actor;
                break;
            }
        }

        if (target != null) {

            // Attack the player if nearby
            hasPlayerEverBeenNearby = true;
            int damage = getTotalDamage();
            if (random.nextDouble() < 0.75) {
                target.hurt(damage);
                display.println(this + " attacks " + target + " for " + damage + " damage.");
            }
        } else {

            // Growth phase if the player has been seen before
            if (hasPlayerEverBeenNearby) {
                if (random.nextDouble() < 0.5) {
                    addPart(new Branch());
                    display.println(this + " is growing...");
                    display.println("it grows a Branch...");
                } else {
                    addPart(new Leaf());
                    display.println(this + " is growing...");
                    display.println("it grows a Leaf...");
                }

                // Trigger all components to grow recursively
                List<BossComponent> snapshot = new ArrayList<>(parts);
                for (BossComponent part : snapshot) {
                    part.grow(this);
                }

                // Heal the boss based on current leaves
                int healing = getHealing();
                if (healing > 0) {
                    this.heal(healing);
                    display.println(this + " is healed");
                }
            }
        }

        return new DoNothingAction();
    }
}