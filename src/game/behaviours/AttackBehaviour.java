package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;
import game.capabilities.Status;

/**
 * A behaviour for hostile creatures to attack those that are hostile to the creature
 * @author Jared Kosem
 */
public class AttackBehaviour implements Behaviour {

    /**
     * Checks the surrounding of the actor creature and attacks those that are hostile
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor() && destination.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
                return new AttackAction(destination.getActor(), exit.getName(), getWeapon());
            }
        }
        return null;
    }

    /**
     * A method to get the weapon used to attack
     * @return To be overridden by child classes such that it can attack with a different weapon
     */
    public Weapon getWeapon() {
        return null;
    }
}
