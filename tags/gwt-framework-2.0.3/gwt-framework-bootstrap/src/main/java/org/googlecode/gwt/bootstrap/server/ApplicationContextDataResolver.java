package org.googlecode.gwt.bootstrap.server;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.ApplicationContextData;

/**
 * 
 * The class was created by
 * @author Arkady Syamtomov (cslas2)
 *
 * 13/nov/07
 *
 */
public interface ApplicationContextDataResolver {
	ApplicationContextData getAppContextData(final HttpServletRequest request,final String appContextName);
}
