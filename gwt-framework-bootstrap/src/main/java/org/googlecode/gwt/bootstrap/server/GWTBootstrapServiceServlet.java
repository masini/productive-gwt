package org.googlecode.gwt.bootstrap.server;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.bootstrap.client.HostedModeBootstrapService;
import org.googlecode.gwt.bootstrap.server.dummy.DummyUserInfoResolver;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GWTBootstrapServiceServlet extends RemoteServiceServlet implements HostedModeBootstrapService {
	
	private static final long serialVersionUID = 1L;

	protected transient BootstrapDataResolver resolver;

	protected transient ApplicationContextDataResolver appContextDataResolver;
	
	private BootstrapDataResolverFactory resolverFactory = new BootstrapDataResolverFactory() {

		@Override
		public BootstrapDataResolver createUserInfoResolver(Map<String, String> params) {
			try {
				DefaultBootstrapDataResolver resolver = new DefaultBootstrapDataResolver();
				
				UserInfoResolver userInfoResolver = new DummyUserInfoResolver();
				resolver.setUserInfoResolver(userInfoResolver);
				
				return resolver;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	};
	
	@Override
	public void init() throws ServletException {

		Map<String, String> params = extractInitParameters();
		
		this.resolver = resolverFactory.createUserInfoResolver(params);
		
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
