package game.components;

import edu.monash.fit2099.engine.displays.Display;
import game.actors.BedOfChaosBoss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A branch can grow additional branches or leaves, increasing the total attack power.
 * Each branch contributes 3 damage and can recursively grow other components.
 *
 * @author Khanh Le
 */
public class Branch implements BossComponent {

    /**
     * Randomly grows either a new Branch or a Leaf and adds it to the boss.
     *
     * @param boss the Bed of Chaos boss to grow under
     */
    @Override
    public void grow(BedOfChaosBoss boss) {
        if (boss.getRandom().nextDouble() < 0.5) {
            boss.addPart(new Branch());
            System.out.println("Branch is growing...\nit grows a Branch...");
        } else {
            boss.addPart(new Leaf());
            System.out.println("Branch is growing...\nit grows a Leaf...");
        }
    }

    /**
     * Returns the damage contribution of a branch.
     *
     * @return 3
     */
    @Override
    public int getDamageContribution() {
        return 3;
    }

    /**
     * Branches do not contribute to healing.
     *
     * @return 0
     */
    @Override
    public int getHealingAmount() {
        return 0;
    }
}