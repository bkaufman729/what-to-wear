
package edu.umich.whattowear;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherReport {
	private JSONObject weatherReport;
	private String location;
	
	public WeatherReport(String json, String loc) {
		try {
			weatherReport = new JSONObject(json);
			location = loc;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getCondition(int hoursLater) {
		String condition="Clear";
		try {
			JSONArray forecasts = weatherReport.getJSONArray("hourly_forecast");
			condition = forecasts.getJSONObject(2 + hoursLater).getString("condition");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return condition;
	}
	
	public String getCity() {
		return location;
	}
	
	public double getTemp(int hoursLater) {
		double currentTemp = 0.0;
		try {
			JSONArray forecasts = weatherReport.getJSONArray("hourly_forecast");
			currentTemp = forecasts.getJSONObject(2 + hoursLater).getJSONObject("temp").getDouble("english");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentTemp;
	}
	
	public String getTempString(int hoursLater) {
		String tempString = "";
		if (hoursLater == 0) {
			tempString = "Current temperature: ";
		} else if (hoursLater == 3) {
			tempString = "Temperature after 3 hours: ";
		} else if (hoursLater == 24) {
			tempString = "Temperature tomorrow: ";
		}
		tempString += String.valueOf(getTemp(hoursLater)) + " F";
		return tempString;
	}
}
