package org.googlecode.gwt.bootstrap.server;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;

/**
 * Implementors of this interface will know how to resolve {@link org.googlecode.gwt.bootstrap.client.BootstrapData } instances in particular
 * application configurations.
 * 
 *         Date: 24-ago-2007
 *         Time: 19.43.27
 */
public interface BootstrapDataResolver {
    BootstrapData getBootstrapData(final HttpServletRequest request);
    ApplicationContextData getAppContextData(final HttpServletRequest request,final String appContextName);
    
}
