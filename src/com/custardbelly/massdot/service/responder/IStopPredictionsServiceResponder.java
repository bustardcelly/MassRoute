package com.custardbelly.massdot.service.responder;

import java.util.List;

import com.custardbelly.massdot.model.StopPrediction;

public interface IStopPredictionsServiceResponder extends IMassRouteServiceResponder 
{
	void handleServiceResult( List<StopPrediction> predictions );
}
