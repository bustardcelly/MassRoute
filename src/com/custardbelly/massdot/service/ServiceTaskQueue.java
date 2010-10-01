package com.custardbelly.massdot.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.custardbelly.massdot.R;

public class ServiceTaskQueue implements IServiceTaskQueue, IQueueableTaskResponder
{	
	// Must wait at least 10 seconds between service calls as explained in MassDOT documentation.
	final long minimumElapse = 10000;
	
	long _lastTaskTime;
	boolean _isRunning;
	List<TaskToken> _tasks;
	Handler _taskHandler;
	
	private Context _context;
	
	public ServiceTaskQueue( Context context )
	{
		_context = context;
		
		_isRunning = false;
		_tasks = new ArrayList<TaskToken>();
		_taskHandler = new Handler();
		_lastTaskTime = 0;
	}
	
	protected void runNextTask()
	{
		_isRunning = ( _tasks.size() > 0 );
		if( _isRunning )
		{
			// check timer.
			final long currentTime = new Date().getTime();
			final long elapse = ( _lastTaskTime == 0 ) ? currentTime : ( currentTime - _lastTaskTime );
			if( elapse < minimumElapse )
			{
				String alert = _context.getString( R.string.delayed_request_alert );
				float seconds = ( ( elapse ) - 1000 ) / 1000;
				Toast.makeText( _context, alert.replace( "{0}", Float.toString( seconds ) ), Toast.LENGTH_LONG);
				// start timer with elapse
				Runnable t = new Runnable() {
					public void run()
					{
						runNextTask();
					}
				};
				_taskHandler.postAtTime( t, elapse );
			}
			else
			{
				// reset time, execute task.
				_lastTaskTime = new Date().getTime();
				TaskToken token = _tasks.remove(0);
				AsyncTask<URL, Integer, Long> task = token.task;
				task.execute( token.url );
			}
		}
	}

	public void handleQueueableTaskResult()
	{
		runNextTask();
	}
	public void handleQueueableTaskFault()
	{
		runNextTask();
	}
	
	public void push( AsyncTask<URL, Integer, Long> task, URL url, boolean autostart ) 
	{
		_tasks.add( new TaskToken( task, url ) );
		
		if( autostart && !_isRunning )
			flush();
	}
	
	public void flush() 
	{
		runNextTask();
	}
	
	public void empty()
	{
		while( _tasks.size() > 0 )
			_tasks.remove(0);
		
		_tasks = new ArrayList<TaskToken>();
	}
	
	private class TaskToken
	{
		AsyncTask<URL, Integer, Long> task;
		URL url;
		public TaskToken( AsyncTask<URL, Integer, Long> task, URL url )
		{
			this.task = task;
			this.url = url;
		}
	}
}
