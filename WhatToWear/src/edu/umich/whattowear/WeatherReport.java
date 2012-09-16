
package edu.umich.whattowear;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherReport {
	private JSONObject weatherReport;
	
	public WeatherReport(String json) {
		try {
			weatherReport = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getFeelLikeCelcius() {
		double degreeCelcius = 0.0;
		try {
			degreeCelcius = weatherReport.getJSONObject("current_observation").getDouble("feelslike_c");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return degreeCelcius;
	}
	
	
	public double getCelcius() {
		double degreeCelcius = 0.0;
		try {
			degreeCelcius = weatherReport.getJSONObject("current_observation").getDouble("temp_c");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return degreeCelcius;
	}
	
	public double getFeelLikeFahrenheit() {
		double degreeFahrenheit = 0.0;
		try {
			degreeFahrenheit = weatherReport.getJSONObject("current_observation").getDouble("feelslike_f");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return degreeFahrenheit;
	}
	
	
	public double getFahrenheit() {
		double degreeFahrenheit = 0.0;
		try {
			degreeFahrenheit = weatherReport.getJSONObject("current_observation").getDouble("temp_f");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return degreeFahrenheit;
	}
	
	public String getCondition() {
		String condition="Clear";
		try {
			condition = weatherReport.getJSONObject("current_observation").getString("weather");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return condition;
	}
	
	public String getCity() {
		String city = "";
		try {
			city = weatherReport.getJSONObject("current_observation").getJSONObject("display_location").getString("city");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return city;
	}
	
	public String getTempString() {
		String tempString = "";
		try {
			tempString = weatherReport.getJSONObject("current_observation").getString("temperature_string");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempString;
	}
	
	public String getFeelslikeString() {
		String feelslikeString = "";
		try {
			feelslikeString = weatherReport.getJSONObject("current_observation").getString("feelslike_string");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feelslikeString;
	}
	
}
