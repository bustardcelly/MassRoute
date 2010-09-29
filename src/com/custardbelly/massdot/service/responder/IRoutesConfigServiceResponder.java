package com.custardbelly.massdot.service.responder;

import com.custardbelly.massdot.model.RouteConfig;

public interface IRoutesConfigServiceResponder extends IMassRouteServiceResponder 
{
	void handleServiceResult( RouteConfig config );
}
