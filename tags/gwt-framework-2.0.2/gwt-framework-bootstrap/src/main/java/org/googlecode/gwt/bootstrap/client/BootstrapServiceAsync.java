package org.googlecode.gwt.bootstrap.client;

import org.googlecode.gwt.base.client.BootstrapData;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define the bootstrap service
 *
 */
public interface BootstrapServiceAsync {

	/**
	 * Get bootstrapData
	 * @param callback the callback to return BootstrapData - BootstrapData
	 */
	void getBootstrapData(AsyncCallback<BootstrapData> callback);
	
	/**
	 * 
	 * @param appName
	 * @param appContext
	 * @param callback the callback to return String of XML data for help tree
	 */
	void getApplicationContextData(String appContextName, AsyncCallback<String> callback);
}
