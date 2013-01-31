package org.googlecode.gwt.bootstrap.client;

/**
 *  Define the callBack methods for the bootstrapService
 *
 */
public interface BootstrapEventListener {

	/**
	 * invoke when service success
	 * @param bootstrapEvent - Event bootstrap
	 */
    void onBootstrap( BootstrapEvent bootstrapEvent );

	/**
	 * invoke when service fail
	 * @param bootstrapEvent
	 */
    void onBootstrapFailed( BootstrapEvent bootstrapEvent );

}
