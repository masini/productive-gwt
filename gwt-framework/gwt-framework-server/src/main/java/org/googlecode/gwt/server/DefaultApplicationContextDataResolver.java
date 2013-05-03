package org.googlecode.gwt.server;

import org.googlecode.gwt.shared.ApplicationContextData;
import org.googlecode.gwt.shared.BootstrapDataResolver;

import javax.servlet.http.HttpServletRequest;

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
