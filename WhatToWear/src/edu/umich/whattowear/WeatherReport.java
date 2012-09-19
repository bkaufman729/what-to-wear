
package edu.umich.whattowear;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class WeatherReport {
	private JSONObject currentObservation;
	private SharedPreferences preferences;
	private JSONObject weatherReport;
	public WeatherReport(String json, JSONObject observation, Context context) {
		try {
			weatherReport = new JSONObject(json);
			preferences = PreferenceManager.getDefaultSharedPreferences(context);
			currentObservation = observation;
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public String getCity() {
		String city = "";
		try {
			city = currentObservation.getJSONObject("display_location").getString("city");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return city;
	}

	public String getCondition(int hoursLater) {
		String condition="Clear";
		if (hoursLater == 0) {
			return getCurrentCondition();
		}
		try {
			JSONArray forecasts = weatherReport.getJSONArray("hourly_forecast");
			condition = forecasts.getJSONObject(hoursLater).getString("condition");
		} catch (JSONException e) {

			e.printStackTrace();
		} 
		return condition;
	}

	public String getCurrentCondition() {
		String currentCondition = "Clear";
		try {
			currentCondition = currentObservation.getString("weather");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return currentCondition;
	}

	public double getCurrentTemp() {
		double currentTemp = 0.0;
		try {
			if (preferences.getString("unit", "Fahrenheit").equals("Fahrenheit")) {
				currentTemp = Double.valueOf(currentObservation.getString("temp_f"));
			} else {
				currentTemp = Double.valueOf(currentObservation.getString("temp_c"));
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return currentTemp;
	}

	public double getTemp(int hoursLater) {
		double currentTemp = 0.0;
		if (hoursLater == 0) {
			return getCurrentTemp();
		}
		try {
			JSONArray forecasts = weatherReport.getJSONArray("hourly_forecast");
			if (preferences.getString("unit", "Fahrenheit").equals("Fahrenheit")) {
				currentTemp = forecasts.getJSONObject(hoursLater).getJSONObject("temp").getDouble("english");
			} else {
				currentTemp = forecasts.getJSONObject(hoursLater).getJSONObject("temp").getDouble("metric");
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return currentTemp;
	}
	public String getTempString(int hoursLater) {
		String tempString = "";
		if (hoursLater == 0) {
			tempString = "Current temperature: " + String.valueOf(getCurrentTemp()) + " " + preferences.getString("unit", "Fahrenheit");
			return tempString;
		} else if (hoursLater == 2) {
			tempString = "Temperature after 3 hours: ";
		} else if (hoursLater == 23) {
			tempString = "Temperature tomorrow: ";
		}

		tempString += String.valueOf(getTemp(hoursLater)) + " " + preferences.getString("unit", "Fahrenheit");
		return tempString;
	}
}
