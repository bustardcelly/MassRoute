package com.custardbelly.massdot.model;

import java.util.ArrayList;
import java.util.List;


public class RouteDirection implements Comparable<RouteDirection> 
{
	private String tag;
	private String title;
	private String name;
	private List<RouteStop> _stops;
	
	public RouteDirection( String tag, String title, String name )
	{
		this.tag = tag;
		this.title = title;
		this.name = name;
		_stops = new ArrayList<RouteStop>();
	}
	
	// RouteStops added are simple RouteStops with just a tag attribute.
	// To get full stops based on the tag attribute use RouteConfig:getStopsForDirection.
	public void addStop( RouteStop stop )
	{
		_stops.add( stop );
	}
	public List<RouteStop> getStops()
	{
		return _stops;
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

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}
	
	public int compareTo( RouteDirection direction )
	{
		int nameCompare = name.compareTo( direction.getName() );
		return ( nameCompare != 0 ? nameCompare : title.compareTo( direction.getTitle() ) );
	}
}
