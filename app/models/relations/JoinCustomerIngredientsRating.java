package models.relations;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.ingredient.Ingredient;
import models.user.Profile;

@Entity
@Table(name = "joincustomeringredientsrating")

		
public class JoinCustomerIngredientsRating implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	Long id;  
    
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Profile profile;
    @JoinColumn(name = "ingredientid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ingredient ingredient;
    
    @Basic(optional = false)
    @Column(name = "rating")
    private double rating;
    @Basic(optional = false)
    @Column(name = "count_rating")
    private int count_rating;

    public JoinCustomerIngredientsRating() {
    }
   

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
		
		public Integer getCount_rating() {
        return count_rating;
    }

    public void setCount_rating(Integer count_rating) {
        this.count_rating = count_rating;
    }

   

    

    @Override
    public String toString() {
        return "disysmodel.JoinUserIngredientsRating[ JoinUserIngredientsRatingPK=" + id + " ]";
    }
    
}
