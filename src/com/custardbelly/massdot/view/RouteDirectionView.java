package com.custardbelly.massdot.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.enumeration.ActivityViewType;
import com.custardbelly.massdot.enumeration.IntentExtraType;
import com.custardbelly.massdot.model.MassRouteModel;
import com.custardbelly.massdot.model.RouteConfig;
import com.custardbelly.massdot.model.RouteDirection;
import com.custardbelly.massdot.service.responder.IRoutesConfigServiceResponder;
import com.custardbelly.massdot.view.adapter.RouteDirectionAdapter;

public class RouteDirectionView extends MassRouteServiceView implements IRoutesConfigServiceResponder
{
	private RouteConfig _routeConfig;
	private List<RouteDirection> _directions;
	private RouteDirectionAdapter _directionAdapter;
	
	private ProgressDialog _routeProgress;
	
	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setContentView(R.layout.directions_list);
		setTitle( "Route " + MassRouteModel.instance().getSelectedRoute().getTitle() );
		
		_directions = new ArrayList<RouteDirection>();
		_directionAdapter = new RouteDirectionAdapter( RouteDirectionView.this, R.layout.directions_row, _directions );
		setListAdapter( _directionAdapter );
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
    {
    	super.onActivityResult( requestCode, resultCode, intent );
    }
	
	@Override
	public void onListItemClick( ListView listView, View view, int position, long id )
	{
		RouteDirection selectedDirection = _directions.get( position );
		MassRouteModel.instance().setSelectedRouteDirection( selectedDirection );
		MassRouteModel.instance().setSelectedRouteConfig( _routeConfig );
		
		String routeId = getIntent().getExtras().getString( IntentExtraType.ROUTE_ID.toString() );
		String routeTitle = getIntent().getExtras().getString( IntentExtraType.ROUTE_TITLE.toString() );
		
		Intent intent = new Intent( RouteDirectionView.this, RouteStopsView.class );
		intent.putExtra( IntentExtraType.ROUTE_ID.toString(), routeId );
		intent.putExtra( IntentExtraType.ROUTE_TITLE.toString(), routeTitle );
		startActivityForResult( intent, ActivityViewType.ACTIVITY_ROUTE_STOPS.getId() );
	}
	
	@Override
	protected void startRequest()
	{
		_routeProgress = ProgressDialog.show( RouteDirectionView.this, "", "Retrieving routes directions...", true);
		
//		Route route = MassRouteModel.instance().getSelectedRoute();
//		String routeId = route.getTag();
		String routeId = getIntent().getExtras().getString( IntentExtraType.ROUTE_ID.toString() );
        _task = getService().getRouteConfig( routeId, RouteDirectionView.this );
	}
	
	@Override 
	protected void finishRequest()
	{
		super.finishRequest();
		_routeProgress.dismiss();
	}
	
	public void handleServiceResult( RouteConfig config )
	{
		this._routeConfig = config;
		List<RouteDirection> dirs = _routeConfig.getDirections();
		Collections.sort( dirs );
		int index;
		for( index = 0; index < dirs.size(); index++ )
		{
			_directions.add( dirs.get( index ) );
		}
		_directionAdapter.notifyDataSetChanged();
		finishRequest();
	}
}
