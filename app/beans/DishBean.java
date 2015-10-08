package beans;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.ingredient.Ingredient;
import models.lists.Hingredientcategory;
import models.relations.Joincustomerprofilehingredientcategory;
import models.relations.Joindishingredient;
import models.restaurant.Dish;
import models.user.User;

public class DishBean {
	public List<String> listOfIngredients = new ArrayList<String>();
	public List<String> listOfUserExcludedIngredients = new ArrayList<String>();
	public void filllistOfUserExcludedIngredients(User user) {
		List<Joincustomerprofilehingredientcategory> profilecats = Ebean.find(Joincustomerprofilehingredientcategory.class)
				.where()
				.eq("customerprofileid", user.getId())
				.findList();
		for (Joincustomerprofilehingredientcategory profilecat : profilecats) {
			Hingredientcategory category = Ebean.find(Hingredientcategory.class,
					profilecat.getHingredientcategoryid());
			listOfUserExcludedIngredients.add(category.getDescription());
		}
	}
	public List<String> getListOfUserExcludedIngredients() {
		return listOfUserExcludedIngredients;
	}
	public void setListOfUserExcludedIngredients(
			List<String> listOfUserExcludedIngredients) {
		this.listOfUserExcludedIngredients = listOfUserExcludedIngredients;
	}
	public void setListOfIngredients(List<String> listOfIngredients) {
		this.listOfIngredients = listOfIngredients;
	}
	public void fillListOfIngredients(Dish dish) {
		List<Joindishingredient> dishingredients = Ebean.find(Joindishingredient.class)
				.where()
				.eq("dishid", dish.getId())
				.findList();
		for (Joindishingredient dishingredient: dishingredients) {
			Ingredient ingredient = Ebean.find(Ingredient.class, dishingredient.getIngredient().getId());
			listOfIngredients.add(ingredient.getShrt_Desc());
		}
	}
	public List<String> getListOfIngredients() {
		return listOfIngredients;
	}
	public void setListOfIngredients(ArrayList<String> listOfIngredients) {
		this.listOfIngredients = listOfIngredients;
	}
}
