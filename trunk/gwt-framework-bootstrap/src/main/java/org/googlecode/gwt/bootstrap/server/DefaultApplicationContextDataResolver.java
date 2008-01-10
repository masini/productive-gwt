package org.googlecode.gwt.bootstrap.server;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.googlecode.gwt.base.client.ApplicationContextData;

public class DefaultApplicationContextDataResolver implements ApplicationContextDataResolver {

	private static Log log = LogFactory.getLog(DefaultApplicationContextDataResolver.class);

	private BootstrapDataResolver bootstrapDataResolver;
	
	public DefaultApplicationContextDataResolver() {
	}
	
	public DefaultApplicationContextDataResolver(BootstrapDataResolver bootstrapDataResolver) {
		this.bootstrapDataResolver = bootstrapDataResolver;
	}
	
	public ApplicationContextData getAppContextData(HttpServletRequest request,String appContextName) {
		if ( request == null ) {
            throw new IllegalArgumentException( "HttpServletRequest request parameter cannot be null" );
        }
				
		ApplicationContextData appContextData = new ApplicationContextData();		
		return appContextData;
	}
	
	public void setBootstrapDataResolver(BootstrapDataResolver bootstrapDataResolver) {
		this.bootstrapDataResolver = bootstrapDataResolver;
	}				
	
}
