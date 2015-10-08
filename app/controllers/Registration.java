package controllers;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

import com.avaje.ebean.Ebean;

import models.lists.Hcategory;
import models.lists.Hcharacteristic;
import models.lists.Hcousine;
import models.lists.Hdiet;
import models.lists.Hgender;
import models.lists.Hphysicalactivity;
import models.lists.Hpregnancy;
import models.lists.Hpricerange;
import models.lists.Hrestaurantcategory;
import models.lists.Hrestaurantlicence;
import models.relations.Joincustomerprofilehingredientcategory;
import models.relations.Joincustomerprofilemedicalcondition;
import models.user.Profile;
import models.user.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.registration.*;

public class Registration extends Controller {
	private static final List<Hcousine> cousines = Hcousine.find.all();
	private static final List<Hcharacteristic> characteristics = Hcharacteristic.find
			.all();
	private static final List<Hcategory> categories = Hcategory.find.all();
	private static final List<Hrestaurantlicence> licences = Hrestaurantlicence.find
			.all();
	private static final List<Hrestaurantcategory> restaurantcategories = Hrestaurantcategory.find
			.all();
	private static final List<Hpricerange> prices = Hpricerange.find.all();	
	public static Result registerCustomerMain() {
		session().clear();
		return ok(customerregistration.render());
	}
	public static Result registerOwnerMain() {
		session().clear();
		return ok(ownerresistration.render(
				cousines, licences, restaurantcategories, prices));
	}	

	public static Result insertOwner() {
		DynamicForm form = Form.form().bindFromRequest();
		User user = new User();
		user.setSurname(form.get("UserSurname"));
		user.setPassword(form.get("password"));
		user.setName(form.get("UserFirstName"));
		user.setUsername(form.get("email"));
		user.setEmail(form.get("email"));
		user.setPhone(form.get("phone"));
		user.setRole("OWNER");	
		user.setHascompletedquestionnaire(0);
		try {
			Ebean.beginTransaction();
			user.save();
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
    	session("username", user.getUsername());
    	session("role", user.getRole());
    	session("userid",  user.getId().toString());		
		//System.out.println(form);
		return ok();
	}
	public static Result insertCustomer() {
		DynamicForm form = Form.form().bindFromRequest();
		String[] notdailyCategories = request().body().asFormUrlEncoded()
				.get("notdailyselect");		
		//System.out.println(form);
		User user = new User();
		user.setSurname(form.get("UserSurname"));
		user.setPassword(form.get("password"));
		user.setName(form.get("UserFirstName"));
		user.setUsername(form.get("email"));
		user.setEmail(form.get("email"));
		user.setPhone(form.get("phone"));
		user.setRole("CUSTOMER");
		Profile profile = new Profile();
		profile.setCountry(form.get("land"));
		
		Integer g = "male".equals(form.get("gender")) ? 1 : 2;
		Hgender gender = Ebean.find(Hgender.class, g);
		profile.setSurname(form.get("UserSurname"));
		profile.setGender(gender);
		profile.setPregnancyStatus(Ebean.find(Hpregnancy.class, Long.parseLong(form.get("pregnancy"))));
		profile.setWeight(Double.parseDouble(form.get("weight")));
		profile.setHeight(Double.parseDouble(form.get("height")));
		int day = Integer.parseInt(form.get("day"));
		int month = Integer.parseInt(form.get("month"));
		int year = Integer.parseInt(form.get("year"));
		LocalDate localDate = new LocalDate(year, month, day);
		Date date = localDate.toDate();
		profile.setDateofbirth(date);
		//profile.setAge(Double.parseDouble(form.get("age")));
		profile.setCity(form.get("city"));
		profile.setName(form.get("UserFirstName"));
		profile.setAlias(form.get("Username"));
		profile.setEmail(form.get("email"));
		profile.setPostalcode(form.get("postcode"));
		profile.setPhone(form.get("phone"));
		profile.setMobilephone(form.get("mobile"));
		user.setHascompletedquestionnaire(0);
		user.setProfile(profile);		
		try {
			Ebean.beginTransaction();
			user.save();
			profile.setId(user.getId());	
			profile.setWatchcalories(0);profile.setWatchhydros(0);profile.setWatchsugars(0);
			profile.setWatchfiber(0);profile.setWatchprotein(0);profile.setWatchfat(0);
			profile.setWatchsaturated(0);profile.setWatchmonosaturated(0);profile.setWatchpolysaturated(0);
			profile.setWatchcholesterol(0);profile.setWatchmetals(0);profile.setWatchvitamins(0);
			profile.setWatchantioxidants(0);
			if (form.get("tracking") != null) {
				profile.setTracking(1);
			} else {
				profile.setTracking(0);
			}
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
			profile.save();
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
			Hphysicalactivity activity = Ebean.find(Hphysicalactivity.class,
					Long.parseLong(form.get("activitylevelselect")));
			profile.setPhysicalactivity(activity);
			profile.setPhysicalactivity(activity);
			Hdiet diet = Ebean.find(Hdiet.class,
					Long.parseLong(form.get("dietlevelselect")));
			profile.setDiet(diet);		
			profile.update();
			
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}
		session().clear();
    	session("username", user.getUsername());
    	session("role", user.getRole());
    	session("userid",  user.getId().toString());
    	session("tracking", user.getProfile().getTracking().toString());
		
		//System.out.println(form);
		return ok();
	}
}
