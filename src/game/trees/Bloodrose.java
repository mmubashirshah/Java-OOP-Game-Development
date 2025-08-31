package game.trees;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A Bloodrose plant that can cause damage to surrounding actors
 * @author Jared Kosem
 */
public class Bloodrose extends Ground {
    private boolean inital = true;
    private int DAMAGE = 10;
    private int BASE_DAMAGE = 5;

    /**
     * Constructor that initialises a Bloodrose with character 'w'
     */
    public Bloodrose() {
        super('w', "Bloodrose");
    }

    /**
     * A method that ensures the Bloodrose causes damage to surrounding actors every tick of the game and ensures initial damage
     * @param location The location of the ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (inital) {
            effectReduceHP(location);
            inital = false;
        }
        effectDamageSurrounding(location);
    }

    /**
     * A method that marks the Bloodrose tree as a wall
     * @param actor the Actor to check
     * @return A boolean value on whether the actor can enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * A method to damage the actors surrounding a Bloodrose by 10 and to ensure that if an actor is on top of the tree it will still get damaged
     * @param location The location of the ground
     */
    private void effectDamageSurrounding(Location location) {
        Actor actorCenter = location.getActor();
        if (actorCenter != null && location.getGround().getDisplayChar() == 'w') {
            actorCenter.hurt(DAMAGE);
        }
        for (Exit exit: location.getExits()) {
            Location destination = exit.getDestination();
            Actor actor = destination.getActor();
            if (actor != null) {
                actor.hurt(DAMAGE);
            }
        }
    }

    /**
     * A method to initially reduce the actors health by 5
     * @param location
     */
    private void effectReduceHP(Location location) {
        Actor actor = location.getActor();
        if (actor == null) {
            return;
        }
        actor.hurt(BASE_DAMAGE);
    }
}
