package com.travelex.travelguide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.travelex.travelguide.model.CountryInfo;
import com.travelex.travelguide.model.TravelInfo;


@RestController
public class TravelGuideController {

	
	@GetMapping(value = "getTravelingInfo/{countryCode}/{budget}/{totalBudget}/{curr}")
	public TravelInfo getTravelInfo(@PathVariable("countryCode") String countryCode,
			@PathVariable("budget") long budget,
			@PathVariable("totalBudget") long totalBudget,
			@PathVariable("curr") String curr) {

		CountryInfo countryInfo = getCountryInfo(countryCode);
		JSONObject rates = getExchangeCurrency(curr);
		int numberofTravel = 0;
		long budgetForRing = countryInfo.getCountryList().size() * budget;

		while (totalBudget >= budgetForRing) {
			int counter = 0;

			for (String country : countryInfo.getCountryList()) {

				totalBudget = totalBudget - budget;

				counter++;
			}
			if (counter == countryInfo.getCountryList().size()) {
				numberofTravel++;
			}
		}

		Map<String, String> budgetForCountryMap = new HashMap<String, String>();
		for (String country : countryInfo.getCountryList()) {

			CountryInfo ci = getCountryInfo(country);

			try {

				budgetForCountryMap.put(country + "-" + ci.getCurrency(),
						calculateTripCostForCountry(rates, ci.getCurrency(), numberofTravel, budget, curr));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		TravelInfo travelInfo = new TravelInfo();
		travelInfo.setLeftover(totalBudget + "-" + curr);
		travelInfo.setNumberOfRings(numberofTravel);
		travelInfo.setBudgetforCountry(budgetForCountryMap);
		return travelInfo;

	}

	private CountryInfo getCountryInfo(String countryCode) {

		List<String> countryList = new ArrayList<String>();
		CountryInfo countryInfo = new CountryInfo();
		try {

			OkHttpClient client = new OkHttpClient();

			StringBuilder sb = new StringBuilder("https://restcountries-v1.p.rapidapi.com/alpha/");
			sb.append(countryCode);
			Request request = new Request.Builder().url(sb.toString()).get()
					.addHeader("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
					.addHeader("x-rapidapi-key", "037ea14b3amshc4e9f61aa06c349p12705ejsn0492ceddd8eb").build();

			Response response = client.newCall(request).execute();
			String jsonData = response.body().string();
			JSONObject Jobject = new JSONObject(jsonData);
			JSONArray jarray = Jobject.getJSONArray("borders");
			for (int i = 0; i < jarray.length(); i++) {
				Object countrycode = jarray.get(i);
				countryList.add(countrycode.toString());
			}
			countryInfo.setCountryList(countryList);

			JSONArray jarrayCurrencies = Jobject.getJSONArray("currencies");

			if (jarrayCurrencies.length() > 0) {
				countryInfo.setCurrency(jarrayCurrencies.get(0).toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return countryInfo;
	}

	private JSONObject getExchangeCurrency(String baseCurr) {

		try {
			OkHttpClient client = new OkHttpClient();

			StringBuilder sb = new StringBuilder("https://api.exchangeratesapi.io/latest?base=");
			sb.append(baseCurr);
			Request request = new Request.Builder().url(sb.toString()).get().build();

			Response response = client.newCall(request).execute();
			String jsonData = response.body().string();
			JSONObject Jobject = new JSONObject(jsonData);
			return (JSONObject) Jobject.get("rates");

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	private String calculateTripCostForCountry(JSONObject rates, String currency, int numberofTravel, long budget,
			String orgCurr) {
		try {

			return Double.valueOf(rates.get(currency).toString()) * numberofTravel * budget + " " + currency;
		} catch (Exception e) {
		}
		return 1d * numberofTravel * budget + " " + orgCurr;
	}

}
