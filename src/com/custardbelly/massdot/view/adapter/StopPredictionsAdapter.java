package com.custardbelly.massdot.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custardbelly.massdot.R;
import com.custardbelly.massdot.model.StopPrediction;

public class StopPredictionsAdapter extends ArrayAdapter<StopPrediction> 
{
	private LayoutInflater inflater;
	private int rowViewResource;
	private List<StopPrediction> predictions;
	
	public StopPredictionsAdapter( Context context, int rowViewResource, List<StopPrediction> predictions )
	{
		super( context, rowViewResource, predictions );
		this.predictions = predictions;
		this.rowViewResource = rowViewResource;
		this.inflater = LayoutInflater.from( context );
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		View view = convertView;
		StopPrediction prediction = predictions.get( position );
		if( view == null )
		{
			view = inflater.inflate( rowViewResource, null );
		}
		if( prediction != null )
		{
			TextView titleView = (TextView) view.findViewById(R.id.predictionTitle);
			if( titleView != null )
			{
				titleView.setText( Integer.toString( prediction.getMinutes() ) );
			}
		}
		return view;
	}
}
