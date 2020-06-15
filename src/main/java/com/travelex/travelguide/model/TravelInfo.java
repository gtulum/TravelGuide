package com.travelex.travelguide.model;

import java.util.Map;

public class TravelInfo {
	
	int numberOfRings;
	Map<String,String> budgetforCountry;
	String leftover;
	
	
	public int getNumberOfRings() {
		return numberOfRings;
	}
	public void setNumberOfRings(int numberOfRings) {
		this.numberOfRings = numberOfRings;
	}
	public Map<String, String> getBudgetforCountry() {
		return budgetforCountry;
	}
	public void setBudgetforCountry(Map<String, String> budgetforCountry) {
		this.budgetforCountry = budgetforCountry;
	}
	public String getLeftover() {
		return leftover;
	}
	public void setLeftover(String leftover) {
		this.leftover = leftover;
	}
	
	

}
