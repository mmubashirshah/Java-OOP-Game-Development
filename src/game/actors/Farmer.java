package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.capabilities.Ability;
import game.capabilities.Status;
import game.displays.FancyMessage;
import game.actions.AttackAction;

/**
 * A farmer that is a form of player in which it can attack nearby NPCs
 * @author Jared Kosem (Modified by Sanjevan Rajasegar - Version 2)
 */
public class Farmer extends Player {
    /**
     * Initialise a farmer of character '@' with 100 hp and 200 stamina
     */
    public Farmer() {
        super("Farmer", '@', 100);
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(200));
        this.addCapability(Status.IS_PLAYER); // adds the status to solidify that they are the player
        this.addCapability(Status.CAN_BE_FOLLOWED); // enables the status for the Farmer to be followed
        this.addCapability(Ability.CAN_RECEIVE_RUNES);
    }

    /**
     * A method for adding an attack action if an NPC is detected in the surroundings
     * @param actions    The collection of possible Actions for this Actor
     * @param lastAction The last action took
     * @param map        The map containing the Actor
     * @param display    The display
     * @return  The parent class playTurn which was the behaviour
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (!this.isConscious()) {
            unconscious(this, map);
            display.println(FancyMessage.YOU_DIED);
            return new DoNothingAction();
        }

        display.println("Runes Available: " + this.getBalance());

        Location location = map.locationOf(this);
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (map.isAnActorAt(destination)) {
                Actor target = map.getActorAt(destination);

                if (target.hasCapability(Status.EATABLE)) {
                    actions.add(new ConsumeAction(target));
                }

                actions.add(new AttackAction(target, exit.getName()));

            }
        }
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * A method for the menu representation of the farmer
     * @return The string representation
     */
    @Override
    public String toString() {
        return name + " HP (" +
                this.getAttribute(BaseActorAttributes.HEALTH) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                ")" + " SP (" +
                this.getAttribute(BaseActorAttributes.STAMINA) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.STAMINA)
                + ")";
    }
}