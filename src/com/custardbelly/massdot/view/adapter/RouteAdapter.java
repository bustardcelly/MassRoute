package com.custardbelly.massdot.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.model.Route;

public class RouteAdapter extends ArrayAdapter<Route> {
	
	private LayoutInflater inflater;
	private int rowViewResource;
	private List<Route> routes;
	
	public RouteAdapter( Context context, int rowViewResource, List<Route> routes )
	{
		super( context, rowViewResource, routes );
		this.routes = routes;
		this.rowViewResource = rowViewResource;
		this.inflater = LayoutInflater.from( context );
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		View view = convertView;
		Route route = routes.get( position );
		if( view == null )
		{
			view = inflater.inflate( rowViewResource, null );
		}
		if( route != null )
		{
			TextView titleView = (TextView) view.findViewById(R.id.routeTitle);
			if( titleView != null )
			{
				titleView.setText( route.getTitle() );
			}
		}
		return view;
	}
}
