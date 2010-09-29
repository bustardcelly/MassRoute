package com.custardbelly.massdot.parser.handler;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class MassRouteServiceParserHandler extends DefaultHandler 
{
	private boolean _isError;
	private String _errorMessage;
	private StringBuffer _errorMessageBuffer;
	
	@Override
	public void characters(char[] ch, int start, int length) 
	{
		if ( _isError && _errorMessageBuffer != null ) 
		{
			_errorMessageBuffer.append(ch, start, length);
		}
	}
	
	@Override
	public void startElement( String uri, String localName, String qName, Attributes attributes )
	{
		if( localName.equalsIgnoreCase( "error" ) )
		{
			_isError = true;
			_errorMessageBuffer = new StringBuffer();
		}
	}
	
	@Override
	public void endDocument()
	{
		if( _isError && _errorMessageBuffer != null )
		{
			_errorMessage = _errorMessageBuffer.toString().trim();
			// TODO: Proper way to set for garbage collection?
			_errorMessageBuffer = null;
		}
	}
	
	public boolean hasError()
	{
		return _isError;
	}
	
	public String getErrorMessage()
	{
		return _errorMessage;
	}
}
