package edu.umich.whattowear;

public class Recommender {
	private WeatherReport weatherReport;
	
	public Recommender(WeatherReport report) {
		weatherReport = report;
	}
	
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
}
