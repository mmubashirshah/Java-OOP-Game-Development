package game.conditions;

import edu.monash.fit2099.engine.positions.Location;

/**
 * A hatch condition that always enables hatching regardless of the egg's location
 *
 * @Author Sanjevan Rajasegar (Version 1)
 */
public class NonConstraintHatch implements HatchCondition {
    /**
     * Determines whether an egg can hatch at a given location
     * this always returns true
     *
     * @param location the location where the egg is being checked
     * @return true always
     */
    @Override
    public boolean canHatch(Location location) {
        return true;
    }
}