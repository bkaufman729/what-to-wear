package edu.umich.whattowear;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Recommender {
	private WeatherReport weatherReport;
	private SharedPreferences preferences;
	public Recommender(WeatherReport report, Context context) {
		weatherReport = report;
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	//Fahrenheit numbers
	//1-5 No rain, Greater then 6 Rain.  Should probably switch to 
	//better system then numbers
	public int getClothesToWear(int hoursLater) {
		String condition=weatherReport.getCondition(hoursLater);
		if(condition.indexOf("Rain")>=0 || condition.indexOf("Thunderstorm") >= 0) {
			condition="Rain";
		}
		
		Log.d(MainActivity.TAG, condition);
		if(weatherReport.getTemp(hoursLater) >= Integer.valueOf(preferences.getString("ttext", "70")))
		{
			//Tshirt and Shorts
			
			if(condition=="Rain")
				return 6;
			else
				return 1;
			
		}
		else if(weatherReport.getTemp(hoursLater) >= Integer.valueOf(preferences.getString("sweattext", "60")))
		{
			//Longsleeve pants
			if(condition=="Rain")
				return 7;
			else
				return 2;
		}
		else if(weatherReport.getTemp(hoursLater) >= Integer.valueOf(preferences.getString("jackettext", "50")))
		{
			//Sweatshirt pants
			return 3;
		}
		else if(weatherReport.getTemp(hoursLater) >= Integer.valueOf(preferences.getString("wintertext", "40")))
		{
			//Jacket pants
			if(condition=="Rain")
				return 8;
			else
				return 4;
		}
		else
		{
			//Jacket Pants Hat Shoes
			if(condition=="Snow")
				return 9;
			else
				return 5;
		}

	}
	
	
	
	/*
	public int getClothesToWear() {
		if (weatherReport.getFeelLikeCelcius() >= 26.0) {
			return 1;
		} else if (weatherReport.getFeelLikeCelcius() >= 15.0) {
			return 2;
		} else if (weatherReport.getFeelLikeCelcius() >= 5.0) {
			return 3;
		} else if (weatherReport.getFeelLikeCelcius() >= -5.0) {
			return 4;
		} else {
			return 5;
		}
	}
	*/
}
