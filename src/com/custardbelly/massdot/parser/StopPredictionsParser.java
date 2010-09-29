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
import com.custardbelly.massdot.model.StopPrediction;
import com.custardbelly.massdot.parser.handler.MassRouteServiceParserHandler;

public class StopPredictionsParser implements IStopPredictionsParser 
{
	private URL service;
	
	public StopPredictionsParser( URL service )
	{
		this.service = service;
	}
	
	public StopPredictionsParser( String service )
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
	
	public List<StopPrediction> parse() throws MassRouteParserException
	{
		PredictionsParserHandler handler = new PredictionsParserHandler();
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
			return handler.predictions;
		}
		catch( Exception e )
		{
			throw new MassRouteParserException( e.getMessage() );
		}
	}
	
	private class PredictionsParserHandler extends MassRouteServiceParserHandler 
	{	
		private String _stopTag;
		private String _stopTitle;
		public List<StopPrediction> predictions;
		
		public PredictionsParserHandler()
		{
			predictions = new ArrayList<StopPrediction>();
		}
		
		@Override
		public void startElement( String uri, String localName, String qName, Attributes attributes )
		{
			// <predictions> is the top level node. store the stop details to be applied to each prediction.
			if( localName.equalsIgnoreCase( "predictions" ) )
			{
				_stopTag = attributes.getValue( "stopTag" );
				_stopTitle = attributes.getValue( "stopTitle" );
			}
			else if( localName.equalsIgnoreCase( "prediction" ) )
			{
				StopPrediction prediction = new StopPrediction( _stopTag, _stopTitle );
				prediction.setSeconds( Integer.parseInt( attributes.getValue( "seconds" ) ) );
				prediction.setMinutes( Integer.parseInt( attributes.getValue( "minutes" ) ) );
				prediction.setVehicle( attributes.getValue( "vehicle" ) );
				prediction.setBlock( attributes.getValue( "block" ) );
				predictions.add( prediction );
			}
			super.startElement(uri, localName, qName, attributes);
		}
	}
}
