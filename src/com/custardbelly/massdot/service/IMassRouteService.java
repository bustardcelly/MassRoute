package com.custardbelly.massdot.service;

import java.net.URL;

import android.os.AsyncTask;

import com.custardbelly.massdot.service.responder.IRoutesConfigServiceResponder;
import com.custardbelly.massdot.service.responder.IRoutesServiceResponder;
import com.custardbelly.massdot.service.responder.IStopPredictionsServiceResponder;

public interface IMassRouteService 
{
	AsyncTask<URL, Integer, Long> getRoutes( IRoutesServiceResponder responder );
	AsyncTask<URL, Integer, Long> getRouteConfig( String tagId, IRoutesConfigServiceResponder responder );
	AsyncTask<URL, Integer, Long> getStopPredictions( String routeId, String stopId, IStopPredictionsServiceResponder responder);
}
