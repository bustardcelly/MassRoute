package com.custardbelly.massdot.service;

import java.net.URL;

import android.os.AsyncTask;

public interface IServiceTaskQueue 
{
	void push( AsyncTask<URL, Integer, Long> task, URL url, boolean autostart );
	void flush();
	void empty();
}
