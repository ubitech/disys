package models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import beans.DailyRatios;
import beans.IngredientHelper;
import beans.IngredientGroup;
import play.db.*;
import models.restaurant.Restaurant;

public class RestaurantDAO {
	public static ArrayList<Restaurant> getRestaurants(int userid) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		Connection connection = DB.getConnection();
		String query = "SELECT * FROM restaurant WHERE userid = 4";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			//preparedStatement.setLong(1, userid);			
			ResultSet rs = preparedStatement.executeQuery(query);
			while (rs.next()) {
				Restaurant restaurant = new Restaurant();
				restaurant.setName(rs.getString("name"));
				//System.out.println("Res name: " + rs.getString("name"));
				restaurant.setId(rs.getLong("id"));
				restaurants.add(restaurant);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return restaurants;
	}
	
	public static boolean saveRestaurant(Restaurant restaurant, int userid) {
		boolean success = false;
		Connection connection = DB.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e2) {
			success = false;
			e2.printStackTrace();
		}
		String query = "INSERT\n"
				+ "INTO\n"
				+ "	restaurant\n"
				+ "SET\n"
				+ "	name=?,\n"
				+ "	city=?,\n"
				+ "	hcousineid=?,\n"
				+ "	postcode=?,\n"
				+ "	"
				+ " userid=4";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, restaurant.getName());
			preparedStatement.setString(2, restaurant.getCity());
			preparedStatement.setInt(4, restaurant.getPostcode());
			preparedStatement.executeUpdate();
			success = true;
		} catch (SQLException e1) {
			success = false;
		}		
				
		System.out.println("RESTAURANT: " + restaurant);
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			connection.commit();
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	public static ArrayList<IngredientGroup> getIngredientGroups() {
		ArrayList<IngredientGroup> ingredientGroupList = new ArrayList<IngredientGroup>();
		Connection connection = DB.getConnection();
		String query = "SELECT id, description FROM hingredientgroup";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);		
			ResultSet rs = preparedStatement.executeQuery(query);
			while (rs.next()) {
				IngredientGroup ingredientgroup = new IngredientGroup();
				ingredientgroup.setId(rs.getString("id"));
				ingredientgroup.setDescription(rs.getString("description"));
				ingredientGroupList.add(ingredientgroup);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredientGroupList;
	}
	public static ArrayList<IngredientHelper> getIngredients() {
		ArrayList<IngredientHelper> ingredientList = new ArrayList<IngredientHelper>();
		Connection connection = DB.getConnection();
		String query = "SELECT id, Code, GroupCode, Shrt_desc  FROM ingredient";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);		
			ResultSet rs = preparedStatement.executeQuery(query);
			while (rs.next()) {
				IngredientHelper ingredient = new IngredientHelper();
				ingredient.setId(rs.getInt("id"));
				ingredient.setCode(rs.getString("Code"));
				ingredient.setGroupCode(rs.getString("GroupCode"));
				ingredient.setDescription(rs.getString("Shrt_desc"));
				ingredientList.add(ingredient);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredientList;
	}
	public static HashMap<String, ArrayList<String>> getIngredientGroupMaps() {
		HashMap<String, ArrayList<String>> ingredientGroupMap = new HashMap<String, ArrayList<String>>();
		Connection connection = DB.getConnection();
		String query = "SELECT id FROM hingredientgroup";	
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);		
			ResultSet rs = preparedStatement.executeQuery(query);
			while (rs.next()) {
				ArrayList<String> ingredients = new ArrayList<String>();
				String ingredientquery = "SELECT id FROM ingredient WHERE GroupCode=\""+rs.getString("id")+"\"";
				PreparedStatement ingredientPreparedStatement = connection.prepareStatement(ingredientquery);		
				ResultSet ing_rs = ingredientPreparedStatement.executeQuery(ingredientquery);	
				while (ing_rs.next()) {
					ingredients.add(ing_rs.getString("id"));
				}
				ingredientGroupMap.put(rs.getString("id"), ingredients);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredientGroupMap;
	}
	public static boolean saveDish(Map<String, String[]> values, int userid)
	{
		boolean success = false;
		int insertedDishId = -1;
		Connection connection = DB.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
			return success;
		}
		//Insert Dish
		String query = "INSERT\n"
				+ "INTO\n"
				+ "	dish\n"
				+ "SET\n"
				+ "	name=\""+values.get("dishName")[0]+"\",\n"
				+ "	cousineid="+Integer.parseInt(values.get("cousine")[0]) + ",\n"
				+ "	portions="+Integer.parseInt(values.get("portionsAmount")[0])+",\n"
				+ "	categoryid="+Integer.parseInt(values.get("category")[0])+",\n"
				+ "	characteristicid="+Integer.parseInt(values.get("characteristic")[0])+"\n";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			//preparedStatement.setString(1, values.get("dishName")[0]);
			//System.out.println(values.get("dishName")[0]);
			preparedStatement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();	
			if (generatedKeys.next()) {
				insertedDishId = generatedKeys.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			success = false;
		}			
		
		//Insert restaurants join
		String restarantIds[] = values.get("selectedRestaurants")[0].split(",");
		for (int i = 1; i < restarantIds.length; i++) {
			restarantIds[i] = restarantIds[i].replace("restaurant", "");
			String rel_query = "INSERT\n"
					+ "INTO\n"
					+ "	joinrestaurantdish\n"
					+ "SET\n"
					+ "	restaurantid="+restarantIds[i]+",\n"
					+ "	dishid="+insertedDishId;
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(rel_query);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				success = false;
			}
		}
		//insert ingredientsjoin
		String ingredients[] = values.get("ingredients[]");
		for (int i = 0; i < ingredients.length; i++) {
			String ingredientQuery = "INSERT INTO joindishingredients\n"
					+ "SET\n"
					+ "dishid="+insertedDishId+",\n"
					+ "ingredientid="+Integer.parseInt(ingredients[i])+",\n"
					+ "amount="+Float.parseFloat(values.get("amounts[]")[i])+",\n";
			if (values.get("metrics[]")[i].equals("GmWt_Desc1")){
				ingredientQuery+="metric=1";
			} else if(values.get("metrics[]")[i].equals("GmWt_Desc2")) {
				ingredientQuery+="metric=2";
			}
			PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement(ingredientQuery);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
		
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
			return success;
		}
		success = true;
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	public static DailyRatios getDailyRatios(Map<String, String[]> ingredients) {
		DailyRatios dailyRatios = new DailyRatios();
		Connection connection = DB.getConnection();		
		for (int i = 0; i < ingredients.get("ingredients[]").length; i++) {			
			String query = "SELECT\n"
					+ "Energ_Kcal,\n"
					+ "Lipid_Tot_g,\n"
					+ "Protein_g,\n"
					+ "Carbohydrt_g,\n"
					+ "FA_Sat_g,\n"
					+ "Sodium_mg,\n"
					+ "Sugar_Tot_g,\n"
					+ ingredients.get("metrics[]")[i].replace("Desc", "") + "\n"
					+ "FROM\n"
					+ "	ingredient\n"
					+ "WHERE\n"
					+ "	id="+ingredients.get("ingredients[]")[i]+"\n";
			try {				
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					float normalizer = (float)( (rs.getFloat(ingredients.get("metrics[]")[i].replace("Desc", ""))/100.0) * 
							Float.parseFloat(ingredients.get("amounts[]")[i]));
					dailyRatios.setCalories(dailyRatios.getCalories()+(int) ( normalizer * (float) rs.getInt("Energ_Kcal")));
					dailyRatios.setFats(dailyRatios.getFats()+ normalizer*rs.getFloat("Lipid_Tot_g"));
					dailyRatios.setProteins(dailyRatios.getProteins()+normalizer*rs.getFloat("Protein_g"));
					dailyRatios.setHydros(dailyRatios.getHydros()+normalizer*rs.getFloat("Carbohydrt_g"));
					dailyRatios.setSaturated(dailyRatios.getSaturated()+normalizer*rs.getFloat("FA_Sat_g"));
					dailyRatios.setSalt(dailyRatios.getSalt()+normalizer*rs.getFloat("Sodium_mg"));
					dailyRatios.setSugars(dailyRatios.getSugars()+normalizer*rs.getFloat("Sugar_Tot_g"));
					//System.out.println("amount of metric: " + rs.getString(ingredients.get("metrics[]")[i].replace("Desc", "")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dailyRatios;
	}
	public static ArrayList<Restaurant> getRestaurantsByCity(String city) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		String query = "SELECT name, city\n"
				+ "FROM\n"
				+ "	restaurant\n"
				+ "WHERE\n"
				+ "	city LIKE \""+city+"%\"";
		Connection connection = DB.getConnection();	
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Restaurant res = new Restaurant();
				res.setCity(rs.getString("city"));
				res.setName(rs.getString("name"));
				restaurants.add(res);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restaurants;
	}
}
