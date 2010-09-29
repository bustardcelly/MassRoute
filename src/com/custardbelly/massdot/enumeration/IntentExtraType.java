package com.custardbelly.massdot.enumeration;

public enum IntentExtraType 
{
	MODEL("model"),
	ROUTE_ID("routeId"),
	ROUTE_TITLE("routeTitle"),
	STOP_ID("stopId"),
	STOP_TITLE("stopTitle"),
	DIRECTION_ID("directionId"),
	DIRECTION_TITLE("directionTitle");
	
	private final String name;
	private IntentExtraType( String name )
	{
		this.name = name;
	}
	public String toString()
	{
		return name;
	}
}
