package com.custardbelly.massdot.model;


public class RouteStop 
{
	private String tag;
	private String title;
	private String directionTag;
	private String latitude;
	private String longitude;
	private String stopId;
	
	public RouteStop( String tag )
	{
		this.tag = tag;
	}
	
	public RouteStop( String tag, String title )
	{
		this.tag = tag;
		this.title = title;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String value) {
		tag = value;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String value) {
		title = value;
	}
	
	public String getDirectionTag() {
		return directionTag;
	}
	public void setDirectionTag(String value) {
		directionTag = value;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String value) {
		latitude = value;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String value) {
		longitude = value;
	}
	
	public String getStopId() {
		return stopId;
	}
	public void setStopId(String value) {
		stopId = value;
	}
}
