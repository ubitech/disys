package recommender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import play.Logger;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

import drools.controller.RuleRunner;
import models.relations.JoinCustomerIngredientsRating;
import models.relations.Joindishingredient;
import models.relations.Joinrestaurantdish;
import models.restaurant.Dish;
import models.user.Profile;

// calculate Preference based on History
public class Recommender {

	public Profile runProfileRules(Profile profile) {
		
		RuleRunner userRunner = new RuleRunner();
		Object[] profileFacts = { profile };
		Logger.debug("before running user rules " + (new Date()));
		userRunner.runUserRules(profileFacts);
		Logger.debug("after running user rules " + (new Date()));
		return profile;
	}

	public ArrayList<Dish> runDishRules(Profile profile, ArrayList<Dish> dishes) {

		ArrayList<Dish> filteredDishes = new ArrayList<Dish>();
		Logger.debug("before running dish rules " + (new Date()));
		RuleRunner runner = new RuleRunner();
		for (int i = 0; i < dishes.size(); i++) {
			Dish dish = dishes.get(i);			
			Object[] facts = { profile, dish };
			runner.runDishRules(facts);
		}
		Logger.debug("after running dish rules " + (new Date()));
		
		return dishes;
	}

	public LinkedHashMap<Dish, Double> getRecommendations(Profile profile,
			ArrayList<Dish> dishes) {		
		LinkedHashMap<Dish, Double> filteredDishesWithPreferences = new LinkedHashMap<Dish, Double>();
		dishes = runDishRules(profile, dishes);		
		for (Dish dish : dishes) {
//			Logger.debug("dish priority: " + dish.getPriority());
			if (!dish.getExclude()) {
				filteredDishesWithPreferences.put(
						dish,
						getPreferenceForDish(profile, dish)
								+ dish.getPriority());
//				Logger.debug("don't exclude");
			}
//			Logger.debug("dish ingredient size: "
//					+ dish.getListOfIngredientCategoriesArrayStr().length);
//			Logger.debug("dish ingredients size: "
//					+ Arrays.toString(dish.getListOfIngredientCategoriesArrayStr()));
		}

		// order
		List<Entry<Dish, Double>> entries = new ArrayList<Map.Entry<Dish, Double>>(
				filteredDishesWithPreferences.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Dish, Double>>() {
			public int compare(Map.Entry<Dish, Double> a,
					Map.Entry<Dish, Double> b) {
				return b.getValue().compareTo(a.getValue());
			}
		});
		LinkedHashMap<Dish, Double> sortedDishes = new LinkedHashMap<Dish, Double>();
		for (Map.Entry<Dish, Double> entry : entries) {
//			Logger.debug(entry.getKey().getName() + ", preference:  " + entry.getValue());
			sortedDishes.put(entry.getKey(), entry.getValue());
		}

		return sortedDishes;
	}

	public double getPreferenceForDish(Profile person, Dish dishBean) {

		int counter = 0;
		double sum = 0.0;

		// lista me ta ingredients tou dishbean
		List<Joindishingredient> ingredientDishes = Ebean
				.find(Joindishingredient.class).where()
				.eq("dishid", dishBean.getId()).findList();

		// gia kathe ingredient tis proigumenis listas vres to rating tu ston
		// pinaka joindishingredientsrating
		try {
			for (Joindishingredient joindishingredients : ingredientDishes) {
				JoinCustomerIngredientsRating ingredientRating = Ebean
						.find(JoinCustomerIngredientsRating.class)
						.where()
						.eq("ingredientid",
								joindishingredients.getIngredient().getId())
						.eq("user_id", person.getId()).findUnique();

				if (ingredientRating != null) {
					counter++;
					sum += ingredientRating.getRating();
					// }
					/*
					 * else { upologismos tu ingredientrating me vasi ratings
					 * xristwn me idia gusta sto fagito -> san enallaktiki
					 * protasi }
					 */

				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		if (sum == 0)
			return 0.0;
		else
			return sum / counter;
	}

	public boolean calculateIngredientRatings(Profile profile, Dish dish,
			Double rating) {

		List<Joindishingredient> ingredientDishes = Ebean
				.find(Joindishingredient.class).where()
				.eq("dishid", dish.getId()).findList();
		// System.out.println(" ingredientid: " +
		// ingredientDishes.get(0).getIngredient() );

		// upothetume oti ola ta ingredients uparxun idi ston pinaka
		// JoinUserIngredientsRating
		for (Joindishingredient joindishingredient : ingredientDishes) {
			// System.out.println(" ingredientid: " +
			// joindishingredients.getIngredient() );
			JoinCustomerIngredientsRating ingredientRating = Ebean
					.find(JoinCustomerIngredientsRating.class)
					.where()
					.eq("ingredientid",
							joindishingredient.getIngredient().getId())
					.eq("user_id", 10/*profile.getId()*/).findUnique();

			// for (JoinUserIngredientsRating temp_r : ratings){
			// JoinUserIngredientsRating temp_r = ratings.get(0);
			if (ingredientRating != null) {
				double temp = ((rating + (ingredientRating.getCount_rating() * ingredientRating
						.getRating())) / (ingredientRating.getCount_rating() + 1));
				ingredientRating.setRating(temp);
				int count_rating = ingredientRating.getCount_rating() + 1;
				ingredientRating.setCount_rating(4);
				Ebean.save(ingredientRating);
				// }
			} else {
				JoinCustomerIngredientsRating newRating = new JoinCustomerIngredientsRating();
				newRating.setRating(rating * 1.0);
				newRating.setCount_rating(1);
				newRating.setIngredient(joindishingredient.getIngredient());
				newRating.setProfile(profile);

				Ebean.save(newRating);
			}
		}
		return true;
	}

}
