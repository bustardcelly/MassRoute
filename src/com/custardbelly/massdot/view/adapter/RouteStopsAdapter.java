package com.custardbelly.massdot.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.model.RouteStop;

public class RouteStopsAdapter extends ArrayAdapter<RouteStop> 
{
	private LayoutInflater inflater;
	private int rowViewResource;
	private List<RouteStop> stops;
	
	public RouteStopsAdapter( Context context, int rowViewResource, List<RouteStop> stops )
	{
		super( context, rowViewResource, stops );
		this.stops = stops;
		this.rowViewResource = rowViewResource;
		this.inflater = LayoutInflater.from( context );
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		View view = convertView;
		RouteStop stop = stops.get( position );
		if( view == null )
		{
			view = inflater.inflate( rowViewResource, null );
		}
		if( stop != null )
		{
			TextView titleView = (TextView) view.findViewById(R.id.stopTitle);
			if( titleView != null )
			{
				titleView.setText( stop.getTitle() );
			}
		}
		return view;
	}
}
