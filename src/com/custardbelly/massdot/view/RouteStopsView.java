package com.custardbelly.massdot.view;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.enumeration.ActivityViewType;
import com.custardbelly.massdot.enumeration.IntentExtraType;
import com.custardbelly.massdot.model.MassRouteModel;
import com.custardbelly.massdot.model.RouteDirection;
import com.custardbelly.massdot.model.RouteStop;
import com.custardbelly.massdot.service.PreferenceService;
import com.custardbelly.massdot.view.adapter.RouteStopsAdapter;

public class RouteStopsView extends ListActivity 
{
	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setContentView( R.layout.stops_list );
		
		RouteDirection direction = MassRouteModel.instance().getSelectedRouteDirection();
		setTitle( direction.getTitle() );
		
		List<RouteStop> stops = MassRouteModel.instance().getSelectedRouteStops();
		RouteStopsAdapter adapter = new RouteStopsAdapter( RouteStopsView.this, R.layout.stop_row, stops );
		setListAdapter( adapter );
	}
	
	@Override
	public void onListItemClick( ListView listView, View view, int position, long id )
	{
		List<RouteStop> stops = MassRouteModel.instance().getSelectedRouteStops();
		RouteStop selectedRouteStop = stops.get( position );
		
		MassRouteModel model = MassRouteModel.instance();
		model.setSelectedRouteStop( selectedRouteStop );
		
		PreferenceService.instance().saveStopRequest( model, getApplicationContext() );
		
		String routeId = getIntent().getExtras().getString( IntentExtraType.ROUTE_ID.toString() );
		String routeTitle = getIntent().getExtras().getString( IntentExtraType.ROUTE_TITLE.toString() );
		
		Intent intent = new Intent( RouteStopsView.this, StopPredictionsView.class );
		intent.putExtra( IntentExtraType.ROUTE_ID.toString(), routeId );
		intent.putExtra( IntentExtraType.ROUTE_TITLE.toString(), routeTitle );
		intent.putExtra( IntentExtraType.STOP_ID.toString(), selectedRouteStop.getTag() );
		intent.putExtra( IntentExtraType.STOP_TITLE.toString(), selectedRouteStop.getTitle() );
		startActivityForResult( intent, ActivityViewType.ACTIVITY_ROUTE_PREDICTIONS.getId() );
	}
}
