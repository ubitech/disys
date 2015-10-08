package models.lists;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import models.relations.Joiningredientcondition;
import models.relations.Joiningredientingredientcategory;

import com.avaje.ebean.Page;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Hcondition extends Model {
	public Hcondition() {
		
	}
	public Hcondition (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	@OneToMany(mappedBy = "condition")
	private Set<Joiningredientcondition> ingredientconditions = 
		new HashSet<Joiningredientcondition>();
	
	public static Finder<Long, Hcondition> find = new Finder<Long, Hcondition>(Long.class, Hcondition.class);
	
	public static Page<Hcondition> find(int page) {
	    return 
	            find.where()
	                .orderBy("id asc")
	                .findPagingList(10)
	                .setFetchAhead(false)
	                .getPage(page);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Joiningredientcondition> getIngredientconditions() {
		return ingredientconditions;
	}
	public void setIngredientconditions(
			Set<Joiningredientcondition> ingredientconditions) {
		this.ingredientconditions = ingredientconditions;
	}
	
}