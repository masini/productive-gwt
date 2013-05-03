package org.googlecode.gwt.server;

import org.googlecode.gwt.shared.ApplicationContextData;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * The class was created by
 * @author Arkady Syamtomov (cslas2)
 *
 * 13/nov/07
 *
 */
public interface ApplicationContextDataResolver {
	ApplicationContextData getAppContextData(final HttpServletRequest request, final String appContextName);
}
