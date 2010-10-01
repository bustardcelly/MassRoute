package com.custardbelly.massdot.service;

import java.lang.ref.WeakReference;
import java.net.URL;

import android.os.AsyncTask;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.RouteConfig;
import com.custardbelly.massdot.parser.IRouteConfigParser;
import com.custardbelly.massdot.parser.RouteConfigParser;
import com.custardbelly.massdot.service.responder.IRoutesConfigServiceResponder;

public class RouteConfigTask extends AsyncTask<URL, Integer, Long>  implements IQueueableTask
{
	private RouteConfig _routeConfig;
	private String _errorMessage;
	private WeakReference<IRoutesConfigServiceResponder> _responder;
	private WeakReference<IQueueableTaskResponder> _queuedResponder;
	
	public RouteConfigTask( IRoutesConfigServiceResponder responder )
	{
		_responder = new WeakReference<IRoutesConfigServiceResponder>( responder );
	}
	
	public RouteConfigTask( IRoutesConfigServiceResponder responder, IQueueableTaskResponder queuedResponder )
	{
		_responder = new WeakReference<IRoutesConfigServiceResponder>( responder );
		_queuedResponder = new WeakReference<IQueueableTaskResponder>( queuedResponder );
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
		final IRoutesConfigServiceResponder serviceResponder = getResponder();
		final IQueueableTaskResponder taskResponder = getQueueableResponder();
		if( _routeConfig != null )
		{
			serviceResponder.handleServiceResult( _routeConfig );
			if( taskResponder != null ) taskResponder.handleQueueableTaskResult();
		}
		else
		{
			// TODO: pull default message from resource.
			String message = ( _errorMessage != null ) ? _errorMessage : "Could not retrieve routes configuration.\nPlease try again at a later time."; 
			serviceResponder.handleServiceFault( message );
			if( taskResponder != null ) taskResponder.handleQueueableTaskFault();
		}
		_routeConfig = null;
		_errorMessage = null;
		_responder = null;
		_queuedResponder = null;
	}
	
	public IRoutesConfigServiceResponder getResponder()
	{
		return _responder.get();
	}
	public void setResponder( IRoutesConfigServiceResponder responder )
	{
		_responder = new WeakReference<IRoutesConfigServiceResponder>( responder );
	}
	
	public IQueueableTaskResponder getQueueableResponder()
	{
		return _queuedResponder.get();
	}
	public void setQueuedResponder( IQueueableTaskResponder responder )
	{
		_queuedResponder = new WeakReference<IQueueableTaskResponder>( responder );
	}
}
