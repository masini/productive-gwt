package org.googlecode.gwt.shared;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementors of this interface will know how to resolve {@link org.googlecode.gwt.shared.BootstrapData } instances in particular
 * application configurations.
 *
 *         Date: 24-ago-2007
 *         Time: 19.43.27
 */
public interface BootstrapDataResolver {
    BootstrapData getBootstrapData(final HttpServletRequest request);
    ApplicationContextData getAppContextData(final HttpServletRequest request, final String appContextName);

}