package com.custardbelly.massdot.service;

import java.util.List;

import android.content.Context;

import com.custardbelly.massdot.model.MassRouteModel;
import com.custardbelly.massdot.model.StoredStopRequest;

public interface IPreferenceService 
{
	boolean saveStopRequest( MassRouteModel model, Context context );
	boolean removeStopRequest( StoredStopRequest request, Context context );
	List<StoredStopRequest> getStopRequests( Context context );
}
