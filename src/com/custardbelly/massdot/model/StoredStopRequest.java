package com.custardbelly.massdot.model;

import java.io.Serializable;

public class StoredStopRequest implements Serializable
{
	public int id;
	public String routeId;
	public String routeTitle;
	public String directionId;
	public String directionTitle;
	public String stopId;
	public String stopTitle;
	
	static final long serialVersionUID = 20100924;
}