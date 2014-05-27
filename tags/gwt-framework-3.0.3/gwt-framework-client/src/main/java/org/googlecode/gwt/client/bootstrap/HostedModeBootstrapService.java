package org.googlecode.gwt.client.bootstrap;

import org.googlecode.gwt.shared.client.BootstrapData;

import java.util.List;


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
