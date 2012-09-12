package edu.umich.whattowear;

public class Recommender {
	private WeatherReport weatherReport;
	
	public Recommender(WeatherReport report) {
		weatherReport = report;
	}
	
	public String getClothesToWear() {
		if (weatherReport.getFeelLikeCelcius() >= 26.0) {
			return "T-Shirts";
		} else if (weatherReport.getFeelLikeCelcius() >= 15.0) {
			return "Sweater";
		} else if (weatherReport.getFeelLikeCelcius() >= 5.0) {
			return "Jacket";
		} else if (weatherReport.getFeelLikeCelcius() >= -5.0) {
			return "Coat";
		} else {
			return "Too cold!";
		}
	}
}
