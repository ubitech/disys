package models.user;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import models.lists.Hdiet;
import models.lists.Hgender;
import models.lists.Hphysicalactivity;
import models.lists.Hpregnancy;
import models.relations.Joincustomerprofilehingredientcategory;
import models.relations.Joincustomerprofilemedicalcondition;
import play.db.ebean.Model;

@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
@Table(name = "customerprofile")
public class Profile extends Model {
	@Id
	Long id;

	String surname;
	String name;
	String alias;
	String phone;
	String country;
	String city;
	String postalcode;
	String email;
	String mobilephone;
	Double weight;
	Double height;
	Date dateofbirth;
	Integer tracking;

	Double age;

	@Transient
	Double goal;

	@Transient
	Boolean thilasmos;

	@Transient
	Boolean pregnant;

	@Transient
	Boolean hasHypertension = false;

	@Transient
	Boolean hasHypercholesterolemia = false;

	@Transient
	Boolean hasDiabetes = false;

	@Transient
	Boolean hasConstipation = false;

	@Transient
	Boolean hasHyperuricemia = false;

	@Transient
	Boolean hasCeliac = false;

	@Transient
	Boolean hasAnemia = false;

	@Transient
	Double pregnancy = 0.0;

	@Transient
	Boolean breastfeeding = false;

	@Transient
	Double calories = 0.0;

	@Transient
	Double bmi = 0.0;

	@Transient
	Double bmr = 0.0;

	@Transient
	Double vitamin_A = 0.0; // intake

	@Transient
	Double vitamin_D = 0.0; // intake

	@Transient
	Double vitamin_E = 0.0; // intake

	@Transient
	Double vitamin_K = 0.0; // intake

	@Transient
	Double vitamin_C = 0.0; // intake

	@Transient
	Double vitamin_B1 = 0.0; // intake

	@Transient
	Double vitamin_B2 = 0.0; // intake

	@Transient
	Double vitamin_B3 = 0.0; // intake

	@Transient
	Double vitamin_B6 = 0.0; // intake

	@Transient
	Double vitamin_B12 = 0.0; // intake

	@Transient
	Double thiamin = 0.0; // intake

	@Transient
	Double riboflavin = 0.0; // intake

	@Transient
	Double nef = 0.0; // intake
	
	@Transient
	Double folate = 0.0;

	@Transient
	Double pantothenic = 0.0; // intake

	@Transient
	Double biotin = 0.0; // intake

	@Transient
	Double choline = 0.0; // intake

	@Transient
	Double magnesium = 0.0; // intake

	@Transient
	Double calcium = 0.0; // intake

	@Transient
	Double phosphorus = 0.0; // intake

	@Transient
	Double iron = 0.0; // intake

	@Transient
	Double zinc = 0.0; // intake

	@Transient
	Double iodine = 0.0; // intake

	@Transient
	Double selenium = 0.0; // intake

	@Transient
	Double fluoride = 0.0; // intake

	@Transient
	Double manganese = 0.0; // intake

	@Transient
	Double molybdenum = 0.0; // intake

	@Transient
	Double chromium = 0.0; // intake

	@Transient
	Double copper = 0.0; // intake

	@Transient
	Double potassium = 0.0; // intake

	@Transient
	Double hydrocarbonate = 0.0; // intake

	@Transient
	Double sugars = 0.0; // intake

	@Transient
	Double fats = 0.0; // intake

	@Transient
	Double polyunsaturated_fats = 0.0; // intake

	@Transient
	Double protein = 0.0; // intake

	@Transient
	Double sodium = 0.0; // intake

	@Transient
	Double salt = 0.0; // intake

	@Transient
	Double fiber = 0.0; // intake

	@Transient
	Double saturated_fats = 0.0; // intake

	@Transient
	Double cholesterol = 0.0; // intake

	@Transient
	Double alcohol = 0.0; // intake

	@Transient
	Double caffeine = 0.0; // intake

	@Transient
	int wantBread = 0;

	@Transient
	int wantEggs = 0;

	@Transient
	int wantFeta = 0;

	@Transient
	int wantKaseri = 0;

	@Transient
	int wantLactose = 0;

	@Transient
	int wantLamp = 0;

	@Transient
	int wantMilk = 0;

	@Transient
	int wantPork = 0;

	@Transient
	int wantSeeds = 0;

	@Transient
	int wantShells = 0;

	@Transient
	int wantSogia = 0;

	@Transient
	int wantVeal = 0;

	@Transient
	int wantWheat = 0;

	@Transient
	int wantFish = 0;

	@Transient
	double activity = 1.0;

	@Transient
	double idealWeight = 0.0;
	@Transient
	double adjustedWeight = 0.0;

	@OneToMany(mappedBy = "profile")
	@JsonManagedReference
	private Set<Joincustomerprofilehingredientcategory> profileIngredientCategories = new HashSet<Joincustomerprofilehingredientcategory>();

	@OneToMany(mappedBy = "profile")
	@JsonManagedReference
	private Set<Joincustomerprofilemedicalcondition> profileConditions = new HashSet<Joincustomerprofilemedicalcondition>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hgenderid", referencedColumnName = "id")
	Hgender gender;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hdietid", referencedColumnName = "id")
	Hdiet diet;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hpregnancyid", referencedColumnName = "id")
	Hpregnancy pregnancyStatus;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hphysicalactivityid", referencedColumnName = "id")
	Hphysicalactivity physicalactivity;
	@Column(columnDefinition = "integer default 0")
	Integer watchcalories;
	@Column(columnDefinition = "integer default 0")
	Integer watchhydros;
	@Column(columnDefinition = "integer default 0")
	Integer watchsugars;
	@Column(columnDefinition = "integer default 0")
	Integer watchfiber;
	@Column(columnDefinition = "integer default 0")
	Integer watchprotein;
	@Column(columnDefinition = "integer default 0")
	Integer watchfat;
	@Column(columnDefinition = "integer default 0")
	Integer watchsaturated;
	@Column(columnDefinition = "integer default 0")
	Integer watchmonosaturated;
	@Column(columnDefinition = "integer default 0")
	Integer watchpolysaturated;
	@Column(columnDefinition = "integer default 0")
	Integer watchcholesterol;
	@Column(columnDefinition = "integer default 0")
	Integer watchmetals;
	@Column(columnDefinition = "integer default 0")
	Integer watchvitamins;
	@Column(columnDefinition = "integer default 0")
	Integer watchantioxidants;
	@OneToOne(mappedBy = "profile")
	@JoinColumn(name = "id")
	User user;

	public void setTransients() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateofbirth);
		int yearOfBirth = cal.get(Calendar.YEAR);
		cal.setTime(new Date());
		int yearNow = cal.get(Calendar.YEAR);
		age = (double) (yearNow - yearOfBirth);
		if (gender.getId() == 1) {
			// 655 + (9.6 X weight in kg) + (1.8 x height in cm) – (4.7 x age in
			// yrs)
			calories = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
		} else if (gender.getId() == 2) {
			if (pregnancyStatus != null) {
				System.out.println("Found pregnancy status: "
						+ pregnancyStatus.getDescription());
				if (pregnancyStatus.getId() == 4) {
					breastfeeding = true;
					thilasmos = true;
					pregnant = false;
				} else if (pregnancyStatus.getId() == 5) {
					pregnant = false;
				} else {
					pregnancy = pregnancyStatus.getId().doubleValue();
					pregnant = true;
				}

			}
			calories = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
		}
		if (physicalactivity.getId() == 1) {
			activity = 1.4;
		} else if (physicalactivity.getId() == 2) {
			activity = 1.6;
		} else if (physicalactivity.getId() == 3) {
			activity = 1.8;
		} else if (physicalactivity.getId() == 4) {
			activity = 2.0;
		}
		// calories *= physicalactivity.getId();
		goal = diet.getId().doubleValue();
		for (Joincustomerprofilemedicalcondition profileCondition : profileConditions) {
			if (profileCondition.getMedicalcondition().getId() == 1) {
				this.hasAnemia = true;
			}
			if (profileCondition.getMedicalcondition().getId() == 2) {
				this.hasDiabetes = true;
			}
			if (profileCondition.getMedicalcondition().getId() == 3) {
				this.hasConstipation = true;
			}
			if (profileCondition.getMedicalcondition().getId() == 4) {
				this.hasCeliac = true;
			}
			if (profileCondition.getMedicalcondition().getId() == 5) {
				this.hasHyperuricemia = true;
			}
			if (profileCondition.getMedicalcondition().getId() == 6) {
				this.hasHypertension = true;
			}
			if (profileCondition.getMedicalcondition().getId() == 7) {
				this.hasHypercholesterolemia = true;
			}
		}
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public double getActivity() {
		return activity;
	}

	public void setActivity(double activity) {
		this.activity = activity;
	}

	public double getIdealWeight() {
		return idealWeight;
	}

	public void setIdealWeight(double idealWeight) {
		this.idealWeight = idealWeight;
	}

	public double getAdjustedWeight() {
		return adjustedWeight;
	}

	public void setAdjustedWeight(double adjustedWeight) {
		this.adjustedWeight = adjustedWeight;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public User getUser() {
		return user;
	}

	public Integer getWatchcalories() {
		return watchcalories;
	}

	public void setWatchcalories(Integer watchcalories) {
		this.watchcalories = watchcalories;
	}

	public Integer getWatchhydros() {
		return watchhydros;
	}

	public void setWatchhydros(Integer watchhydros) {
		this.watchhydros = watchhydros;
	}

	public Integer getWatchsugars() {
		return watchsugars;
	}

	public void setWatchsugars(Integer watchsugars) {
		this.watchsugars = watchsugars;
	}

	public Integer getWatchfiber() {
		return watchfiber;
	}

	public void setWatchfiber(Integer watchfiber) {
		this.watchfiber = watchfiber;
	}

	public Integer getWatchprotein() {
		return watchprotein;
	}

	public void setWatchprotein(Integer watchprotein) {
		this.watchprotein = watchprotein;
	}

	public Integer getWatchfat() {
		return watchfat;
	}

	public void setWatchfat(Integer watchfat) {
		this.watchfat = watchfat;
	}

	public Integer getWatchsaturated() {
		return watchsaturated;
	}

	public void setWatchsaturated(Integer watchsaturated) {
		this.watchsaturated = watchsaturated;
	}

	public Integer getWatchmonosaturated() {
		return watchmonosaturated;
	}

	public void setWatchmonosaturated(Integer watchmonosaturated) {
		this.watchmonosaturated = watchmonosaturated;
	}

	public Integer getWatchpolysaturated() {
		return watchpolysaturated;
	}

	public void setWatchpolysaturated(Integer watchpolysaturated) {
		this.watchpolysaturated = watchpolysaturated;
	}

	public Integer getWatchcholesterol() {
		return watchcholesterol;
	}

	public void setWatchcholesterol(Integer watchcholesterol) {
		this.watchcholesterol = watchcholesterol;
	}

	public Integer getWatchmetals() {
		return watchmetals;
	}

	public void setWatchmetals(Integer watchmetals) {
		this.watchmetals = watchmetals;
	}

	public Integer getWatchvitamins() {
		return watchvitamins;
	}

	public void setWatchvitamins(Integer watchvitamins) {
		this.watchvitamins = watchvitamins;
	}

	public Integer getWatchantioxidants() {
		return watchantioxidants;
	}

	public void setWatchantioxidants(Integer watchantioxidants) {
		this.watchantioxidants = watchantioxidants;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Joincustomerprofilehingredientcategory> getProfileIngredientCategories() {
		return profileIngredientCategories;
	}

	public void setProfileIngredientCategories(
			Set<Joincustomerprofilehingredientcategory> profileIngredientCategories) {
		this.profileIngredientCategories = profileIngredientCategories;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public Hpregnancy getPregnancyStatus() {
		return pregnancyStatus;
	}

	public void setPregnancyStatus(Hpregnancy pregnancyStatus) {
		this.pregnancyStatus = pregnancyStatus;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public Hgender getGender() {
		return gender;
	}

	public Hdiet getDiet() {
		return diet;
	}

	public void setDiet(Hdiet diet) {
		this.diet = diet;
	}

	public Set<Joincustomerprofilemedicalcondition> getProfileConditions() {
		return profileConditions;
	}

	public Hphysicalactivity getPhysicalactivity() {
		return physicalactivity;
	}

	public void setPhysicalactivity(Hphysicalactivity physicalactivity) {
		this.physicalactivity = physicalactivity;
	}

	public void setProfileConditions(
			Set<Joincustomerprofilemedicalcondition> profileConditions) {
		this.profileConditions = profileConditions;
	}

	public void setGender(Hgender gender) {
		this.gender = gender;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public Double getGoal() {
		return goal;
	}

	public void setGoal(Double goal) {
		this.goal = goal;
	}

	public Boolean getThilasmos() {
		return thilasmos;
	}

	public void setThilasmos(Boolean thilasmos) {
		this.thilasmos = thilasmos;
	}

	public Boolean getPregnant() {
		return pregnant;
	}

	public void setPregnant(Boolean pregnant) {
		this.pregnant = pregnant;
	}

	public Boolean getHasHypertension() {
		return hasHypertension;
	}

	public void setHasHypertension(Boolean hasHypertension) {
		this.hasHypertension = hasHypertension;
	}

	public Boolean getHasHypercholesterolemia() {
		return hasHypercholesterolemia;
	}

	public void setHasHypercholesterolemia(Boolean hasHypercholesterolemia) {
		this.hasHypercholesterolemia = hasHypercholesterolemia;
	}

	public Boolean getHasDiabetes() {
		return hasDiabetes;
	}

	public void setHasDiabetes(Boolean hasDiabetes) {
		this.hasDiabetes = hasDiabetes;
	}

	public Boolean getHasConstipation() {
		return hasConstipation;
	}

	public void setHasConstipation(Boolean hasConstipation) {
		this.hasConstipation = hasConstipation;
	}

	public Boolean getHasHyperuricemia() {
		return hasHyperuricemia;
	}

	public void setHasHyperuricemia(Boolean hasHyperuricemia) {
		this.hasHyperuricemia = hasHyperuricemia;
	}

	public Boolean getHasCeliac() {
		return hasCeliac;
	}

	public void setHasCeliac(Boolean hasCeliac) {
		this.hasCeliac = hasCeliac;
	}

	public Boolean getHasAnemia() {
		return hasAnemia;
	}

	public void setHasAnemia(Boolean hasAnemia) {
		this.hasAnemia = hasAnemia;
	}

	public Double getPregnancy() {
		return pregnancy;
	}

	public void setPregnancy(Double pregnancy) {
		this.pregnancy = pregnancy;
	}

	public Boolean getBreastfeeding() {
		return breastfeeding;
	}

	public void setBreastfeeding(Boolean breastfeeding) {
		this.breastfeeding = breastfeeding;
	}

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	public Double getBmi() {
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public Double getBmr() {
		return bmr;
	}

	public void setBmr(Double bmr) {
		this.bmr = bmr;
	}

	public Double getVitamin_A() {
		return vitamin_A;
	}

	public void setVitamin_A(Double vitamin_A) {
		this.vitamin_A = vitamin_A;
	}

	public Double getVitamin_D() {
		return vitamin_D;
	}

	public void setVitamin_D(Double vitamin_D) {
		this.vitamin_D = vitamin_D;
	}

	public Double getVitamin_E() {
		return vitamin_E;
	}

	public void setVitamin_E(Double vitamin_E) {
		this.vitamin_E = vitamin_E;
	}

	public Double getVitamin_K() {
		return vitamin_K;
	}

	public void setVitamin_K(Double vitamin_K) {
		this.vitamin_K = vitamin_K;
	}

	public Double getVitamin_C() {
		return vitamin_C;
	}

	public void setVitamin_C(Double vitamin_C) {
		this.vitamin_C = vitamin_C;
	}

	public Double getVitamin_B1() {
		return vitamin_B1;
	}

	public void setVitamin_B1(Double vitamin_B1) {
		this.vitamin_B1 = vitamin_B1;
	}

	public Double getVitamin_B2() {
		return vitamin_B2;
	}

	public void setVitamin_B2(Double vitamin_B2) {
		this.vitamin_B2 = vitamin_B2;
	}

	public Double getVitamin_B3() {
		return vitamin_B3;
	}

	public void setVitamin_B3(Double vitamin_B3) {
		this.vitamin_B3 = vitamin_B3;
	}

	public Double getVitamin_B6() {
		return vitamin_B6;
	}

	public void setVitamin_B6(Double vitamin_B6) {
		this.vitamin_B6 = vitamin_B6;
	}

	public Double getVitamin_B12() {
		return vitamin_B12;
	}

	public void setVitamin_B12(Double vitamin_B12) {
		this.vitamin_B12 = vitamin_B12;
	}

	public Double getThiamin() {
		return thiamin;
	}

	public void setThiamin(Double thiamin) {
		this.thiamin = thiamin;
	}

	public Double getRiboflavin() {
		return riboflavin;
	}

	public void setRiboflavin(Double riboflavin) {
		this.riboflavin = riboflavin;
	}

	public Double getNef() {
		return nef;
	}

	public void setNef(Double nef) {
		this.nef = nef;
	}

	public Double getPantothenic() {
		return pantothenic;
	}

	public void setPantothenic(Double pantothenic) {
		this.pantothenic = pantothenic;
	}

	public Double getBiotin() {
		return biotin;
	}

	public void setBiotin(Double biotin) {
		this.biotin = biotin;
	}

	public Double getCholine() {
		return choline;
	}

	public void setCholine(Double choline) {
		this.choline = choline;
	}

	public Double getMagnesium() {
		return magnesium;
	}

	public void setMagnesium(Double magnesium) {
		this.magnesium = magnesium;
	}

	public Double getCalcium() {
		return calcium;
	}

	public void setCalcium(Double calcium) {
		this.calcium = calcium;
	}

	public Double getPhosphorus() {
		return phosphorus;
	}

	public void setPhosphorus(Double phosphorus) {
		this.phosphorus = phosphorus;
	}

	public Double getIron() {
		return iron;
	}

	public void setIron(Double iron) {
		this.iron = iron;
	}

	public Double getZinc() {
		return zinc;
	}

	public void setZinc(Double zinc) {
		this.zinc = zinc;
	}

	public Double getIodine() {
		return iodine;
	}

	public void setIodine(Double iodine) {
		this.iodine = iodine;
	}

	public Double getSelenium() {
		return selenium;
	}

	public void setSelenium(Double selenium) {
		this.selenium = selenium;
	}

	public Double getFluoride() {
		return fluoride;
	}

	public void setFluoride(Double fluoride) {
		this.fluoride = fluoride;
	}

	public Double getManganese() {
		return manganese;
	}

	public void setManganese(Double manganese) {
		this.manganese = manganese;
	}

	public Double getMolybdenum() {
		return molybdenum;
	}

	public void setMolybdenum(Double molybdenum) {
		this.molybdenum = molybdenum;
	}

	public Double getChromium() {
		return chromium;
	}

	public void setChromium(Double chromium) {
		this.chromium = chromium;
	}

	public Double getCopper() {
		return copper;
	}

	public void setCopper(Double copper) {
		this.copper = copper;
	}

	public Double getPotassium() {
		return potassium;
	}

	public void setPotassium(Double potassium) {
		this.potassium = potassium;
	}

	public Double getHydrocarbonate() {
		return hydrocarbonate;
	}

	public void setHydrocarbonate(Double hydrocarbonate) {
		this.hydrocarbonate = hydrocarbonate;
	}

	public Double getSugars() {
		return sugars;
	}

	public void setSugars(Double sugars) {
		this.sugars = sugars;
	}

	public Double getFats() {
		return fats;
	}

	public void setFats(Double fats) {
		this.fats = fats;
	}

	public Double getPolyunsaturated_fats() {
		return polyunsaturated_fats;
	}

	public void setPolyunsaturated_fats(Double polyunsaturated_fats) {
		this.polyunsaturated_fats = polyunsaturated_fats;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	public Double getSodium() {
		return sodium;
	}

	public void setSodium(Double sodium) {
		this.sodium = sodium;
	}

	public Double getSalt() {
		return salt;
	}

	public void setSalt(Double salt) {
		this.salt = salt;
	}

	public Double getFiber() {
		return fiber;
	}

	public void setFiber(Double fiber) {
		this.fiber = fiber;
	}

	public Double getSaturated_fats() {
		return saturated_fats;
	}

	public void setSaturated_fats(Double saturated_fats) {
		this.saturated_fats = saturated_fats;
	}

	public Double getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(Double cholesterol) {
		this.cholesterol = cholesterol;
	}

	public Double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(Double alcohol) {
		this.alcohol = alcohol;
	}

	public Double getCaffeine() {
		return caffeine;
	}

	public void setCaffeine(Double caffeine) {
		this.caffeine = caffeine;
	}

	public int getWantBread() {
		return wantBread;
	}

	public void setWantBread(int wantBread) {
		this.wantBread = wantBread;
	}

	public int getWantEggs() {
		return wantEggs;
	}

	public void setWantEggs(int wantEggs) {
		this.wantEggs = wantEggs;
	}

	public int getWantFeta() {
		return wantFeta;
	}

	public void setWantFeta(int wantFeta) {
		this.wantFeta = wantFeta;
	}

	public int getWantKaseri() {
		return wantKaseri;
	}

	public void setWantKaseri(int wantKaseri) {
		this.wantKaseri = wantKaseri;
	}

	public int getWantLactose() {
		return wantLactose;
	}

	public void setWantLactose(int wantLactose) {
		this.wantLactose = wantLactose;
	}

	public int getWantLamp() {
		return wantLamp;
	}

	public void setWantLamp(int wantLamp) {
		this.wantLamp = wantLamp;
	}

	public int getWantMilk() {
		return wantMilk;
	}

	public void setWantMilk(int wantMilk) {
		this.wantMilk = wantMilk;
	}

	public int getWantPork() {
		return wantPork;
	}

	public void setWantPork(int wantPork) {
		this.wantPork = wantPork;
	}

	public int getWantSeeds() {
		return wantSeeds;
	}

	public void setWantSeeds(int wantSeeds) {
		this.wantSeeds = wantSeeds;
	}

	public int getWantShells() {
		return wantShells;
	}

	public void setWantShells(int wantShells) {
		this.wantShells = wantShells;
	}

	public int getWantSogia() {
		return wantSogia;
	}

	public void setWantSogia(int wantSogia) {
		this.wantSogia = wantSogia;
	}

	public int getWantVeal() {
		return wantVeal;
	}

	public void setWantVeal(int wantVeal) {
		this.wantVeal = wantVeal;
	}

	public int getWantWheat() {
		return wantWheat;
	}

	public void setWantWheat(int wantWheat) {
		this.wantWheat = wantWheat;
	}

	public int getWantFish() {
		return wantFish;
	}

	public void setWantFish(int wantFish) {
		this.wantFish = wantFish;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Integer getTracking() {
		return tracking;
	}

	public void setTracking(Integer tracking) {
		this.tracking = tracking;
	}

	public Double getFolate() {
		return folate;
	}

	public void setFolate(Double folate) {
		this.folate = folate;
	}

}
