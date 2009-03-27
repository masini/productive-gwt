package org.googlecode.gwt.bootstrap.client;

import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Define the bootstrap service
 *
 */
public interface BootstrapService extends RemoteService {

	/**
	 * Get bootstrapData
	 * @return BootstrapData - BootstrapData
	 */
	BootstrapData getBootstrapData();
	
	/**
	 * 
	 * @param appName
	 * @param appContext
	 * @return String of XML data for help tree
	 */
	ApplicationContextData getApplicationContextData(String appContextName);
}
