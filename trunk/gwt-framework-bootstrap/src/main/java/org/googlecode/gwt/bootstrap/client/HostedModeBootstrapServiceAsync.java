package org.googlecode.gwt.bootstrap.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface HostedModeBootstrapServiceAsync {

	/**
	 * Get bootstrapData
	 * @param callback the callback to return BootstrapData - BootstrapData
	 */
	void getBootstrapData(AsyncCallback callback);

	/**
	 * 
	 * @param String menuInterfaceClass - class' name of menu interface
	 * @param callback the callback to return BootstrapData - BootstrapData
	 */
    void getBootstrapData(String menuInterfaceClass, AsyncCallback callback);
    
    /**
     */
    void getBootstrapData(List roles, AsyncCallback callback);
}
