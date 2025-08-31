package game.utils;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Utility class for checking capabilities in surrounding locations.
 */
public class SurroundingUtils {

    /**
     * Checks if any adjacent locations (ground, actors, or items) have the specified capability.
     * @param location Center location to check around
     * @param capability Capability to look for
     * @return true if capability is found in any adjacent location
     */
    public static boolean hasSurroundingCapability(Location location, Enum<?> capability) {
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();

            if (destination.getGround().hasCapability(capability)) return true;

            Actor actor = destination.getActor();
            if (actor != null && actor.hasCapability(capability)) return true;

            for (Item item : destination.getItems()) {
                if (item.hasCapability(capability)) return true;
            }
        }
        return false;
    }
}