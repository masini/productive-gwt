package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.googlecode.gwt.shared.BootstrapData;

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
	 * @param appContextName
	 * @param callback the callback to return String of XML data for help tree
	 */
	void getApplicationContextData(String appContextName, AsyncCallback<String> callback);
}
