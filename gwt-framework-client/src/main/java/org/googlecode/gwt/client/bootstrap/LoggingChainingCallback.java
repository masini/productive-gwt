package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoggingChainingCallback implements AsyncCallback {

	private AsyncCallback callback;
	
	public LoggingChainingCallback()
	{}
	
	public LoggingChainingCallback(AsyncCallback callback)
	{
		this.callback = callback;
	}
	
	public void setAsyncCallback(AsyncCallback callback)
	{
		this.callback = callback; 
	}
		
	public void onFailure(Throwable caught)
	{
		GWT.log("RPC failure [" + callback.getClass().getName() + "]", caught);
		callback.onFailure(caught);
	}
	
	public void onSuccess(Object result)
	{
		GWT.log("RPC success [" + result.getClass().getName() + "]", null);
		callback.onSuccess(result);
	}
}
