package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.weapons.BareFist;
import game.actions.ListenAction;
import game.capabilities.Status;
import game.behaviours.GutsAttackBehaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * A hostile NPC that wanders, automatically attacks any adjacent actor
 * whose HP < 50 using its bare fists, and can be listened to (monologues).
 * @Author Muhammad Mubashir Shah
 */
public class Guts extends HostileCharacter implements Talkable {

    /**
     * Constructs Guts with 500 HP, a BareFist as intrinsic weapon,
     * and registers the custom attack behaviour.
     */
    public Guts() {
        super("Guts", 'g', 500);
        this.setIntrinsicWeapon(new BareFist());
        this.addBehaviour(1, new GutsAttackBehaviour());
    }

    /**
     * Allows the player (actor with IS_PLAYER) to add ListenAction.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        if (otherActor.hasCapability(Status.IS_PLAYER)) {
            actions.add(new ListenAction(this));
        }
        return actions;
    }

    /**
     * Provides Guts’s monologue pool.  Always includes two battle cries;
     * if the listener is the player and has < 50 HP, also adds a taunt.
     *
     * @param listener the actor listening (usually the player)
     * @param map the game map
     * @return a list of available monologue lines
     */
    @Override
    public List<String> getTalkingLines(Actor listener, GameMap map) {
        List<String> lines = new ArrayList<>();
        lines.add("RAAAAGH!");
        lines.add("I’LL CRUSH YOU ALL!");
        if (listener.hasCapability(Status.IS_PLAYER)
                && listener.getAttribute(BaseActorAttributes.HEALTH) < 50) {
            lines.add("WEAK! TOO WEAK TO FIGHT ME!");
        }
        return lines;
    }
}
