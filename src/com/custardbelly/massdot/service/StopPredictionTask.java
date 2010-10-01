package com.custardbelly.massdot.service;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import android.os.AsyncTask;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.StopPrediction;
import com.custardbelly.massdot.parser.IStopPredictionsParser;
import com.custardbelly.massdot.parser.StopPredictionsParser;
import com.custardbelly.massdot.service.responder.IStopPredictionsServiceResponder;

public class StopPredictionTask extends AsyncTask<URL, Integer, Long> implements IQueueableTask
{
	private List<StopPrediction> _predictions;
	private String _errorMessage;
	private WeakReference<IStopPredictionsServiceResponder> _responder;
	private WeakReference<IQueueableTaskResponder> _queuedResponder;
	
	public StopPredictionTask( IStopPredictionsServiceResponder responder )
	{
		_responder = new WeakReference<IStopPredictionsServiceResponder>( responder );
	}
	
	public StopPredictionTask( IStopPredictionsServiceResponder responder, IQueueableTaskResponder queuedResponder )
	{
		_responder = new WeakReference<IStopPredictionsServiceResponder>( responder );
		_queuedResponder = new WeakReference<IQueueableTaskResponder>( queuedResponder );
	}
	
	@Override
	protected Long doInBackground(URL... urls) 
	{
		URL serviceUrl = urls[0];
		final IStopPredictionsParser parser = new StopPredictionsParser( serviceUrl );
		try
		{
			_predictions = parser.parse();
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
		IStopPredictionsServiceResponder serviceResponder = getResponder();
		IQueueableTaskResponder taskResponder = getQueueableResponder();
		if( _predictions != null )
		{
			serviceResponder.handleServiceResult( _predictions );
			if( taskResponder != null ) taskResponder.handleQueueableTaskResult();
		}
		else
		{
			// TODO: pull default message from resource.
			String message = ( _errorMessage != null ) ? _errorMessage : "Could not retrieve predictions available.\nPlease try again at a later time."; 
			serviceResponder.handleServiceFault( message );
			if( taskResponder != null ) taskResponder.handleQueueableTaskFault();
		}
	}
	
	public IStopPredictionsServiceResponder getResponder()
	{
		return _responder.get();
	}
	public void setResponder( IStopPredictionsServiceResponder responder )
	{
		_responder = new WeakReference<IStopPredictionsServiceResponder>( responder );
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
