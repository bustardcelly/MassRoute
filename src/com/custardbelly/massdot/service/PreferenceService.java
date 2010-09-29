package com.custardbelly.massdot.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.custardbelly.massdot.model.MassRouteModel;
import com.custardbelly.massdot.model.StoredStopRequest;
import com.custardbelly.massdot.model.StoredStopRequests;

public class PreferenceService implements IPreferenceService 
{
	private StoredStopRequests _storedRequests;
	static private final String REQUESTS_FILE = "massroute_stop_requests";
	static private IPreferenceService _instance;
	
	private PreferenceService() {}
	
	static public IPreferenceService instance()
	{
		if( _instance == null )
			_instance = new PreferenceService();
		
		return _instance;
	}
	
	protected StoredStopRequests read( Context context )
	{
//		context.deleteFile(PreferenceService.REQUESTS_FILE);
		if( _storedRequests == null )
		{
			_storedRequests = new StoredStopRequests();
			try
			{
				FileInputStream fileInput = context.openFileInput( PreferenceService.REQUESTS_FILE );
				// Read in stored requests.
				ObjectInputStream stream = new ObjectInputStream( fileInput );
				StoredStopRequests storedRequests = (StoredStopRequests) stream.readObject();
				List<StoredStopRequest> list = storedRequests.requests;
				// Add to list.
				int i;
				for( i = 0; i < list.size(); i++ )
				{
					_storedRequests.addRequest( list.get(i) );
				}
				stream.close();
			}
			catch( Exception e )
			{
				Log.d( "PREFERENCE SERVICE", "Read stored request Exception: " + e.getMessage() );
			}
		}
		return _storedRequests;
	}
	
	protected boolean save( Context context )
	{
		boolean success = false;
		try
		{
			FileOutputStream fileOutput = context.openFileOutput( PreferenceService.REQUESTS_FILE, Context.MODE_PRIVATE );
			ObjectOutputStream stream = new ObjectOutputStream( fileOutput );
			stream.writeObject( _storedRequests );
			stream.close();
			success = true;
		}
		catch( Exception e )
		{
			Log.d( "PREFERENCE SERVICE", "Write stored request Exception: " + e.getMessage() );
		}
		return success;
	}
	
	public boolean saveStopRequest( MassRouteModel model, Context context ) 
	{
		boolean success = false;
	
		StoredStopRequest request = new StoredStopRequest();
		request.routeId = model.getSelectedRoute().getTag();
		request.routeTitle = model.getSelectedRoute().getTitle();
		request.directionId = model.getSelectedRouteDirection().getTag();
		request.directionTitle = model.getSelectedRouteDirection().getTitle();
		request.stopId = model.getSelectedRouteStop().getTag();
		request.stopTitle = model.getSelectedRouteStop().getTitle();
		
		StoredStopRequests requests = read( context );
		if( requests != null )
		{
			_storedRequests.addRequest( request );
		}
		else
		{
			_storedRequests = new StoredStopRequests();
			_storedRequests.addRequest( request );
		}
		
		success = save( context );
		if( !success )
		{
			_storedRequests.removeRequest( request );
		}
		return success;
	}
	
	public boolean removeStopRequest( StoredStopRequest request, Context context )
	{
		boolean success = false;
		StoredStopRequests requests = read( context );
		if( requests != null )
		{
			_storedRequests.removeRequest( request );
			success = save( context );
			if( !success )
			{
				_storedRequests.addRequest( request );
			}
		}
		return success;
	}
	
	public List<StoredStopRequest> getStopRequests( Context context )
	{
		StoredStopRequests storedRequests = read( context );
		return ( storedRequests != null ) ? storedRequests.requests : null;
	}
}
