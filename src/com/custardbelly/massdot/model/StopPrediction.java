package com.custardbelly.massdot.model;

public class StopPrediction 
{
	private String tag;
	private String title;
	private int minutes;
	private int seconds;
	private String vehicle;
	private String block;
	
	public StopPrediction( String tag, String title ) 
	{
		this.tag = tag;
		this.title = title;
	}

	public String getTag() {
		return tag;
	}
	public void setTag( String value ) 
	{
		tag = value;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String value) {
		title = value;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int value) {
		minutes = value;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int value) {
		seconds = value;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String value) {
		vehicle = value;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String value) {
		block = value;
	}
}
