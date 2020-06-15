package com.travelex.travelguide.model;

import java.util.List;

public class CountryInfo {
	List<String> countryList;
	String currency;
	
	public List<String> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

}
