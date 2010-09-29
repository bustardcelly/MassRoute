package com.custardbelly.massdot.service;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import android.os.AsyncTask;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.Route;
import com.custardbelly.massdot.parser.IRouteParser;
import com.custardbelly.massdot.parser.RouteParser;
import com.custardbelly.massdot.service.responder.IRoutesServiceResponder;

public class RouteListTask extends AsyncTask<URL, Integer, Long> 
{
	private List<Route> _routes;
	private String _errorMessage;
	private WeakReference<IRoutesServiceResponder> _responder;
	
	public RouteListTask( IRoutesServiceResponder responder )
	{
		_responder = new WeakReference<IRoutesServiceResponder>( responder );
	}
	
	@Override
	protected Long doInBackground(URL... urls) {
		URL serviceUrl = urls[0];
		final IRouteParser parser = new RouteParser( serviceUrl );
		try
		{
			_routes = parser.parse();
		}
		catch( MassRouteParserException e )
		{
			_errorMessage = e.getMessage();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute( Long result )
	{
		IRoutesServiceResponder serviceResponder = _responder.get();
		if( _routes != null )
		{
			serviceResponder.handleServiceResult( _routes );
		}
		else
		{
			// TODO: pull default message from resource.
			String message = ( _errorMessage != null ) ? _errorMessage : "Could not retrieve routes available.\nPlease try again at a later time."; 
			serviceResponder.handleServiceFault( message );
		}
		_routes = null;
		_errorMessage = null;
	}
	
	public IRoutesServiceResponder getResponder()
	{
		return _responder.get();
	}
	public void setResponder( IRoutesServiceResponder responder )
	{
		_responder = new WeakReference<IRoutesServiceResponder>( responder );
	}
}
