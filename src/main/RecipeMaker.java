package src.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the RecipeMaker which will read the recipe file and find a match
 * if there is one to find.
 * 
 * @author Abby Bellamy
 * @author Eulices Gomez
 * @author Keith Haith
 * @author Gus McKee
 * @author Artem Tikhomirov
 */
public class RecipeMaker {
	public static final String RECIPE_FILE = "RecipeList with Ingredients.txt";

	/**
	 * The method that gets the recipe from the
	 * recipe file based on matching ingredients.
	 * 
	 * @param void
	 * @return List - the list of recipes to be returned
	 */
	public static List<Recipe> recipeGet() {

		ArrayList<Recipe> theRecipes = new ArrayList<Recipe>();
		ArrayList<String> theIngredients = null;
		File recipeFile = new File(RECIPE_FILE);

		try (Scanner fileScanner = new Scanner(recipeFile)) {
			Recipe currentRecipe = null;
			Ingredient currentIngredients = null;
			while (fileScanner.hasNext()) {
				String line = fileScanner.nextLine();
				if (line.startsWith("*")) {
					theIngredients = new ArrayList<String>();
					String recipeName = line.substring(1).replaceAll(":$", "");
					currentRecipe = new Recipe(recipeName, null);
				}

				else if (line.startsWith("-")) {
					String ingredientName = line.substring(1).replaceAll(".$", "");
					theIngredients.add(ingredientName.toUpperCase());
					currentIngredients = new Ingredient(theIngredients);

				}

				else if (line.startsWith("/")) {
					currentRecipe.setIngredients(currentIngredients);

					theRecipes.add(currentRecipe);
				}

			}
			fileScanner.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("Could not open file " + RECIPE_FILE);
			e.printStackTrace();
		}

		return theRecipes;
	}

	/**
	 * Main which prints the recipes out.
	 * 
	 * @param String[]
	 * @return void
	 */
	public static void main(String[] args) {
		List<Recipe> recipes = recipeGet();

		for (Recipe recipe : recipes) {
			System.out.println("Recipe: " + recipe.getName());
			System.out.println(recipe.getIngredients());
			System.out.println("--------------------------------------------");
		}
	}
}
