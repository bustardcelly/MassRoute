package com.custardbelly.massdot.service;

public interface IQueueableTask 
{
	void setQueuedResponder( IQueueableTaskResponder responder );
}
