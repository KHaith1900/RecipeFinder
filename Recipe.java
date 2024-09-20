
/**
 * This is the main Recipe entity for the project.
 * 
 * It is called recipe. This is where the recipes will be
 * processed and created for use in main.
 * 
 * @author Abby Bellamy
 * @author Eulices Gomez
 * @author Keith Haith
 * @author Gus McKee
 * @author Artem Tikhomirov
 */
public class Recipe {

	private String name;
	private Ingredient ingredients;

	/**
	 * Recipe constructor.
	 * 
	 * @param String     - the name of the recipe
	 * @param Ingredient - the ingredients that are the Recipe
	 * @return void
	 */
	public Recipe(String name, Ingredient ingredients) {
		this.name = name;
		this.ingredients = ingredients;
	}

	/**
	 * Recipe method that returns
	 * the name of the recipe.
	 * 
	 * @param void
	 * @return String - the name of the recipe
	 */
	public String getName() {
		return name;
	}

	/**
	 * Recipe method that sets the
	 * name of the recipe.
	 * 
	 * @param String - the name of the recipe
	 * @return void
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * GetIngredients that returns the ingreidents in the recipe.
	 * 
	 * @param void
	 * @return Ingredient - the list of ingreidents for the recipe
	 */
	public Ingredient getIngredients() {
		return ingredients;
	}

	/**
	 * setIngredients sets the ingrident list of a recipe
	 * 
	 * @param Ingredient - ingredients list
	 * @return void
	 */
	public void setIngredients(Ingredient ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * toString for the recipe class
	 * 
	 * @param void
	 * @return String - the string of the recipe names
	 */
	public String toString() {
		return "Name: " + name;
	}
}
