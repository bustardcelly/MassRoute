package com.custardbelly.massdot;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.custardbelly.massdot.enumeration.ActivityViewType;
import com.custardbelly.massdot.enumeration.IntentExtraType;
import com.custardbelly.massdot.model.StoredStopRequest;
import com.custardbelly.massdot.service.IPreferenceService;
import com.custardbelly.massdot.service.PreferenceService;
import com.custardbelly.massdot.view.RouteListView;
import com.custardbelly.massdot.view.StopPredictionsView;
import com.custardbelly.massdot.view.adapter.StoredStopRequestsAdapter;

public class MassRoute extends Activity 
{
	private List<StoredStopRequest> _storedRequests;
	private ArrayAdapter<StoredStopRequest> _storedRequestsAdapter;
	
	private OnClickListener buttonOnClickListener = new View.OnClickListener() {
		public void onClick( View v )
		{
			Intent routeListIntent = new Intent( MassRoute.this, RouteListView.class );
	        startActivityForResult( routeListIntent, ActivityViewType.ACTIVITY_ROUTE_LIST.getId() );
		}
	};
	
	private AdapterView.OnItemClickListener listOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick( AdapterView parentView, View childView, int position, long id )
		{
			StoredStopRequest request = (StoredStopRequest) _storedRequestsAdapter.getItem( position );
			activateStoredRequest( request );
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        final Button button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener( buttonOnClickListener );
         
        final ListView list = (ListView) findViewById( R.id.storedRequestList );
        list.setOnItemClickListener( listOnClickListener );
        
        IPreferenceService requestsService = PreferenceService.instance();
        _storedRequests = requestsService.getStopRequests( getApplicationContext() );
        
        _storedRequestsAdapter = new StoredStopRequestsAdapter( MassRoute.this, R.layout.stored_request_row, _storedRequests );
    	list.setAdapter( _storedRequestsAdapter );
    	registerForContextMenu( list );
         
    	handleStoredRequestsUpdate();
    }
	
	protected void handleStoredRequestsUpdate()
	{
		_storedRequestsAdapter.notifyDataSetChanged();
		showStoredRequests( ( _storedRequests != null && _storedRequests.size() > 0 ) ? View.VISIBLE : View.INVISIBLE );
	}
	
	protected void showStoredRequests( int isVisible )
	{
		final TextView header = (TextView) findViewById(R.id.mainOrField);
		final ListView list = (ListView) findViewById(R.id.storedRequestList);
		header.setVisibility( isVisible );
		list.setVisibility( isVisible );
	}
	
	protected void activateStoredRequest( StoredStopRequest request )
	{
		Intent intent = new Intent( MassRoute.this, StopPredictionsView.class );
		intent.putExtra( IntentExtraType.ROUTE_ID.toString(), request.routeId );
		intent.putExtra( IntentExtraType.ROUTE_TITLE.toString(), request.routeTitle );
		intent.putExtra( IntentExtraType.STOP_ID.toString(), request.stopId );
		intent.putExtra( IntentExtraType.STOP_TITLE.toString(), request.stopTitle );
		startActivityForResult( intent, ActivityViewType.ACTIVITY_ROUTE_PREDICTIONS.getId() );
	}
    
	@Override
	public void onCreateContextMenu( ContextMenu menu, View view, ContextMenuInfo menuInfo )
	{
		super.onCreateContextMenu( menu, view, menuInfo );
		
		ListView list = (ListView) findViewById( R.id.storedRequestList );
		if( view == list )
		{
			MenuInflater inflater = getMenuInflater();
			inflater.inflate( R.menu.stored_requests_menu, menu );
		}
	}
	
	@Override
	public boolean onContextItemSelected( MenuItem item )
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		int index = info.position;
		StoredStopRequest request = (StoredStopRequest) _storedRequestsAdapter.getItem( index );
		if( request == null ) return false;
		
		switch( item.getItemId() )
		{
			case R.id.viewRequest:
				activateStoredRequest( request );
				break;
			case R.id.deleteRequest:
				boolean removeSuccess = PreferenceService.instance().removeStopRequest( request, getApplicationContext() );
				if( removeSuccess )
				{
					handleStoredRequestsUpdate();
				}
				break;
		}
		return true;
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
    {
    	super.onActivityResult( requestCode, resultCode, intent );
    	
    	final int routeActivity = ActivityViewType.ACTIVITY_ROUTE_LIST.getId();
    	final int predictionActivity = ActivityViewType.ACTIVITY_ROUTE_PREDICTIONS.getId();
    	
    	if( requestCode == routeActivity || requestCode == predictionActivity )
    	{    
    		handleStoredRequestsUpdate();
    	}
    }
}