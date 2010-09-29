package com.custardbelly.massdot.parser;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.RouteConfig;

public interface IRouteConfigParser 
{
	RouteConfig parse() throws MassRouteParserException;
}
