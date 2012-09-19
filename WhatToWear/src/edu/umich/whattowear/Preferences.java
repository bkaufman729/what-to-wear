package edu.umich.whattowear;

public class Preferences {
	private static Preferences instance;
	private String tempUnit = "Fahrenheit";
	private int tshirtTemp = 70;
	private int sweatshirtTemp = 60;
	private int jacketTemp = 50;
	private int winterJacketTemp = 40;
	
	private Preferences() {
	}
	
	public static Preferences getInstance() {
		if (instance == null) {
			return new Preferences();
		} else {
			return instance;
		}
	}

	public int getWinterJacketTemp() {
		return winterJacketTemp;
	}

	public void setWinterJacketTemp(int winterJacketTemp) {
		this.winterJacketTemp = winterJacketTemp;
	}

	public int getJacketTemp() {
		return jacketTemp;
	}

	public void setJacketTemp(int jacketTemp) {
		this.jacketTemp = jacketTemp;
	}

	public int getSweatshirtTemp() {
		return sweatshirtTemp;
	}

	public void setSweatshirtTemp(int sweatshirtTemp) {
		this.sweatshirtTemp = sweatshirtTemp;
	}

	public int getTshirtTemp() {
		return tshirtTemp;
	}

	public void setTshirtTemp(int tshirtTemp) {
		this.tshirtTemp = tshirtTemp;
	}

	public String getTempUnit() {
		return tempUnit;
	}

	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}
	
	
}
