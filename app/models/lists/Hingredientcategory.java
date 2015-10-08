package models.lists;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import models.relations.Joincustomerprofilehingredientcategory;
import models.relations.Joiningredientingredientcategory;

import com.avaje.ebean.Page;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Hingredientcategory extends Model {
	public Hingredientcategory() {
		
	}
	public Hingredientcategory (String description) {
		this.description = description;
	}
	@Id
	Long id;
	String description;
	
	@OneToMany(mappedBy = "ingredientcategory")
	private Set<Joincustomerprofilehingredientcategory> ingredientcategories = 
		new HashSet<Joincustomerprofilehingredientcategory>();
	
	@OneToMany(mappedBy = "ingredientcategory")
	private Set<Joiningredientingredientcategory> ingredientingredientcategories = 
		new HashSet<Joiningredientingredientcategory>();
	
	public static Finder<Long, Hingredientcategory> find = new Finder<Long, Hingredientcategory>(Long.class, Hingredientcategory.class);
	
	public static Page<Hingredientcategory> find(int page) {
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
	public Set<Joincustomerprofilehingredientcategory> getIngredientcategories() {
		return ingredientcategories;
	}
	public void setIngredientcategories(
			Set<Joincustomerprofilehingredientcategory> ingredientcategories) {
		this.ingredientcategories = ingredientcategories;
	}
	public Set<Joiningredientingredientcategory> getIngredientingredientcategories() {
		return ingredientingredientcategories;
	}
	public void setIngredientingredientcategories(
			Set<Joiningredientingredientcategory> ingredientingredientcategories) {
		this.ingredientingredientcategories = ingredientingredientcategories;
	}
	
	
}