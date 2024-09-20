package src.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

/**
 * RecipeFinder class. This will find the matching recipe.
 * 
 * @author Abby Bellamy
 * @author Eulices Gomez
 * @author Keith Haith
 * @author Gus McKee
 * @author Artem Tikhomirov
 */
public class RecipeFinder {
	public static final String RECIPE_FILE = "RecipeListWithIngredients.txt";
	public static final String INSTRUCTION_FILE = "InstructionList.txt";
	private static List<Recipe> closeList = new ArrayList<>();

	public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[32m";
	public static final String BLUE = "\u001B[34m";
	public static final String RED = "\u001B[31m";
	public static final String PURP = "\u001B[35m";
	public static final String WHITE = "\u001B[37m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE_UNDER = "\033[4;37m";
	public static final String BOLD_BLUE = "\033[1;94m";
	public static final String BOLD_GREEN = "\033[1;92m";
	public static final String BOLD_RED = "\033[1;91m";

	/**
	 * This method takes 2 arguments an Ingredient Object
	 * and a List of Recipe Objects and returns recipes
	 * that only differ by at most 2 ingredients
	 * 
	 * @param Ingredient - the Ingredients that came from the user
	 * @param List       - a generic list of recipes
	 * @return List - that returns the matching recipes from inregidents
	 */
	public static List<Recipe> findRecipes(Ingredient targetIngredients, List<Recipe> recipes) {
		List<Recipe> matchingRecipes = new ArrayList<>();
		List<String> targetList = targetIngredients.getIngredientList();
		List<String> recipeList = new ArrayList<>();

		for (Recipe recipe : recipes) {
			Ingredient recipeIngredients = recipe.getIngredients();

			recipeList = recipeIngredients.getIngredientList();

			int count = 0;
			for (String ingredient : recipeList) {
				if (!targetList.contains(ingredient))
					count++;
			}
			if (count <= 3) {
				matchingRecipes.add(recipe);
			} else if (count > 3 && count < 5)
				closeList.add(recipe);
		}
		return matchingRecipes;

	}

	/**
	 * This method will find what ingredients are missing
	 * and then tell the user the ingredients.
	 * 
	 * @param match    - the recipe matches that were found in main
	 * @param userFood - the Ingredient object of the users foods
	 * @return void
	 */
	public static void missingIngredients(List<Recipe> match, Ingredient userFood) {
		System.out.print(RESET);
		Ingredient recipeIngredients;
		List<String> recipeFoods;
		List<String> userFoods = userFood.getIngredientList();
		List<String> missingFoods = new ArrayList<>();
		List<String> missingFoodsTemp = new ArrayList<>();
		HashMap<Recipe, List<String>> incompleteRecipes = new HashMap<Recipe, List<String>>();

		for (Recipe recipe : match) {
			recipeIngredients = recipe.getIngredients();
			recipeFoods = recipeIngredients.getIngredientList();
			missingFoods.clear();
			for (String ingredient : recipeFoods) {
				if (!userFoods.contains(ingredient))
					missingFoods.add(ingredient);
			}
			missingFoodsTemp = cloneList(missingFoods);

			if (missingFoods.isEmpty())
				break;
			else
				incompleteRecipes.put(recipe, missingFoodsTemp);
		}

		if (match.isEmpty()) {
			return;
		} else {
			if (incompleteRecipes.isEmpty())
				System.out.println(BOLD_GREEN + "You have no missing foods for your recipe(s)!");
			else {
				System.out.println(BOLD_RED + "Your missing ingredients per recipe:" + RESET);
				for (Recipe r : incompleteRecipes.keySet()) {
					System.out.println(GREEN + r.getName() + WHITE + ": " + incompleteRecipes.get(r) + RESET + "\n");
				}
			}
		}
	}

	public static List<Recipe> matchInstructions(List<Recipe> matchingRecipes, List<Recipe> theInstructions) {
		List<Recipe> matchedRecipe = new ArrayList<>();
		List<String> targetList = new ArrayList<>();

		for (Recipe rname : matchingRecipes) {
			targetList.add(rname.getName());
		}

		for (Recipe instruction : theInstructions) {
			if (targetList.contains(instruction.getName())) {
				matchedRecipe.add(instruction);
			}
		}

		return matchedRecipe;
	}

	/**
	 * This method creates recipe objects using the RecipeList textfile,
	 * mostly scans for matches and processes the recipe txt file.
	 * 
	 * @param void
	 * @return List - that returns a recipes requested
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

	public static void nearRecipes(Ingredient userFood, List<Recipe> match) {
		if (closeList.isEmpty() && !match.isEmpty()) {
			System.out.println(CYAN + "There are no additional recipes that are a 50%+ match.");
		} else if (closeList.isEmpty()) {
			System.out.println(CYAN + "There are no recipes that are a 50%+ match you could almost make.");
		} else {
			System.out.println(BOLD_BLUE + "Here are the recipes that are a 50%+ match that you could almost make."
					+ RESET);
			for (Recipe r : closeList) {
				System.out.println(r.getName());
			}
		}
	}

	/**
	 * This method creates recipe objects that contain the instructions using the
	 * InstructionList textfile,
	 * mostly scans for matches and processes the recipe txt file.
	 * 
	 * @param void
	 * @return List - that returns a recipes requested
	 */
	public static List<Recipe> getInstruction() {
		ArrayList<Recipe> theInstructions = new ArrayList<Recipe>();
		ArrayList<String> theIngredients = null;
		File instructionFile = new File(INSTRUCTION_FILE);

		try (Scanner fileScanner = new Scanner(instructionFile)) {
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
					theInstructions.add(currentRecipe);
				}

			}
			fileScanner.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("Could not open file " + RECIPE_FILE);
			e.printStackTrace();
		}

		return theInstructions;
	}

	/**
	 * The clone implementation to be able to
	 * prevent the memory aliasing from before.
	 * 
	 * @param list - generic list that is going to be copied
	 * @return - List<T> - the clone to be returned
	 */
	public static <T> List<T> cloneList(List<T> list) {
		List<T> clone = new ArrayList<T>();
		for (T item : list) {
			clone.add(item);
		}
		return clone;
	}

	/**
	 * This method finds and prints the recipes that matched.
	 * 
	 * @param List - recipes that were found, Prints none if no matches
	 * @return void
	 */
	public static void recipePrint(List<Recipe> rec) {
		if (!rec.isEmpty()) {
			System.out.println("\nHere are your 90%+ matching recipes:" + RESET + GREEN);
			for (Recipe recipe : rec) {
				System.out.println(recipe.getName() + "\n");
			}
		} else {
			System.out.println("There are no recipes that match the ingredients listed");
		}
	}

	public static void instructionPrint(List<Recipe> instr) {
		if (!instr.isEmpty()) {
			System.out.println("Here are your matching Recipes: ");
			for (Recipe recipe : instr) {
				System.out.println(GREEN + recipe.getName());
				System.out.println(recipe.getIngredients());
			}
		} else {
			System.out.println("There are no recipes that match the ingredients listed." + RESET);
		}
	}
}
