package com.custardbelly.massdot.model;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class RouteSearchContentProvider extends ContentProvider 
{
	public static final String[] CURSOR_COLUMNS = new String[]{ BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA };

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		MatrixCursor cursor = new MatrixCursor( CURSOR_COLUMNS );
		String entry = selectionArgs[0];
		List<Route> routes = new WeakReference<List<Route>>( MassRouteModel.instance().getAvailableRoutes() ).get();
		Iterator<Route> iterator = routes.iterator();
		Route route;
		String routeTitle;
		int index = 0;
		while( iterator.hasNext() )
		{
			route = iterator.next();
			routeTitle = route.getTitle();
			if( routeTitle.contains( entry ) )
				cursor.addRow( new Object[]{ index++, routeTitle, routeTitle } );
		}
		// Add default row.
		if( cursor.getCount() == 0 )
			cursor.addRow( new Object[]{ index, "No route found.", "none" } );
		
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

}
