package beans;

import java.util.Date;

public class DiaryEntry {
	String dayperiod;
	String ingredient;
	String amount;
	String metric;
	Long joinId;
	Long ingredientId;
	int metricId;
	
	public String getDayperiod() {
		return dayperiod;
	}
	public void setDayperiod(String dayperiod) {
		this.dayperiod = dayperiod;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public Long getJoinId() {
		return joinId;
	}
	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}
	public Long getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}
	public int getMetricId() {
		return metricId;
	}
	public void setMetricId(int metricId) {
		this.metricId = metricId;
	}
}
