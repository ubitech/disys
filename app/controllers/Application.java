package controllers;

import models.user.User;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import views.html.customer.*;

public class Application extends Controller {
	private static final Form<User> userForm = Form.form(User.class);
	
    public static Result login() {    	
    	return ok(login.render(userForm));
    }
    
    public static Result logout() {
    	session().clear();
    	return redirect("/disys/login");
    }
    
    public static Result loginUser() {
    	Form<User> boundForm = userForm.bindFromRequest();

    	User user = boundForm.get();
    	if (User.findbyUsername(user.getUsername()) == null) {
    		flash("UserNotExists", "UserNotExists");
    		return redirect("/disys/login");    		
    	} else if ( ! (User.findbyUsername(user.getUsername()).getPassword().equals(user.getPassword())) ) {
    		flash("UserNotExists", "UserNotExists");
    		return redirect("/disys/login");     		
    	}
    	session("username", user.getUsername());
    	session("role", User.findbyUsername(user.getUsername()).getRole());
    	session("userid",  User.findbyUsername(user.getUsername()).getId().toString());

    	if (session("role").equals("CUSTOMER")) {
    		session("tracking", User.findbyUsername(user.getUsername()).getProfile().getTracking().toString());
    		return redirect("/disys/customerWelcomeScreen");
    	} else if (session("role").equals("OWNER")) {
    		return redirect("/disys/ownerWelcomeScreen");
    	} else {
    		return badRequest("Unknown User type");
    	}
    }
    
    public static Result customerSaveMeal() {
    	return ok(customerSaveMeal.render());
    }
    
    public static Result customerSystemSettings() {
    	return ok(customerSystemSettings.render());
    }
    
    public static Result customerAccountSettings() {
    	//return ok(customerAccountSettings.render());
    	return TODO;
    }
    
    public static Result customerNotificationSettings() {
    	//return ok(customerNotificationSettings.render());
    	return TODO;
    }  
    
    public static Result customerApplication() {
    	return ok(customerApplication.render());
    }   
    
}
