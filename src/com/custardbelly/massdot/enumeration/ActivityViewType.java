package com.custardbelly.massdot.enumeration;

public enum ActivityViewType 
{
	ACTIVITY_ROUTE_LIST(0),
	ACTIVITY_ROUTE_DIRECTION(1),
	ACTIVITY_ROUTE_STOPS(2),
	ACTIVITY_ROUTE_PREDICTIONS(3);
	
	private final int id;
	private ActivityViewType( int id )
	{
		this.id = id;
	}
	public int getId()
	{
		return id;
	}
}
