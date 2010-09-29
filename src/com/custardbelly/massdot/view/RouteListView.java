package com.custardbelly.massdot.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.enumeration.ActivityViewType;
import com.custardbelly.massdot.enumeration.IntentExtraType;
import com.custardbelly.massdot.model.MassRouteModel;
import com.custardbelly.massdot.model.Route;
import com.custardbelly.massdot.service.responder.IRoutesServiceResponder;
import com.custardbelly.massdot.view.adapter.RouteAdapter;

public class RouteListView extends MassRouteServiceView implements IRoutesServiceResponder
{
	private RouteAdapter _routeAdapter;
	private List<Route> _routeList;
	
	private ProgressDialog _routeProgress;
	
	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setContentView(R.layout.routes_list);
		
		_routeList = new ArrayList<Route>();
        _routeAdapter = new RouteAdapter( this, R.layout.routes_row, _routeList );
        setListAdapter( _routeAdapter );
        
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
	}
	
	@Override
	// onNewIntent() is invoked due to settings in manifest (android:launchMode=singleTop). 
	// The intent is based on the search response associated with this activity.
	public void onNewIntent( Intent intent )
	{
		// Search response.
        if( Intent.ACTION_VIEW.equals(intent.getAction()) )
        {
        	// Uri data from new intent after suggestion/search selection.
//        	Uri data = intent.getData();
        	// Suggestion selection.
        	String key = intent.getDataString();
        	// User-entered search query.
//        	String userQuery = intent.getStringExtra(SearchManager.USER_QUERY);
        	
        	// Iterate through route list and find searched route if available.
        	Iterator<Route> iterator = _routeList.iterator();
        	Route selectedRoute = null;
        	while( iterator.hasNext() )
        	{
        		selectedRoute = iterator.next();
        		if( selectedRoute.getTitle().equalsIgnoreCase( key ) )
        			break;
        		else
        			selectedRoute = null;
        	}
        	// If we have found a route to match our search, open the corresponding activity.
        	if( selectedRoute != null )
        	{
        		showDirectionActivity( selectedRoute );
        	}
        }
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
    {
    	super.onActivityResult( requestCode, resultCode, intent );
    }
	
	@Override
	public void onListItemClick( ListView listView, View view, int position, long id )
	{
		super.onListItemClick( listView, view, position, id );
		
		Route selectedRoute = _routeList.get( position );
		showDirectionActivity( selectedRoute );
	}
	
	@Override
	protected void startRequest()
	{
		super.startRequest();
		_routeProgress = ProgressDialog.show( RouteListView.this, "", "Retrieving routes...", true);
        _task = getService().getRoutes( RouteListView.this );
	}
	
	@Override 
	protected void finishRequest()
	{
		super.finishRequest();
		_routeProgress.dismiss();
	}
	
	protected void showDirectionActivity( Route selectedRoute )
	{
		MassRouteModel.instance().setSelectedRoute( selectedRoute );
		
		Intent intent = new Intent( RouteListView.this, RouteDirectionView.class );
		intent.putExtra( IntentExtraType.ROUTE_ID.toString(), selectedRoute.getTag() );
		intent.putExtra( IntentExtraType.ROUTE_TITLE.toString(), selectedRoute.getTitle() );
		startActivityForResult( intent, ActivityViewType.ACTIVITY_ROUTE_DIRECTION.getId() );
	}
	
	public void handleServiceResult( List<Route> routes )
	{
		int index;
		for( index = 0; index < routes.size(); index++ )
		{
			_routeList.add( routes.get( index ) );
		}
		_routeAdapter.notifyDataSetChanged();
		MassRouteModel.instance().setAvailableRoutes( _routeList );
		finishRequest();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.options_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	switch (item.getItemId()) 
        {
            case R.id.search:
                return onSearchRequested();
            default:
                return false;
        }
    }
}
