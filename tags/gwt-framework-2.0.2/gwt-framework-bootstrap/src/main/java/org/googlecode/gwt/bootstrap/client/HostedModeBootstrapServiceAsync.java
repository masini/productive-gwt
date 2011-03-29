package org.googlecode.gwt.bootstrap.client;

import java.util.List;

import org.googlecode.gwt.base.client.BootstrapData;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface HostedModeBootstrapServiceAsync {
	void getApplicationContextData(java.lang.String appContextName, com.google.gwt.user.client.rpc.AsyncCallback<BootstrapData> arg2);
	/**
	 * Get bootstrapData
	 * @param callback the callback to return BootstrapData - BootstrapData
	 */
	void getBootstrapData(AsyncCallback<BootstrapData> callback);

	/**
	 * 
	 * @param String menuInterfaceClass - class' name of menu interface
	 * @param callback the callback to return BootstrapData - BootstrapData
	 */
    void getBootstrapData(String menuInterfaceClass, AsyncCallback<BootstrapData> callback);
    
    /**
     */
    void getBootstrapData(List<String> roles, AsyncCallback<String> callback);
}
