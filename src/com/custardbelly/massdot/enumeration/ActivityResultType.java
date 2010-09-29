package com.custardbelly.massdot.enumeration;

public enum ActivityResultType 
{
	ROUTE_TAG("routeTag"),
	ROUTE_DIRECTION_TAG("routeDirectionTag");
	
	private final String name;
	private ActivityResultType( String name )
	{
		this.name = name;
	}
	public String toString()
	{
		return name;
	}
}
