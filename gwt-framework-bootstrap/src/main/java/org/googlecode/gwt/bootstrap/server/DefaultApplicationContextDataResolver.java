package org.googlecode.gwt.bootstrap.server;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.ApplicationContextData;

public class DefaultApplicationContextDataResolver implements ApplicationContextDataResolver {


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
