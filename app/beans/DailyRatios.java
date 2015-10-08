package beans;

public class DailyRatios {
	double alphaCarot=0;
	int alphaCarotDisplayed;
	double betaCarot = 0;
	int betaCarotDisplayed;
	double crypto=0;
	int cryptoDisplayed;
	double lycopene = 0;
	int lycopeneDisplayed;
	double luzea = 0;
	int luzeaDisplayed;
	double vitamina;
	double vitaminaNeeded=0;
	int vitaminaDisplayed=0;
	double niacin;
	double niacinNeeded=0;
	int niacinDisplayed=0;;
	double b6;
	double b6Needed=0;
	int b6Displayed=0;
	double b12;
	double b12Needed=0;
	int b12Displayed=0;
	double pantothenic;
	double pantothenicNeeded=0;
	int pantothenicDisplayed=0;
	double potassium;
	double potassiumNeeded=0;
	int potassiumDisplayed=0;
	double calcium;
	double calciumNeeded=0;
	int calciumDisplayed=0;
	double phosphorus;
	double phosphorusNeeded=0;
	int phosphorusDisplayed=0;
	double magnesium;
	double magnesiumNeeded=0;
	int magnesiumDisplayed=0;
	double iron;
	double ironNeeded=0;
	int ironDisplayed=0;
	double zinc;
	double zincNeeded=0;
	int zincDisplayed=0;
	double copper;
	double copperNeeded=0;
	int copperDisplayed=0;
	double maganese;
	double maganeseNeeded=0;
	int maganeseDisplayed=0;
	double selenium;
	double seleniumNeeded=0;
	int seleniumDisplayed=0;
	double vitamine;
	double vitamineNeeded=0;
	int vitamineDisplayed=0;
	double vitamink;
	double vitaminkNeeded=0;
	int vitaminkDisplayed=0;
	double monosaturated=0;
	double polysaturated;
	double choline=0;
	double cholineNeeded=0;
	public double getCholineNeeded() {
		return cholineNeeded;
	}
	int cholineDisplayed=0;
	public void setCholineNeeded(double cholineNeeded) {
		this.cholineNeeded = cholineNeeded;
	}
	public double getCholine() {
		return choline;
	}
	public void setCholine(double choline) {
		this.choline = choline;
	}
	double cholesterol = 0;
	public double getCholesterol() {
		return cholesterol;
	}
	public void setCholesterol(double cholesterol) {
		this.cholesterol = cholesterol;
	}
	int cholesterolDisplayed=0;
	public double getPolysaturated() {
		return polysaturated;
	}
	public void setPolysaturated(double polysaturated) {
		this.polysaturated = polysaturated;
	}
	public double getVitamine() {
		return Math.round(100*vitamine/100D);
	}
	public void setVitamine(double vitamine) {
		this.vitamine = vitamine;
	}
	public double getVitamink() {
		return Math.round(100*vitamink/100D);
	}
	public void setVitamink(double vitamink) {
		this.vitamink = vitamink;
	}
	double vitamind;
	double vitamindNeeded;
	int vitamindDisplayed=0;
	public double getVitamind() {
		return Math.round(100*vitamind/100D);
	}
	public void setVitamind(double vitamind) {
		this.vitamind = vitamind;
	}
	double vitaminc;
	double vitamincNeeded;
	int vitamincDisplayed=0;
	double folate;
	double folateNeeded;
	int folateDisplayed=0;
	public double getFolate() {
		return Math.round(100*folate/100D);
	}
	public double getVitaminc() {
		return Math.round(100*vitaminc/100D);
	}
	public void setVitaminc(double vitaminc) {
		this.vitaminc = vitaminc;
	}
	public void setFolate(double folate) {
		this.folate = folate;
	}
	double riboflavin;
	double riboflavinNeeded;
	int riboflavinDisplayed=0;
	double thiamin;
	double thiaminNeeded;
	int thiaminDisplayed=0;
	public double getThiamin() {
		return Math.round(100*thiamin/100D);
	}
	public double getRiboflavin() {
		return Math.round(100*riboflavin/100D);
	}
	public void setRiboflavin(double riboflavin) {
		this.riboflavin = riboflavin;
	}
	public void setThiamin(double thiamin) {
		this.thiamin = thiamin;
	}
	double fiber;
	double fiberNeeded;
	int fiberDisplayed=0;
	public double getFiber() {
		return Math.round(fiber*100/100D);
	}
	public void setFiber(double fiber) {
		this.fiber = fiber;
	}
	public double getMonosaturated() {
		return monosaturated;
	}
	public void setMonosaturated(double monosaturated) {
		this.monosaturated = monosaturated;
	}
	double weight;
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVitamina() {
		return vitamina;
	}
	public void setVitamina(double vitamina) {
		this.vitamina = vitamina;
	}
	public double getNiacin() {
		return niacin;
	}
	public void setNiacin(double niacin) {
		this.niacin = niacin;
	}
	public double getVitamindNeeded() {
		return vitamindNeeded;
	}
	public void setVitamindNeeded(double vitamindNeeded) {
		this.vitamindNeeded = vitamindNeeded;
	}
	public double getB6() {
		return b6;
	}
	public void setB6(double b6) {
		this.b6 = b6;
	}
	public double getB12() {
		return b12;
	}
	public void setB12(double b12) {
		this.b12 = b12;
	}
	public double getPantothenic() {
		return pantothenic;
	}
	public void setPantothenic(double pantothenic) {
		this.pantothenic = pantothenic;
	}
	public double getPotassium() {
		return potassium;
	}
	public void setPotassium(double potassium) {
		this.potassium = potassium;
	}
	public double getCalcium() {
		return calcium;
	}
	public void setCalcium(double calcium) {
		this.calcium = calcium;
	}
	public double getPhosphorus() {
		return phosphorus;
	}
	public void setPhosphorus(double phosphorus) {
		this.phosphorus = phosphorus;
	}
	public double getMagnesium() {
		return magnesium;
	}
	public void setMagnesium(double magnesium) {
		this.magnesium = magnesium;
	}
	public double getIron() {
		return iron;
	}
	public void setIron(double iron) {
		this.iron = iron;
	}
	public double getZinc() {
		return zinc;
	}
	public void setZinc(double zinc) {
		this.zinc = zinc;
	}
	public double getCopper() {
		return copper;
	}
	public void setCopper(double copper) {
		this.copper = copper;
	}
	public double getVitamincNeeded() {
		return vitamincNeeded;
	}
	public void setVitamincNeeded(double vitamincNeeded) {
		this.vitamincNeeded = vitamincNeeded;
	}
	public double getFolateNeeded() {
		return folateNeeded;
	}
	public void setFolateNeeded(double folateNeeded) {
		this.folateNeeded = folateNeeded;
	}
	public double getRiboflavinNeeded() {
		return riboflavinNeeded;
	}
	public void setRiboflavinNeeded(double riboflavinNeeded) {
		this.riboflavinNeeded = riboflavinNeeded;
	}
	public double getThiaminNeeded() {
		return thiaminNeeded;
	}
	public void setThiaminNeeded(double thiaminNeeded) {
		this.thiaminNeeded = thiaminNeeded;
	}
	public double getFiberNeeded() {
		return fiberNeeded;
	}
	public void setFiberNeeded(double fiberNeeded) {
		this.fiberNeeded = fiberNeeded;
	}
	public double getMaganese() {
		return maganese;
	}
	public void setMaganese(double maganese) {
		this.maganese = maganese;
	}
	public double getSelenium() {
		return selenium;
	}
	public void setSelenium(double selenium) {
		this.selenium = selenium;
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}
	public double getProteins() {
		return Math.round(100*proteins/100D);
	}
	public void setProteins(double proteins) {
		this.proteins = proteins;
	}
	public double getHydros() {
		return Math.round(100*hydros/100D);
	}
	public void setHydros(double hydros) {
		this.hydros = hydros;
	}
	public double getFats() {
		return Math.round(100*fats/100D);
	}
	public void setFats(double fats) {
		this.fats = fats;
	}
	public double getSaturated() {
		return Math.round(100*saturated/100D);
	}
	public void setSaturated(double saturated) {
		this.saturated = saturated;
	}
	public double getSugars() {
		return Math.round(100*sugars/100D);
	}
	public void setSugars(double sugars) {
		this.sugars = sugars;
	}
	public double getSalt() {
		return Math.round(salt*100)/100D;
	}
	public void setSalt(double salt) {
		this.salt = salt;
	}
	int calories = 0;
	double caloriesNeeded=0;
	double proteins = 0;
	double proteinsNeeded=0;
	double hydros = 0;
	double hydrosNeeded=0;
	double fats = 0;
	double fatsNeeded=0;
	double saturated = 0;
	double saturatedNeeded=0;
	double sugars = 0;
	double sugarsNeeded=0;
	double salt = 0;
	double saltNeeded=0;
	int saltDisplayed=0;
	@Override
	public String toString() {
		return "{\"calories\":"+calories+",\"fats\":"+fats+",\"hydros\":"+hydros+",\"proteins\":"+proteins+","
				+ "\"saturated\":"+saturated+",\"salt\":"+salt+",\"sugars\":"+sugars+"}";
	}
	public double getVitaminaNeeded() {
		return vitaminaNeeded;
	}
	public void setVitaminaNeeded(double vitaminaNeeded) {
		this.vitaminaNeeded = vitaminaNeeded;
	}
	public double getNiacinNeeded() {
		return niacinNeeded;
	}
	public void setNiacinNeeded(double niacinNeeded) {
		this.niacinNeeded = niacinNeeded;
	}
	public double getB6Needed() {
		return b6Needed;
	}
	public void setB6Needed(double b6Needed) {
		this.b6Needed = b6Needed;
	}
	public double getB12Needed() {
		return b12Needed;
	}
	public void setB12Needed(double b12Needed) {
		this.b12Needed = b12Needed;
	}
	public double getPantothenicNeeded() {
		return pantothenicNeeded;
	}
	public void setPantothenicNeeded(double pantothenicNeeded) {
		this.pantothenicNeeded = pantothenicNeeded;
	}
	public double getPotassiumNeeded() {
		return potassiumNeeded;
	}
	public void setPotassiumNeeded(double potassiumNeeded) {
		this.potassiumNeeded = potassiumNeeded;
	}
	public double getCalciumNeeded() {
		return calciumNeeded;
	}
	public void setCalciumNeeded(double calciumNeeded) {
		this.calciumNeeded = calciumNeeded;
	}
	public double getPhosphorusNeeded() {
		return phosphorusNeeded;
	}
	public void setPhosphorusNeeded(double phosphorusNeeded) {
		this.phosphorusNeeded = phosphorusNeeded;
	}
	public double getMagnesiumNeeded() {
		return magnesiumNeeded;
	}
	public void setMagnesiumNeeded(double magnesiumNeeded) {
		this.magnesiumNeeded = magnesiumNeeded;
	}
	public double getIronNeeded() {
		return ironNeeded;
	}
	public void setIronNeeded(double ironNeeded) {
		this.ironNeeded = ironNeeded;
	}
	public double getZincNeeded() {
		return zincNeeded;
	}
	public void setZincNeeded(double zincNeeded) {
		this.zincNeeded = zincNeeded;
	}
	public double getCopperNeeded() {
		return copperNeeded;
	}
	public void setCopperNeeded(double copperNeeded) {
		this.copperNeeded = copperNeeded;
	}
	public double getMaganeseNeeded() {
		return maganeseNeeded;
	}
	public void setMaganeseNeeded(double maganeseNeeded) {
		this.maganeseNeeded = maganeseNeeded;
	}
	public double getSeleniumNeeded() {
		return seleniumNeeded;
	}
	public void setSeleniumNeeded(double seleniumNeeded) {
		this.seleniumNeeded = seleniumNeeded;
	}
	public double getVitamineNeeded() {
		return vitamineNeeded;
	}
	public void setVitamineNeeded(double vitamineNeeded) {
		this.vitamineNeeded = vitamineNeeded;
	}
	public double getVitaminkNeeded() {
		return vitaminkNeeded;
	}
	public void setVitaminkNeeded(double vitaminkNeeded) {
		this.vitaminkNeeded = vitaminkNeeded;
	}
	public double getCaloriesNeeded() {
		return caloriesNeeded;
	}
	public void setCaloriesNeeded(double caloriesNeeded) {
		this.caloriesNeeded = caloriesNeeded;
	}
	public double getProteinsNeeded() {
		return proteinsNeeded;
	}
	public void setProteinsNeeded(double proteinsNeeded) {
		this.proteinsNeeded = proteinsNeeded;
	}
	public double getHydrosNeeded() {
		return hydrosNeeded;
	}
	public void setHydrosNeeded(double hydrosNeeded) {
		this.hydrosNeeded = hydrosNeeded;
	}
	public double getFatsNeeded() {
		return fatsNeeded;
	}
	public void setFatsNeeded(double fatsNeeded) {
		this.fatsNeeded = fatsNeeded;
	}
	public double getSaturatedNeeded() {
		return saturatedNeeded;
	}
	public void setSaturatedNeeded(double saturatedNeeded) {
		this.saturatedNeeded = saturatedNeeded;
	}
	public double getSugarsNeeded() {
		return sugarsNeeded;
	}
	public void setSugarsNeeded(double sugarsNeeded) {
		this.sugarsNeeded = sugarsNeeded;
	}
	public double getSaltNeeded() {
		return saltNeeded;
	}
	public void setSaltNeeded(double saltNeeded) {
		this.saltNeeded = saltNeeded;
	}
	public int getVitaminaDisplayed() {
		return vitaminaDisplayed;
	}
	public void setVitaminaDisplayed(int vitaminaDisplayed) {
		this.vitaminaDisplayed = vitaminaDisplayed;
	}
	public int getNiacinDisplayed() {
		return niacinDisplayed;
	}
	public void setNiacinDisplayed(int niacinDisplayed) {
		this.niacinDisplayed = niacinDisplayed;
	}
	public int getB6Displayed() {
		return b6Displayed;
	}
	public void setB6Displayed(int b6Displayed) {
		this.b6Displayed = b6Displayed;
	}
	public int getB12Displayed() {
		return b12Displayed;
	}
	public void setB12Displayed(int b12Displayed) {
		this.b12Displayed = b12Displayed;
	}
	public int getPantothenicDisplayed() {
		return pantothenicDisplayed;
	}
	public void setPantothenicDisplayed(int pantothenicDisplayed) {
		this.pantothenicDisplayed = pantothenicDisplayed;
	}
	public int getPotassiumDisplayed() {
		return potassiumDisplayed;
	}
	public void setPotassiumDisplayed(int potassiumDisplayed) {
		this.potassiumDisplayed = potassiumDisplayed;
	}
	public int getCalciumDisplayed() {
		return calciumDisplayed;
	}
	public void setCalciumDisplayed(int calciumDisplayed) {
		this.calciumDisplayed = calciumDisplayed;
	}
	public int getPhosphorusDisplayed() {
		return phosphorusDisplayed;
	}
	public void setPhosphorusDisplayed(int phosphorusDisplayed) {
		this.phosphorusDisplayed = phosphorusDisplayed;
	}
	public int getMagnesiumDisplayed() {
		return magnesiumDisplayed;
	}
	public void setMagnesiumDisplayed(int magnesiumDisplayed) {
		this.magnesiumDisplayed = magnesiumDisplayed;
	}
	public int getIronDisplayed() {
		return ironDisplayed;
	}
	public void setIronDisplayed(int ironDisplayed) {
		this.ironDisplayed = ironDisplayed;
	}
	public int getZincDisplayed() {
		return zincDisplayed;
	}
	public void setZincDisplayed(int zincDisplayed) {
		this.zincDisplayed = zincDisplayed;
	}
	public int getCopperDisplayed() {
		return copperDisplayed;
	}
	public void setCopperDisplayed(int copperDisplayed) {
		this.copperDisplayed = copperDisplayed;
	}
	public int getMaganeseDisplayed() {
		return maganeseDisplayed;
	}
	public void setMaganeseDisplayed(int maganeseDisplayed) {
		this.maganeseDisplayed = maganeseDisplayed;
	}
	public int getSeleniumDisplayed() {
		return seleniumDisplayed;
	}
	public void setSeleniumDisplayed(int seleniumDisplayed) {
		this.seleniumDisplayed = seleniumDisplayed;
	}
	public int getVitamineDisplayed() {
		return vitamineDisplayed;
	}
	public void setVitamineDisplayed(int vitamineDisplayed) {
		this.vitamineDisplayed = vitamineDisplayed;
	}
	public int getVitaminkDisplayed() {
		return vitaminkDisplayed;
	}
	public void setVitaminkDisplayed(int vitaminkDisplayed) {
		this.vitaminkDisplayed = vitaminkDisplayed;
	}
	public int getCholineDisplayed() {
		return cholineDisplayed;
	}
	public void setCholineDisplayed(int cholineDisplayed) {
		this.cholineDisplayed = cholineDisplayed;
	}
	public int getCholesterolDisplayed() {
		return cholesterolDisplayed;
	}
	public void setCholesterolDisplayed(int cholesterolDisplayed) {
		this.cholesterolDisplayed = cholesterolDisplayed;
	}
	public int getVitamindDisplayed() {
		return vitamindDisplayed;
	}
	public void setVitamindDisplayed(int vitamindDisplayed) {
		this.vitamindDisplayed = vitamindDisplayed;
	}
	public int getVitamincDisplayed() {
		return vitamincDisplayed;
	}
	public void setVitamincDisplayed(int vitamincDisplayed) {
		this.vitamincDisplayed = vitamincDisplayed;
	}
	public int getFolateDisplayed() {
		return folateDisplayed;
	}
	public void setFolateDisplayed(int folateDisplayed) {
		this.folateDisplayed = folateDisplayed;
	}
	public int getRiboflavinDisplayed() {
		return riboflavinDisplayed;
	}
	public void setRiboflavinDisplayed(int riboflavinDisplayed) {
		this.riboflavinDisplayed = riboflavinDisplayed;
	}
	public int getThiaminDisplayed() {
		return thiaminDisplayed;
	}
	public void setThiaminDisplayed(int thiaminDisplayed) {
		this.thiaminDisplayed = thiaminDisplayed;
	}
	public int getFiberDisplayed() {
		return fiberDisplayed;
	}
	public void setFiberDisplayed(int fiberDisplayed) {
		this.fiberDisplayed = fiberDisplayed;
	}
	public int getSaltDisplayed() {
		return saltDisplayed;
	}
	public void setSaltDisplayed(int saltDisplayed) {
		this.saltDisplayed = saltDisplayed;
	}
	public double getAlphaCarot() {
		return alphaCarot;
	}
	public void setAlphaCarot(double alphaCarot) {
		this.alphaCarot = alphaCarot;
	}
	public int getAlphaCarotDisplayed() {
		return alphaCarotDisplayed;
	}
	public void setAlphaCarotDisplayed(int alphaCarotDisplayed) {
		this.alphaCarotDisplayed = alphaCarotDisplayed;
	}
	public double getBetaCarot() {
		return betaCarot;
	}
	public void setBetaCarot(double betaCarot) {
		this.betaCarot = betaCarot;
	}
	public int getBetaCarotDisplayed() {
		return betaCarotDisplayed;
	}
	public void setBetaCarotDisplayed(int betaCarotDisplayed) {
		this.betaCarotDisplayed = betaCarotDisplayed;
	}
	public double getCrypto() {
		return crypto;
	}
	public void setCrypto(double crypto) {
		this.crypto = crypto;
	}
	public int getCryptoDisplayed() {
		return cryptoDisplayed;
	}
	public void setCryptoDisplayed(int cryptoDisplayed) {
		this.cryptoDisplayed = cryptoDisplayed;
	}
	public double getLycopene() {
		return lycopene;
	}
	public void setLycopene(double lycopene) {
		this.lycopene = lycopene;
	}
	public int getLycopeneDisplayed() {
		return lycopeneDisplayed;
	}
	public void setLycopeneDisplayed(int lycopeneDisplayed) {
		this.lycopeneDisplayed = lycopeneDisplayed;
	}
	public double getLuzea() {
		return luzea;
	}
	public void setLuzea(double luzea) {
		this.luzea = luzea;
	}
	public int getLuzeaDisplayed() {
		return luzeaDisplayed;
	}
	public void setLuzeaDisplayed(int luzeaDisplayed) {
		this.luzeaDisplayed = luzeaDisplayed;
	}
}
