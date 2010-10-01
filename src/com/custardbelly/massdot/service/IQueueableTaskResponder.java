package com.custardbelly.massdot.service;

public interface IQueueableTaskResponder 
{
	void handleQueueableTaskResult();
	void handleQueueableTaskFault();
}
