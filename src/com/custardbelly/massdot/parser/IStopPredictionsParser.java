package com.custardbelly.massdot.parser;

import java.util.List;

import com.custardbelly.massdot.exception.MassRouteParserException;
import com.custardbelly.massdot.model.StopPrediction;

public interface IStopPredictionsParser 
{
	List<StopPrediction> parse() throws MassRouteParserException;
}
