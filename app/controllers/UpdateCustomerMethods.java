package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.joda.time.LocalDate;

import models.user.Profile;
import models.user.User;

import com.avaje.ebean.Ebean;

import play.db.DB;
import play.mvc.Controller;
import play.mvc.Result;

public class UpdateCustomerMethods extends Controller {
	public static Result updateSurname() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setSurname(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	surname=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return internalServerError();
    	}    	
	}
	public static Result updateFirstName() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setName(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	name=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}
	public static Result updateUsername() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setAlias(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	alias=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}
	public static Result updateEmail() {
    	Long userid = Long.parseLong(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setEmail(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	email=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}	
	public static Result updateMobilePhone() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setMobilephone(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	mobilephone=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}
	public static Result updatePhone() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setPhone(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	phone=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}
	public static Result updateCountry() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setCountry(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	country=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}
	public static Result updateCity() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setCity(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	city=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}
	public static Result updatePostCode() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setPostalcode(values.get("value")[0]);  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	postalcode=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}	
	public static Result updateHeight() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setHeight(Double.parseDouble(values.get("value")[0]));  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	height=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}	
	public static Result updateWeight() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	profile.setWeight(Double.parseDouble(values.get("value")[0]));  
    	System.out.println(request().body().asFormUrlEncoded());
    	try {
    		Connection connection = DB.getConnection();
    		String query = "UPDATE\n"
    				+ "	customerprofile\n"
    				+ "SET\n"
    				+ "	weight=\""+values.get("value")[0]+"\"\n"
    				+ "WHERE\n"
    				+ "id="+userid;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
    		return ok();
    	} catch (Exception e) {
    		return internalServerError();
    	}    
	}	
	public static Result updateDateDay() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();
    	Date date = profile.getDateofbirth();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);    	
    	LocalDate localDate = new LocalDate(year, month, Integer.parseInt(values.get("value")[0]));
    	date = localDate.toDate();
    	profile.setDateofbirth(date);
    	profile.update();
		return ok();
	}
	public static Result updateDateMonth() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();	
    	Date date = profile.getDateofbirth();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);    	
    	LocalDate localDate = new LocalDate(year, Integer.parseInt(values.get("value")[0]), day);
    	date = localDate.toDate();
    	profile.setDateofbirth(date);
    	profile.update();    	
		return ok();
	}
	public static Result updateDateYear() {
    	int userid = Integer.parseInt(session("userid"));
    	Profile profile = Ebean.find(Profile.class, userid);
    	Map<String, String[]> values = request().body().asFormUrlEncoded();	
    	Date date = profile.getDateofbirth();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);    	
    	LocalDate localDate = new LocalDate(Integer.parseInt(values.get("value")[0]), month, day);
    	date = localDate.toDate();
    	profile.setDateofbirth(date);
    	profile.update();    	
		return ok();
	}	
}
