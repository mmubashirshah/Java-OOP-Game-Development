package game.behaviours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;

import game.actions.AttackAction;

/**
 * A Behaviour for Guts: scan adjacent tiles,
 * and if any Actor has more than 50 HP, perform an attack with the
 * actor's intrinsic weapon.
 * @Author Muhammad Mubashir Shah
 */
public class GutsAttackBehaviour implements Behaviour {

    /**
     * Finds a valid target in adjacent locations and returns an AttackAction
     * if that target has HP 50.
     *
     * @param actor the Guts instance performing the check
     * @param map the game map
     * @return an AttackAction if a valid target is found, otherwise null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        for (Exit exit : here.getExits()) {
            Location dest = exit.getDestination();
            if (dest.containsAnActor()) {
                Actor target = dest.getActor();
                Integer hp = target.getAttribute(BaseActorAttributes.HEALTH);
                if (hp != null && hp > 50) {
                    Weapon weapon = actor.getIntrinsicWeapon();
                    return new AttackAction(target, exit.getName(), weapon);
                }
            }
        }
        return null;
    }
}
