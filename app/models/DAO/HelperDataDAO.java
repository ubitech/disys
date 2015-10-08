package models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import play.db.DB;
import beans.IngredientHelper;

public class HelperDataDAO {
	public static ArrayList<IngredientHelper> getIngredientsByCategory(String catId) {
		ArrayList<IngredientHelper> ingredients = new ArrayList<IngredientHelper>();
		
		Connection connection = DB.getConnection();
		String query = "SELECT id, Shrt_Desc FROM ingredient WHERE GroupCode = \""+catId+"\"";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			//preparedStatement.setString(1, catId);			
			ResultSet rs = preparedStatement.executeQuery(query);
			while (rs.next()) {
				IngredientHelper ingredient = new IngredientHelper();
				ingredient.setId(rs.getInt("id"));
				ingredient.setDescription(rs.getString("Shrt_Desc"));
				ingredients.add(ingredient);
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
		return ingredients;
	}
	
	public static IngredientHelper getIngredientById(int id) {
		IngredientHelper ingredient = new IngredientHelper();
		Connection connection = DB.getConnection();
		String query = "SELECT GmWt_Desc1, GmWt_Desc2 FROM ingredient WHERE id="+id;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			//preparedStatement.setString(1, catId);			
			ResultSet rs = preparedStatement.executeQuery(query);
			if (rs.next()) {
				ingredient.setPackage1(rs.getString("GmWt_Desc1"));
				ingredient.setPackage2(rs.getString("GmWt_Desc2"));
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
		return ingredient;
	}

}
