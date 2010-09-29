package com.custardbelly.massdot.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.Route;
import com.custardbelly.massdot.parser.handler.MassRouteServiceParserHandler;

public class RouteParser implements IRouteParser 
{
	private URL service;
	
	public RouteParser( URL service )
	{
		this.service = service;
	}
	
	public RouteParser( String service )
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
	
	// Android SAX parser - http://www.ibm.com/developerworks/opensource/library/x-android/index.html
	public List<Route> parse() throws MassRouteParserException
	{
		RouteParserHandler handler = new RouteParserHandler();
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
			return handler.routes;
		}
		catch( Exception e )
		{
			throw new MassRouteParserException( e.getMessage() );
		}
	}
	
	private class RouteParserHandler extends MassRouteServiceParserHandler 
	{	
		public List<Route> routes;
		
		public RouteParserHandler()
		{
			routes = new ArrayList<Route>();
		}
		
		@Override
		public void startElement( String uri, String localName, String qName, Attributes attributes )
		{
			if( localName.equalsIgnoreCase( "route" ) )
			{
				routes.add( new Route( attributes.getValue("tag"), attributes.getValue("title" ) ) );
			}
			super.startElement(uri, localName, qName, attributes);
		}
	}
}
