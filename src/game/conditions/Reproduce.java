package game.conditions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface which defines the immediate behaviour/actions which occur after a specific Actor
 * has reproduced
 * @Author Jared Kosem
 */
public interface Reproduce {
    /**
     *
     * @param map
     * @param actor
     */
    public void onReproduce(GameMap map, Actor actor);
}
