package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface that represents the ability of an Actor to produce an egg
 * defining what happens when an egg is produced
 * @author Jared Kosem
 */
public interface ProduceEgg {
    /**
     * Called when an Actor produces an egg in the game world.
     *
     * @param map The current game map where the egg will be produced
     * @param actor The Actor that is producing the egg
     */
    void onProduceEgg(GameMap map, Actor actor);
}
