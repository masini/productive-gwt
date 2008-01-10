package org.googlecode.gwt.bootstrap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

class LoggingChainingCallback implements AsyncCallback {

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
		GWT.log("RPC failure [" + GWT.getTypeName(callback) + "]", caught);
		callback.onFailure(caught);
	}
	
	public void onSuccess(Object result)
	{
		GWT.log("RPC success [" + GWT.getTypeName(callback) + "]", null);
		callback.onSuccess(result);
	}
}
