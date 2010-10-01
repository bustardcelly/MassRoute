package com.custardbelly.massdot.view;

import java.net.URL;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.custardbelly.massdot.service.IMassRouteService;
import com.custardbelly.massdot.service.MassRouteService;
import com.custardbelly.massdot.service.responder.IMassRouteServiceResponder;

public class MassRouteServiceView extends ListActivity implements IMassRouteServiceResponder 
{
	protected static IMassRouteService service;
	
	protected boolean _hasProcessedRequest;
	protected AsyncTask<URL, Integer, Long> _task;
	
	private DialogInterface.OnClickListener errorDialogClickHandler = new DialogInterface.OnClickListener() {
		public void onClick( DialogInterface dialog, int id )
		{
			MassRouteServiceView.this.finish();
		}
	};
	
	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		
		if( MassRouteServiceView.service == null)
			MassRouteServiceView.service = new MassRouteService( MassRouteServiceView.this );
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		if( !_hasProcessedRequest )
		{
			startRequest();
		}
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		if( _task != null )
			_task.cancel( true );
	}	
	
	protected IMassRouteService getService()
	{
		return MassRouteServiceView.service;
	}
	
	protected void startRequest()
	{
		// abstract
	}
	
	protected void finishRequest()
	{
		_hasProcessedRequest = true;
		_task.cancel( true );
		_task = null;
	}
	
	public void handleServiceFault(String message) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this );
		builder.setTitle( "Error" );
		builder.setMessage( message );
		builder.setCancelable( true );
		builder.setPositiveButton( "OK", errorDialogClickHandler );
		AlertDialog alert = builder.create();
		alert.show();
		
		finishRequest();
	}
}
