package game.seeds;

import edu.monash.fit2099.engine.positions.Ground;
import game.trees.Bloodrose;

/**
 * A Bloodrose seed that can be used to plant a Bloodrose and return the stamina cost of planting the seed
 * @author Jared Kosem
 */
public class BloodroseSeed extends Seed {
    private int STAMINA_COST = 75;

    /**
     * Constructor that initialises the Bloodrose seed with the character '*'
     */
    public BloodroseSeed() {
        super("Bloodrose seed", '*', true);
    }

    /**
     * A method for returning the stamina cost of planting the Bloodrose seed
     * @return An integer representation of the stamina cost
     */
    @Override
    public int getStaminaCost() {
        return STAMINA_COST;
    }

    /**
     * A method for creating the Bloodrose tree
     * @return The Bloodrose tree to be created
     */
    @Override
    public Ground createPlant() {
        return new Bloodrose();
    }
}
