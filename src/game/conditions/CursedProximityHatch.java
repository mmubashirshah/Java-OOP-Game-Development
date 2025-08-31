package game.conditions;

import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.utils.SurroundingUtils;

/**
 * A condition for hatching only near when cursed entities are nearby, implementing the HatchCondition
 * interface
 *
 * @Author Sanjevan Rajasegar (Version 1)
 */
public class CursedProximityHatch implements HatchCondition {
    /**
     * Determines if an egg can hatch based on the proximity to a cursed entity/ground
     *
     * @param location The current location of the egg attempting to hatch
     * @return A boolean of whether the egg can hatch or not based on these conditions
     */
    @Override
    public boolean canHatch(Location location) {
        return SurroundingUtils.hasSurroundingCapability(location, Status.CURSED);
    }
}
