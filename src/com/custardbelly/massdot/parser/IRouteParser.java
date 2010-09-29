package com.custardbelly.massdot.parser;

import java.util.List;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.Route;

public interface IRouteParser 
{
	List<Route> parse() throws MassRouteParserException;
}
