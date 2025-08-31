package game.components;

import game.actors.BedOfChaosBoss;

/**
 * Interface for components of the Bed of Chaos tree structure.
 * Each component may contribute to the boss's damage or healing, and may grow further.
 *
 * @author Khanh Le
 */
public interface BossComponent {

    /**
     * Grows a new component (if applicable) and adds it to the boss.
     *
     * @param boss the Bed of Chaos boss to which the component may add growth
     */
    void grow(BedOfChaosBoss boss);

    /**
     * Returns the additional damage contributed by this component to the boss's total attack power.
     *
     * @return damage contribution
     */
    int getDamageContribution();

    /**
     * Returns the amount of healing this component provides when grown.
     *
     * @return healing amount
     */
    int getHealingAmount();
}