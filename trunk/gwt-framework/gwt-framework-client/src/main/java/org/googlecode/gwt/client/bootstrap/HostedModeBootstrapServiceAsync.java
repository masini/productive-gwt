package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.googlecode.gwt.shared.client.BootstrapData;

import java.util.List;


public interface HostedModeBootstrapServiceAsync {
	void getApplicationContextData(String appContextName, com.google.gwt.user.client.rpc.AsyncCallback<BootstrapData> arg2);
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
