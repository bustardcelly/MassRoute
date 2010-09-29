package com.custardbelly.massdot.model;

import java.util.List;

public class MassRouteModel 
{
	private List<Route> _availableRoutes;
	
	private Route _selectedRoute;
	private RouteConfig _selectedRouteConfig;
	private RouteDirection _selectedRouteDirection;
	private RouteStop _selectedRouteStop;

	private List<RouteStop> _selectedRouteStops;
	private Boolean _requiresSelectedStopsRefresh;
	
	private MassRouteModel() {}
	
	static private MassRouteModel _instance;
	
	static public MassRouteModel instance()
	{
		if( _instance == null )
		{
			_instance = new MassRouteModel();
		}
		return _instance;
	}
	
	public List<RouteStop> getSelectedRouteStops()
	{
		if( _selectedRouteStops == null || _requiresSelectedStopsRefresh )
		{
			_selectedRouteStops = _selectedRouteConfig.getStopsFromDirection( _selectedRouteDirection );
			_requiresSelectedStopsRefresh = false;
		}
		return _selectedRouteStops;
	}

	public Route getSelectedRoute() {
		return _selectedRoute;
	}

	public void setSelectedRoute(Route value) {
		_selectedRoute = value;
		_requiresSelectedStopsRefresh = true;
	}

	public RouteConfig getSelectedRouteConfig() {
		return _selectedRouteConfig;
	}

	public void setSelectedRouteConfig(RouteConfig value) {
		_selectedRouteConfig = value;
		_requiresSelectedStopsRefresh = true;
	}

	public RouteDirection getSelectedRouteDirection() {
		return _selectedRouteDirection;
	}

	public void setSelectedRouteDirection(RouteDirection value) {
		_selectedRouteDirection = value;
		_requiresSelectedStopsRefresh = true;
	}
	
	public RouteStop getSelectedRouteStop()
	{
		return _selectedRouteStop;
	}
	public void setSelectedRouteStop( RouteStop value )
	{
		_selectedRouteStop = value;
	}

	public List<Route> getAvailableRoutes() 
	{
		return _availableRoutes;
	}
	public void setAvailableRoutes( List<Route> value ) 
	{
		_availableRoutes = value;
	}
}
