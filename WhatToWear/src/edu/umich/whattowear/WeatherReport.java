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
}
