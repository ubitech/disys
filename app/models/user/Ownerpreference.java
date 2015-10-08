package models.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import play.db.ebean.Model;

@Entity
public class Ownerpreference extends Model {
	
	@OneToOne(mappedBy="ownerpreference")
	@JoinColumn(name="id")
	User user;
	
	@Id
	Long id;
	Integer easeofusage;
	Integer infoquickness;
	Integer functionalaspect;
	Integer design;
	Integer easeofrestaurantdataentry;
	Integer easeofrecipeentry;
	Integer easeofingredientsearch;
	Integer gdaeasiness;
	Integer customerusage;
	Integer customercoverage;
	Integer preferredname;
	String  preferrednametext;
	String suggestions;
	Integer dishcredibility;
	String usefulness;
	String increasecustomers;
	String recipedesign;
	String applicationrevlevance;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getEaseofusage() {
		return easeofusage;
	}
	public void setEaseofusage(Integer easeofusage) {
		this.easeofusage = easeofusage;
	}
	public Integer getInfoquickness() {
		return infoquickness;
	}
	public void setInfoquickness(Integer infoquickness) {
		this.infoquickness = infoquickness;
	}
	public Integer getFunctionalaspect() {
		return functionalaspect;
	}
	public void setFunctionalaspect(Integer functionalaspect) {
		this.functionalaspect = functionalaspect;
	}
	public Integer getDesign() {
		return design;
	}
	public void setDesign(Integer design) {
		this.design = design;
	}
	public Integer getEaseofrestaurantdataentry() {
		return easeofrestaurantdataentry;
	}

	public void setEaseofrestaurantdataentry(Integer easeofrestaurantdataentry) {
		this.easeofrestaurantdataentry = easeofrestaurantdataentry;
	}
	public Integer getEaseofrecipeentry() {
		return easeofrecipeentry;
	}
	public void setEaseofrecipeentry(Integer easeofrecipeentry) {
		this.easeofrecipeentry = easeofrecipeentry;
	}
	public Integer getEaseofingredientsearch() {
		return easeofingredientsearch;
	}
	public void setEaseofingredientsearch(Integer easeofingredientsearch) {
		this.easeofingredientsearch = easeofingredientsearch;
	}
	public Integer getGdaeasiness() {
		return gdaeasiness;
	}
	public void setGdaeasiness(Integer gdaeasiness) {
		this.gdaeasiness = gdaeasiness;
	}
	public Integer getCustomerusage() {
		return customerusage;
	}
	public void setCustomerusage(Integer customerusage) {
		this.customerusage = customerusage;
	}
	public Integer getCustomercoverage() {
		return customercoverage;
	}
	public void setCustomercoverage(Integer customercoverage) {
		this.customercoverage = customercoverage;
	}
	public Integer getPreferredname() {
		return preferredname;
	}
	public void setPreferredname(Integer preferredname) {
		this.preferredname = preferredname;
	}
	public String getPreferrednametext() {
		return preferrednametext;
	}
	public void setPreferrednametext(String preferrednametext) {
		this.preferrednametext = preferrednametext;
	}
	public String getIncreasecustomers() {
		return increasecustomers;
	}
	public void setIncreasecustomers(String increasecustomers) {
		this.increasecustomers = increasecustomers;
	}
	public String getRecipedesign() {
		return recipedesign;
	}
	public void setRecipedesign(String recipedesign) {
		this.recipedesign = recipedesign;
	}
	public String getApplicationrevlevance() {
		return applicationrevlevance;
	}
	public void setApplicationrevlevance(String applicationrevlevance) {
		this.applicationrevlevance = applicationrevlevance;
	}
	public String getSuggestions() {
		return suggestions;
	}
	public String getUsefulness() {
		return usefulness;
	}
	public void setUsefulness(String usefulness) {
		this.usefulness = usefulness;
	}
	public Integer getDishcredibility() {
		return dishcredibility;
	}
	public void setDishcredibility(Integer dishcredibility) {
		this.dishcredibility = dishcredibility;
	}
	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}
	
	
}
