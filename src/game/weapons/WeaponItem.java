package game.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.*;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;

import java.util.Random;

/**
 * Class representing items that can be used as a weapon. (Modified as Purchasable Weapon, can be redone further if it can not be purchased)
 * @author Adrian Kristanto (Modified by Jared and Khanh - version 2)
 */
public abstract class WeaponItem extends Item implements Weapon, Purchasable {
    private static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0f;
    private int damage;
    private int hitRate;
    private final String verb;
    private float damageMultiplier;

    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public WeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, true);
        this.damage = damage;
        this.verb = verb;
        this.hitRate = hitRate;
        this.damageMultiplier = DEFAULT_DAMAGE_MULTIPLIER;
    }

    /**
     * A method that determines the amount of damage the weapon does
     * @param attacker The actor who performed the attack
     * @param target   The actor who is the target of the attack
     * @param map      The map on which the attack was executed
     * @return The amount of damage the weapon does
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();
        if (!(rand.nextInt(100) < this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(Math.round(damage * damageMultiplier));

        return String.format("%s %s %s for %d damage", attacker, verb, target, damage);
    }

    /**
     * Allows this weapon to be used for attacking nearby actors.
     * Returns an AttackAction targeting the given actor in the specified direction.
     *
     * @param otherActor The adjacent actor to attack
     * @param location   The actor's location, used for direction info
     * @return An ActionList with an AttackAction using this weapon
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actionList = new ActionList();
        actionList.add(new AttackAction(otherActor, location.toString(), this));
        return actionList;
    }
}
