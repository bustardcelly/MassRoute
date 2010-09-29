package com.custardbelly.massdot.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.enumeration.IntentExtraType;
import com.custardbelly.massdot.model.StopPrediction;
import com.custardbelly.massdot.service.responder.IStopPredictionsServiceResponder;
import com.custardbelly.massdot.view.adapter.StopPredictionsAdapter;

public class StopPredictionsView extends MassRouteServiceView implements IStopPredictionsServiceResponder
{
	private List<StopPrediction> _predictions;
	private StopPredictionsAdapter _adapter;
	
	private ProgressDialog _progress;
	
	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setContentView(R.layout.predictions_list);
		
//		String routeTitle = MassRouteModel.instance().getSelectedRoute().getTitle();
//		String stopTitle = MassRouteModel.instance().getSelectedRouteStop().getTitle();
		String routeTitle = getIntent().getExtras().getString( IntentExtraType.ROUTE_TITLE.toString() );
		String stopTitle = getIntent().getExtras().getString( IntentExtraType.STOP_TITLE.toString() );
		setTitle( "Route " + routeTitle + ": " + stopTitle );
		
		_predictions = new ArrayList<StopPrediction>();
		_adapter = new StopPredictionsAdapter( StopPredictionsView.this, R.layout.prediction_row, _predictions );
		setListAdapter( _adapter );
	}
	
	@Override
	protected void startRequest()
	{
		_progress = ProgressDialog.show( StopPredictionsView.this, "", "Retrieving stop predictions...", true );
		
//		MassRouteModel model = MassRouteModel.instance();
//		String routeId = model.getSelectedRouteConfig().getTag();
//		String stopId = model.getSelectedRouteStop().getTag();
		String routeId = getIntent().getExtras().getString( IntentExtraType.ROUTE_ID.toString() );
		String stopId = getIntent().getExtras().getString( IntentExtraType.STOP_ID.toString() );
		_task = getService().getStopPredictions( routeId, stopId, StopPredictionsView.this );
	}
	
	@Override 
	protected void finishRequest()
	{
		super.finishRequest();
		_progress.dismiss();
	}
	
	public void handleServiceResult( List<StopPrediction> predictions )
	{
		int index;
		for( index = 0; index < predictions.size(); index++ )
		{
			_adapter.add( predictions.get( index ) );
		}
		_adapter.notifyDataSetChanged();
		finishRequest();
	}
}
