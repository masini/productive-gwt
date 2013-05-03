package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.user.client.rpc.RemoteService;
import org.googlecode.gwt.shared.ApplicationContextData;
import org.googlecode.gwt.shared.BootstrapData;

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
	 * @param appContextName
	 * @return String of XML data for help tree
	 */
	ApplicationContextData getApplicationContextData(String appContextName);
}
