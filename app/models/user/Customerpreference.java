package models.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Customerpreference extends Model {
	@Id
	Long id;
	String dishes;
	String dishesrating;
	String notlikedish;
	String notlikedishdescription;
	String recommendationsatisfaction;
	String canbeimproved;
	String canbeimproveddescription;
	String missingcomponent;
	String missingcomponentdescription;
	String dietcontribution;
	String dietcontributiondescription;
	String recommendationsadequate;
	String usageforweightreduction;
	String howuseforweightreduction;
	String usability;
	String willuse;
	String wouldpay;
	String wouldpaydescription;
	String suitablename;
	String relevanttoapplication;
	String wouldrecommend;
	String generalremarks;
	Date dateofbirth;
	Double bmi;
	Long pregnancy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDishes() {
		return dishes;
	}
	public void setDishes(String dishes) {
		this.dishes = dishes;
	}
	public String getDishesrating() {
		return dishesrating;
	}
	public void setDishesrating(String dishesrating) {
		this.dishesrating = dishesrating;
	}
	public String getNotlikedish() {
		return notlikedish;
	}
	public void setNotlikedish(String notlikedish) {
		this.notlikedish = notlikedish;
	}
	public String getNotlikedishdescription() {
		return notlikedishdescription;
	}
	public void setNotlikedishdescription(String notlikedishdescription) {
		this.notlikedishdescription = notlikedishdescription;
	}
	public String getRecommendationsatisfaction() {
		return recommendationsatisfaction;
	}
	public void setRecommendationsatisfaction(String recommendationsatisfaction) {
		this.recommendationsatisfaction = recommendationsatisfaction;
	}
	public String getCanbeimproved() {
		return canbeimproved;
	}
	public void setCanbeimproved(String canbeimproved) {
		this.canbeimproved = canbeimproved;
	}
	public String getCanbeimproveddescription() {
		return canbeimproveddescription;
	}
	public void setCanbeimproveddescription(String canbeimproveddescription) {
		this.canbeimproveddescription = canbeimproveddescription;
	}
	public String getMissingcomponent() {
		return missingcomponent;
	}
	public void setMissingcomponent(String missingcomponent) {
		this.missingcomponent = missingcomponent;
	}
	public String getMissingcomponentdescription() {
		return missingcomponentdescription;
	}
	public void setMissingcomponentdescription(String missingcomponentdescription) {
		this.missingcomponentdescription = missingcomponentdescription;
	}
	public String getHowuseforweightreduction() {
		return howuseforweightreduction;
	}
	public void setHowuseforweightreduction(String howuseforweightreduction) {
		this.howuseforweightreduction = howuseforweightreduction;
	}
	public String getDietcontribution() {
		return dietcontribution;
	}
	public void setDietcontribution(String dietcontribution) {
		this.dietcontribution = dietcontribution;
	}
	public String getDietcontributiondescription() {
		return dietcontributiondescription;
	}
	public void setDietcontributiondescription(String dietcontributiondescription) {
		this.dietcontributiondescription = dietcontributiondescription;
	}
	public String getRecommendationsadequate() {
		return recommendationsadequate;
	}
	public void setRecommendationsadequate(String recommendationsadequate) {
		this.recommendationsadequate = recommendationsadequate;
	}
	public String getUsageforweightreduction() {
		return usageforweightreduction;
	}
	public void setUsageforweightreduction(String usageforweightreduction) {
		this.usageforweightreduction = usageforweightreduction;
	}
	public String getUsability() {
		return usability;
	}
	public void setUsability(String usability) {
		this.usability = usability;
	}
	public String getWilluse() {
		return willuse;
	}
	public void setWilluse(String willuse) {
		this.willuse = willuse;
	}
	public String getWouldpay() {
		return wouldpay;
	}
	public void setWouldpay(String wouldpay) {
		this.wouldpay = wouldpay;
	}
	public String getWouldpaydescription() {
		return wouldpaydescription;
	}
	public void setWouldpaydescription(String wouldpaydescription) {
		this.wouldpaydescription = wouldpaydescription;
	}
	public String getSuitablename() {
		return suitablename;
	}
	public void setSuitablename(String suitablename) {
		this.suitablename = suitablename;
	}
	public String getRelevanttoapplication() {
		return relevanttoapplication;
	}
	public void setRelevanttoapplication(String relevanttoapplication) {
		this.relevanttoapplication = relevanttoapplication;
	}
	public String getWouldrecommend() {
		return wouldrecommend;
	}
	public void setWouldrecommend(String wouldrecommend) {
		this.wouldrecommend = wouldrecommend;
	}
	public String getGeneralremarks() {
		return generalremarks;
	}
	public void setGeneralremarks(String generalremarks) {
		this.generalremarks = generalremarks;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public Double getBmi() {
		return bmi;
	}
	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}
	public Long getPregnancy() {
		return pregnancy;
	}
	public void setPregnancy(Long pregnancy) {
		this.pregnancy = pregnancy;
	}

}
