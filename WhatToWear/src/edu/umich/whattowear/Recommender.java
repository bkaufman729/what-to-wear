package edu.umich.whattowear;

public class Recommender {
	private WeatherReport weatherReport;
	
	public Recommender(WeatherReport report) {
		weatherReport = report;
	}
	
	//Fahrenheit numbers
	//1-5 No rain, Greater then 6 Rain.  Should probably switch to 
	//better system then numbers
	public int getClothesToWear(int hoursLater) {
		
		String condition=weatherReport.getCondition(hoursLater);
		if(condition.indexOf("Rain")>=0)
			condition="Rain";
		
		if(weatherReport.getTemp(hoursLater) >= 70)
		{
			//Tshirt and Shorts
			
			if(condition=="Rain")
				return 6;
			else
				return 1;
			
		}
		else if(weatherReport.getTemp(hoursLater) >= 60)
		{
			//Longsleeve pants
			if(condition=="Rain")
				return 7;
			else
				return 2;
		}
		else if(weatherReport.getTemp(hoursLater) >= 50)
		{
			//Sweatshirt pants
			return 3;
		}
		else if(weatherReport.getTemp(hoursLater) >= 40)
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
	
	public String getTempString(int hoursLater) {
		String tempString = "";
		if (hoursLater == 0) {
			tempString = "Current temperature: ";
		} else if (hoursLater == 3) {
			tempString = "Temperature after 3 hours: ";
		} else if (hoursLater == 24) {
			tempString = "Temperature tomorrow: ";
		}
		tempString += String.valueOf(weatherReport.getTemp(hoursLater));
		return tempString;
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
