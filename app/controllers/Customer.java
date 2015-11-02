package controllers;

import drools.controller.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import beans.DailyRatios;
import beans.DiaryEntry;
import beans.DishBean;
import beans.DishDetails;
import beans.IngredientEntryBean;
import beans.IngredientGroup;
import beans.IngredientHelper;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.SqlUpdate;

import drools.controller.RuleRunner;
import models.DAO.RestaurantDAO;
import models.ingredient.Ingredient;
import models.lists.Hallergen;
import models.lists.Hcategory;
import models.lists.Hcerealswithglouten;
import models.lists.Hcharacteristic;
import models.lists.Hcousine;
import models.lists.Hdiet;
import models.lists.Hfruitwithshell;
import models.lists.Hgender;
import models.lists.Hingredientcategory;
import models.lists.Hphysicalactivity;
import models.lists.Hpregnancy;
import models.lists.Hpricerange;
import models.lists.Hrestaurantcategory;
import models.lists.Hrestaurantlicence;
import models.lists.Medicalcondition;
import models.relations.Joincombodish;
import models.relations.Joincustomerprofilehingredientcategory;
import models.relations.Joincustomerprofilemedicalcondition;
import models.relations.Joindishallergen;
import models.relations.Joindishcerealswithglouten;
import models.relations.Joindishfruitwithshell;
import models.relations.Joindishingredient;
import models.relations.Joiningredientingredientcategory;
import models.relations.Joinrestaurantdish;
import models.relations.Joinrestauranthrestaurantcategory;
import models.relations.Joinuserdish;
import models.restaurant.Dish;
import models.restaurant.Restaurant;
import models.user.Customerpreference;
import models.user.Profile;
import models.user.User;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import recommender.Recommender;
import views.html.customer.*;

public class Customer extends Controller {
	private static final List<Hcousine> cousines = Hcousine.find.all();
	private static final List<Hcharacteristic> characteristics = Hcharacteristic.find
			.all();
	private static final List<Hcategory> categories = Hcategory.find.all();
	private static final List<Hrestaurantlicence> licences = Hrestaurantlicence.find
			.all();
	private static final List<Hrestaurantcategory> restaurantcategories = Hrestaurantcategory.find
			.all();
	private static final List<Hpricerange> prices = Hpricerange.find.all();
	private static final List<Hcerealswithglouten> cerealswithglouten = Hcerealswithglouten.find
			.all();
	private static final List<Hfruitwithshell> fruitswithshell = Hfruitwithshell.find
			.all();
	final static Form<Profile> profileForm = Form.form(Profile.class);

	public static Result customerWelcomeScreen() {
		return ok(customerWelcomeScreen.render());
	}

	public static Result customerSearchMeal() {
		return ok(customerSearchMeal.render(restaurantcategories));
	}
	
	public static Result customerMealRecommendations() {
		//return ok(customerMealRecommendations.render());
		return ok();
	}

	public static Result customerSearchRestaurantByCity() {
		List<Restaurant> restaurants = Ebean
				.find(Restaurant.class)
				.where()
				.like("city",
						request().body().asFormUrlEncoded().get("city")[0]
								+ "%").findList();
		return ok(play.libs.Json.toJson(restaurants));
	}

	public static Result customerSearchRestaurantByCityAndKind() {
		Map<String, String[]> params = request().body().asFormUrlEncoded();
		List<Long> restaurantCategories = new ArrayList<Long>();
		List<String> restaurantCategoriesStrings;
		if (params.get("kinds[]") != null) {
			restaurantCategoriesStrings = new ArrayList<String>(
					Arrays.asList(params.get("kinds[]")));
		} else {
			restaurantCategoriesStrings = new ArrayList<String>();
		}
		for (int i = 0; i < restaurantCategoriesStrings.size(); i++) {
			restaurantCategories.add(Long.parseLong(restaurantCategoriesStrings
					.get(i)));
		}

		// GET RESTAURANTIDS
		List<Joinrestauranthrestaurantcategory> joindata = Ebean
				.find(Joinrestauranthrestaurantcategory.class).where()
				.in("hrestaurantcategoryid", restaurantCategories).findList();

		List<Long> restaurantIds = new ArrayList<Long>();
		for (int i = 0; i < joindata.size(); i++) {
			restaurantIds.add(joindata.get(i).getRestaurantid());
		}
		List<Restaurant> restaurants = Ebean
				.find(Restaurant.class)
				.where()
				.in("id", restaurantIds)
				.like("city",
						request().body().asFormUrlEncoded().get("city")[0]
								+ "%").findList();
		return ok(play.libs.Json.toJson(restaurants));
	}

	public static Result customerAccountSettings() {
		Long userid = Long.parseLong(session("userid"));
		Profile profile = Ebean.find(Profile.class, userid);
		// System.out.println(play.libs.Json.toJson(profile));
		profile.setTransients();
		Recommender recommender = new Recommender();
		profile = recommender.runProfileRules(profile);
		return ok(customerAccountSettings.render(profile));
		// return TODO;
	}

	public static Result updateCustomerPersonalData() {
		Long userid = Long.parseLong(session("userid"));
		DynamicForm form = new DynamicForm().bindFromRequest();
		String[] notdailyCategories = request().body().asFormUrlEncoded()
				.get("notdailyselect");

		Profile profile = Ebean.find(Profile.class, userid);

		List<Joincustomerprofilemedicalcondition> conditionsToDelete = Ebean
				.find(Joincustomerprofilemedicalcondition.class).where()
				.eq("customerprofileid", userid).findList();
		List<Joincustomerprofilehingredientcategory> categoriesToDelete = Ebean
				.find(Joincustomerprofilehingredientcategory.class).where()
				.eq("customerprofileid", userid).findList();
		Ebean.beginTransaction();
		try {

			String gender = form.get("gender");
			if (gender.equals("male")) {
				Hgender hgender = Ebean.find(Hgender.class, 1L);
				profile.setGender(hgender);
				Hpregnancy hpregnancy = Ebean.find(Hpregnancy.class, 5L);
				profile.setPregnancyStatus(hpregnancy);
			} else if (gender.equals("female")) {
				Hgender hgender = Ebean.find(Hgender.class, 2L);
				profile.setGender(hgender);
				Hpregnancy hpregnancy = Ebean.find(Hpregnancy.class,
						Long.parseLong(form.get("egkymosuni")));
				profile.setPregnancyStatus(hpregnancy);
				/*
				 * String s =
				 * "UPDATE customerprofile set hpregnancyid = :count where id = :id"
				 * ; SqlUpdate update = Ebean.createSqlUpdate(s);
				 * update.setParameter("id", userid);
				 * update.setParameter("count", form.get("egkymosuni"));
				 * 
				 * Ebean.execute(update);
				 */
			}

			for (Joincustomerprofilemedicalcondition conditionToDelete : conditionsToDelete) {
				conditionToDelete.delete();
			}
			for (Joincustomerprofilehingredientcategory categoryToDelete : categoriesToDelete) {
				categoryToDelete.delete();
			}
			if (notdailyCategories != null) {
				for (int i = 0; i < notdailyCategories.length; i++) {
					Joincustomerprofilehingredientcategory categoryToAdd = new Joincustomerprofilehingredientcategory();
					categoryToAdd.setCustomerprofileid(profile.getId());
					categoryToAdd.setHingredientcategoryid(Long
							.parseLong(notdailyCategories[i]));
					categoryToAdd.save();
				}
			}
			for (int i = 1; i < 8; i++) {
				if (form.get("condition" + i) != null) {
					Joincustomerprofilemedicalcondition conditionToAdd = new Joincustomerprofilemedicalcondition();
					conditionToAdd.setCustomerprofileid(profile.getId());
					conditionToAdd.setMedicalconditionid(Long.parseLong(form
							.get("condition" + i)));
					conditionToAdd.save();
				}
			}
			profile.setWatchcalories(0);
			profile.setWatchhydros(0);
			profile.setWatchsugars(0);
			profile.setWatchfiber(0);
			profile.setWatchprotein(0);
			profile.setWatchfat(0);
			profile.setWatchsaturated(0);
			profile.setWatchmonosaturated(0);
			profile.setWatchpolysaturated(0);
			profile.setWatchcholesterol(0);
			profile.setWatchmetals(0);
			profile.setWatchvitamins(0);
			profile.setWatchantioxidants(0);
			for (int i = 1; i < 14; i++) {
				if (form.get("nutrient" + i) != null) {
					System.out.println("Found nutrient: " + i);
					switch (i) {
					case 1:
						profile.setWatchcalories(1);
						break;
					case 2:
						profile.setWatchhydros(1);
						break;
					case 3:
						profile.setWatchsugars(1);
						break;
					case 4:
						profile.setWatchfiber(1);
						break;
					case 5:
						profile.setWatchprotein(1);
						break;
					case 6:
						profile.setWatchfat(1);
						break;
					case 7:
						profile.setWatchsaturated(1);
						break;
					case 8:
						profile.setWatchmonosaturated(1);
						break;
					case 9:
						profile.setWatchpolysaturated(1);
						break;
					case 10:
						profile.setWatchcholesterol(1);
						break;
					case 11:
						profile.setWatchmetals(1);
						break;
					case 12:
						profile.setWatchvitamins(1);
						break;
					case 13:
						profile.setWatchantioxidants(1);
						break;
					}
				}
			}
			Hphysicalactivity activity = Ebean.find(Hphysicalactivity.class,
					Long.parseLong(form.get("activitylevelselect")));
			profile.setPhysicalactivity(activity);
			Hdiet diet = Ebean.find(Hdiet.class,
					Long.parseLong(form.get("dietlevelselect")));
			profile.setDiet(diet);

			profile.update();
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
		// Profile profile = filledForm.get();
		// System.out.println(play.libs.Json.toJson(profile));
		Recommender recommender = new Recommender();
		profile.setTransients();
		profile = recommender.runProfileRules(profile);
		return ok(customerAccountSettings.render(profile));
	}

	public static Result AddCustomerDishes() {
		return TODO;
	}
    
    public static Result GetCustomerDishesForRecommenderStep1() {
    	
    	
    	List<Joinrestaurantdish> restaurantdishes = Ebean.find(Joinrestaurantdish.class)
    									.where()
    									.findList();
    	ArrayList<Dish> dishes = new ArrayList<Dish>(); 
    	List<DishDetails> dishDetailsList = new ArrayList<DishDetails>();    	
    	Logger.debug("GetCustomerDishesForRecommenderStep1: " + restaurantdishes.size());
    	
    	for (int i = 0; i < 10; i++) {
    		Dish dish = Ebean.find(Dish.class, restaurantdishes.get(i).getDish().getId()); 
    		dishes.add(dish);
    		DishDetails dishdetails = new DishDetails();
    		dishdetails.setName(dish.getName());
    		dishdetails.setPrice(dish.getPrice());
    		if (dish.getCalories() != null){
    			dishdetails.setCalories(dish.getCalories().intValue());
    		}
    		dishdetails.setLocation("Athens");//restaurant.getCity());
    		dishdetails.setDishid(dish.getId());
    		dishDetailsList.add(dishdetails);
    	}  
    
    	return ok(play.libs.Json.toJson(dishDetailsList));
    }
    
    public static Result StoreRatingsFromRecommenderStep1() {
    	int userid = 10;
    	Profile profile = Ebean.find(Profile.class, userid);
    	String[] postAction = request().body().asFormUrlEncoded().get("ratings[]");
    	Recommender recommender = new Recommender();
    	String dishIds = ""; 
    	 if (postAction == null || postAction.length == 0) {
    	   	    return badRequest("You must provide a valid action");
    	 }
    	 else {
    		// 1) apothikeusi ston pinaka joinuserdish
    	   	    for (int i=0; i<postAction.length; i++){
    	   	    	String[] parts = postAction[i].split("=");
    	   	    	String dish_id = parts[0]; 
    	   	    	String dish_rating = parts[1]; 
    	   			Dish tdish = Ebean.find(Dish.class, Integer.parseInt(dish_id));
    	   			recommender.calculateIngredientRatings(profile, tdish, Integer.parseInt(dish_rating)/2.0);
    	   			dishIds = dishIds + dish_id;
    	   			if(i<postAction.length-1) {
    	   				dishIds = dishIds + ", ";
    	   			}
    	   			else{
    	   				dishIds = dishIds + " ";
    	   			}
    	   	    }
    	   	    
    	   	    String query = "id NOT IN (" + dishIds + ")";
    	   	    List<Dish> dishList = Ebean.find(Dish.class)  
    	   		    .where(query)
    	   		    .findList();  

    	   	    Logger.debug("list size: " + dishList.size());
    	   	    
    	   	    profile = recommender.runProfileRules(profile);
    	    	
    	    	LinkedHashMap<Dish, Double> recommendedDishes = recommender.getRecommendations(profile, new ArrayList<Dish>(dishList));
    	   		
    	    	List<DishDetails> dishDetailsList = new ArrayList<DishDetails>();   
    	    	int br=0;
    	    	for (Map.Entry<Dish, Double> recommendedDish : recommendedDishes.entrySet()) {
    	    		if (br==5) break;
    	    		Dish dish =  recommendedDish.getKey(); 
    	        	//Restaurant restaurant = Ebean.find(Restaurant.class, restaurantdishes.get(i).getRestaurant().getId());
    	    		DishDetails dishdetails = new DishDetails();
    	    		dishdetails.setName(dish.getName());
    	    		dishdetails.setPrice(dish.getPrice());
    	    		if (dish.getCalories() != null){
    	    			dishdetails.setCalories(0);
    	    		}
    	    		else{
    	    			dishdetails.setCalories(0);//dish.getCalories().intValue());
    	    		}
    	    		dishdetails.setLocation("Athens");//restaurant.getCity());
    	    		dishdetails.setRestaurantname("KOHENOOR");//restaurant.getName());
    	    		dishdetails.setDishid(dish.getId()); //Logger.debug("a:" + dishdetails.getId()); Logger.debug("ai:" + i);
    	    		dishDetailsList.add(dishdetails);
    	    		List<Joindishingredient> dishingredients = dish.getDishingredients();
    	    		//for (Joindishingredient dishingredient : dishingredients){
    	    			//Logger.debug("ingredient name: " + dishingredient.getIngredient().getCode());
    	    		//}
    	    		br++;
    	    	}  
    	    	
	   	    	Logger.debug("database update_step2");
	   	    	return ok(play.libs.Json.toJson(dishDetailsList));   	   	   
    	   }     	         	         	    
    }
    
    public static Result GetRecommendedDishes() {
    	
    	int userid = 10;
    	Profile profile = Ebean.find(Profile.class, userid);
    	
    	ArrayList<Long> restaurantIdsLong = new ArrayList<Long>();
    	restaurantIdsLong.add(25L);
    	
    	List<Joinrestaurantdish> restaurantdishes = Ebean.find(Joinrestaurantdish.class)
    									.where()
    									.in("restaurantid", restaurantIdsLong)
    									.findList();
    	Logger.debug("restaurants dishes size: " + restaurantdishes.size());
    	ArrayList<Dish> dishes = new ArrayList<Dish>(); 
    	
    	
    	for (int i = 0; i < restaurantdishes.size(); i++) {
    		Dish dish = Ebean.find(Dish.class, restaurantdishes.get(i).getDish().getId()); 
    		//Logger.debug("dish id: " + dish.getId());
    		dishes.add(dish);
    	}  
    	
    	Recommender recommender = new Recommender();
    	profile = recommender.runProfileRules(profile);
    	
    	LinkedHashMap<Dish, Double> recommendedDishes = recommender.getRecommendations(profile, dishes);
    	
    	List<DishDetails> dishDetailsList = new ArrayList<DishDetails>();   
    	int br=0;
    	for (Map.Entry<Dish, Double> recommendedDish : recommendedDishes.entrySet()) {
    		if (br==5) break;
    		Dish dish =  recommendedDish.getKey(); 
        	//Restaurant restaurant = Ebean.find(Restaurant.class, restaurantdishes.get(i).getRestaurant().getId());
    		DishDetails dishdetails = new DishDetails();
    		dishdetails.setName(dish.getName());
    		dishdetails.setPrice(dish.getPrice());
    		if (dish.getCalories() != null){
    			dishdetails.setCalories(0);
    		}
    		else{
    			dishdetails.setCalories(0);//dish.getCalories().intValue());
    		}
    		dishdetails.setLocation("Athens");//restaurant.getCity());
    		dishdetails.setRestaurantname("KOHENOOR");//restaurant.getName());
    		dishdetails.setDishid(dish.getId()); //Logger.debug("a:" + dishdetails.getId()); Logger.debug("ai:" + i);
    		dishDetailsList.add(dishdetails);
    		List<Joindishingredient> dishingredients = dish.getDishingredients();
    		//for (Joindishingredient dishingredient : dishingredients){
    			//Logger.debug("ingredient name: " + dishingredient.getIngredient().getCode());
    		//}
    		br++;
    	}  
    	return ok(play.libs.Json.toJson(dishDetailsList));
    }
    
    public static Result StoreRatingsFromRecommenderStep2() {
    	int userid = 2;
    	Profile profile = Ebean.find(Profile.class, userid);
    	String[] postAction = request().body().asFormUrlEncoded().get("ratings2[]");
    	Recommender recommender = new Recommender();
    	 if (postAction == null || postAction.length == 0) {
    	   	    return badRequest("You must provide a valid action");
    	 }
    	 else {
    		// 1) enhmerwsi calculateingredientsrating
    	   	    for (int i=0; i<postAction.length; i++){
    	   	    	String[] parts = postAction[i].split("=");
    	   	    	String dish_id = parts[0]; 
    	   	    	String dish_rating = parts[1]; 
    	   			Dish tdish = Ebean.find(Dish.class, Integer.parseInt(dish_id));
    	   			recommender.calculateIngredientRatings(profile, tdish, Integer.parseInt(dish_rating)/2.0);
    	   	// 2) apothikeusi twn ratings se neo pinaka "feedbackratings"
    	   	    }    	   
	   	    	Logger.debug("database update_step3");
    	   	    return ok();
    	   	    
    	   }
     	         	         	    
    }

	public static Result SearchDishes() {

		int userid = Integer.parseInt(session("userid"));

		Profile profile = Ebean.find(Profile.class, userid);

		ArrayList<String> restaurantIdsString = new ArrayList<String>(
				Arrays.asList(request().body().asFormUrlEncoded()
						.get("restaurantIds[]")));

		// Set some preferences
		String[] notdaily = request().body().asFormUrlEncoded()
				.get("notdaily[]");

		//user has set mandatory preferences
		boolean hasMandatory = false;
		if (notdaily != null && notdaily.length >0 && false) {
			hasMandatory = true;
			profile.setWantEggs(1);
			profile.setWantLamp(1);
			profile.setWantLactose(1);
			profile.setWantMilk(1);		
			profile.setWantKaseri(1);
			profile.setWantFeta(1);
			profile.setWantVeal(1);
			profile.setWantShells(1);
			profile.setWantPork(1);
			profile.setWantFish(1);
			profile.setWantWheat(1);
			profile.setWantBread(1);
			profile.setWantSogia(1);
			profile.setWantSeeds(1);
		}		
		if (notdaily != null && notdaily.length > 0) {
			for (String entry : notdaily) {
				switch (entry) {
				case "1":
					profile.setWantEggs(2);
					break;
				case "2":
					profile.setWantLamp(2);
					break;
				case "3":
					profile.setWantLactose(2);
					profile.setWantMilk(2);
					break;
				case "4":
					profile.setWantKaseri(2);
					break;
				case "5":
					profile.setWantFeta(2);
					break;
				case "6":
					profile.setWantVeal(2);
					break;
				case "7":
					profile.setWantShells(2);
					break;
				case "8":
					profile.setWantPork(2);
					break;
				case "9":
					profile.setWantFish(2);
					break;
				case "10":
					profile.setWantWheat(2);
					break;
				case "11":
					profile.setWantBread(2);
					break;
				case "12":
					profile.setWantSogia(2);
					break;
				case "13":
					profile.setWantSeeds(2);
					break;
				default:
					break;
				}
			}
		}

		// Set some conditions
		/*
		 * String[] conditions = request().body().asFormUrlEncoded()
		 * .get("conditions[]");
		 * 
		 * if (conditions != null && conditions.length > 0){ for(String entry :
		 * conditions){ switch (entry){ case "anemia":
		 * profile.setHasAnemia(true); break; case "diabetes":
		 * profile.setHasDiabetes(true); break; case "constipation":
		 * profile.setHasConstipation(true); break; case "celiac":
		 * profile.setHasCeliac(true); break; case "hyperuricemia":
		 * profile.setHasHyperuricemia(true); break; case "hypertension":
		 * profile.setHasHypertension(true); break; case "hypercholesterolemia":
		 * profile.setHasHypercholesterolemia(true); break; default: break; } }
		 * }
		 */

		// not needed for now
		/*
		 * String[] price = request().body().asFormUrlEncoded().get("price"); if
		 * (price != null && price.length > 0){ for(String entry : price){
		 * Logger.debug("not price entry: " + entry); } }
		 */

		ArrayList<Long> restaurantIdsLong = new ArrayList<Long>();

		if (restaurantIdsString != null) {
			for (int i = 0; i < restaurantIdsString.size(); i++) {
				restaurantIdsLong
						.add(Long.parseLong(restaurantIdsString.get(i)));
			}
		}

		List<Joinrestaurantdish> restaurantdishes = Ebean
				.find(Joinrestaurantdish.class).where()
				.in("restaurantid", restaurantIdsLong).findList();
		Logger.debug("restaurants dishes size: " + restaurantdishes.size());
		ArrayList<Dish> dishes = new ArrayList<Dish>();

		List<DishDetails> dishDetailsList = new ArrayList<DishDetails>();

		for (int i = 0; i < restaurantdishes.size(); i++) {
			Dish dish = Ebean.find(Dish.class, restaurantdishes.get(i)
					.getDish().getId());
			Logger.debug("dish id: " + dish.getId());
			dishes.add(dish);
			Restaurant restaurant = Ebean.find(Restaurant.class,
					restaurantdishes.get(i).getRestaurant().getId());
			DishDetails dishdetails = new DishDetails();
			dishdetails.setName(dish.getName());
			dishdetails.setLocation(restaurant.getCity());// restaurant.getCity());
			dishdetails.setRestaurantname(restaurant.getName());// restaurant.getName());
			dishdetails.setDishid(dish.getId());
			// dishdetails.setCharacteristic(dish.getCharacteristic().getDescription());
			dishdetails.setPrice(dish.getPrice());
			dishdetails.setLat(restaurant.getLatitude());
			dishdetails.setLng(restaurant.getLongtitude());
			dishdetails.setRestaurantname(restaurant.getName());
			dishdetails.setCalories(getDishCalories(dish));
			// INCLUDE EXCLUDE based on Price range
			// UGLY! CHANGE IT AFTER MODEL UPDATE
			
			int price = Integer.parseInt(request().body().asFormUrlEncoded()
					.get("price")[0]);
			double dishPrice = dish.getPrice();
			switch (price) {
			case 0:
				break;
			case 1:
				if (dishPrice > 10.0) {
					dishdetails.selectable = false;
				}
				break;
			case 2:
				if (dishPrice <= 10.0 || dishPrice > 15.0) {
					dishdetails.selectable = false;
				}
				break;
			case 3:
				if (dishPrice <= 15.0 || dishPrice > 20.0) {
					dishdetails.selectable = false;
				}
				break;
			case 4:
				if (dishPrice <= 20.0 || dishPrice > 25.0) {
					dishdetails.selectable = false;
				}
				break;
			case 5:
				if (dishPrice <= 25.0 || dishPrice > 30.0) {
					dishdetails.selectable = false;
				}
				break;
			}
			int dishCalories = getDishCalories(dish);
			int calories = Integer.parseInt(request().body().asFormUrlEncoded()
					.get("calories")[0]);	
			switch (calories) {
			case 0:
				break;
			case 1:
				if (dishCalories > 300) {
					dishdetails.selectable = false;
				}
				break;
			case 2:
				if (dishCalories <= 300 || dishCalories > 600) {
					dishdetails.selectable = false;
				}
				break;
			case 3:
				if (dishCalories <= 600 || dishCalories > 900) {
					dishdetails.selectable = false;
				}
				break;
			case 4:
				if (dishCalories <= 900 || dishCalories > 1200) {
					dishdetails.selectable = false;
				}
				break;
			case 5:
				if (dishCalories <= 1200 || dishCalories > 1500) {
					dishdetails.selectable = false;
				}
				break;
			case 6:
				if (dishCalories <= 1500 || dishCalories > 1800) {
					dishdetails.selectable = false;
				}
				break;
			case 7:
				if (dishCalories <= 1800 || dishCalories > 2100) {
					dishdetails.selectable = false;
				}
				break;
			case 8:
				if (dishCalories <= 2100 || dishCalories > 2400) {
					dishdetails.selectable = false;
				}
				break;				
			}			
			 
			if (!dishdetails.selectable) {
				continue;
			}
			if (dishdetails.selectable) {
				dishDetailsList.add(dishdetails);
			}
			List<Joindishingredient> dishingredients = dish
					.getDishingredients();
			for (Joindishingredient dishingredient : dishingredients) {
				Logger.debug("ingredient name: "
						+ dishingredient.getIngredient().getCode());
			}
		}

		profile.setTransients();
		Recommender recommender = new Recommender();
		profile = recommender.runProfileRules(profile);

		LinkedHashMap<Dish, Double> recommendedDishesPlain = recommender
				.getRecommendations(profile, dishes);
		//exclude if mandatory has been set
		if (hasMandatory) {
		    for(Iterator<Map.Entry<Dish, Double>> it = recommendedDishesPlain.entrySet().iterator(); it.hasNext(); ) {
		        Map.Entry<Dish, Double> entry = it.next();
		        if(entry.getKey().getPriority() < 2.0*notdaily.length) {
		          it.remove();
		        }
		      }	
		}
		ArrayList<Dish> dishArray = new ArrayList<Dish>();
		for (Map.Entry<Dish, Double> recommendedDish : recommendedDishesPlain
				.entrySet()) {
			dishArray.add(recommendedDish.getKey());
		}
		Collections.sort(dishArray);

		List<DishDetails> dishDetailsListFinal = new ArrayList<DishDetails>();
		for (Dish dish : dishArray) {

			Logger.debug("recommended dish id: " + dish.getId());
			Logger.debug("\tPriority: " + dish.getPriority());

			List<Joindishingredient> dishingredients = dish
					.getDishingredients();

			for (Joindishingredient dishingredient : dishingredients) {
				List<Joiningredientingredientcategory> ingredientingredientcategories = dishingredient
						.getIngredient().getIngredientingredientcategories();
				Logger.debug("ingredient categories size: "
						+ ingredientingredientcategories.size());
				for (Joiningredientingredientcategory ingredientcategory : ingredientingredientcategories) {
					Logger.debug("category: "
							+ ingredientcategory.getHingredientcategoryid()
							+ "category name: "
							+ ingredientcategory.getIngredientcategory()
									.getDescription());
				}
			}
			for (Iterator<DishDetails> iterator = dishDetailsList.iterator(); iterator
					.hasNext();) {
				DishDetails dishDetails = iterator.next();
				Long dishid = dishDetails.getDishid();
				if (dishid == dish.getId() && dish.getSelectable()) {
					//add explanation
					double rating = recommender.getPreferenceForDish(profile,dish);
					if (rating > 0){
						dishDetails.addPreferenceExplanation("Το ΔΙΣΥΣ υπολόγισε ότι το πιάτο αυτό θα το αξιολογούσατε με " + rating + " στα 5.");
						Logger.debug("Το ΔΙΣΥΣ υπολόγισε ότι το πιάτο αυτό θα το αξιολογούσατε με " + rating + " στα 5.");						
					}
					else{
						Logger.debug("no rating available: " +rating);
					}
					
					if (dish.getBreadPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει ψωμί, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains bread");
					}
					if (dish.getKaseriPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει κασέρι, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains kaseri");
					}
					if (dish.getEggsPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει αυγά, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains eggs");
					}
					if (dish.getFetaPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει φέτα, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains feta");
					}
					if (dish.getLampPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει αρνί, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains lamp");
					}
					if (dish.getFishPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει ψάρι, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains fish");
					}
					if (dish.getMilkPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει γάλα, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains milk");
					}
					if (dish.getLactosePriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει λακτόζη, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains lactose");
					}
					if (dish.getPorkPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει χοιρινό, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains pork");
					}
					if (dish.getSeedsPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει σπόρους, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains seeds");
					}
					if (dish.getSogiaPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει σόγια, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains sogia");
					}
					if (dish.getVealPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει βοδινό, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains veal");
					}
					if (dish.getShellsPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει όστρακα/ μαλάκια, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains shells");
					}
					if (dish.getWheatPriority() > 0){
						dishDetails.addPreferenceExplanation("Το πιάτο περιέχει σιτάρι, σύμφωνα με τις προτιμήσεις σας!");
						Logger.debug("dish contains wheat");
					}
					dishDetailsListFinal.add(dishDetails);
				}
			}

		}
		return ok(play.libs.Json.toJson(dishDetailsListFinal));
	}

	/****************************************************/
	// INTEGRATE EXTRA CODE TO NEW SEARCH DISHES
	/****************************************************/
	public static Result SearchDishesBackup() {
		Long userid = Long.parseLong(session("userid"));
		User user = Ebean.find(User.class, userid);
		String[] notDailyStr = request().body().asFormUrlEncoded()
				.get("notdaily[]");
		ArrayList<String> restaurantIdsString = new ArrayList<String>(
				Arrays.asList(request().body().asFormUrlEncoded()
						.get("restaurantIds[]")));
		ArrayList<Long> restaurantIdsLong = new ArrayList<Long>();
		if (restaurantIdsString != null) {
			for (int i = 0; i < restaurantIdsString.size(); i++) {
				restaurantIdsLong
						.add(Long.parseLong(restaurantIdsString.get(i)));
			}
		}

		List<Joinrestaurantdish> restaurantdishes = Ebean
				.find(Joinrestaurantdish.class).where()
				.in("restaurantid", restaurantIdsLong).findList();
		List<DishDetails> dishDetailsList = new ArrayList<DishDetails>();
		String[] rules = { "exclude.drl" };
		for (int i = 0; i < restaurantdishes.size(); i++) {
			Dish dish = Ebean.find(Dish.class, restaurantdishes.get(i)
					.getDish().getId());
			Restaurant restaurant = Ebean.find(Restaurant.class,
					restaurantdishes.get(i).getRestaurant().getId());
			DishDetails dishdetails = new DishDetails();
			// EXCLUDE Dish based on user PROFILE constraints - Food Categories
			// Get Ingredients of Dish
			DishBean dishBean = new DishBean();
			dishBean.fillListOfIngredients(dish);
			dishBean.filllistOfUserExcludedIngredients(user);
			for (int j = 0; j < dishBean.getListOfIngredients().size(); j++) {
				String dishIngs = Normalizer.normalize(dishBean
						.getListOfIngredients().get(j), Normalizer.Form.NFD);
				dishIngs.replace("[^\\p{ASCII}]", "");
				for (int k = 0; k < dishBean.getListOfUserExcludedIngredients()
						.size(); k++) {
					String userIngs = Normalizer.normalize(dishBean
							.getListOfUserExcludedIngredients().get(k),
							Normalizer.Form.NFD);
					userIngs.replace("[^\\p{ASCII}]", "");
					if (dishIngs.toUpperCase().contains(userIngs.toUpperCase())) {
						dishdetails.selectable = false;
						System.out
								.println("Drools: Excluded from Profile Settings for dish: "
										+ dish.getName()
										+ " Excluded ingredient: "
										+ userIngs.toUpperCase());
					}
				}
			}
			// INCLUDE Dish based on user MENU options - Food Categories
			if (notDailyStr != null) {
				for (int j = 0; j < notDailyStr.length; j++) {
					Hingredientcategory ingredientCategory = Ebean.find(
							Hingredientcategory.class,
							Long.parseLong(notDailyStr[j]));
					String menuIngs = Normalizer.normalize(
							ingredientCategory.getDescription(),
							Normalizer.Form.NFD);
					for (int k = 0; k < dishBean
							.getListOfUserExcludedIngredients().size(); k++) {
						String userIngs = Normalizer.normalize(dishBean
								.getListOfUserExcludedIngredients().get(k),
								Normalizer.Form.NFD);
						userIngs.replace("[^\\p{ASCII}]", "");
						if (menuIngs.toUpperCase().contains(
								userIngs.toUpperCase())) {
							if (!dishdetails.selectable) {
								System.out
										.println("Drools rule overrided by menu choices for dish: "
												+ dish.getName()
												+ " Overrided ingredient: "
												+ menuIngs.toUpperCase());
							}
							dishdetails.selectable = true;
						}
					}
				}
			}
			// INCLUDE EXCLUDE based on Price range
			// UGLY! CHANGE IT AFTER MODEL UPDATE
			int price = Integer.parseInt(request().body().asFormUrlEncoded()
					.get("price")[0]);
			double dishPrice = dish.getPrice();
			switch (price) {
			case 1:
				if (dishPrice > 10.0) {
					dishdetails.selectable = false;
				}
				break;
			case 2:
				if (dishPrice <= 10.0 || dishPrice > 15.0) {
					dishdetails.selectable = false;
				}
				break;
			case 3:
				if (dishPrice <= 15.0 || dishPrice > 20.0) {
					dishdetails.selectable = false;
				}
				break;
			case 4:
				if (dishPrice <= 20.0 || dishPrice > 25.0) {
					dishdetails.selectable = false;
				}
				break;
			case 5:
				if (dishPrice <= 25.0 || dishPrice > 30.0) {
					dishdetails.selectable = false;
				}
				break;
			}
			if (!dishdetails.selectable) {
				continue;
			}
			/*
			 * RuleRunner runner = new RuleRunner(); List<Object> facts = new
			 * ArrayList<Object>(); facts.add(dish); //facts.add(user); DishBean
			 * dishBean = new DishBean(); dishBean.fillListOfIngredients(dish);
			 * facts.add(dishBean); runner.runRules(rules, facts);
			 */
			// dishdetails.setCharacteristic(dish.getCharacteristic()
			// .getDescription());
			dishdetails.setPrice(dish.getPrice());
			dishdetails.setName(dish.getName());
			dishdetails.setLocation(restaurant.getCity());
			dishdetails.setRestaurantname(restaurant.getName());
			dishdetails.setLat(restaurant.getLatitude());
			dishdetails.setLng(restaurant.getLongtitude());
			dishdetails.setDishid(dish.getId());
			dishDetailsList.add(dishdetails);
		}
		return ok(play.libs.Json.toJson(dishDetailsList));
	}

	public static Result getDish() {
		DynamicForm form = Form.form().bindFromRequest();
		Dish dish = Ebean.find(Dish.class, Long.parseLong(form.get("dishid")));
		return ok(play.libs.Json.toJson(dish));
	}

	public static Result insertDishes() {
		Long userid = Long.parseLong(session("userid"));
		User user = Ebean.find(User.class, userid);
		ArrayList<String> dishIds = new ArrayList<String>(
				Arrays.asList(request().body().asFormUrlEncoded()
						.get("dishes[]")));
		System.out.println(dishIds);
		for (String dishId : dishIds) {
			Joinuserdish userDish = new Joinuserdish();
			userDish.setUser(Ebean.find(User.class, userid));
			userDish.setDish(Ebean.find(Dish.class, Long.parseLong(dishId)));
			userDish.setDateofselection(new Date());
			userDish.save();
		}
		if (user.getHascompletedquestionnaire() == 1) {
			return ok("has");
		} else {
			return ok("notHas");
		}
	}

	public static Result customerTracking() {
		// System.out.println("ldsjfalkfd");
		ArrayList<IngredientGroup> ingredientGroups = RestaurantDAO
				.getIngredientGroups();
		ArrayList<IngredientHelper> ingredients = RestaurantDAO
				.getIngredients();
		return ok(customerTracking.render(ingredientGroups, ingredients,
				cousines, characteristics, categories, fruitswithshell,
				cerealswithglouten));
	}

	public static Result customerTrackingMain() {

		return ok(trackingmain.render());
	}

	public static Result fetchStats() throws ParseException {
		DynamicForm form = Form.form().bindFromRequest();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date = format.parse(form.get("date"));
		// System.out.println(date);
		int userid = Integer.parseInt(session("userid"));
		List<Joinuserdish> joinuserdishes = Ebean.find(Joinuserdish.class)
				.where().eq("userid", userid).findList();
		ArrayList<Long> dishids = new ArrayList<Long>();
		for (Joinuserdish joinuserdish : joinuserdishes) {
			dishids.add(joinuserdish.getDish().getId());
		}
		List<Dish> dishes = Ebean
				.find(Dish.class)
				.where()
				.eq("dateadded",
						new SimpleDateFormat("yyyy-MM-dd").format(date))
				.in("id", dishids).findList();
		System.out.println(play.libs.Json.toJson(dishes));
		DailyRatios dailyRatios = new DailyRatios();
		List<DiaryEntry> diaryEntries = new ArrayList<DiaryEntry>();
		for (Dish dish : dishes) {
			List<Joindishingredient> dishingredients = Ebean
					.find(Joindishingredient.class).where()
					.eq("dishid", dish.getId()).findList();
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			for (int i = 0; i < dishingredients.size(); i++) {
				DiaryEntry diaryEntry = new DiaryEntry();
				Ingredient ingredient = Ebean.find(Ingredient.class,
						dishingredients.get(i).getIngredient().getId());
				Double normalization = 0.0;
				// normalization factor based on metric
				if (dishingredients.get(i).getMetric() == 1) {
					normalization = ingredient.getGmWt_1() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 2) {
					normalization = ingredient.getGmWt_2() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 10) {
					normalization = 1.0 / 100.0;
				} else if (dishingredients.get(i).getMetric() == 20) {
					normalization = 1000.0 / 100.0;
				}

				normalization *= dishingredients.get(i).getAmount();
				// calories
				dailyRatios.setCalories(dailyRatios.getCalories()
						+ (int) (normalization * (float) ingredient
								.getEnerg_Kcal()));
				dailyRatios.setFats(dailyRatios.getFats() + normalization
						* ingredient.getLipid_Tot_g());
				dailyRatios.setProteins(dailyRatios.getProteins()
						+ normalization * ingredient.getProtein_g());
				dailyRatios.setHydros(dailyRatios.getHydros() + normalization
						* ingredient.getCarbohydrt_g());
				dailyRatios.setSaturated(dailyRatios.getSaturated()
						+ normalization * ingredient.getFA_Sat_g());
				dailyRatios.setSalt(dailyRatios.getSalt() + normalization
						* (2.5*ingredient.getSodium_mg()/1000.0));
				dailyRatios.setSugars(dailyRatios.getSugars() + normalization
						* ingredient.getSugar_Tot_g());
				double weight = 0.0;
				if (dishingredients.get(i).getMetric() == 1) {
					weight = ingredient.getGmWt_1();
					diaryEntry.setMetric(ingredient.getGmWt_Desc1());
					diaryEntry.setMetricId(1);
				} else if (dishingredients.get(i).getMetric() == 2) {
					diaryEntry.setMetric(ingredient.getGmWt_Desc2());
					weight = ingredient.getGmWt_2();
					diaryEntry.setMetricId(2);
				} else if (dishingredients.get(i).getMetric() == 10) {
					diaryEntry.setMetric("Γραμμάρια");
					weight = 1.0;
					diaryEntry.setMetricId(10);
				} else if (dishingredients.get(i).getMetric() == 20) {
					diaryEntry.setMetric("Κιλά");
					weight = 100.0;
					diaryEntry.setMetricId(20);
				}

				dailyRatios.setWeight(dailyRatios.getWeight() + weight);
				diaryEntry.setIngredient(dishingredients.get(i).getIngredient()
						.getShrt_Desc());
				diaryEntry.setIngredientId(dishingredients.get(i).getIngredient().getId());
				diaryEntry.setAmount(dishingredients.get(i).getAmount()
						.toString());
				if (dish.getDayperiod() == 1) {
					diaryEntry.setDayperiod("Πρωϊνό");
				} else if (dish.getDayperiod() == 2) {
					diaryEntry.setDayperiod("Μεσημεριανό");
				} else if (dish.getDayperiod() == 3) {
					diaryEntry.setDayperiod("Βραδινό");
				} else if (dish.getDayperiod() == 4) {
					diaryEntry.setDayperiod("Ενδιάμεσο Σνακ");
				} else if (dish.getDayperiod() == 5) {
					diaryEntry.setDayperiod("Υγρό");
				}
				diaryEntry.setJoinId(dishingredients.get(i).getId());
				diaryEntries.add(diaryEntry);
			}
		}
		// Get CompleteList
		Profile prof = Ebean.find(Profile.class, userid);
		prof.setTransients();
		Recommender recommender = new Recommender();
		prof = recommender.runProfileRules(prof);
		dailyRatios
				.setCalories((int) ((int) 100.0 * (dailyRatios.getCalories() / prof
						.getCalories())));
		dailyRatios.setFats((int) ((int) 100.0 * (dailyRatios.getFats() / prof
				.getFats())));
		dailyRatios
				.setSugars((int) ((int) 100.0 * (dailyRatios.getSugars() / prof
						.getSugars())));
		dailyRatios
				.setHydros((int) ((int) 100.0 * (dailyRatios.getHydros() / prof
						.getHydrocarbonate())));
		dailyRatios.setSalt((int) ((int) 100.0 * (dailyRatios.getSalt() / prof
				.getSalt())));
		return ok(customerTrackingDiary.render(dailyRatios, diaryEntries,
				form.get("date")));
	}

	public static Result customerDiary() {
		Date date = new Date();
		int userid = Integer.parseInt(session("userid"));
		List<Joinuserdish> joinuserdishes = Ebean.find(Joinuserdish.class)
				.where().eq("userid", userid).findList();
		ArrayList<Long> dishids = new ArrayList<Long>();
		for (Joinuserdish joinuserdish : joinuserdishes) {
			dishids.add(joinuserdish.getDish().getId());
		}
		List<Dish> dishes = Ebean
				.find(Dish.class)
				.where()
				.eq("dateadded",
						new SimpleDateFormat("yyyy-MM-dd").format(date))
				.in("id", dishids).findList();
		System.out.println(play.libs.Json.toJson(dishes));
		DailyRatios dailyRatios = new DailyRatios();
		List<DiaryEntry> diaryEntries = new ArrayList<DiaryEntry>();
		for (Dish dish : dishes) {
			List<Joindishingredient> dishingredients = Ebean
					.find(Joindishingredient.class).where()
					.eq("dishid", dish.getId()).findList();
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			for (int i = 0; i < dishingredients.size(); i++) {
				DiaryEntry diaryEntry = new DiaryEntry();
				Ingredient ingredient = Ebean.find(Ingredient.class,
						dishingredients.get(i).getIngredient().getId());
				Double normalization = 0.0;
				// normalization factor based on metric
				if (dishingredients.get(i).getMetric() == 1) {
					normalization = ingredient.getGmWt_1() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 2) {
					normalization = ingredient.getGmWt_2() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 10) {
					normalization = 1.0 / 100.0;
				} else if (dishingredients.get(i).getMetric() == 20) {
					normalization = 1000.0 / 100.0;
				}

				normalization *= dishingredients.get(i).getAmount();
				// calories
				dailyRatios.setCalories(dailyRatios.getCalories()
						+ (int) (normalization * (float) ingredient
								.getEnerg_Kcal()));
				dailyRatios.setFats(dailyRatios.getFats() + normalization
						* ingredient.getLipid_Tot_g());
				dailyRatios.setProteins(dailyRatios.getProteins()
						+ normalization * ingredient.getProtein_g());
				dailyRatios.setHydros(dailyRatios.getHydros() + normalization
						* ingredient.getCarbohydrt_g());
				dailyRatios.setSaturated(dailyRatios.getSaturated()
						+ normalization * ingredient.getFA_Sat_g());
				dailyRatios.setSalt(dailyRatios.getSalt() + normalization
						* (2.5*ingredient.getSodium_mg()/1000.0));
				dailyRatios.setSugars(dailyRatios.getSugars() + normalization
						* ingredient.getSugar_Tot_g());
				double weight = 0.0;
				if (dishingredients.get(i).getMetric() == 1) {
					diaryEntry.setMetric(ingredient.getGmWt_Desc1());
					weight = ingredient.getGmWt_1();
					diaryEntry.setMetricId(1);
				} else if (dishingredients.get(i).getMetric() == 2) {
					diaryEntry.setMetric(ingredient.getGmWt_Desc2());
					weight = ingredient.getGmWt_2();
					diaryEntry.setMetricId(2);
				} else if (dishingredients.get(i).getMetric() == 10) {
					diaryEntry.setMetric("Γραμμάρια");
					weight = 1.0;
					diaryEntry.setMetricId(10);
				} else if (dishingredients.get(i).getMetric() == 20) {
					diaryEntry.setMetric("Κιλά");
					weight = 100.0;
					diaryEntry.setMetricId(20);
				}

				dailyRatios.setWeight(dailyRatios.getWeight() + weight);
				diaryEntry.setIngredient(dishingredients.get(i).getIngredient()
						.getShrt_Desc());
				diaryEntry.setIngredientId(dishingredients.get(i).getIngredient().getId());
				diaryEntry.setAmount(dishingredients.get(i).getAmount()
						.toString());
				if (dish.getDayperiod() == 1) {
					diaryEntry.setDayperiod("Πρωϊνό");
				} else if (dish.getDayperiod() == 2) {
					diaryEntry.setDayperiod("Μεσημεριανό");
				} else if (dish.getDayperiod() == 3) {
					diaryEntry.setDayperiod("Βραδινό");
				} else if (dish.getDayperiod() == 4) {
					diaryEntry.setDayperiod("Ενδιάμεσο Σνακ");
				} else if (dish.getDayperiod() == 5) {
					diaryEntry.setDayperiod("Υγρό");
				}
				diaryEntry.setJoinId(dishingredients.get(i).getId());
				diaryEntries.add(diaryEntry);
			}

		}
		Profile prof = Ebean.find(Profile.class, userid);
		prof.setTransients();
		// System.out.println(play.libs.Json.toJson(prof));
		Recommender recommender = new Recommender();
		prof = recommender.runProfileRules(prof);
		System.out.println("Profile stats: ");
		System.out.println("cals " + prof.getCalories());
		System.out.println("fats " + prof.getFats());
		System.out.println("sugars " + prof.getSugars());
		System.out.println("hydros " + prof.getHydrocarbonate());
		System.out.println("salt " + prof.getSalt());

		dailyRatios
				.setCalories((int) ((int) 100.0 * (dailyRatios.getCalories() / prof
						.getCalories())));
		dailyRatios.setFats((int) ((int) 100.0 * (dailyRatios.getFats() / prof
				.getFats())));
		dailyRatios
				.setSugars((int) ((int) 100.0 * (dailyRatios.getSugars() / prof
						.getSugars())));
		dailyRatios
				.setHydros((int) ((int) 100.0 * (dailyRatios.getHydros() / prof
						.getHydrocarbonate())));
		dailyRatios.setSalt((int) ((int) 100.0 * (dailyRatios.getSalt() / prof
				.getSalt())));
		return ok(customerTrackingDiary.render(dailyRatios, diaryEntries,
				new SimpleDateFormat("MM/dd/yyyy").format(date)));
	}

	public static Result addDayDish() {
		DynamicForm form = Form.form().bindFromRequest();
		int userid = Integer.parseInt(session("userid"));

		Dish dishToAdd = new Dish();
		int ingredientsNum = Integer.parseInt(form.get("ingredientsLength"));

		Ebean.beginTransaction();
		try {
			dishToAdd.setDayperiod(Long.parseLong(form.get("dayperiod")));

			if (form.get("date") == null || form.get("date").equals("")) {
				Date today = new Date();
				dishToAdd.setDateadded(today);
			} else {
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				Date date = format.parse(form.get("date"));
				dishToAdd.setDateadded(date);
			}

			dishToAdd.save();
			System.out.println("Number of Ingredients: " + ingredientsNum);
			for (int i = 0; i < ingredientsNum; i++) {
				Joindishingredient joindishingredient = new Joindishingredient();
				joindishingredient.setAmount(Double.parseDouble(form
						.get("amounts" + i)));
				int metric = 0;
				if (form.get("metrics" + i).equals("1")) {
					metric = 1;
				} else if (form.get("metrics" + i).equals("2")) {
					metric = 2;
				} else if (form.get("metrics" + i).equals("10")) {
					metric = 10;
				} else if (form.get("metrics" + i).equals("20")) {
					metric = 20;
				}
				joindishingredient.setMetric(metric);

				joindishingredient.setDish(dishToAdd);
				joindishingredient.setIngredient(Ebean.find(Ingredient.class,
						Long.parseLong(form.get("ingredients" + i))));
				joindishingredient.save();

			}
			User user = Ebean.find(User.class, userid);
			Joinuserdish joinuserdish = new Joinuserdish();
			joinuserdish.setDish(dishToAdd);
			joinuserdish.setUser(user);
			joinuserdish.save();
			Ebean.commitTransaction();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			Ebean.endTransaction();
		}

		ArrayList<IngredientGroup> ingredientGroups = RestaurantDAO
				.getIngredientGroups();
		ArrayList<IngredientHelper> ingredients = RestaurantDAO
				.getIngredients();
		return customerTracking();
	}

	public static Integer getDishCalories(Dish dish) {

		List<Joindishingredient> dishingredients = Ebean
				.find(Joindishingredient.class).where()
				.eq("dishid", dish.getId()).findList();
		dishingredients.addAll(getComboIngredients(dish));
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		DailyRatios dailyRatios = new DailyRatios();
		for (int i = 0; i < dishingredients.size(); i++) {
			Ingredient ingredient = Ebean.find(Ingredient.class,
					dishingredients.get(i).getIngredient().getId());
			Double normalization = 0.0;
			// normalization factor based on metric
			if (dishingredients.get(i).getMetric() == 1) {
				normalization = ingredient.getGmWt_1() / 100.0;
			} else if (dishingredients.get(i).getMetric() == 2) {
				normalization = ingredient.getGmWt_2() / 100.0;
			} else if (dishingredients.get(i).getMetric() == 10) {
				normalization = 1.0 / 100.0;
			} else if (dishingredients.get(i).getMetric() == 20) {
				normalization = 1000.0 / 100.0;
			}
			if (dish.getIscombo() == null) {
				normalization /= 1;
			} else if (dish.getIscombo() == 0) {
				// renormalize based on amount
				normalization /= dish.getPortions();
			} else {
				normalization /= 1;
			}
			normalization *= dishingredients.get(i).getAmount();
			// calories
			dailyRatios
					.setCalories(dailyRatios.getCalories()
							+ (int) (normalization * (float) ingredient
									.getEnerg_Kcal()));
		}
		return dailyRatios.getCalories();

	}

	private static List<Joindishingredient> getComboIngredients(Dish combo) {
		List<Joincombodish> combodishList = Ebean.find(Joincombodish.class)
				.where().eq("comboid", combo.getId()).findList();
		List<Joindishingredient> comboingredientList = new ArrayList<Joindishingredient>();
		for (Joincombodish combodish : combodishList) {
			List<Joindishingredient> dishingredientList = Ebean
					.find(Joindishingredient.class).where()
					.eq("dishid", combodish.getDish().getId()).findList();
			comboingredientList.addAll(dishingredientList);
		}

		return comboingredientList;
	}

	public static Result customerQuestionnaire() {
		int userid = Integer.parseInt(session("userid"));
		List<Joinuserdish> joinuserdishes = Ebean.find(Joinuserdish.class)
				.where().eq("userid", userid).findList();
		ArrayList<Long> dishids = new ArrayList<Long>();
		for (Joinuserdish joinuserdish : joinuserdishes) {
			dishids.add(joinuserdish.getDish().getId());
		}
		List<Dish> dishes = Ebean.find(Dish.class).where().in("id", dishids)
				.isNotNull("name").findList();
		return ok(customerQuestionnaire.render(dishes));
	}

	public static Result fillCustomerQuestionnaire() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println(form);
		
		Customerpreference pref = new Customerpreference();
		Long userid = Long.parseLong(session("userid"));
		User user = Ebean.find(User.class, userid);
		pref.setId(user.getId());
		user.setHascompletedquestionnaire(1);
		
		Profile profile = Ebean.find(Profile.class, user.getId());
		profile.setTransients();
		Recommender recommender = new Recommender();
		profile = recommender.runProfileRules(profile);
		
		pref.setDateofbirth(profile.getDateofbirth());
		pref.setBmi(profile.getBmi());
		if (profile.getPregnancyStatus() != null) {
			pref.setPregnancy(profile.getPregnancyStatus().getId());
		} else {
			pref.setPregnancy(0L);
		}
		String dishes = "";
		String dishesratings = "";
		for (int i = 0; form.get("hidden-"+i) != null; i++) {
			dishes += (form.get("hidden-"+i) + ",");
			dishesratings += (form.get("rating-"+i) + ",");
		}
		pref.setDishes(dishes);
		pref.setDishesrating(dishesratings);
		String notlikedish = "";
		for (int i = 1; i < 5; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				notlikedish += i + ",";
			}
		}	
		pref.setNotlikedish(notlikedish);
		pref.setNotlikedishdescription(form.get("input14"));
		pref.setRecommendationsatisfaction(form.get("optionsRadios3"));
		String canbeimproved="";
		for (int i = 6; i < 13; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				canbeimproved += (i-6) + ",";
			}			
		}	
		pref.setCanbeimproved(canbeimproved);
		pref.setCanbeimproveddescription(form.get("input4"));
		String missincomponent="";
		for (int i = 13; i < 18; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				missincomponent += (i-12) + ",";
			}			
		}	
		pref.setMissingcomponent(missincomponent);
		pref.setMissingcomponentdescription(form.get("input5"));
		String dietconribution="";
		for (int i = 18; i < 26; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				dietconribution += (i-17) + ",";
			}			
		}		
		pref.setDietcontribution(dietconribution);
		pref.setDietcontributiondescription(form.get("input6"));
		pref.setRecommendationsadequate(form.get("optionsRadios7"));
		pref.setUsageforweightreduction(form.get("optionsRadios8"));
		String howuseforweightreduction="";
		for (int i = 26; i < 33; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				howuseforweightreduction += (i-25) + ",";
			}			
		}		
		pref.setHowuseforweightreduction(howuseforweightreduction);
		pref.setUsability(form.get("optionsRadios10"));
		pref.setWilluse(form.get("optionsRadios11"));
		pref.setWouldpay(form.get("optionsRadios12"));
		pref.setWouldpaydescription(form.get("input12"));
		pref.setSuitablename(form.get("optionsRadios13"));
		String relevanttoapplication="";
		for (int i = 33; i < 46; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				relevanttoapplication += (i-32) + ",";
			}			
		}		
		pref.setRelevanttoapplication(relevanttoapplication);
		pref.setWouldrecommend(form.get("optionsRadios15"));
		pref.setGeneralremarks(form.get("general"));
		user.setHascompletedquestionnaire(1);
		Ebean.beginTransaction();
		try {
			pref.save();
			user.update();
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
		return redirect("/disys/customerSearchMeal");
	}

	public static Result fetchDailyDishes() throws ParseException {
		DynamicForm form = Form.form().bindFromRequest();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		if (form.get("date") != null && !form.get("date").isEmpty()) {
			date = format.parse(form.get("date"));
		} else {
			date = new Date();
		}
		int userid = Integer.parseInt(session("userid"));
		List<Joinuserdish> joinuserdishes = Ebean.find(Joinuserdish.class)
				.where().eq("userid", userid).findList();
		ArrayList<Long> dishids = new ArrayList<Long>();
		for (Joinuserdish joinuserdish : joinuserdishes) {
			dishids.add(joinuserdish.getDish().getId());
		}
		List<Dish> dishes = Ebean
				.find(Dish.class)
				.where()
				.in("id", dishids)
				.eq("dateadded",
						new SimpleDateFormat("yyyy-MM-dd").format(date))
				.findList();

		List<DiaryEntry> diaryEntries = new ArrayList<DiaryEntry>();
		for (Dish dish : dishes) {
			List<Joindishingredient> dishingredients = Ebean
					.find(Joindishingredient.class).where()
					.eq("dishid", dish.getId()).findList();
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			for (int i = 0; i < dishingredients.size(); i++) {
				DiaryEntry diaryEntry = new DiaryEntry();
				Ingredient ingredient = Ebean.find(Ingredient.class,
						dishingredients.get(i).getIngredient().getId());
				if (dishingredients.get(i).getMetric() == 1) {
					diaryEntry.setMetric(ingredient.getGmWt_Desc1());
				} else if (dishingredients.get(i).getMetric() == 2) {
					diaryEntry.setMetric(ingredient.getGmWt_Desc2());
				} else if (dishingredients.get(i).getMetric() == 10) {
					diaryEntry.setMetric("Γραμμάρια");
				} else if (dishingredients.get(i).getMetric() == 20) {
					diaryEntry.setMetric("Κιλά");
				}

				diaryEntry.setIngredient(dishingredients.get(i).getIngredient()
						.getShrt_Desc());
				diaryEntry.setAmount(dishingredients.get(i).getAmount()
						.toString());
				if (dish.getDayperiod() == 1) {
					diaryEntry.setDayperiod("Πρωϊνό");
				} else if (dish.getDayperiod() == 2) {
					diaryEntry.setDayperiod("Μεσημεριανό");
				} else if (dish.getDayperiod() == 3) {
					diaryEntry.setDayperiod("Βραδινό");
				} else if (dish.getDayperiod() == 4) {
					diaryEntry.setDayperiod("Ενδιάμεσο Σνακ");
				} else if (dish.getDayperiod() == 5) {
					diaryEntry.setDayperiod("Υγρό");
				}
				diaryEntries.add(diaryEntry);
			}
		}
		return ok(play.libs.Json.toJson(diaryEntries));
	}

	public static Result fetchDishInfo() {
		return ok();
	}

	public static Result calculateDishDailyRatios() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println("Form Data:");
		System.out.println(form.get("dishid"));
		String dishes[] = form.get("dishid").split(",");
		DailyRatios dailyRatios = new DailyRatios();
		int userid = Integer.parseInt(session("userid"));
		Profile profile = Ebean.find(Profile.class, userid);
		profile.setTransients();
		Recommender recommender = new Recommender();
		profile = recommender.runProfileRules(profile);		
		for (String dishid : dishes) {
			if (dishid.isEmpty() || dishid == null) {
				continue;
			}
			Dish dish = Ebean.find(Dish.class,
					Long.parseLong(dishid));
			List<Joindishingredient> dishingredients = Ebean
					.find(Joindishingredient.class).where()
					.eq("dishid", dish.getId()).findList();
			dishingredients.addAll(getComboIngredients(dish));
			List<Ingredient> ingredients = new ArrayList<Ingredient>();			
			for (int i = 0; i < dishingredients.size(); i++) {
				Ingredient ingredient = Ebean.find(Ingredient.class,
						dishingredients.get(i).getIngredient().getId());
				Double normalization = 0.0;
				// normalization factor based on metric
				if (dishingredients.get(i).getMetric() == 1) {
					normalization = ingredient.getGmWt_1() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 2) {
					normalization = ingredient.getGmWt_2() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 10) {
					normalization = 1.0 / 100.0;
				} else if (dishingredients.get(i).getMetric() == 20) {
					normalization = 1000.0 / 100.0;
				}
				if (dish.getIscombo() == 0) {
					// renormalize based on amount
					normalization /= dish.getPortions();
				} else {
					normalization /= 1;
				}
				normalization *= dishingredients.get(i).getAmount();
				// Calories
				dailyRatios.setCalories(dailyRatios.getCalories()
						+ (int) (normalization * (float) ingredient
								.getEnerg_Kcal()));
				dailyRatios.setCaloriesNeeded(profile.getCalories());
				// Hydros
				dailyRatios.setHydros(dailyRatios.getHydros() + normalization
						* ingredient.getCarbohydrt_g());
				dailyRatios.setHydrosNeeded(profile.getHydrocarbonate());
				// Sugars
				dailyRatios.setSugars(dailyRatios.getSugars() + normalization
						* ingredient.getSugar_Tot_g());
				dailyRatios.setSugarsNeeded(profile.getSugars());
				// Fiber
				dailyRatios.setFiber(dailyRatios.getFiber() + normalization
						* ingredient.getFiber_TD_g());
				dailyRatios.setFiberNeeded(profile.getFiber());
				// Proteins
				dailyRatios.setProteins(dailyRatios.getProteins()
						+ normalization * ingredient.getProtein_g());
				dailyRatios.setProteinsNeeded(profile.getProtein());
				// Fats
				dailyRatios.setFats(dailyRatios.getFats() + normalization
						* ingredient.getLipid_Tot_g());
				dailyRatios.setFatsNeeded(profile.getFats());
				// Saturated
				dailyRatios.setSaturated(dailyRatios.getSaturated()
						+ normalization * ingredient.getFA_Sat_g());
				dailyRatios.setSaturatedNeeded(profile.getSaturated_fats());
				// Monosaturated
				dailyRatios.setMonosaturated(dailyRatios.getMonosaturated()
						+ normalization * ingredient.getFA_Mono_g());
				// PolySaturated
				dailyRatios.setPolysaturated(dailyRatios.getPolysaturated()
						+ normalization * ingredient.getFA_Poly_g());
				// Cholesterol
				dailyRatios.setCholesterol(dailyRatios.getCholesterol()
						+ normalization * ingredient.getCholestrl_mg());
				// Salt
				dailyRatios.setSalt(dailyRatios.getSalt() + normalization
						* (2.5*ingredient.getSodium_mg()/1000.0));
				dailyRatios.setSaltNeeded(profile.getSalt());

				// -----------------------------------------------//
				// Metala & Ixnostoixeia
				// -----------------------------------------------//

				// Calcium
				dailyRatios.setCalcium(dailyRatios.getCalcium() + normalization
						* ingredient.getCalcium_mg());
				dailyRatios.setCalciumNeeded(profile.getCalcium());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getCalcium() / dailyRatios
								.getCalciumNeeded()) >= 0.15) {
					dailyRatios.setCalciumDisplayed(1);
				} else {
					dailyRatios.setCalciumDisplayed(0);
				}

				// Iron
				dailyRatios.setIron(dailyRatios.getIron() + normalization
						* ingredient.getIron_mg());
				dailyRatios.setIronNeeded(profile.getIron());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getIron() / dailyRatios.getIronNeeded()) >= 0.15) {
					dailyRatios.setIronDisplayed(1);
				} else {
					dailyRatios.setIronDisplayed(0);
				}

				// Magnesium
				dailyRatios.setMagnesium(dailyRatios.getMagnesium()
						+ normalization * ingredient.getMagnesium_mg());
				dailyRatios.setMagnesiumNeeded(profile.getMagnesium());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getMagnesium() / dailyRatios
								.getMagnesiumNeeded()) >= 0.15) {
					dailyRatios.setMagnesiumDisplayed(1);
				} else {
					dailyRatios.setMagnesiumDisplayed(0);
				}

				// Phosphorus
				dailyRatios.setPhosphorus(dailyRatios.getPhosphorus()
						+ normalization * ingredient.getPhosphorus_mg());
				dailyRatios.setPhosphorusNeeded(profile.getPhosphorus());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getPhosphorus() / dailyRatios
								.getPhosphorusNeeded()) >= 0.15) {
					dailyRatios.setPhosphorusDisplayed(1);
				} else {
					dailyRatios.setPhosphorusDisplayed(0);
				}

				// Kalio
				dailyRatios.setPotassium(dailyRatios.getPotassium()
						+ normalization * ingredient.getPotassium_mg());
				dailyRatios.setPotassiumNeeded(profile.getPotassium());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getPotassium() / dailyRatios
								.getPotassiumNeeded()) >= 0.15) {
					dailyRatios.setPotassiumDisplayed(1);
				} else {
					dailyRatios.setPotassiumDisplayed(0);
				}

				// Natrio
				dailyRatios.setSalt(dailyRatios.getSalt() + normalization
						* (2.5*ingredient.getSodium_mg()/1000.0));
				dailyRatios.setSaltNeeded(profile.getSalt());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getSalt() / dailyRatios.getSaltNeeded()) >= 0.15) {
					dailyRatios.setSaltDisplayed(1);
				} else {
					dailyRatios.setSaltDisplayed(0);
				}

				// Pseydargyros
				dailyRatios.setZinc(dailyRatios.getZinc() + normalization
						* ingredient.getZinc_mg());
				dailyRatios.setZincNeeded(profile.getZinc());
				if (profile.getWatchmetals() == 1
						|| profile.getWatchantioxidants() == 1
						|| (dailyRatios.getZinc() / dailyRatios.getZincNeeded()) >= 0.15) {
					dailyRatios.setZincDisplayed(1);
				} else {
					dailyRatios.setZincDisplayed(0);
				}

				// Xalkos
				dailyRatios.setCopper(dailyRatios.getCopper() + normalization
						* ingredient.getCopper_mg());
				dailyRatios.setCopperNeeded(profile.getCopper());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getCopper() / dailyRatios
								.getCopperNeeded()) >= 0.15) {
					dailyRatios.setCopperDisplayed(1);
				} else {
					dailyRatios.setCopperDisplayed(0);
				}

				// Magganio
				dailyRatios.setMaganese(dailyRatios.getMaganese()
						+ normalization * ingredient.getManganese_mg());
				dailyRatios.setMaganeseNeeded(profile.getManganese());
				if (profile.getWatchmetals() == 1
						|| (dailyRatios.getMaganese() / dailyRatios
								.getMaganeseNeeded()) >= 0.15) {
					dailyRatios.setMaganeseDisplayed(1);
				} else {
					dailyRatios.setMaganeseDisplayed(0);
				}

				// Selinio
				dailyRatios.setSelenium(dailyRatios.getSelenium()
						+ normalization * ingredient.getSelenium_µg());
				dailyRatios.setSeleniumNeeded(profile.getSelenium());
				if (profile.getWatchmetals() == 1
						|| profile.getWatchantioxidants() == 1
						|| (dailyRatios.getSelenium() / dailyRatios
								.getSeleniumNeeded()) >= 0.15) {
					dailyRatios.setSeleniumDisplayed(1);
				} else {
					dailyRatios.setSeleniumDisplayed(0);
				}
				// ----------------------------------------------------------//
				// Vitamins
				// ----------------------------------------------------------//

				// C
				dailyRatios.setVitaminc(dailyRatios.getVitaminc()
						+ normalization * ingredient.getVit_C_mg());
				dailyRatios.setVitamincNeeded(profile.getVitamin_C());
				if (profile.getWatchvitamins() == 1
						|| profile.getWatchantioxidants() == 1
						|| (dailyRatios.getVitaminc() / dailyRatios
								.getVitamincNeeded()) >= 0.15) {
					dailyRatios.setVitamincDisplayed(1);
				} else {
					dailyRatios.setVitamincDisplayed(0);
				}

				// Thiamini
				dailyRatios.setThiamin(dailyRatios.getThiamin() + normalization
						* ingredient.getThiamin_mg());
				dailyRatios.setThiaminNeeded(profile.getThiamin());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getThiamin() / dailyRatios
								.getThiaminNeeded()) >= 0.15) {
					dailyRatios.setThiaminDisplayed(1);
				} else {
					dailyRatios.setThiaminDisplayed(0);
				}

				// Riboflavini
				dailyRatios.setRiboflavin(dailyRatios.getRiboflavin()
						+ normalization * ingredient.getRiboflavin_mg());
				dailyRatios.setRiboflavinNeeded(profile.getRiboflavin());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getRiboflavin() / dailyRatios
								.getRiboflavinNeeded()) >= 0.15) {
					dailyRatios.setRiboflavinDisplayed(1);
				} else {
					dailyRatios.setRiboflavinDisplayed(0);
				}

				// Niacin
				dailyRatios.setNiacin(dailyRatios.getNiacin() + normalization
						* ingredient.getNiacin_mg());
				dailyRatios.setNiacinNeeded(profile.getVitamin_B3());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getNiacin() / dailyRatios
								.getNiacinNeeded()) >= 0.15) {
					dailyRatios.setNiacinDisplayed(1);
				} else {
					dailyRatios.setNiacinDisplayed(0);
				}

				// Pantotheniko oksy
				dailyRatios.setPantothenic(dailyRatios.getPantothenic()
						+ normalization * ingredient.getPanto_Acid_mg());
				dailyRatios.setPantothenicNeeded(profile.getPantothenic());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getPantothenic() / dailyRatios
								.getPantothenicNeeded()) >= 0.15) {
					dailyRatios.setPantothenicDisplayed(1);
				} else {
					dailyRatios.setPantothenicDisplayed(0);
				}

				// B6
				dailyRatios.setB6(dailyRatios.getB6() + normalization
						* ingredient.getVit_B6_mg());
				dailyRatios.setB6Needed(profile.getVitamin_B6());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getB6() / dailyRatios.getB6Needed()) >= 0.15) {
					dailyRatios.setB6Displayed(1);
				} else {
					dailyRatios.setB6Displayed(0);
				}

				// Foliko oksy
				dailyRatios.setFolate(dailyRatios.getFolate() + normalization
						* ingredient.getFolate_Tot_µg());
				dailyRatios.setFolateNeeded(profile.getFolate());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getFolate() / dailyRatios
								.getFolateNeeded()) >= 0.15) {
					dailyRatios.setFolateDisplayed(1);
				} else {
					dailyRatios.setFolateDisplayed(0);
				}

				// Cholini
				dailyRatios.setCholine(dailyRatios.getCholine() + normalization
						* ingredient.getCholine_Tot_mg());
				dailyRatios.setCholineNeeded(profile.getCholine());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getCholine() / dailyRatios
								.getCholineNeeded()) >= 0.15) {
					dailyRatios.setCholineDisplayed(1);
				} else {
					dailyRatios.setCholineDisplayed(0);
				}

				// B12
				dailyRatios.setB12(dailyRatios.getB12() + normalization
						* ingredient.getVit_B12_µg());
				dailyRatios.setB12Needed(profile.getVitamin_B12());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getB12() / dailyRatios.getB12Needed()) >= 0.15) {
					dailyRatios.setB12Displayed(1);
				} else {
					dailyRatios.setB12Displayed(0);
				}

				// Vitamin A
				dailyRatios.setVitamina(dailyRatios.getVitamina()
						+ normalization * ingredient.getVit_A_RAE_µg_());
				dailyRatios.setVitaminaNeeded(profile.getVitamin_A());
				if (profile.getWatchvitamins() == 1
						|| profile.getWatchantioxidants() == 1
						|| (dailyRatios.getVitamina() / dailyRatios
								.getVitaminaNeeded()) >= 0.15) {
					dailyRatios.setVitaminaDisplayed(1);
				} else {
					dailyRatios.setVitaminaDisplayed(0);
				}

				// Vitamin D
				dailyRatios.setVitamind(dailyRatios.getVitamind()
						+ normalization * ingredient.getVit_D_µg());
				dailyRatios.setVitamindNeeded(profile.getVitamin_D());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getVitamind() / dailyRatios
								.getVitamindNeeded()) >= 0.15) {
					dailyRatios.setVitamindDisplayed(1);
				} else {
					dailyRatios.setVitamindDisplayed(0);
				}

				// Vitamin E
				dailyRatios.setVitamine(dailyRatios.getVitamine()
						+ normalization * ingredient.getVit_E_mg());
				dailyRatios.setVitamineNeeded(profile.getVitamin_E());
				if (profile.getWatchvitamins() == 1
						|| profile.getWatchantioxidants() == 1
						|| (dailyRatios.getVitamine() / dailyRatios
								.getVitamineNeeded()) >= 0.15) {
					dailyRatios.setVitamineDisplayed(1);
				} else {
					dailyRatios.setVitamineDisplayed(0);
				}

				// Vitamin K
				dailyRatios.setVitamink(dailyRatios.getVitamink()
						+ normalization * ingredient.getVit_K_µg());
				dailyRatios.setVitaminkNeeded(profile.getVitamin_K());
				if (profile.getWatchvitamins() == 1
						|| (dailyRatios.getVitamink() / dailyRatios
								.getVitaminkNeeded()) >= 0.15) {
					dailyRatios.setVitaminkDisplayed(1);
				} else {
					dailyRatios.setVitaminkDisplayed(0);
				}

				// --------------------------------------------------------//
				// Antioxidants
				// --------------------------------------------------------//
				dailyRatios.setAlphaCarot(dailyRatios.getAlphaCarot()
						+ normalization * ingredient.getAlpha_Carot_µg());
				if (profile.getWatchantioxidants() == 1) {
					dailyRatios.setAlphaCarotDisplayed(1);
				} else {
					dailyRatios.setAlphaCarotDisplayed(0);
				}
				dailyRatios.setBetaCarot(dailyRatios.getBetaCarot()
						+ normalization * ingredient.getBeta_Carot_µg());
				if (profile.getWatchantioxidants() == 1) {
					dailyRatios.setBetaCarotDisplayed(1);
				} else {
					dailyRatios.setBetaCarotDisplayed(0);
				}	
				dailyRatios.setCrypto(dailyRatios.getCrypto()
						+ normalization * ingredient.getBeta_Crypt_µg());
				if (profile.getWatchantioxidants() == 1) {
					dailyRatios.setCryptoDisplayed(1);
				} else {
					dailyRatios.setCryptoDisplayed(0);
				}	
				dailyRatios.setLycopene(dailyRatios.getLycopene()
						+ normalization * ingredient.getLycopene_µg());
				if (profile.getWatchantioxidants() == 1) {
					dailyRatios.setLycopeneDisplayed(1);
				} else {
					dailyRatios.setLycopeneDisplayed(0);
				}	
				dailyRatios.setLuzea(dailyRatios.getLuzea()
						+ normalization * ingredient.getLutZea_µg());
				if (profile.getWatchantioxidants() == 1) {
					dailyRatios.setLuzeaDisplayed(1);
				} else {
					dailyRatios.setLuzeaDisplayed(0);
				}				

				// calculate weight of Dish
				double weight = 0.0;
				if (dishingredients.get(i).getMetric() == 1) {
					weight = ingredient.getGmWt_1();
				} else if (dishingredients.get(i).getMetric() == 2) {
					weight = ingredient.getGmWt_2();
				} else if (dishingredients.get(i).getMetric() == 10) {
					weight = 1.0;
				} else if (dishingredients.get(i).getMetric() == 20) {
					weight = 100.0;
				}
				weight *= dishingredients.get(i).getAmount();
				if (dish.getIscombo() == 0) {
					weight /= dish.getPortions();
				} else {
					weight /= 1;
				}
				dailyRatios.setWeight(dailyRatios.getWeight() + weight);
			}
		}

		// dailyRatios.set
		return ok(play.libs.Json.toJson(dailyRatios));
	}

	public static Result changeIngredientAmount() {
		DynamicForm form = new DynamicForm().bindFromRequest();
		Joindishingredient dishingredient = Ebean.find(
				Joindishingredient.class, Long.parseLong(form.get("joinid")));
		dishingredient.setAmount(Double.parseDouble(form.get("amount")));
		Ebean.beginTransaction();
		try {
			dishingredient.update();
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
		return ok();
	}
	public static Result calculateIngredientsDailyRatios() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println("Form Data:");
		System.out.println(form.get("ingredientList"));
		System.out.println(form.get("amountList"));
		System.out.println(form.get("metricList"));
		String ingredients[] = form.get("ingredientList").split(",");
		String amounts[] = form.get("amountList").split(",");
		String metrics[] = form.get("metricList").split(",");

		int userid = Integer.parseInt(session("userid"));
		Profile profile = Ebean.find(Profile.class, userid);
		profile.setTransients();
		Recommender recommender = new Recommender();
		profile = recommender.runProfileRules(profile);
		
		DailyRatios dailyRatios = new DailyRatios();
		for (int i = 0; i < ingredients.length; i++) {
			if (ingredients[i].isEmpty() || ingredients[i] == null) {
				continue;
			}
			Ingredient ingredient = Ebean.find(Ingredient.class,
					Long.parseLong(ingredients[i]));
			Double normalization = 0.0;
			// normalization factor based on metric
			if (Integer.parseInt(metrics[i]) == 1) {
				normalization = ingredient.getGmWt_1() / 100.0;
			} else if (Integer.parseInt(metrics[i]) == 2) {
				normalization = ingredient.getGmWt_2() / 100.0;
			} else if (Integer.parseInt(metrics[i]) == 10) {
				normalization = 1.0 / 100.0;
			} else if (Integer.parseInt(metrics[i]) == 20) {
				normalization = 1000.0 / 100.0;
			}		
			
			normalization *= Double.parseDouble(amounts[i]);
			
			// Calories
			dailyRatios.setCalories(dailyRatios.getCalories()
					+ (int) (normalization * (float) ingredient
							.getEnerg_Kcal()));
			dailyRatios.setCaloriesNeeded(profile.getCalories());
			// Hydros
			dailyRatios.setHydros(dailyRatios.getHydros() + normalization
					* ingredient.getCarbohydrt_g());
			dailyRatios.setHydrosNeeded(profile.getHydrocarbonate());
			// Sugars
			dailyRatios.setSugars(dailyRatios.getSugars() + normalization
					* ingredient.getSugar_Tot_g());
			dailyRatios.setSugarsNeeded(profile.getSugars());
			// Fiber
			dailyRatios.setFiber(dailyRatios.getFiber() + normalization
					* ingredient.getFiber_TD_g());
			dailyRatios.setFiberNeeded(profile.getFiber());
			// Proteins
			dailyRatios.setProteins(dailyRatios.getProteins()
					+ normalization * ingredient.getProtein_g());
			dailyRatios.setProteinsNeeded(profile.getProtein());
			// Fats
			dailyRatios.setFats(dailyRatios.getFats() + normalization
					* ingredient.getLipid_Tot_g());
			dailyRatios.setFatsNeeded(profile.getFats());
			// Saturated
			dailyRatios.setSaturated(dailyRatios.getSaturated()
					+ normalization * ingredient.getFA_Sat_g());
			dailyRatios.setSaturatedNeeded(profile.getSaturated_fats());
			// Monosaturated
			dailyRatios.setMonosaturated(dailyRatios.getMonosaturated()
					+ normalization * ingredient.getFA_Mono_g());
			// PolySaturated
			dailyRatios.setPolysaturated(dailyRatios.getPolysaturated()
					+ normalization * ingredient.getFA_Poly_g());
			// Cholesterol
			dailyRatios.setCholesterol(dailyRatios.getCholesterol()
					+ normalization * ingredient.getCholestrl_mg());
			// Salt
			dailyRatios.setSalt(dailyRatios.getSalt() + normalization
					* (2.5*ingredient.getSodium_mg()/1000.0));
			dailyRatios.setSaltNeeded(profile.getSalt());

			// -----------------------------------------------//
			// Metala & Ixnostoixeia
			// -----------------------------------------------//

			// Calcium
			dailyRatios.setCalcium(dailyRatios.getCalcium() + normalization
					* ingredient.getCalcium_mg());
			dailyRatios.setCalciumNeeded(profile.getCalcium());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getCalcium() / dailyRatios
							.getCalciumNeeded()) >= 0.15) {
				dailyRatios.setCalciumDisplayed(1);
			} else {
				dailyRatios.setCalciumDisplayed(0);
			}

			// Iron
			dailyRatios.setIron(dailyRatios.getIron() + normalization
					* ingredient.getIron_mg());
			dailyRatios.setIronNeeded(profile.getIron());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getIron() / dailyRatios.getIronNeeded()) >= 0.15) {
				dailyRatios.setIronDisplayed(1);
			} else {
				dailyRatios.setIronDisplayed(0);
			}

			// Magnesium
			dailyRatios.setMagnesium(dailyRatios.getMagnesium()
					+ normalization * ingredient.getMagnesium_mg());
			dailyRatios.setMagnesiumNeeded(profile.getMagnesium());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getMagnesium() / dailyRatios
							.getMagnesiumNeeded()) >= 0.15) {
				dailyRatios.setMagnesiumDisplayed(1);
			} else {
				dailyRatios.setMagnesiumDisplayed(0);
			}

			// Phosphorus
			dailyRatios.setPhosphorus(dailyRatios.getPhosphorus()
					+ normalization * ingredient.getPhosphorus_mg());
			dailyRatios.setPhosphorusNeeded(profile.getPhosphorus());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getPhosphorus() / dailyRatios
							.getPhosphorusNeeded()) >= 0.15) {
				dailyRatios.setPhosphorusDisplayed(1);
			} else {
				dailyRatios.setPhosphorusDisplayed(0);
			}

			// Kalio
			dailyRatios.setPotassium(dailyRatios.getPotassium()
					+ normalization * ingredient.getPotassium_mg());
			dailyRatios.setPotassiumNeeded(profile.getPotassium());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getPotassium() / dailyRatios
							.getPotassiumNeeded()) >= 0.15) {
				dailyRatios.setPotassiumDisplayed(1);
			} else {
				dailyRatios.setPotassiumDisplayed(0);
			}

			// Natrio
			dailyRatios.setSalt(dailyRatios.getSalt() + normalization
					* (2.5*ingredient.getSodium_mg()/1000.0));
			dailyRatios.setSaltNeeded(profile.getSalt());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getSalt() / dailyRatios.getSaltNeeded()) >= 0.15) {
				dailyRatios.setSaltDisplayed(1);
			} else {
				dailyRatios.setSaltDisplayed(0);
			}

			// Pseydargyros
			dailyRatios.setZinc(dailyRatios.getZinc() + normalization
					* ingredient.getZinc_mg());
			dailyRatios.setZincNeeded(profile.getZinc());
			if (profile.getWatchmetals() == 1
					|| profile.getWatchantioxidants() == 1
					|| (dailyRatios.getZinc() / dailyRatios.getZincNeeded()) >= 0.15) {
				dailyRatios.setZincDisplayed(1);
			} else {
				dailyRatios.setZincDisplayed(0);
			}

			// Xalkos
			dailyRatios.setCopper(dailyRatios.getCopper() + normalization
					* ingredient.getCopper_mg());
			dailyRatios.setCopperNeeded(profile.getCopper());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getCopper() / dailyRatios
							.getCopperNeeded()) >= 0.15) {
				dailyRatios.setCopperDisplayed(1);
			} else {
				dailyRatios.setCopperDisplayed(0);
			}

			// Magganio
			dailyRatios.setMaganese(dailyRatios.getMaganese()
					+ normalization * ingredient.getManganese_mg());
			dailyRatios.setMaganeseNeeded(profile.getManganese());
			if (profile.getWatchmetals() == 1
					|| (dailyRatios.getMaganese() / dailyRatios
							.getMaganeseNeeded()) >= 0.15) {
				dailyRatios.setMaganeseDisplayed(1);
			} else {
				dailyRatios.setMaganeseDisplayed(0);
			}

			// Selinio
			dailyRatios.setSelenium(dailyRatios.getSelenium()
					+ normalization * ingredient.getSelenium_µg());
			dailyRatios.setSeleniumNeeded(profile.getSelenium());
			if (profile.getWatchmetals() == 1
					|| profile.getWatchantioxidants() == 1
					|| (dailyRatios.getSelenium() / dailyRatios
							.getSeleniumNeeded()) >= 0.15) {
				dailyRatios.setSeleniumDisplayed(1);
			} else {
				dailyRatios.setSeleniumDisplayed(0);
			}
			// ----------------------------------------------------------//
			// Vitamins
			// ----------------------------------------------------------//

			// C
			dailyRatios.setVitaminc(dailyRatios.getVitaminc()
					+ normalization * ingredient.getVit_C_mg());
			dailyRatios.setVitamincNeeded(profile.getVitamin_C());
			if (profile.getWatchvitamins() == 1
					|| profile.getWatchantioxidants() == 1
					|| (dailyRatios.getVitaminc() / dailyRatios
							.getVitamincNeeded()) >= 0.15) {
				dailyRatios.setVitamincDisplayed(1);
			} else {
				dailyRatios.setVitamincDisplayed(0);
			}

			// Thiamini
			dailyRatios.setThiamin(dailyRatios.getThiamin() + normalization
					* ingredient.getThiamin_mg());
			dailyRatios.setThiaminNeeded(profile.getThiamin());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getThiamin() / dailyRatios
							.getThiaminNeeded()) >= 0.15) {
				dailyRatios.setThiaminDisplayed(1);
			} else {
				dailyRatios.setThiaminDisplayed(0);
			}

			// Riboflavini
			dailyRatios.setRiboflavin(dailyRatios.getRiboflavin()
					+ normalization * ingredient.getRiboflavin_mg());
			dailyRatios.setRiboflavinNeeded(profile.getRiboflavin());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getRiboflavin() / dailyRatios
							.getRiboflavinNeeded()) >= 0.15) {
				dailyRatios.setRiboflavinDisplayed(1);
			} else {
				dailyRatios.setRiboflavinDisplayed(0);
			}

			// Niacin
			dailyRatios.setNiacin(dailyRatios.getNiacin() + normalization
					* ingredient.getNiacin_mg());
			dailyRatios.setNiacinNeeded(profile.getVitamin_B3());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getNiacin() / dailyRatios
							.getNiacinNeeded()) >= 0.15) {
				dailyRatios.setNiacinDisplayed(1);
			} else {
				dailyRatios.setNiacinDisplayed(0);
			}

			// Pantotheniko oksy
			dailyRatios.setPantothenic(dailyRatios.getPantothenic()
					+ normalization * ingredient.getPanto_Acid_mg());
			dailyRatios.setPantothenicNeeded(profile.getPantothenic());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getPantothenic() / dailyRatios
							.getPantothenicNeeded()) >= 0.15) {
				dailyRatios.setPantothenicDisplayed(1);
			} else {
				dailyRatios.setPantothenicDisplayed(0);
			}

			// B6
			dailyRatios.setB6(dailyRatios.getB6() + normalization
					* ingredient.getVit_B6_mg());
			dailyRatios.setB6Needed(profile.getVitamin_B6());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getB6() / dailyRatios.getB6Needed()) >= 0.15) {
				dailyRatios.setB6Displayed(1);
			} else {
				dailyRatios.setB6Displayed(0);
			}

			// Foliko oksy
			dailyRatios.setFolate(dailyRatios.getFolate() + normalization
					* ingredient.getFolate_Tot_µg());
			dailyRatios.setFolateNeeded(profile.getFolate());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getFolate() / dailyRatios
							.getFolateNeeded()) >= 0.15) {
				dailyRatios.setFolateDisplayed(1);
			} else {
				dailyRatios.setFolateDisplayed(0);
			}

			// Cholini
			dailyRatios.setCholine(dailyRatios.getCholine() + normalization
					* ingredient.getCholine_Tot_mg());
			dailyRatios.setCholineNeeded(profile.getCholine());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getCholine() / dailyRatios
							.getCholineNeeded()) >= 0.15) {
				dailyRatios.setCholineDisplayed(1);
			} else {
				dailyRatios.setCholineDisplayed(0);
			}

			// B12
			dailyRatios.setB12(dailyRatios.getB12() + normalization
					* ingredient.getVit_B12_µg());
			dailyRatios.setB12Needed(profile.getVitamin_B12());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getB12() / dailyRatios.getB12Needed()) >= 0.15) {
				dailyRatios.setB12Displayed(1);
			} else {
				dailyRatios.setB12Displayed(0);
			}

			// Vitamin A
			dailyRatios.setVitamina(dailyRatios.getVitamina()
					+ normalization * ingredient.getVit_A_RAE_µg_());
			dailyRatios.setVitaminaNeeded(profile.getVitamin_A());
			if (profile.getWatchvitamins() == 1
					|| profile.getWatchantioxidants() == 1
					|| (dailyRatios.getVitamina() / dailyRatios
							.getVitaminaNeeded()) >= 0.15) {
				dailyRatios.setVitaminaDisplayed(1);
			} else {
				dailyRatios.setVitaminaDisplayed(0);
			}

			// Vitamin D
			dailyRatios.setVitamind(dailyRatios.getVitamind()
					+ normalization * ingredient.getVit_D_µg());
			dailyRatios.setVitamindNeeded(profile.getVitamin_D());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getVitamind() / dailyRatios
							.getVitamindNeeded()) >= 0.15) {
				dailyRatios.setVitamindDisplayed(1);
			} else {
				dailyRatios.setVitamindDisplayed(0);
			}

			// Vitamin E
			dailyRatios.setVitamine(dailyRatios.getVitamine()
					+ normalization * ingredient.getVit_E_mg());
			dailyRatios.setVitamineNeeded(profile.getVitamin_E());
			if (profile.getWatchvitamins() == 1
					|| profile.getWatchantioxidants() == 1
					|| (dailyRatios.getVitamine() / dailyRatios
							.getVitamineNeeded()) >= 0.15) {
				dailyRatios.setVitamineDisplayed(1);
			} else {
				dailyRatios.setVitamineDisplayed(0);
			}

			// Vitamin K
			dailyRatios.setVitamink(dailyRatios.getVitamink()
					+ normalization * ingredient.getVit_K_µg());
			dailyRatios.setVitaminkNeeded(profile.getVitamin_K());
			if (profile.getWatchvitamins() == 1
					|| (dailyRatios.getVitamink() / dailyRatios
							.getVitaminkNeeded()) >= 0.15) {
				dailyRatios.setVitaminkDisplayed(1);
			} else {
				dailyRatios.setVitaminkDisplayed(0);
			}

			// --------------------------------------------------------//
			// Antioxidants
			// --------------------------------------------------------//
			dailyRatios.setAlphaCarot(dailyRatios.getAlphaCarot()
					+ normalization * ingredient.getAlpha_Carot_µg());
			if (profile.getWatchantioxidants() == 1) {
				dailyRatios.setAlphaCarotDisplayed(1);
			} else {
				dailyRatios.setAlphaCarotDisplayed(0);
			}
			dailyRatios.setBetaCarot(dailyRatios.getBetaCarot()
					+ normalization * ingredient.getBeta_Carot_µg());
			if (profile.getWatchantioxidants() == 1) {
				dailyRatios.setBetaCarotDisplayed(1);
			} else {
				dailyRatios.setBetaCarotDisplayed(0);
			}	
			dailyRatios.setCrypto(dailyRatios.getCrypto()
					+ normalization * ingredient.getBeta_Crypt_µg());
			if (profile.getWatchantioxidants() == 1) {
				dailyRatios.setCryptoDisplayed(1);
			} else {
				dailyRatios.setCryptoDisplayed(0);
			}	
			dailyRatios.setLycopene(dailyRatios.getLycopene()
					+ normalization * ingredient.getLycopene_µg());
			if (profile.getWatchantioxidants() == 1) {
				dailyRatios.setLycopeneDisplayed(1);
			} else {
				dailyRatios.setLycopeneDisplayed(0);
			}	
			dailyRatios.setLuzea(dailyRatios.getLuzea()
					+ normalization * ingredient.getLutZea_µg());
			if (profile.getWatchantioxidants() == 1) {
				dailyRatios.setLuzeaDisplayed(1);
			} else {
				dailyRatios.setLuzeaDisplayed(0);
			}				
			
			
			// calculate weight of Dish
			double weight = 0.0;
			if (Integer.parseInt(metrics[i]) == 1) {
				weight = ingredient.getGmWt_1();
			} else if (Integer.parseInt(metrics[i]) == 2) {
				weight = ingredient.getGmWt_2();
			} else if (Integer.parseInt(metrics[i]) == 10) {
				weight = 1.0;
			} else if (Integer.parseInt(metrics[i])== 20) {
				weight = 100.0;
			}
			weight *= Double.parseDouble(amounts[i]);

			dailyRatios.setWeight(dailyRatios.getWeight() + weight);			
			
		}
		/*
		for (String dishid : dishes) {
			if (dishid.isEmpty() || dishid == null) {
				continue;
			}
			Dish dish = Ebean.find(Dish.class,
					Long.parseLong(dishid));

			List<Joindishingredient> dishingredients = Ebean
					.find(Joindishingredient.class).where()
					.eq("dishid", dish.getId()).findList();
			dishingredients.addAll(getComboIngredients(dish));
			List<Ingredient> ingredients = new ArrayList<Ingredient>();			
			for (int i = 0; i < dishingredients.size(); i++) {
				Ingredient ingredient = Ebean.find(Ingredient.class,
						dishingredients.get(i).getIngredient().getId());
				Double normalization = 0.0;
				// normalization factor based on metric
				if (dishingredients.get(i).getMetric() == 1) {
					normalization = ingredient.getGmWt_1() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 2) {
					normalization = ingredient.getGmWt_2() / 100.0;
				} else if (dishingredients.get(i).getMetric() == 10) {
					normalization = 1.0 / 100.0;
				} else if (dishingredients.get(i).getMetric() == 20) {
					normalization = 1000.0 / 100.0;
				}
				if (dish.getIscombo() == 0) {
					// renormalize based on amount
					normalization /= dish.getPortions();
				} else {
					normalization /= 1;
				}
				normalization *= dishingredients.get(i).getAmount();


			}
		}*/

		// dailyRatios.set
		return ok(play.libs.Json.toJson(dailyRatios));
	}
	
	public static Result getIngredientsByName() {
		ArrayList<IngredientEntryBean> ingredientEntries = new ArrayList<IngredientEntryBean>();
		String searchTerm = Form.form().bindFromRequest().get("term");
		List<Ingredient> ingredients = Ebean.find(Ingredient.class)
				.where()
				.like("shrt_desc", "%"+searchTerm+"%")
				.findList();
		for (Ingredient ingredient: ingredients) {
			IngredientEntryBean entryBean = new IngredientEntryBean();
			entryBean.setId(ingredient.getId());
			entryBean.setLabel(ingredient.getShrt_Desc());
			entryBean.setValue(ingredient.getShrt_Desc());
			if (ingredient.getGmWt_1() != null) {
				entryBean.setMet1(ingredient.getGmWt_1().toString());
				entryBean.setDesc1(ingredient.getGmWt_Desc1());
			}
			if (ingredient.getGmWt_2() != null) {
				entryBean.setMet2(ingredient.getGmWt_2().toString());
				entryBean.setDesc2(ingredient.getGmWt_Desc2());
			}			
			ingredientEntries.add(entryBean);
		}
			
		return ok(play.libs.Json.toJson(ingredientEntries));
	}
}
