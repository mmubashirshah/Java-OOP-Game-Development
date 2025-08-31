package game.components;

import edu.monash.fit2099.engine.displays.Display;
import game.actors.BedOfChaosBoss;

/**
 * A leaf provides 1 additional attack power and heals the Bed of Chaos by 5 when grown.
 * Leaves cannot grow additional components.
 *
 * @author Khanh Le
 */
public class Leaf implements BossComponent {

    /**
     * Leaf cannot grow anything further.
     *
     * @param boss the Bed of Chaos boss
     */
    @Override
    public void grow(BedOfChaosBoss boss) {
        // Leaf cannot grow anything
    }

    /**
     * Returns the damage contribution of a leaf.
     *
     * @return 1
     */
    @Override
    public int getDamageContribution() {
        return 1;
    }

    /**
     * Returns the healing amount when the leaf is grown.
     *
     * @return 5
     */
    @Override
    public int getHealingAmount() {
        return 5;
    }
}
