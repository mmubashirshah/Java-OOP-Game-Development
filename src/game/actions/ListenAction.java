package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Talkable;

import java.util.List;
import java.util.Random;

/**
 * An Action that lets one Actor (usually the player) listen to another Actor
 * that implements Talkable. When executed, it gathers all available
 * lines via Talkable.getTalkingLines(Actor, GameMap) and prints one at random.
 * @Author Muhammad Mubashir Shah
 */
public class ListenAction extends Action {

    /** The actor being listened to. */
    private final Talkable target;

    /**
     * Constructor.
     *
     * @param target the Talkable actor whose lines will be spoken
     */
    public ListenAction(Talkable target) {
        this.target = target;
    }

    /**
     * Executes the listening: fetches the Talkable’s available lines and
     * returns one at random, or a message of silence if none are present.
     *
     * @param actor the actor performing the listening (the listener)
     * @param map the map the actors are on
     * @return the chosen line wrapped in quotes, or a silence message
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        List<String> lines = target.getTalkingLines(actor, map);
        if (lines.isEmpty()) {
            return actor + " hears silence from " + target + ".";
        }
        String chosen = lines.get(new Random().nextInt(lines.size()));
        return "\"" + chosen + "\"";
    }

    /**
     * The menu text shown to the listener.
     *
     * @param actor the actor performing the action
     * @return a description like "Farmer listens to Guts"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " listens to " + target;
    }
}
