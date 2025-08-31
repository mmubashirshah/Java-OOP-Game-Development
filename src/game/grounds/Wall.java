package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing a wall that cannot be entered by any actor
 * @author Riordan D. Alfredo
 */
public class Wall extends Ground {

    /**
     * Initialises the wall
     */
    public Wall() {
        super('#', "Wall");
    }

    /**
     * A method that marks the wall as a wall
     * @param actor The actor
     * @return A boolean value on whether the actor can enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
