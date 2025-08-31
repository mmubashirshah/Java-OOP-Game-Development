package game.conditions;

import edu.monash.fit2099.engine.positions.Location;

/**
 * An interface representing the condition under which an egg/entity can hatch
 * @Author Sanjevan Rajasegar (Version 1)
 *
 * REFERENCE - SANJEVAN RAJASEGAR
 * I used DeepSeek AI (https://deepseek.com) to help figure out the logic of implementing
 * follow behaviour for the Golden Beetle, and to inform the design of a flexible
 * hatching system using a HatchCondition interface and multiple implementing classes
 * The tool was used to guide the structuring of conditional logic
 * and OOP. This ensures the code is extensible for future iterations
 */
public interface HatchCondition {
    /**
     * Determines whether hatching is permitted at the given location.
     *
     * @param location the location to evaluate for hatching eligibility
     * @return true if hatching is allowed at the location, false oetherwise
     */
    boolean canHatch(Location location);

    /**
     * A turn based condition for hatching eggs which is defaulted always to true and can be
     * changed for future implementations
     * @return
     */
    default boolean isTurnBased() { return true; }
}