package com.custardbelly.massdot.service;

import java.lang.ref.WeakReference;
import java.net.URL;

import android.os.AsyncTask;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.RouteConfig;
import com.custardbelly.massdot.parser.IRouteConfigParser;
import com.custardbelly.massdot.parser.RouteConfigParser;
import com.custardbelly.massdot.service.responder.IRoutesConfigServiceResponder;

public class RouteConfigTask extends AsyncTask<URL, Integer, Long> 
{
	private RouteConfig _routeConfig;
	private String _errorMessage;
	private WeakReference<IRoutesConfigServiceResponder> _responder;
	
	public RouteConfigTask( IRoutesConfigServiceResponder responder )
	{
		_responder = new WeakReference<IRoutesConfigServiceResponder>( responder );
	}
	
	@Override
	protected Long doInBackground(URL... urls) 
	{
		URL serviceUrl = urls[0];
		final IRouteConfigParser  parser= new RouteConfigParser( serviceUrl );
		try
		{
			_routeConfig = parser.parse();
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
		IRoutesConfigServiceResponder serviceResponder = _responder.get();
		if( _routeConfig != null )
		{
			serviceResponder.handleServiceResult( _routeConfig );
		}
		else
		{
			// TODO: pull default message from resource.
			String message = ( _errorMessage != null ) ? _errorMessage : "Could not retrieve routes configuration.\nPlease try again at a later time."; 
			serviceResponder.handleServiceFault( message );
		}
	}
	
	public IRoutesConfigServiceResponder getResponder()
	{
		return _responder.get();
	}
	public void setResponder( IRoutesConfigServiceResponder responder )
	{
		_responder = new WeakReference<IRoutesConfigServiceResponder>( responder );
	}
}
