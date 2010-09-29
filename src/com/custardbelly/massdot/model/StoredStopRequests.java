package com.custardbelly.massdot.model;

import java.io.Serializable;
import java.util.ArrayList;

public class StoredStopRequests implements Serializable
{
	public ArrayList<StoredStopRequest> requests;
	
	static final long serialVersionUID = 20100925;
	
	public StoredStopRequests()
	{
		requests = new ArrayList<StoredStopRequest>();
	}
	
	public void addRequest( StoredStopRequest request )
	{
		requests.add( request );
	}
	public void removeRequest( StoredStopRequest request )
	{
		requests.remove( request );
	}
}
