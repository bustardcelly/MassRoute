package com.custardbelly.massdot.service;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

import com.custardbelly.massdot.service.responder.IRoutesConfigServiceResponder;
import com.custardbelly.massdot.service.responder.IRoutesServiceResponder;
import com.custardbelly.massdot.service.responder.IStopPredictionsServiceResponder;

public class MassRouteService implements IMassRouteService
{
	private RouteListTask _routeListTask;
	private RouteConfigTask _routeConfigTask;
	private StopPredictionTask _predictionTask;
	private ServiceTaskQueue _taskQueue;
	
	public MassRouteService( Context context )
	{
		_taskQueue = new ServiceTaskQueue( context );
	}
	
	public AsyncTask<URL, Integer, Long> getRoutes( IRoutesServiceResponder responder )
	{	
		try
		{
			URL url = new URL( "http://webservices.nextbus.com/service/publicXMLFeed?command=routeList&a=mbta" );
			// Set up task if not exists.
			if( _routeListTask == null )
			{
				_routeListTask = new RouteListTask( responder, _taskQueue );
			}
			// Supply responder if different.
			if( _routeListTask.getResponder() != responder )
			{
				_routeListTask.setResponder( responder );
			}
			// Push to task queue to ensure it is run in  accordance to MassDOT requirement.
			_taskQueue.push( _routeListTask, url, true );
			// Return.
			return _routeListTask;
		}
		catch( MalformedURLException e )
		{
			throw new RuntimeException( e );
		}
	}
	
	public AsyncTask<URL, Integer, Long> getRouteConfig( String tagId, IRoutesConfigServiceResponder responder )
	{
		try
		{
			String urlString = "http://webservices.nextbus.com/service/publicXMLFeed?command=routeConfig&a=mbta&r=" + tagId;
			URL url = new URL( urlString );
			if( _routeConfigTask == null )
			{
				_routeConfigTask = new RouteConfigTask( responder, _taskQueue );
			}
			if( _routeConfigTask.getResponder() != responder )
			{
				_routeConfigTask.setResponder( responder );
			}
			// Push to task queue to ensure it is run in  accordance to MassDOT requirement.
			_taskQueue.push( _routeConfigTask, url, true );
			return _routeConfigTask;
		}
		catch( MalformedURLException e )
		{
			throw new RuntimeException( e );
		}
	}
	
	public AsyncTask<URL, Integer, Long> getStopPredictions( String routeId, String stopId, IStopPredictionsServiceResponder responder )
	{
		try
		{
			String urlString = "http://webservices.nextbus.com/service/publicXMLFeed?command=predictions&a=mbta&r=" + routeId + "&s=" + stopId;
			URL url = new URL( urlString );
			if( _predictionTask == null )
			{
				_predictionTask = new StopPredictionTask( responder, _taskQueue );
			}
			if( _predictionTask.getResponder() != responder )
			{
				_predictionTask.setResponder( responder );
			}
			// Push to task queue to ensure it is run in  accordance to MassDOT requirement.
			_taskQueue.push( _predictionTask, url, true );
			return _predictionTask;
		}
		catch( MalformedURLException e )
		{
			throw new RuntimeException( e );
		}
	}
}
