package game.recipes;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for handling recipes
 * @author Jared Kosem
 */
public class RecipeHandler {
    private final List<Recipe> recipes;

    /**
     * Constructor for depending on recipes
     */
    public RecipeHandler() {
        recipes = new ArrayList<>();
        recipes.add(new Recipe("Broadsword", 5, 0));
        recipes.add(new Recipe("Talisman", 0, 3));
        recipes.add(new Recipe("Dragonslayer Greatsword", 10, 5));
    }

    /**
     * Method for getting recipes
     * @return List of recipes
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }
}