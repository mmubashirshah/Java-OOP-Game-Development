package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CraftAction;
import game.capabilities.Status;
import game.recipes.Recipe;
import game.recipes.RecipeHandler;

/**
 * Arcane bench that allows for crafting
 * @author Jared Kosem
 */
public class ArcaneBench extends Ground {

    /**
     * Creates the arcane bench which has capability of crafting station which may be used in the future
     */
    public ArcaneBench() {
        super('&', "Arcane Bench");
        this.addCapability(Status.CRAFTING_STATION);
    }

    /**
     * Method to detect if a player is nearby and then give them a crafting action
     * @param actor     the Actor
     * @param location  the current Location
     * @param direction the direction
     * @return the ActionList
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        if (!actor.hasCapability(Status.IS_PLAYER)) {
            return actions;
        }
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor() && destination.getActor().hasCapability(Status.IS_PLAYER)) {
                RecipeHandler handler = new RecipeHandler();
                for (Recipe recipe : handler.getRecipes()) {
                    actions.add(new CraftAction(recipe));
                }
            }
        }
        return actions;
    }

    /**
     * Method to make the player unable to walk over the bench
     * @param actor the Actor to check
     * @return false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}