package game.seeds;

import edu.monash.fit2099.engine.positions.Ground;
import game.conditions.CursedGround;
import game.trees.Inheritree;

/**
 * The inheritree seed that creates a inheritree of cursed ground type
 * @author Jared Kosem
 */
public class InheritreeSeed extends Seed {
    private final CursedGround cursedGround;
    private int STAMINA_COST = 25;

    /**
     * Initalises the inheritree seed and the type of cursed ground
     * @param cursedGround
     */
    public InheritreeSeed(CursedGround cursedGround) {
        super("Inheritree seed" + " (" + cursedGround.getCursedChar() + ")", '*', true);
        this.cursedGround = cursedGround;
    }

    /**
     * A method that gets the stamina cost
     * @return An integer representing the stamina cost
     */
    @Override
    public int getStaminaCost() {
        return STAMINA_COST;
    }

    /**
     * A method that creates the inheritree with the cursed ground type
     * @return The inheritree
     */
    @Override
    public Ground createPlant() {
        return new Inheritree(cursedGround);
    }
}
