package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.util.IOUtils;

//import org.apache.commons.io.FileUtils;
import beans.DailyRatios;
import beans.IngredientHelper;
import models.ingredient.Ingredient;
import beans.IngredientGroup;

import com.avaje.ebean.Ebean;

import models.DAO.HelperDataDAO;
import models.DAO.RestaurantDAO;
import models.lists.Hallergen;
import models.lists.Hcategory;
import models.lists.Hcerealswithglouten;
import models.lists.Hcharacteristic;
import models.lists.Hcousine;
import models.lists.Hfruitwithshell;
import models.lists.Hpricerange;
import models.lists.Hrestaurantcategory;
import models.lists.Hrestaurantlicence;
import models.relations.Joincombodish;
import models.relations.Joindishallergen;
import models.relations.Joindishcerealswithglouten;
import models.relations.Joindishcharacteristic;
import models.relations.Joindishfruitwithshell;
import models.relations.Joindishingredient;
import models.relations.Joindishportions;
import models.relations.Joinrestaurantcousine;
import models.relations.Joinrestaurantdish;
import models.relations.Joinrestauranthrestaurantcategory;
import models.restaurant.Dish;
import models.restaurant.Restaurant;
import models.user.Ownerpreference;
import models.user.User;
import play.api.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.owner.ownerWelcomeScreen;
import views.html.owner.profile;
import views.html.recipes.newRecipe;
import views.html.recipes.recipesmain;
import views.html.recipes.myrecipes;
import views.html.recipes.combos;
import views.html.owner.restaurants;
import views.html.owner.ownerQuestionnaire;
import views.html.checkdishimage;

public class Owner extends Controller {
	private static final Form<Restaurant> restaurantForm = Form
			.form(Restaurant.class);
	private static final List<Hcousine> cousines = Hcousine.find.all();
	private static final List<Hcharacteristic> characteristics = Hcharacteristic.find
			.where().orderBy("id desc").findList();
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

	public static Result welcomeScreen() {
		if (session("userid") == null) {
			return redirect("/disys/login");
		}
		return ok(ownerWelcomeScreen.render());
	}

	public static Result ownerRestaurants() {
		Long userid = Long.parseLong(session("userid"));
		List<Restaurant> restaurants = Ebean.find(Restaurant.class).where()
				.eq("userid", userid).findList();
		return ok(views.html.owner.restaurants.render(restaurantForm,
				restaurants, cousines, licences, prices, restaurantcategories));
	}

	public static Result ownerNewRecipe() {
		int userid = Integer.parseInt(session("userid"));
		//ArrayList<Restaurant> restaurants = RestaurantDAO
		//		.getRestaurants(userid);
		List<Restaurant> restaurants = Ebean.find(Restaurant.class)
				.where()
				.eq("userid", userid)
				.findList();
		ArrayList<IngredientGroup> ingredientGroups = RestaurantDAO
				.getIngredientGroups();
		ArrayList<IngredientHelper> ingredients = RestaurantDAO
				.getIngredients();
		return ok(newRecipe.render(restaurants, ingredientGroups, ingredients,
				cousines, characteristics, categories, fruitswithshell,
				cerealswithglouten));
	}

	public static Result ownerProfile() {
		if (session("userid") == null) {
			return redirect("/disys/login");
		}
		int userid = Integer.parseInt(session("userid"));
		User user = Ebean.find(User.class, userid);
		List<Restaurant> restaurants = Ebean.find(Restaurant.class)
				.where()
				.eq("userid", userid)
				.findList();
		Map<Long, Restaurant> restaurantMap = new HashMap<Long, Restaurant>();
		for (Restaurant res : restaurants) {
			restaurantMap.put(res.getId(), res);
		}
		return ok(profile.render(restaurantForm, restaurants, restaurantMap,
				cousines, licences, restaurantcategories, prices, user));
	}

	public static Result ownerUpdatePersonalDataSurname() {
		int userid = Integer.parseInt(session("userid"));
		User user = Ebean.find(User.class, userid);
		Map<String, String[]> values = request().body().asFormUrlEncoded();
		user.setSurname(values.get("value")[0]);
		try {
			user.update();
			return ok();
		} catch (Exception e) {
			return internalServerError();
		}
	}

	public static Result ownerUpdatePersonalDataMail() {
		int userid = Integer.parseInt(session("userid"));
		User user = Ebean.find(User.class, userid);
		Map<String, String[]> values = request().body().asFormUrlEncoded();
		System.out.println("type: " + values.get("value")[0]);
		user.setEmail(values.get("value")[0]);
		try {
			user.update();
			return ok();
		} catch (Exception e) {
			return internalServerError();
		}
	}

	public static Result ownerUpdatePersonalDataName() {
		int userid = Integer.parseInt(session("userid"));
		User user = Ebean.find(User.class, userid);
		Map<String, String[]> values = request().body().asFormUrlEncoded();
		System.out.println("type: " + values.get("value")[0]);
		user.setName(values.get("value")[0]);
		try {
			user.update();
			return ok();
		} catch (Exception e) {
			return internalServerError();
		}
	}

	public static Result ownerUpdatePersonalDataPhone() {
		int userid = Integer.parseInt(session("userid"));
		User user = Ebean.find(User.class, userid);
		Map<String, String[]> values = request().body().asFormUrlEncoded();
		// System.out.println("type: " + values.get("value")[0]);
		user.setPhone(values.get("value")[0]);
		try {
			user.update();
			return ok();
		} catch (Exception e) {
			return internalServerError();
		}
	}
	public static Result ownerAddCombo() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println(form);
		int userid = Integer.parseInt(session("userid"));

		Dish dishToAdd = new Dish();
		//Hcharacteristic characteristic = Ebean.find(Hcharacteristic.class,
		//		Long.parseLong(form.get("characteristic")));
		//dishToAdd.setCharacteristic(characteristic);
		//dishToAdd.setCategory(Ebean.find(Hcategory.class,
		//		Long.parseLong(form.get("category"))));
		dishToAdd.setCousine(Ebean.find(Hcousine.class,
				Long.parseLong(form.get("cuisine"))));
		//dishToAdd.setPortions(Integer.parseInt(form.get("portionsAmount")));
		dishToAdd.setName(form.get("dishName"));
		try {
			Double price = Double.parseDouble(form.get("DishPrice").replace(',', '.'));
			dishToAdd.setPrice(price);
		} catch (Exception e) {
			System.out.println("Could not parse price");
		}
		dishToAdd.setIscombo(1);
		dishToAdd.setPortions(Double.parseDouble(form.get("persons")));
		dishToAdd.setCombocategoryid(Integer.parseInt(form.get("comboCategory")));		
		int ingredientsNum = Integer.parseInt(form.get("ingredientsLength"));
		int dishNum = Integer.parseInt(form.get("dishLength"));
		// for (int i = 0; i < ingredientsNum; i++) {
		// System.out.println("Metrci: " + form.get("metrics"+i) + "amount; " +
		// form.get("amounts"+i) +
		// "ingredient: " + form.get("ingredients" + i));
		// }
		
		Ebean.beginTransaction();
		try {
			dishToAdd.save();

			List<String> dishRestaurants = Arrays.asList(form.get(
					"selectedRestaurants").split(","));
			for (int i = 0; i < dishRestaurants.size(); i++) {
				Joinrestaurantdish joinrestaurantdish = new Joinrestaurantdish();
				joinrestaurantdish.setDish(dishToAdd);
				joinrestaurantdish.setRestaurant(Ebean.find(Restaurant.class,
						Long.parseLong(dishRestaurants.get(i))));
				joinrestaurantdish.save();
			}
			List<String> dishCharacteristics = Arrays.asList(form.get(
					"characteristic").split(","));
			for (int i = 0; i < dishCharacteristics.size(); i++) {
				Joindishcharacteristic joindishcharacteristic = new Joindishcharacteristic();
				joindishcharacteristic.setDish(dishToAdd);
				joindishcharacteristic.setCharacteristic(Ebean.find(Hcharacteristic.class,
						Long.parseLong(dishCharacteristics.get(i))));
				joindishcharacteristic.save();
			}			
			for (int i = 0; i < dishNum; i++) {
				Joincombodish joincombodish = new Joincombodish();
				joincombodish.setAmount(Integer.parseInt(form.get("dishamounts"+i)));
				joincombodish.setCombo(dishToAdd);
				joincombodish.setDish(
						Ebean.find(Dish.class, Long.parseLong(form.get("dish"+i)))
						);
				joincombodish.save();
			}
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
			Ebean.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			flash("ComboError", "ComboError");
			return redirect("/disys/ownerNewCombo");			
		} finally {
			Ebean.endTransaction();
		}

		return redirect("/disys/ownerMyRecipes");
	}

	public static Result ownerAddDish() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println(form);
		int userid = Integer.parseInt(session("userid"));

		FilePart image;
        MultipartFormData data = request().body().asMultipartFormData();
        //String[] formData = data.asFormUrlEncoded().get("RestaurantEmail");
        //System.out.println(form);
        image = data.getFile("file");
        
		Dish dishToAdd = new Dish();
		
		if (image == null) {
			System.out.println("got null image!");
		} else {		
			FileInputStream fileInputStream=null;
			File file = image.getFile();
	        byte[] bFile = new byte[(int) file.length()];
	        
	        try {
	            //convert file into array of bytes
		    fileInputStream = new FileInputStream(file);
		    fileInputStream.read(bFile);
		    fileInputStream.close();
	 
		    //for (int i = 0; i < bFile.length; i++) {
		    //   	System.out.print((char)bFile[i]);
	        //}
		    dishToAdd.setPicture(bFile);
		    System.out.println("Done");
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
			//String path = File.separator+"public"+File.separator+"images"+File.separator+"restaurants";
			//file.renameTo(new File(Play.current().path().getAbsolutePath()+path, res.getId()+".png"));
			//res.setImagepath(res.getId()+".png");
		}		
		//Hcharacteristic characteristic = Ebean.find(Hcharacteristic.class,
		//		Long.parseLong(form.get("characteristic")));
		//dishToAdd.setCharacteristic(characteristic);
		dishToAdd.setCategory(Ebean.find(Hcategory.class,
				Long.parseLong(form.get("category"))));
		dishToAdd.setCousine(Ebean.find(Hcousine.class,
				Long.parseLong(form.get("cousine"))));
		dishToAdd.setPortions(Double.parseDouble(form.get("portionsAmount")));
		dishToAdd.setName(form.get("dishName"));
		dishToAdd.setIscombo(0);
		try {
			Double price = Double.parseDouble(form.get("dishPrice").replace(',', '.'));
			dishToAdd.setPrice(price);
		} catch (Exception e) {
			System.out.println("Could not parse price");
		}
		int ingredientsNum = Integer.parseInt(form.get("ingredientsLength"));
		int allergenNum = Integer.parseInt(form.get("allergenNum"));
		// for (int i = 0; i < ingredientsNum; i++) {
		// System.out.println("Metrci: " + form.get("metrics"+i) + "amount; " +
		// form.get("amounts"+i) +
		// "ingredient: " + form.get("ingredients" + i));
		// }
		Ebean.beginTransaction();
		try {
			dishToAdd.save();
			List<String> fruitswithShellStr = Arrays.asList(form.get(
					"selectedkarpoi").split(","));
			for (int i = 0; i < fruitswithShellStr.size(); i++) {
				if (fruitswithShellStr.get(i).isEmpty()) {
					continue;
				}
				Joindishfruitwithshell fruitWithShell = new Joindishfruitwithshell();
				fruitWithShell.setDish(dishToAdd);
				fruitWithShell.setFruitswithshell(Ebean.find(
						Hfruitwithshell.class,
						Long.parseLong(fruitswithShellStr.get(i))));
				fruitWithShell.save();
			}
			for (int i = 1; i < 4; i++) {
				if (form.get("portion"+i) != null && !form.get("portion"+i).equals("")) {
					if (form.get("DishPrice"+i) != null && !form.get("DishPrice"+i).equals("")) {
						Joindishportions joindishportions = new Joindishportions();
						joindishportions.setDishid(dishToAdd.getId());
						joindishportions.setPortionid(Long.parseLong(form.get("portion"+i)));
						joindishportions.setPrice(Double.parseDouble(form.get("DishPrice"+i)));
						joindishportions.save();
					}
				}
			}			
			List<String> cerealsWithGloutenStr = Arrays.asList(form.get(
					"selectedGlouten").split(","));
			for (int i = 0; i < cerealsWithGloutenStr.size(); i++) {
				if (cerealsWithGloutenStr.get(i).isEmpty()) {
					continue;
				}
				Joindishcerealswithglouten cerealWithGlouten = new Joindishcerealswithglouten();
				cerealWithGlouten.setDish(dishToAdd);
				cerealWithGlouten.setCerealswithglouten(Ebean.find(
						Hcerealswithglouten.class,
						Long.parseLong(cerealsWithGloutenStr.get(i))));
				cerealWithGlouten.save();
			}
			List<String> dishRestaurants = Arrays.asList(form.get(
					"selectedRestaurants").split(","));
			for (int i = 0; i < dishRestaurants.size(); i++) {
				Joinrestaurantdish joinrestaurantdish = new Joinrestaurantdish();
				joinrestaurantdish.setDish(dishToAdd);
				joinrestaurantdish.setRestaurant(Ebean.find(Restaurant.class,
						Long.parseLong(dishRestaurants.get(i))));
				joinrestaurantdish.save();
			}
			List<String> dishCharacteristics = Arrays.asList(form.get(
					"characteristic").split(","));
			for (int i = 0; i < dishCharacteristics.size(); i++) {
				Joindishcharacteristic joindishcharacteristic = new Joindishcharacteristic();
				joindishcharacteristic.setDish(dishToAdd);
				joindishcharacteristic.setCharacteristic(Ebean.find(Hcharacteristic.class,
						Long.parseLong(dishCharacteristics.get(i))));
				joindishcharacteristic.save();
			}
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
			for (int i = 0; i < allergenNum; i++) {
				Joindishallergen joindishallergen = new Joindishallergen();
				joindishallergen.setDish(dishToAdd);
				joindishallergen.setAllergen(Ebean.find(Hallergen.class,
						Long.parseLong(form.get("allergen" + i))));
				joindishallergen.save();
			}
			Ebean.commitTransaction();
			//Ebean.rollbackTransaction();
		} finally {
			Ebean.endTransaction();
		}

		ArrayList<Restaurant> restaurants = RestaurantDAO
				.getRestaurants(userid);
		ArrayList<IngredientGroup> ingredientGroups = RestaurantDAO
				.getIngredientGroups();
		ArrayList<IngredientHelper> ingredients = RestaurantDAO
				.getIngredients();
		User user = Ebean.find(User.class, userid);
		if (user.getHascompletedquestionnaire() == 0) {
			return redirect("/disys/ownerQuestionnaire");
		} else {
			return redirect("/disys/ownerMyRecipes");
		}
	}
	
	public static Result ownerQuestionnaire() {
		return ok(ownerQuestionnaire.render());
	}

	public static Result fetchIngredientsbyCat() {
		String param = request().body().asFormUrlEncoded().get("ingredientCat")[0];
		ArrayList<IngredientHelper> ingredients = HelperDataDAO
				.getIngredientsByCategory(param);
		String response = "";
		for (int i = 0; i < ingredients.size(); i++) {
			response += ingredients.get(i).getDescription() + "="
					+ ingredients.get(i).getId();
			if (ingredients.get(i).getPackage1() != null) {
				response += "=" + ingredients.get(i).getPackage1();
			}
			if (ingredients.get(i).getPackage2() != null) {
				response += "=" + ingredients.get(i).getPackage2();
			}
			response += "|";
		}
		return ok(response);
	}

	public static Result fetchMetrics() {
		Long ingredientId = Long.parseLong(request().body().asFormUrlEncoded()
				.get("ingredientId")[0]);
		Ingredient ingredient = Ebean.find(Ingredient.class, ingredientId);
		// System.out.println(play.libs.Json.toJson(ingredient));
		/*
		 * IngredientHelper ingredient =
		 * HelperDataDAO.getIngredientById(ingredientId); String response = "";
		 * response += "GmWt_Desc1=" + ingredient.getPackage1()+"|"; if (!
		 * (ingredient.getPackage2().isEmpty())) { response += "GmWt_Desc2="+
		 * ingredient.getPackage2(); }
		 */
		return ok(play.libs.Json.toJson(ingredient));
	}

	public static Result calculateDishDailyRatiosByDish() {
		DynamicForm form = Form.form().bindFromRequest();
		Dish dish = Ebean.find(Dish.class, Long.parseLong(form.get("dishid")));
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
			if (dish.getIscombo() == 0) {
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
			dailyRatios.setFats(dailyRatios.getFats() + normalization
					* ingredient.getLipid_Tot_g());
			dailyRatios.setProteins(dailyRatios.getProteins() + normalization
					* ingredient.getProtein_g());
			dailyRatios.setHydros(dailyRatios.getHydros() + normalization
					* ingredient.getCarbohydrt_g());
			dailyRatios.setSaturated(dailyRatios.getSaturated() + normalization
					* ingredient.getFA_Sat_g());
			dailyRatios.setSalt(dailyRatios.getSalt() + normalization
					* ingredient.getSodium_mg());
			dailyRatios.setSugars(dailyRatios.getSugars() + normalization
					* ingredient.getSugar_Tot_g());
			dailyRatios.setFiber(dailyRatios.getFiber() + normalization
					* ingredient.getFiber_TD_g());
			dailyRatios.setThiamin(dailyRatios.getThiamin() + normalization
					* ingredient.getThiamin_mg());
			dailyRatios.setRiboflavin(dailyRatios.getRiboflavin()
					+ normalization * ingredient.getRiboflavin_mg());
			dailyRatios.setFolate(dailyRatios.getFolate() + normalization
					* ingredient.getFolate_Tot_µg());
			dailyRatios.setVitaminc(dailyRatios.getVitaminc() + normalization
					* ingredient.getVit_C_mg());
			dailyRatios.setVitamind(dailyRatios.getVitamind() + normalization
					* ingredient.getVit_D_µg());
			dailyRatios.setVitamink(dailyRatios.getVitamink() + normalization
					* ingredient.getVit_K_µg());
			dailyRatios.setVitamine(dailyRatios.getVitamine() + normalization
					* ingredient.getVit_E_mg());
			dailyRatios.setVitamina(dailyRatios.getVitamina()+normalization*ingredient.getVit_A_RAE_µg_());
			dailyRatios.setNiacin(dailyRatios.getNiacin()+normalization*ingredient.getNiacin_mg());
			dailyRatios.setB6(dailyRatios.getB6()+normalization*ingredient.getVit_B6_mg());
			dailyRatios.setB12(dailyRatios.getB12()+normalization*ingredient.getVit_B12_µg());
			dailyRatios.setPantothenic(dailyRatios.getPantothenic()+normalization*ingredient.getPanto_Acid_mg());
			dailyRatios.setPotassium(dailyRatios.getPotassium()+normalization*ingredient.getPotassium_mg());
			dailyRatios.setCalcium(dailyRatios.getCalcium()+normalization*ingredient.getCalcium_mg());
			dailyRatios.setPhosphorus(dailyRatios.getPhosphorus()+normalization*ingredient.getPhosphorus_mg());
			dailyRatios.setMagnesium(dailyRatios.getMagnesium()+normalization*ingredient.getMagnesium_mg());
			dailyRatios.setIron(dailyRatios.getIron()+normalization*ingredient.getIron_mg());
			dailyRatios.setZinc(dailyRatios.getZinc()+normalization*ingredient.getZinc_mg());
			dailyRatios.setCopper(dailyRatios.getCopper()+normalization*ingredient.getCopper_mg());
			dailyRatios.setMaganese(dailyRatios.getMaganese()+normalization*ingredient.getManganese_mg());
			dailyRatios.setSelenium(dailyRatios.getSelenium()+normalization*ingredient.getSelenium_µg());
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

		return ok(play.libs.Json.toJson(dailyRatios));
	}

	public static Result calculateDishDailyRatios() {
		// DailyRatios dailyRatios =
		// RestaurantDAO.getDailyRatios(request().body().asFormUrlEncoded());
		DailyRatios dailyRatios = new DailyRatios();
		Map<String, String[]> params = request().body().asFormUrlEncoded();
		List<String> ingredientsStr = Arrays
				.asList(params.get("ingredients[]"));
		for (int i = 0; i < ingredientsStr.size(); i++) {
			Ingredient ingredient = Ebean.find(Ingredient.class,
					Long.parseLong(ingredientsStr.get(i)));
			// System.out.println(play.libs.Json.toJson(ingredient));
			Double normalization = 0.0;
			// normalization factor based on metric
			if (params.get("metrics[]")[i].equals("1")) {
				normalization = ingredient.getGmWt_1() / 100.0;
			} else if (params.get("metrics[]")[i].equals("2")) {
				normalization = ingredient.getGmWt_2() / 100.0;
			} else if (params.get("metrics[]")[i].equals("10")) {
				normalization = 1.0/100.0;
			} else if (params.get("metrics[]")[i].equals("20")) {
				normalization = 1000.0/100.0;
			}
			// renormalize based on amount
			normalization *= Double.parseDouble(params.get("amounts[]")[i]);
			normalization /= Double.parseDouble(params.get("portionamount")[0]);
			// calories
			dailyRatios
					.setCalories(dailyRatios.getCalories()
							+ (int) (normalization * (float) ingredient
									.getEnerg_Kcal()));
			dailyRatios.setFats(dailyRatios.getFats() + normalization
					* ingredient.getLipid_Tot_g());
			dailyRatios.setProteins(dailyRatios.getProteins() + normalization
					* ingredient.getProtein_g());
			dailyRatios.setHydros(dailyRatios.getHydros() + normalization
					* ingredient.getCarbohydrt_g());
			dailyRatios.setSaturated(dailyRatios.getSaturated() + normalization
					* ingredient.getFA_Sat_g());
			dailyRatios.setSalt(dailyRatios.getSalt() + normalization
					* ingredient.getSodium_mg());
			dailyRatios.setSugars(dailyRatios.getSugars() + normalization
					* ingredient.getSugar_Tot_g());
			dailyRatios.setFiber(dailyRatios.getFiber() + normalization
					* ingredient.getFiber_TD_g());
			dailyRatios.setThiamin(dailyRatios.getThiamin() + normalization
					* ingredient.getThiamin_mg());
			dailyRatios.setRiboflavin(dailyRatios.getRiboflavin()
					+ normalization * ingredient.getRiboflavin_mg());
			dailyRatios.setFolate(dailyRatios.getFolate() + normalization
					* ingredient.getFolate_Tot_µg());
			dailyRatios.setVitaminc(dailyRatios.getVitaminc() + normalization
					* ingredient.getVit_C_mg());
			dailyRatios.setVitamind(dailyRatios.getVitamind() + normalization
					* ingredient.getVit_D_µg());
			dailyRatios.setVitamink(dailyRatios.getVitamink() + normalization
					* ingredient.getVit_K_µg());
			dailyRatios.setVitamine(dailyRatios.getVitamine() + normalization
					* ingredient.getVit_E_mg());	
			dailyRatios.setVitamina(dailyRatios.getVitamina()+normalization*ingredient.getVit_A_RAE_µg_());
			dailyRatios.setNiacin(dailyRatios.getNiacin()+normalization*ingredient.getNiacin_mg());
			dailyRatios.setB6(dailyRatios.getB6()+normalization*ingredient.getVit_B6_mg());
			dailyRatios.setB12(dailyRatios.getB12()+normalization*ingredient.getVit_B12_µg());
			dailyRatios.setPantothenic(dailyRatios.getPantothenic()+normalization*ingredient.getPanto_Acid_mg());
			dailyRatios.setPotassium(dailyRatios.getPotassium()+normalization*ingredient.getPotassium_mg());
			dailyRatios.setCalcium(dailyRatios.getCalcium()+normalization*ingredient.getCalcium_mg());
			dailyRatios.setPhosphorus(dailyRatios.getPhosphorus()+normalization*ingredient.getPhosphorus_mg());
			dailyRatios.setMagnesium(dailyRatios.getMagnesium()+normalization*ingredient.getMagnesium_mg());
			dailyRatios.setIron(dailyRatios.getIron()+normalization*ingredient.getIron_mg());
			dailyRatios.setZinc(dailyRatios.getZinc()+normalization*ingredient.getZinc_mg());
			dailyRatios.setCopper(dailyRatios.getCopper()+normalization*ingredient.getCopper_mg());
			dailyRatios.setMaganese(dailyRatios.getMaganese()+normalization*ingredient.getManganese_mg());
			dailyRatios.setSelenium(dailyRatios.getSelenium()+normalization*ingredient.getSelenium_µg());			
			// calculate weight of Dish
			double weight = 0.0;
			if (params.get("metrics[]")[i].equals("1")) {
				weight = ingredient.getGmWt_1();
			} else if (params.get("metrics[]")[i].equals("2")) {
				weight = ingredient.getGmWt_2();
			} else if (params.get("metrics[]")[i].equals("10")) {
				weight = 1.0;
			} else if (params.get("metrics[]")[i].equals("20")) {
				weight = 100.0;
			}
			weight *= Double.parseDouble(params.get("amounts[]")[i]);
			weight /= Double.parseDouble(params.get("portionamount")[0]);
			dailyRatios.setWeight(dailyRatios.getWeight() + weight);
		}
		return ok(play.libs.Json.toJson(dailyRatios));
	}

	public static Result ownerDeleteRestaurant() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println(form);
		Ebean.delete(Restaurant.class, Long.parseLong(form.get("restaurantId")));
		return ok();
	}

	public static Result getRestaurant() {
		DynamicForm form = Form.form().bindFromRequest();
		Restaurant res = Ebean.find(Restaurant.class,
				Long.parseLong(form.get("restaurantId")));
		Set<Joinrestaurantcousine> cousines = res.getRestaurantcousines();

		return ok(play.libs.Json.toJson(res));
	}

	public static Result getRestaurantDishes() {
		DynamicForm form = Form.form().bindFromRequest();
		List<Joinrestaurantdish> restaurantdishes = Ebean
				.find(Joinrestaurantdish.class).where()
				.eq("restaurantid", Long.parseLong(form.get("restaurantId")))
				.findList();
		Map<Long, List<Dish>> dishMap = new HashMap<Long, List<Dish>>();
		for (Joinrestaurantdish restaurantdish : restaurantdishes) {
			Dish dish = Ebean
					.find(Dish.class, restaurantdish.getDish().getId());
			if (dish.getCategory() == null) continue;
			List<Dish> dishes = dishMap.get(dish.getCategory().getId());
			if (dishes == null) {
				dishes = new ArrayList<Dish>();
				dishMap.put(dish.getCategory().getId(), dishes);
			}
			dishes.add(dish);

		}

		return ok(play.libs.Json.toJson(dishMap));
	}

	/******************************************************************************/
	/*
	 * ΠΡΟΣΘΗΚΗ ΝΕΟΥ ΕΣΤΙΑΤΟΡΙΟΥ
	 * /***********************************************
	 * ******************************
	 */
	//@BodyParser.Of(BodyParser.MultipartFormData.class)
	public static Result ownerInsertRestaurant() {
		Long userid = Long.parseLong(session("userid"));
		Restaurant res = new Restaurant();
		User user = Ebean.find(User.class, userid);
		res.setUserid(userid);
		DynamicForm form = Form.form().bindFromRequest();
		
		FilePart image;
        MultipartFormData data = request().body().asMultipartFormData();
        //String[] formData = data.asFormUrlEncoded().get("RestaurantEmail");
        //System.out.println(form);
        image = data.getFile("RestaurantPicture");

		Ebean.beginTransaction();
		try {
			res.setEmail(form.get("RestaurantEmail"));
			res.setName(form.get("RestaurantNameInput"));
			res.setCity(form.get("RestaurantCity"));
			res.setPostcode(Integer.parseInt(form.get("RestaurantPostcode").replace(" ", "")));
			res.setStreet(form.get("RestaurantStreet"));
			res.setStreetnumber(form.get("RestaurantStreetNumber"));
			res.setPersoninchargename(form.get("PersonInCharge"));
			res.setPhonenumber(form.get("RestaurantPhone"));
			res.setMobilephonenumber(form.get("RestaurantCellPhone"));
			res.setFax(form.get("RestaurantFax"));
			res.setEmail(form.get("RestaurantEmail"));
			res.setWebpage(form.get("OrganizationWebPage"));
			res.setExtrainfo(form.get("RestaurantInfoInsert"));
			res.setLatitude(Double.parseDouble(form.get("lat")));
			res.setLongtitude(Double.parseDouble(form.get("lng")));
			res.save();
			if (image == null) {
				
			} else {		
				FileInputStream fileInputStream=null;
				File file = image.getFile();
		        byte[] bFile = new byte[(int) file.length()];
		        
		        try {
		            //convert file into array of bytes
			    fileInputStream = new FileInputStream(file);
			    fileInputStream.read(bFile);
			    fileInputStream.close();
		 
			    for (int i = 0; i < bFile.length; i++) {
			       	System.out.print((char)bFile[i]);
		        }
			    res.setPicture(bFile);
			    System.out.println("Done");
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
				//String path = File.separator+"public"+File.separator+"images"+File.separator+"restaurants";
				//file.renameTo(new File(Play.current().path().getAbsolutePath()+path, res.getId()+".png"));
				//res.setImagepath(res.getId()+".png");
			}

			List<String> categoriesStr = new ArrayList<String>();
			int categoriescounter=0;
			for (;;) {
				if (form.get("storekind"+categoriescounter) == null) {
					break;
				} else {
					categoriesStr.add(form.get("storekind"+categoriescounter));
				}
				categoriescounter++;
			}
			for (String categoryStr : categoriesStr) {
				Joinrestauranthrestaurantcategory restaurantCategory = new Joinrestauranthrestaurantcategory();
				restaurantCategory.setRestaurantid(res.getId());
				restaurantCategory.setHrestaurantcategoryid(Long
						.parseLong(categoryStr));
				restaurantCategory.save();
			}
			List<String> cousinesStr = new ArrayList<String>();
			int cuisinecounter=0;
			for (;;) {
				if (form.get("cuisine"+cuisinecounter) == null) {
					break;
				} else {
					cousinesStr.add(form.get("cuisine"+cuisinecounter));
				}
				cuisinecounter++;
			}			

			for (String cousineStr : cousinesStr) {
				Joinrestaurantcousine restaurantcousine = new Joinrestaurantcousine();
				restaurantcousine.setRestaurantid(res.getId());
				restaurantcousine.setCousineid(Long.parseLong(cousineStr));
				restaurantcousine.save();
			}				

			res.setOrganisationname(form.get("OrganizationName"));
			Hrestaurantlicence licence = Ebean.find(Hrestaurantlicence.class,
					Long.parseLong(form.get("SelectStorelisence")));
			res.setRestaurantlicence(licence);
			Hpricerange pricerange = Ebean.find(Hpricerange.class,
					Long.parseLong(form.get("PriceSelect")));
			res.setPricerange(pricerange);
			res.update();
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
		return ok();

	}

	/******************************************************************************/
	/*
	 * ΑΝΑΝΕΩΣΗ ΣΤΟΙΧΕΙΩΝ ΕΣΤΙΑΤΟΡΙΟΥ
	 * /******************************************
	 * ***********************************
	 */
	public static Result updateRestaurant() {
		DynamicForm form = Form.form().bindFromRequest();
		// System.out.println(form);
		Restaurant res = Ebean.find(Restaurant.class,
				Long.parseLong(form.get("restaurantIdModal")));
		List<Joinrestaurantcousine> cousines = Ebean
				.find(Joinrestaurantcousine.class)
				.where()
				.eq("restaurantid",
						Long.parseLong(form.get("restaurantIdModal")))
				.findList();
		List<Joinrestauranthrestaurantcategory> categories = Ebean
				.find(Joinrestauranthrestaurantcategory.class)
				.where()
				.eq("restaurantid",
						Long.parseLong(form.get("restaurantIdModal")))
				.findList();
		Ebean.beginTransaction();
		System.out.println(play.libs.Json.toJson(request().body()
				.asFormUrlEncoded().get("SelectStoreKindModal")));
		try {
			for (Joinrestauranthrestaurantcategory category : categories) {
				category.delete();
			}
			for (Joinrestaurantcousine cousine : cousines) {
				cousine.delete();
			}
			List<String> categoriesStr = Arrays.asList(request().body()
					.asFormUrlEncoded().get("SelectStoreKindModal"));
			for (String categoryStr : categoriesStr) {
				Joinrestauranthrestaurantcategory restaurantCategory = new Joinrestauranthrestaurantcategory();
				restaurantCategory.setRestaurantid(Long.parseLong(form
						.get("restaurantIdModal")));
				restaurantCategory.setHrestaurantcategoryid(Long
						.parseLong(categoryStr));
				restaurantCategory.save();
			}
			if(request().body().asFormUrlEncoded().get("selectcuisineModal") != null) {
				List<String> cousinesStr = Arrays.asList(request().body()
						.asFormUrlEncoded().get("selectcuisineModal"));
				for (String cousineStr : cousinesStr) {
					Joinrestaurantcousine restaurantcousine = new Joinrestaurantcousine();
					restaurantcousine.setRestaurantid(Long.parseLong(form
							.get("restaurantIdModal")));
					restaurantcousine.setCousineid(Long.parseLong(cousineStr));
					restaurantcousine.save();
				}
			}
			res.setEmail(form.get("RestaurantEmailModal"));
			res.setName(form.get("RestaurantNameInputModal"));
			res.setOrganisationname(form.get("OrganizationNameModal"));
			Hrestaurantlicence licence = Ebean.find(Hrestaurantlicence.class,
					Long.parseLong(form.get("SelectStorelisenceModal")));
			res.setRestaurantlicence(licence);
			Hpricerange pricerange = Ebean.find(Hpricerange.class,
					Long.parseLong(form.get("PriceSelectModal")));
			res.setPricerange(pricerange);
			res.setCity(form.get("RestaurantCityModal"));
			res.setPostcode(Integer.parseInt(form
					.get("RestaurantPostcodeModal").replace(" ", "")));
			res.setStreet(form.get("RestaurantStreetModal"));
			res.setStreetnumber(form.get("RestaurantStreetNumberModal"));
			res.setPersoninchargename(form.get("PersonInChargeModal"));
			res.setPhonenumber(form.get("RestaurantPhoneModal"));
			res.setMobilephonenumber(form.get("RestaurantCellPhoneModal"));
			res.setFax(form.get("RestaurantFaxModal"));
			res.setEmail(form.get("RestaurantEmailModal"));
			res.setWebpage(form.get("OrganizationWebPageModal"));
			res.setLatitude(Double.parseDouble(form.get("latModal")));
			res.setLongtitude(Double.parseDouble(form.get("lngModal")));
			res.setExtrainfo(form.get("RestaurantInfoModal"));
			res.update();
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
		return ok();
	}

	public static Result checkPassword() {
		int userid = Integer.parseInt(session("userid"));
		DynamicForm form = Form.form().bindFromRequest();
		List<User> user = Ebean.find(User.class).where().eq("id", userid)
				.like("password", form.get("password")).findList();
		if (user.isEmpty()) {
			return ok("not_found");
		} else {
			return ok("found");
		}
	}

	public static Result changePassword() {
		int userid = Integer.parseInt(session("userid"));
		User user = Ebean.find(User.class, userid);
		DynamicForm form = Form.form().bindFromRequest();
		user.setPassword(form.get("password"));
		user.save();
		return ok();
	}

	public static Result ownerRecipesMain() {
		return ok(recipesmain.render());
	}

	public static Result ownerNewCombo() {
		int userid = Integer.parseInt(session("userid"));
		List<Restaurant> restaurants = Ebean.find(Restaurant.class)
				.where()
				.eq("userid", userid)
				.findList();
		ArrayList<Long> restaurantsIds = new ArrayList<Long>();
		for (Restaurant restaurant: restaurants) {
			restaurantsIds.add(restaurant.getId());
		}
		List<Joinrestaurantdish> restaurantdishes = Ebean.find(Joinrestaurantdish.class)
				.fetch("restaurant")
				.where()
				.in("restaurantid", restaurantsIds)
				.findList();
		ArrayList<Long> dishIds = new ArrayList<Long>();
		for (Joinrestaurantdish restaurantdish: restaurantdishes) {
			dishIds.add(restaurantdish.getDish().getId());
		}
		//System.out.println(play.libs.Json.toJson(restaurantdishes));
		ArrayList<IngredientGroup> ingredientGroups = RestaurantDAO
				.getIngredientGroups();
		ArrayList<IngredientHelper> ingredients = RestaurantDAO
				.getIngredients();	
		List<Dish> dishes = Ebean.find(Dish.class)
				.where()
				.eq("iscombo", 0)
				.isNotNull("name")
				.in("id", dishIds)
				.findList();
		return ok(combos.render(restaurants, ingredientGroups, ingredients, characteristics, cousines,dishes));
	}

	public static Result ownerMyRecipes() {
		int userid = Integer.parseInt(session("userid"));
		List<Restaurant> restaurants = Ebean.find(Restaurant.class).where()
				.eq("userid", userid).findList();
		// List<Dish> dishes =
		List<Long> restaurandIds = new ArrayList<Long>();
		for (int i = 0; i < restaurants.size(); i++) {
			restaurandIds.add(restaurants.get(i).getId());
		}
		List<Joinrestaurantdish> joindishrestaurants = Ebean
				.find(Joinrestaurantdish.class).where()
				.in("restaurantid", restaurandIds).findList();

		List<Long> dishesIds = new ArrayList<Long>();
		for (int i = 0; i < joindishrestaurants.size(); i++) {
			dishesIds.add(joindishrestaurants.get(i).getDish().getId());
		}

		List<Dish> dishes = Ebean.find(Dish.class).where().in("id", dishesIds)
				.findList();
		return ok(myrecipes.render(dishes));
	}

	public static Result uploadImage() {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart resourceFile = body.getFile("RestaurantPicture");
		File f = resourceFile.getFile();
	    String fileName = resourceFile.getFilename();
	    f.renameTo(new File("C:\testimage.png"));		
		return TODO;
	}
	public static Result deleteDish() {
		DynamicForm form = Form.form().bindFromRequest();
		Dish dish = Ebean.find(Dish.class, Long.parseLong(form.get("dishid")));
		try {
			Ebean.beginTransaction();
			Ebean.delete(Dish.class,dish.getId());
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
		return redirect("/disys/ownerMyRecipes");
	}
	private static List<Joindishingredient> getComboIngredients(Dish combo) {
		List<Joincombodish> combodishList = Ebean.find(Joincombodish.class)
				.where()
				.eq("comboid", combo.getId())
				.findList();
		List<Joindishingredient> comboingredientList = new ArrayList<Joindishingredient>();
		for (Joincombodish combodish: combodishList) {
			List<Joindishingredient> dishingredientList = Ebean.find(Joindishingredient.class)
					.where()
					.eq("dishid", combodish.getDish().getId())
					.findList();
			comboingredientList.addAll(dishingredientList);
		}
		
		
		return comboingredientList;
	}
	public static Result fillOwnerQuestionnaire() {
		DynamicForm form = Form.form().bindFromRequest();
		System.out.println(form);
		Ownerpreference pref = new Ownerpreference();
		
		int userid = Integer.parseInt(session("userid"));
		User user = Ebean.find(User.class, userid);
		pref.setId(user.getId());
		pref.setEaseofusage(Integer.parseInt(form.get("optionsRadios1")));
		pref.setInfoquickness(Integer.parseInt(form.get("optionsRadios2")));
		pref.setFunctionalaspect(Integer.parseInt(form.get("optionsRadios3")));
		pref.setDesign(Integer.parseInt(form.get("optionsRadios4")));
		pref.setEaseofrestaurantdataentry(Integer.parseInt(form.get("optionsRadios5")));
		pref.setEaseofrecipeentry(Integer.parseInt(form.get("optionsRadios6")));
		pref.setEaseofingredientsearch(Integer.parseInt(form.get("optionsRadios7")));
		pref.setDishcredibility(Integer.parseInt(form.get("optionsRadios8")));
		pref.setGdaeasiness(Integer.parseInt(form.get("optionsRadios10")));
		pref.setCustomerusage(Integer.parseInt(form.get("optionsRadios11")));
		pref.setCustomercoverage(Integer.parseInt(form.get("optionsRadios13")));
		pref.setPreferredname(Integer.parseInt(form.get("optionsRadios15")));

		String usefulness="";
		for (int i = 1; i < 7; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				usefulness += i + ",";
			}
		}
		String increasecustomers="";
		for (int i = 7; i < 16; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				increasecustomers += (i-6) + ",";
			}			
		}
		String recipedesign="";
		for (int i = 16; i < 27; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				recipedesign += (i-15) + ",";
			}			
		}
		String applicationrevlevance="";
		for (int i = 27; i < 37; i++) {
			if (form.get("inlineCheckbox"+i) != null) {
				applicationrevlevance += (i-26) + ",";
			}			
		}		
		pref.setUsefulness(usefulness);
		pref.setIncreasecustomers(increasecustomers);
		pref.setRecipedesign(recipedesign);
		pref.setApplicationrevlevance(applicationrevlevance);
		if (form.get("commercialname") != null) {
			pref.setPreferrednametext(form.get("commercialname"));
		}
		if (form.get("suggestions") != null) {
			pref.setSuggestions(form.get("suggestions"));
		}		
		
		user.setHascompletedquestionnaire(1);
		try {
			Ebean.beginTransaction();
			pref.save();	
			user.update();
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}		
		
		return redirect("/disys/ownerMyRecipes");
	}
	public static Result checkDishImage(Long dishid) {		
		return ok(checkdishimage.render());
	}
	public static Result getCheckDishImage() {
		DynamicForm form = Form.form().bindFromRequest();
		Long dishid = Long.parseLong(form.get("dishid"));
		Dish dish = Ebean.find(Dish.class, dishid);
		return ok(play.libs.Json.toJson(dish));
	}
}
