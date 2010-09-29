package com.custardbelly.massdot.parser;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.RouteConfig;
import com.custardbelly.massdot.model.RouteDirection;
import com.custardbelly.massdot.model.RouteStop;
import com.custardbelly.massdot.parser.handler.MassRouteServiceParserHandler;

public class RouteConfigParser implements IRouteConfigParser 
{
	private URL service;
	
	public RouteConfigParser( URL service )
	{
		this.service = service;
	}
	
	public RouteConfigParser( String service )
	{
		try
		{
			this.service = new URL( service );
		}
		catch( MalformedURLException e )
		{
			throw new RuntimeException( e );
		}
	}
	
	public RouteConfig parse() throws MassRouteParserException
	{
		RouteConfigParserHandler handler = new RouteConfigParserHandler();
		try
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader reader = sp.getXMLReader();
			reader.setContentHandler( handler );
			InputSource input = new InputSource( service.openStream() );
			reader.parse( input );
			if( handler.hasError() )
			{
				throw new MassRouteParserException( handler.getErrorMessage() );
			}
			return handler.config;
		}
		catch( Exception e )
		{
			throw new MassRouteParserException( e.getMessage() );
		}
	}
	
	private class RouteConfigParserHandler extends MassRouteServiceParserHandler
	{	
		public RouteConfig config;
		
		private boolean _isInspectingDirection;
		private RouteDirection _currentDirection;

		public RouteConfigParserHandler()
		{
			config = new RouteConfig();
		}
		
		@Override
		public void startElement( String uri, String localName, String qName, Attributes attributes )
		{
			if( localName.equalsIgnoreCase( "route" ) )
			{
				config.setTag( attributes.getValue( "tag" ) );
				config.setTitle( attributes.getValue( "title" ) );
			}
			else if( localName.equalsIgnoreCase( "stop" ) )
			{
				RouteStop stop;
				if( !_isInspectingDirection )
				{
					stop = new RouteStop( attributes.getValue( "tag" ), attributes.getValue( "title" ) );
					stop.setDirectionTag( attributes.getValue("dirTag") );
					stop.setStopId( attributes.getValue("stopId") );
					stop.setLatitude( attributes.getValue( "lat") );
					stop.setLongitude( attributes.getValue( "lon") );
					config.addStop( stop );
				}
				else if( _isInspectingDirection && _currentDirection != null )
				{
					stop = new RouteStop( attributes.getValue( "tag" ) );
					_currentDirection.addStop( stop );
				}
			}
			else if( localName.equalsIgnoreCase( "direction") )
			{
				_isInspectingDirection = true;
				_currentDirection = new RouteDirection( attributes.getValue( "tag" ), attributes.getValue( "title" ), attributes.getValue( "name" ) );
				
				config.addDirection( _currentDirection );
			}
			super.startElement(uri, localName, qName, attributes);
		}
		
		@Override
		public void endElement( String uri, String localName, String name)
		{
			if( localName.equalsIgnoreCase( "direction" ) && _isInspectingDirection )
			{
				_isInspectingDirection = false;
				// TODO: find if correct garbage collection.
				_currentDirection = null;
			}
		}
	}
}
