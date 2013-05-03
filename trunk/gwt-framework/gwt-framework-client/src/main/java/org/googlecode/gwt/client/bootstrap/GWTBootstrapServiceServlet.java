package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.googlecode.gwt.server.ApplicationContextDataResolver;
import org.googlecode.gwt.server.BootstrapDataResolverFactory;
import org.googlecode.gwt.server.DefaultApplicationContextDataResolver;
import org.googlecode.gwt.server.MenuInterfaceRegistry;
import org.googlecode.gwt.shared.ApplicationContextData;
import org.googlecode.gwt.shared.BootstrapData;
import org.googlecode.gwt.shared.BootstrapDataResolver;

import javax.servlet.ServletException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GWTBootstrapServiceServlet extends RemoteServiceServlet implements HostedModeBootstrapService {
	
	private static final long serialVersionUID = 1L;

	protected transient BootstrapDataResolver resolver;

	protected transient ApplicationContextDataResolver appContextDataResolver;
	
	@Override
	public void init() throws ServletException {

		Map<String, String> params = extractInitParameters();
		
		BootstrapDataResolverFactory dataResolverFactory = BootstrapDataResolverFactory.Utils.createBootstrapDataResolverFactory(getServletConfig());
		this.resolver = dataResolverFactory.createUserInfoResolver(params);
		
		appContextDataResolver = new DefaultApplicationContextDataResolver(resolver);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> extractInitParameters() {
		Map<String, String> params = new HashMap<String, String>();
		for(Enumeration<String> paramsName = getServletConfig().getInitParameterNames(); paramsName.hasMoreElements();) {
			String param = paramsName.nextElement();
			
			params.put(param, getServletConfig().getInitParameter(param));
		}
		return params;
	}

	public BootstrapData getBootstrapData(String menuInterfaceClass) {
		
		if(menuInterfaceClass==null){
			throw new IllegalArgumentException("menuInterfaceClass cannot be null");
		}
		
		MenuInterfaceRegistry.setMenuInterfaceClass(menuInterfaceClass);
		return getBootstrapData();
	}
	
	public BootstrapData getBootstrapData(List<String> roles) {

		if(roles==null){
			throw new IllegalArgumentException("I ruoli sono nulli");
		}
		MenuInterfaceRegistry.setRoles(roles);
		
		return getBootstrapData();
	}
	
	public BootstrapData getBootstrapData() {
		return resolver.getBootstrapData(getThreadLocalRequest());
	}
	
	public ApplicationContextData getApplicationContextData(String appContextName) {
		return appContextDataResolver.getAppContextData(getThreadLocalRequest(), appContextName);
	}
}
