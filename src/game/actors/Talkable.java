package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.List;

/**
 * Interface for actors that can speak monologues when listened to.
 * Implementations return all dialogue lines whose conditions are currently true.
 *
 * @Author Muhammad Mubashir Shah
 */
public interface Talkable {
    /**
     * Returns a list of dialogue lines available given the listener and map context.
     * The caller (e.g. ListenAction) will pick one line at random to display.
     *
     * @param listener The actor (player) who is listening.
     * @param map The game map (for checking surroundings, etc).
     * @return List of applicable monologue lines.
     */
    List<String> getTalkingLines(Actor listener, GameMap map);
}
