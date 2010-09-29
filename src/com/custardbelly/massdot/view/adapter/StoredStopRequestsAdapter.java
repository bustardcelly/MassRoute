package com.custardbelly.massdot.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.model.StoredStopRequest;

public class StoredStopRequestsAdapter extends ArrayAdapter<StoredStopRequest> 
{
	private LayoutInflater inflater;
	private int rowViewResource;
	private List<StoredStopRequest> requests;
	
	public StoredStopRequestsAdapter( Context context, int rowViewResource, List<StoredStopRequest> requests )
	{
		super( context, rowViewResource, requests );
		this.requests = requests;
		this.rowViewResource = rowViewResource;
		this.inflater = LayoutInflater.from( context );
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		View view = convertView;
		StoredStopRequest request = requests.get( position );
		if( view == null )
		{
			view = inflater.inflate( rowViewResource, null );
		}
		if( request != null )
		{
			TextView routeTitleView = (TextView) view.findViewById( R.id.storedRouteTitle );
			TextView directionTitleView = (TextView) view.findViewById( R.id.storedDirectionTitle );
			TextView stopTitleView = (TextView) view.findViewById( R.id.storedStopTitle );
			if( routeTitleView != null )
			{
				routeTitleView.setText( request.routeTitle );
			}
			if( directionTitleView != null )
			{
				directionTitleView.setText( request.directionTitle );
			}
			if( stopTitleView != null )
			{
				stopTitleView.setText( request.stopTitle );
			}
		}
		return view;
	}
}
