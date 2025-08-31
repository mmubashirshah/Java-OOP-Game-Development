package game.grounds.cursed;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.conditions.Cure;
import game.conditions.CursedGround;
import game.grounds.Soil;
import game.capabilities.Status;

/**
 * A class representing a blight covering the ground of the valley.
 * @author Adrian Kristanto and Jared Kosem
 */
public class Blight extends Ground implements CursedGround, Cure {

    /**
     * Constructor that initialises the blight as 'x' with the CURABLE status
     */
    public Blight() {
        super('x', "Blight");
        this.addCapability(Status.CURABLE);
        this.addCapability(Status.CURSED);
    }

    /**
     * A method for returning the char representation of the blight knowing that it is cursed
     * @return
     */
    @Override
    public char getCursedChar() {
        return 'x';
    }

    /**
     * A method for curing the blight where the ground is replaced with soil and the stamina of the actor is decreased
     * @param actor The actor performing the purification
     * @param location The location of the blight
     * @return A string return to show that the blight has been cured
     */
    @Override
    public String cure(Actor actor, Location location) {
        location.setGround(new Soil());
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 50);
        return "Blight has been cured...";
    }

    @Override
    public int getCureCost() {
        return 50;
    }
}
