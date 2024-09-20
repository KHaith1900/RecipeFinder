package src.main;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Ingredient File. Where all the Ingredient Objects are handled.
 * 
 * @author Abby Bellamy
 * @author Eulices Gomez
 * @author Keith Haith
 * @author Gus McKee
 * @author Artem Tikhomirov
 */

public class Ingredient {

	private ArrayList<String> ingredientList;
	public static final String BLUE = "\u001B[34m";

	/**
	 * Takes an ArrayList of ingredients and makes an
	 * object of ingredient type.
	 * 
	 * @param list - Arraylist of ingredients
	 * @return void
	 */
	public Ingredient(List<String> list) {
		this.ingredientList = (ArrayList<String>) list;
	}

	/**
	 * getIngredientList which returns the ingredientList object created.
	 * 
	 * @param void
	 * @return ArrayList - the ingredientList
	 */
	public ArrayList<String> getIngredientList() {
		return ingredientList;
	}

	/**
	 * addIngredient which takes an Ingredient
	 * and adds it to the list.
	 * 
	 * @param String - ingredient to add to the list object
	 * @return void
	 */
	public void addIngredient(String ingredient) {
		ingredientList.add(ingredient);
	}

	/**
	 * removeIngredient which takes a string
	 * input and removes it from the list
	 * 
	 * @param String - the ingredient to remove
	 * @return void
	 */
	public void removeIngredient(String ingredient) {
		ingredientList.remove(ingredient);
	}

	/**
	 * toString method whichs returns the ingredient list
	 * object as a String.
	 * 
	 * @param void
	 * @return void
	 */
	public String toString() {
		String result = BLUE + "Ingredients:\n";

		for (String ingredient : ingredientList) {
			result += ingredient + "\n";
		}

		return result;
	}
}