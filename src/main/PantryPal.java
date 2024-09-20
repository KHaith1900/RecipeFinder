package src.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the main entity for our project.
 * 
 * It is called PantryPal. Where when given a list of ingredients,
 * the program returns a recipe and how to make said recipe.
 * 
 * @author Abby Bellamy
 * @author Eulices Gomez
 * @author Keith Haith
 * @author Gus McKee
 * @author Artem Tikhomirov
 */
public class PantryPal {
	// global variable so that the user's list can
	// be accessed without an object in the other classes
	public static ArrayList<String> ingredientList;
	public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[32m";
	public static final String BLUE = "\u001B[34m";
	public static final String RED = "\u001B[31m";
	public static final String PURP = "\u001B[35m";
	public static final String WHITE = "\u001B[37m";
	public static final String CYAN = "\u001B[36m";
	public static final String RED_BACK = "\u001B[41m";
	public static final String GREEN_BACK = "\u001B[42m";
	public static final String BOLD_PINK = "\033[1;95m";
	public static final String BOLD_BLUE = "\033[1;94m";

	/**
	 * The main method takes input from the user and creates an Ingredient Object.
	 * The object is then used in the findRecipe Method.
	 * If there are not enough ingredient matches no recipe is returned.
	 * Mainly done in the terminal, no returns and such.
	 */
	public static void main(String[] args) {
		ingredientList = new ArrayList<String>();

		System.out.println(GREEN + "Welcome to " + WHITE + GREEN_BACK + "Pantry Pal" + RESET + GREEN +
				", where your food troubles will go away :)");
		System.out.println(BLUE + "Enter a new ingredient or type " + RED + "exit" +
				BLUE + " to end. \n" + "If you wish to" + RED + " remove " +
				BLUE + "ingredient type -'ingredient'." + PURP);

		Scanner kb = new Scanner(System.in);

		String word;

		while (true) {
			word = kb.nextLine();
			try {
				if (word.equals("exit"))
					break;
				else if (word.substring(0, 1).equals("-"))
					ingredientList.remove(word.substring(1).toUpperCase());
				else
					ingredientList.add(word.toUpperCase());
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Please enter a valid ingredient");
			}
		}
		kb.close();
		System.out.println(BLUE + "Your final list of Ingredients are: " + RESET +
				ingredientList);

		List<Recipe> recipes = RecipeFinder.recipeGet();

		List<Recipe> instructions = RecipeFinder.getInstruction();

		Ingredient list = new Ingredient(ingredientList);

		List<Recipe> rec = RecipeFinder.findRecipes(list, recipes);

		List<Recipe> instr = RecipeFinder.matchInstructions(rec, instructions);

		if (!instr.isEmpty())
			System.out.print(BOLD_BLUE);
		else
			System.out.print(RED);

		RecipeFinder.instructionPrint(instr);
		System.out.print(RESET);
		RecipeFinder.missingIngredients(rec, list);
		RecipeFinder.nearRecipes(list, rec);
		System.out.println(BOLD_PINK + "Thank you for using PantryPal! Enjoy your yummy meal.");
	}

}