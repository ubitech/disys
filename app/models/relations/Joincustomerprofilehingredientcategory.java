package models.relations;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.lists.Hingredientcategory;
import models.user.Profile;

import com.fasterxml.jackson.annotation.JsonBackReference;

import play.db.ebean.Model;

@Entity
public class Joincustomerprofilehingredientcategory extends Model {
	@Id
	Long id;
	
	@ManyToOne
	@JoinColumn(name="customerprofileid", insertable=false, updatable=false)
	@JsonBackReference
	Profile profile;
	
	@ManyToOne
	@JoinColumn(name="hingredientcategoryid", insertable=false, updatable=false)
	@JsonBackReference
	Hingredientcategory ingredientcategory;
	
	Long customerprofileid;
	Long hingredientcategoryid;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Hingredientcategory getIngredientcategory() {
		return ingredientcategory;
	}

	public void setIngredientcategory(Hingredientcategory ingredientcategory) {
		this.ingredientcategory = ingredientcategory;
	}

	public Long getCustomerprofileid() {
		return customerprofileid;
	}

	public void setCustomerprofileid(Long customerprofileid) {
		this.customerprofileid = customerprofileid;
	}

	public Long getHingredientcategoryid() {
		return hingredientcategoryid;
	}

	public void setHingredientcategoryid(Long hingredientcategoryid) {
		this.hingredientcategoryid = hingredientcategoryid;
	}

}
