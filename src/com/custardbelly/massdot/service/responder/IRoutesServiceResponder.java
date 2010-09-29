package com.custardbelly.massdot.service.responder;

import java.util.List;

import com.custardbelly.massdot.model.Route;

public interface IRoutesServiceResponder extends IMassRouteServiceResponder 
{
	void handleServiceResult( List<Route> routes );
}
