package com.custardbelly.massdot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RouteConfig
{
	private String tag;
	private String title;
	private HashMap<String, RouteStop> stops;
	private List<RouteDirection> directions;
	private HashMap<String, List<RouteStop>> directionStopCache;
	
	public RouteConfig()
	{
		initialize();  
	}
	
	public RouteConfig(String tag, String title) 
	{
		this.tag = tag;
		this.title = title;
		
		initialize();
	}
	
	protected void initialize()
	{
		stops = new HashMap<String, RouteStop>();
		directions = new ArrayList<RouteDirection>();
		directionStopCache = new HashMap<String, List<RouteStop>>();
	}
	
	public void addStop( RouteStop stop )
	{
		stops.put( stop.getTag(), stop );
	}
	
	public void addDirection( RouteDirection direction )
	{
		directions.add( direction );
	}
	
	public List<RouteStop> getStopsFromDirection( RouteDirection direction )
	{
		List<RouteStop> directionStops;
		String directionKey = direction.getTag();
		if( !directionStopCache.containsKey( directionKey ) )
		{
			directionStops = new ArrayList<RouteStop>();
			List<RouteStop> stopTagList = direction.getStops();
			Iterator<RouteStop> iterator = stopTagList.iterator();
			RouteStop stopToken;
			RouteStop stop;
			while( iterator.hasNext() )
			{
				stopToken = (RouteStop) iterator.next();
				stop = stops.get( stopToken.getTag() );
				directionStops.add( stop );
			}
			directionStopCache.put( directionKey, directionStops );
		}
		else
		{
			directionStops = directionStopCache.get( directionKey );
		}
		return directionStops;
	}
	
	public HashMap<String, RouteStop> getStops()
	{
		return stops;
	}
	
	public List<RouteDirection> getDirections()
	{
		return directions;
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
}
