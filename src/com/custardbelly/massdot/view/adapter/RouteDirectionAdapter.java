package com.custardbelly.massdot.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.model.RouteDirection;

public class RouteDirectionAdapter extends ArrayAdapter<RouteDirection> 
{
	private LayoutInflater inflater;
	private int rowViewResource;
	private List<RouteDirection> directions;
	
	public RouteDirectionAdapter( Context context, int rowViewResource, List<RouteDirection> directions )
	{
		super( context, rowViewResource, directions );
		this.directions = directions;
		this.rowViewResource = rowViewResource;
		this.inflater = LayoutInflater.from( context );
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		View view = convertView;
		RouteDirection direction = directions.get( position );
		if( view == null )
		{
			view = inflater.inflate( rowViewResource, null );
		}
		if( direction != null )
		{
			TextView dirView = (TextView) view.findViewById(R.id.directionField);
			TextView destView = (TextView) view.findViewById(R.id.destinationField);
			if( dirView != null )
			{
				dirView.setText( direction.getName() );
			}
			if( destView != null )
			{
				destView.setText( direction.getTitle() );
			}
		}
		return view;
	}
}
