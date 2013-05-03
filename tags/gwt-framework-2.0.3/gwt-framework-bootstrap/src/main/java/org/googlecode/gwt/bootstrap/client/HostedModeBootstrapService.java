package org.googlecode.gwt.bootstrap.client;

import java.util.List;

import org.googlecode.gwt.base.client.BootstrapData;


public interface HostedModeBootstrapService extends BootstrapService {

	/**
	 * Get bootstrapData
	 * @return BootstrapData - BootstrapData
	 */
	BootstrapData getBootstrapData();

	/**
	 * 
	 * @param String menuInterfaceClass - class' name of menu interface
	 * @return BootstrapData - BootstrapData
	 */
    BootstrapData getBootstrapData(String menuInterfaceClass);
    
    BootstrapData getBootstrapData(List<String> roles);
}
