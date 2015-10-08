package models.ingredient;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import models.relations.Joindishingredient;
import models.relations.Joiningredientcondition;
import models.relations.Joiningredientingredientcategory;
import play.db.ebean.Model;

@Entity
public class Ingredient extends Model {
	@Id
	Long id;
	
	String Code; 
	@Column(name = "GroupCode")
	String GroupCode;
	@Column(name="Shrt_Desc")
	String Shrt_Desc;
	Double Water_g;
	Long Energ_Kcal; 
	Double Protein_g; 
	Double Lipid_Tot_g; 
	Double Ash_g;
	Double Carbohydrt_g; 
	Double Fiber_TD_g;
	Double Sugar_Tot_g;
	Integer Calcium_mg;
	Double Iron_mg;
	Integer Magnesium_mg;
	Integer Phosphorus_mg;
	Integer Potassium_mg;
	Integer Sodium_mg;
	Double Zinc_mg;
	Double Copper_mg;
	Double Manganese_mg;
	Double Selenium_µg; 
	Double Vit_C_mg;
	Double Thiamin_mg;
	Double Riboflavin_mg;
	Double Niacin_mg;
	Double Panto_Acid_mg; 
	Double Vit_B6_mg; 
	Integer Folate_Tot_µg;
	Integer Folic_Acid_µg;
	Integer Food_Folate_µg;
	Integer Folate_DFE_µg;
	Double Choline_Tot_mg;
	Double Vit_B12_µg;
	Integer Vit_A_IU;
	Integer Vit_A_RAE_µg_;
	Integer Retinol_µg;
	Integer Alpha_Carot_µg;
	Integer Beta_Carot_µg;
	Integer Beta_Crypt_µg;
	Integer Lycopene_µg;
	@Column(name = "LutZea_µg")	
	Integer LutZea_µg;
	Double Vit_E_mg;
	Double Vit_D_µg;
	Integer Vit_D_IU;
	Double Vit_K_µg;
	Double FA_Sat_g;
	Double FA_Mono_g;
	Double FA_Poly_g;
	Integer Cholestrl_mg;
	@Column(name = "GmWt_1")	
	Double GmWt_1;
	@Column(name = "GmWt_Desc1")	
	String GmWt_Desc1;
	@Column(name = "GmWt_2")	
	Double GmWt_2;
	@Column(name = "GmWt_Desc2")	
	String GmWt_Desc2;	
	
	@OneToMany(mappedBy = "ingredient") 
	@JsonManagedReference
    private List<Joindishingredient> dishingredient;	
	
	@OneToMany(mappedBy = "ingredient")   
	@JsonManagedReference
    private List<Joiningredientingredientcategory> ingredientingredientcategories;
	
	@OneToMany(mappedBy = "ingredient")  
	@JsonManagedReference
    private List<Joiningredientcondition> ingredientconditions;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getGroupCode() {
		return GroupCode;
	}
	public void setGroupCode(String groupCode) {
		GroupCode = groupCode;
	}
	public String getShrt_Desc() {
		return Shrt_Desc;
	}
	public void setShrt_Desc(String shrt_Desc) {
		Shrt_Desc = shrt_Desc;
	}
	public Double getWater_g() {
		return Water_g;
	}
	public void setWater_g(Double water_g) {
		Water_g = water_g;
	}
	public Long getEnerg_Kcal() {
		return Energ_Kcal;
	}
	public void setEnerg_Kcal(Long energ_Kcal) {
		Energ_Kcal = energ_Kcal;
	}
	public Double getProtein_g() {
		return (Protein_g != null ? Protein_g : 0.0);
	}
	public void setProtein_g(Double protein_g) {
		Protein_g = protein_g;
	}
	public Double getLipid_Tot_g() {
		return (Lipid_Tot_g != null ? Lipid_Tot_g : 0.0);
	}
	public void setLipid_Tot_g(Double lipid_Tot_g) {
		Lipid_Tot_g = lipid_Tot_g;
	}
	public Double getAsh_g() {
		return Ash_g;
	}
	public void setAsh_g(Double ash_g) {
		Ash_g = ash_g;
	}
	public Double getCarbohydrt_g() {
		return (Carbohydrt_g != null ? Carbohydrt_g : 0.0);
	}
	public void setCarbohydrt_g(Double carbohydrt_g) {
		Carbohydrt_g = carbohydrt_g;
	}
	public Double getFiber_TD_g() {
		return (Fiber_TD_g != null ? Fiber_TD_g : 0.0);
	}
	public void setFiber_TD_g(Double fiber_TD_g) {
		Fiber_TD_g = fiber_TD_g;
	}
	public Double getSugar_Tot_g() {
		return (Sugar_Tot_g != null ? Sugar_Tot_g : 0.0);
	}
	public void setSugar_Tot_g(Double sugar_Tot_g) {
		Sugar_Tot_g = sugar_Tot_g;
	}
	public Integer getCalcium_mg() {
		return (Calcium_mg != null ? Calcium_mg : 0);
	}
	public void setCalcium_mg(Integer calcium_mg) {
		Calcium_mg = calcium_mg;
	}
	public Double getIron_mg() {
		return (Iron_mg != null ? Iron_mg : 0.0);
	}
	public void setIron_mg(Double iron_mg) {
		Iron_mg = iron_mg;
	}
	public Integer getMagnesium_mg() {
		return (Magnesium_mg != null ? Magnesium_mg : 0);
	}
	public void setMagnesium_mg(Integer magnesium_mg) {
		Magnesium_mg = magnesium_mg;
	}
	public Integer getPhosphorus_mg() {
		return (Phosphorus_mg != null ? Phosphorus_mg : 0);
	}
	public void setPhosphorus_mg(Integer phosphorus_mg) {
		Phosphorus_mg = phosphorus_mg;
	}
	public Integer getPotassium_mg() {
		return (Potassium_mg != null ? Potassium_mg : 0);
	}
	public void setPotassium_mg(Integer potassium_mg) {
		Potassium_mg = potassium_mg;
	}
	public Integer getSodium_mg() {
		return (Sodium_mg != null ? Sodium_mg : 0);
	}
	public void setSodium_mg(Integer sodium_mg) {
		Sodium_mg = sodium_mg;
	}
	public Double getZinc_mg() {
		return (Zinc_mg != null ? Zinc_mg : 0);
	}
	public void setZinc_mg(Double zinc_mg) {
		Zinc_mg = zinc_mg;
	}
	public Double getCopper_mg() {
		return (Copper_mg != null ? Copper_mg : 0.0);
	}
	public void setCopper_mg(Double copper_mg) {
		Copper_mg = copper_mg;
	}
	public Double getManganese_mg() {
		return (Manganese_mg != null ? Manganese_mg : 0.0);
	}
	public void setManganese_mg(Double manganese_mg) {
		Manganese_mg = manganese_mg;
	}
	public Double getSelenium_µg() {
		return (Selenium_µg != null ? Selenium_µg : 0.0);
	}
	public void setSelenium_µg(Double selenium_µg) {
		Selenium_µg = selenium_µg;
	}
	public Double getVit_C_mg() {
		return (Vit_C_mg != null ? Vit_C_mg : 0.0);
	}
	public void setVit_C_mg(Double vit_C_mg) {
		Vit_C_mg = vit_C_mg;
	}
	public Double getThiamin_mg() {
		return (Thiamin_mg != null ? Thiamin_mg : 0.0);
	}
	public void setThiamin_mg(Double thiamin_mg) {
		Thiamin_mg = thiamin_mg;
	}
	public Double getRiboflavin_mg() {
		return (Riboflavin_mg != null ? Riboflavin_mg : 0.0);
	}
	public void setRiboflavin_mg(Double riboflavin_mg) {
		Riboflavin_mg = riboflavin_mg;
	}
	public Double getNiacin_mg() {
		return (Niacin_mg != null ? Niacin_mg : 0.0);
	}
	public void setNiacin_mg(Double niacin_mg) {
		Niacin_mg = niacin_mg;
	}
	public Double getPanto_Acid_mg() {
		return (Panto_Acid_mg != null ? Panto_Acid_mg : 0.0);
	}
	public void setPanto_Acid_mg(Double panto_Acid_mg) {
		Panto_Acid_mg = panto_Acid_mg;
	}
	public Double getVit_B6_mg() {
		return (Vit_B6_mg != null ? Vit_B6_mg : 0.0);
	}
	public void setVit_B6_mg(Double vit_B6_mg) {
		Vit_B6_mg = vit_B6_mg;
	}
	public Integer getFolate_Tot_µg() {
		return (Folate_Tot_µg != null ? Folate_Tot_µg : 0);
	}
	public void setFolate_Tot_µg(Integer folate_Tot_µg) {
		Folate_Tot_µg = folate_Tot_µg;
	}
	public Integer getFolic_Acid_µg() {
		return (Folic_Acid_µg != null ? Folic_Acid_µg : 0);
	}
	public void setFolic_Acid_µg(Integer folic_Acid_µg) {
		Folic_Acid_µg = folic_Acid_µg;
	}
	public Integer getFood_Folate_µg() {
		return Food_Folate_µg;
	}
	public void setFood_Folate_µg(Integer food_Folate_µg) {
		Food_Folate_µg = food_Folate_µg;
	}
	public Integer getFolate_DFE_µg() {
		return Folate_DFE_µg;
	}
	public void setFolate_DFE_µg(Integer folate_DFE_µg) {
		Folate_DFE_µg = folate_DFE_µg;
	}
	public Double getCholine_Tot_mg() {
		return (Choline_Tot_mg != null ? Choline_Tot_mg : 0.0);
	}
	public void setCholine_Tot_mg(Double choline_Tot_mg) {
		Choline_Tot_mg = choline_Tot_mg;
	}
	public Double getVit_B12_µg() {
		return (Vit_B12_µg != null ? Vit_B12_µg : 0.0);
	}
	public void setVit_B12_µg(Double vit_B12_µg) {
		Vit_B12_µg = vit_B12_µg;
	}
	public Integer getVit_A_IU() {
		return Vit_A_IU;
	}
	public void setVit_A_IU(Integer vit_A_IU) {
		Vit_A_IU = vit_A_IU;
	}
	public Integer getVit_A_RAE_µg_() {
		return (Vit_A_RAE_µg_ != null ? Vit_A_RAE_µg_ : 0);
	}
	public void setVit_A_RAE_µg_(Integer vit_A_RAE_µg_) {
		Vit_A_RAE_µg_ = vit_A_RAE_µg_;
	}
	public Integer getRetinol_µg() {
		return Retinol_µg;
	}
	public void setRetinol_µg(Integer retinol_µg) {
		Retinol_µg = retinol_µg;
	}
	public Integer getAlpha_Carot_µg() {
		return (Alpha_Carot_µg != null ? Alpha_Carot_µg : 0) ;
	}
	public void setAlpha_Carot_µg(Integer alpha_Carot_µg) {
		Alpha_Carot_µg = alpha_Carot_µg;
	}
	public Integer getBeta_Carot_µg() {
		return (Beta_Carot_µg != null ? Beta_Carot_µg :0);
	}
	public void setBeta_Carot_µg(Integer beta_Carot_µg) {
		Beta_Carot_µg = beta_Carot_µg;
	}
	public Integer getBeta_Crypt_µg() {
		return (Beta_Crypt_µg != null ? Beta_Crypt_µg : 0);
	}
	public void setBeta_Crypt_µg(Integer beta_Crypt_µg) {
		Beta_Crypt_µg = beta_Crypt_µg;
	}
	public Integer getLycopene_µg() {
		return (Lycopene_µg != null ? Lycopene_µg : 0);
	}
	public void setLycopene_µg(Integer lycopene_µg) {
		Lycopene_µg = lycopene_µg;
	}
	public Integer getLutZea_µg() {
		return (LutZea_µg != null ? LutZea_µg : 0);
	}
	public void setLutZea_µg(Integer lutZea_µg) {
		LutZea_µg = lutZea_µg;
	}
	public Double getVit_E_mg() {
		return (Vit_E_mg != null ? Vit_E_mg : 0.0);
	}
	public void setVit_E_mg(Double vit_E_mg) {
		Vit_E_mg = vit_E_mg;
	}
	public Double getVit_D_µg() {
		return (Vit_D_µg != null ? Vit_D_µg : 0.0);
	}
	public void setVit_D_µg(Double vit_D_µg) {
		Vit_D_µg = vit_D_µg;
	}
	public Integer getVit_D_IU() {
		return Vit_D_IU;
	}
	public void setVit_D_IU(Integer vit_D_IU) {
		Vit_D_IU = vit_D_IU;
	}
	public Double getVit_K_µg() {
		return (Vit_K_µg != null ? Vit_K_µg : 0.0);
	}
	public void setVit_K_µg(Double vit_K_µg) {
		Vit_K_µg = vit_K_µg;
	}
	public Double getFA_Sat_g() {
		return (FA_Sat_g != null ? FA_Sat_g : 0.0);
	}
	public void setFA_Sat_g(Double fA_Sat_g) {
		FA_Sat_g = fA_Sat_g;
	}
	public Double getFA_Mono_g() {
		return (FA_Mono_g != null ? FA_Mono_g : 0.0);
	}
	public void setFA_Mono_g(Double fA_Mono_g) {
		FA_Mono_g = fA_Mono_g;
	}
	public Double getFA_Poly_g() {
		return (FA_Poly_g != null ? FA_Poly_g : 0.0);
	}
	public void setFA_Poly_g(Double fA_Poly_g) {
		FA_Poly_g = fA_Poly_g;
	}
	public Integer getCholestrl_mg() {
		return Cholestrl_mg;
	}
	public void setCholestrl_mg(Integer cholestrl_mg) {
		Cholestrl_mg = cholestrl_mg;
	}
	public Double getGmWt_1() {
		return GmWt_1;
	}
	public void setGmWt_1(Double gmWt_1) {
		GmWt_1 = gmWt_1;
	}
	public String getGmWt_Desc1() {
		return GmWt_Desc1;
	}
	public void setGmWt_Desc1(String gmWt_Desc1) {
		GmWt_Desc1 = gmWt_Desc1;
	}
	public Double getGmWt_2() {
		return GmWt_2;
	}
	public void setGmWt_2(Double gmWt_2) {
		GmWt_2 = gmWt_2;
	}
	public String getGmWt_Desc2() {
		return GmWt_Desc2;
	}
	public void setGmWt_Desc2(String gmWt_Desc2) {
		GmWt_Desc2 = gmWt_Desc2;
	}
	public List<Joiningredientingredientcategory> getIngredientingredientcategories() {
		return ingredientingredientcategories;
	}
	public void setIngredientingredientcategories(
			List<Joiningredientingredientcategory> ingredientingredientcategories) {
		this.ingredientingredientcategories = ingredientingredientcategories;
	}
	public List<Joindishingredient> getDishingredient() {
		return dishingredient;
	}
	public void setDishingredient(List<Joindishingredient> dishingredient) {
		this.dishingredient = dishingredient;
	}
	public List<Joiningredientcondition> getIngredientconditions() {
		return ingredientconditions;
	}
	public void setIngredientconditions(
			List<Joiningredientcondition> ingredientconditions) {
		this.ingredientconditions = ingredientconditions;
	}
	
}
