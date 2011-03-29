package org.googlecode.gwt.bootstrap.server;

import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.bootstrap.client.BootstrapService;
import org.gwtwidgets.server.spring.GWTSpringController;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

/**
 * A simple implementation of the {@link org.googlecode.gwt.bootstrap.client.BootstrapService } delegating to a
 * {@link org.googlecode.gwt.bootstrap.server.BootstrapDataResolver }.
 */
public class GWTSLBootstrapService extends GWTSpringController implements BootstrapService {
	
    private transient BootstrapDataResolver resolver;
    private transient ApplicationContextDataResolver appContextDataResolver;
    private transient Resource configResource; 
        
    private static final long serialVersionUID = 4361274602975946428L;
        
    public BootstrapData getBootstrapData() {
        return resolver.getBootstrapData(getRequest());
    }			
	
	public ApplicationContextData getApplicationContextData(String appContextName) {
		if(appContextDataResolver == null) {
			appContextDataResolver = new DefaultApplicationContextDataResolver(resolver);			
		}
		return appContextDataResolver.getAppContextData(getRequest(), appContextName);
	}

	public void setConfigResource(Resource configResource) {
		this.configResource = configResource;
	}

    
	@Required
    public void setResolver( BootstrapDataResolver resolver ) {
        this.resolver = resolver;
    }

	public void setAppContextDataResolver(ApplicationContextDataResolver appContextDataResolver) {
		this.appContextDataResolver = appContextDataResolver;
	}

}
