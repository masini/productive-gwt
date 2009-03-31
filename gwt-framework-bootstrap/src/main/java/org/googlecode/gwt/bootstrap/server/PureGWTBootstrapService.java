package org.googlecode.gwt.bootstrap.server;

import static org.googlecode.gwt.bootstrap.server.MenuInterfaceRegistry.setMenuInterfaceClass;
import static org.googlecode.gwt.bootstrap.server.MenuInterfaceRegistry.setRoles;
import it.iconmedialab.esselunga.auth.validation.ValidationAccessor;

import java.util.List;

import javax.servlet.ServletException;


import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.bootstrap.client.HostedModeBootstrapService;
import org.googlecode.gwt.bootstrap.server.dummy.HostedModeJavaEESecurityExtractor;
import org.googlecode.gwt.bootstrap.server.security.DefaultJavaEESecurityExtractor;
import org.googlecode.gwt.bootstrap.server.security.JavaEESecurityExtractor;
import org.googlecode.gwt.bootstrap.server.security.ReflectionRoleDefinitionExtractor;
import org.googlecode.gwt.bootstrap.server.security.WebOrReflectionRoleDefinitionExtractor;
import org.googlecode.gwt.bootstrap.server.security.WebXMLRoleDefinitionExtractor;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PureGWTBootstrapService extends RemoteServiceServlet implements HostedModeBootstrapService {
	
	private static final long serialVersionUID = 1L;

	protected transient BootstrapDataResolver resolver;

	protected transient ApplicationContextDataResolver appContextDataResolver;
	
	@Override
	public void init() throws ServletException {
		super.init();
    	try {
			DefaultBootstrapDataResolver resolver = new DefaultBootstrapDataResolver();
			
			ValidationUserInfoResolver userInfoResolver = new ValidationUserInfoResolver();
			userInfoResolver.setApplicationCode(getInitParameterOrDefault("applicationCode", "01"));
			userInfoResolver.setJ2eeMode(Boolean.valueOf(getInitParameterOrDefault("j2eeMode", "true")));
			userInfoResolver.setValidationAccessor((ValidationAccessor) Class.forName(getInitParameterOrDefault("validationAccessor", "org.googlecode.gwt.bootstrap.server.dummy.DummyValidationAccessor")).newInstance());
			
			WebXMLRoleDefinitionExtractor webXMLRoleDefinitionExtractor = new WebXMLRoleDefinitionExtractor(getServletContext().getResourceAsStream("/WEB-INF/web.xml"));
			ReflectionRoleDefinitionExtractor reflectionRoleDefinitionExtractor = new ReflectionRoleDefinitionExtractor();  
			
			JavaEESecurityExtractor javaEESecurityExtractor;
			
			if(ServerUtility.isHostedMode() || getInitParameterOrDefault("forceHostedMode", false)){
				 javaEESecurityExtractor = new HostedModeJavaEESecurityExtractor(new WebOrReflectionRoleDefinitionExtractor(reflectionRoleDefinitionExtractor, webXMLRoleDefinitionExtractor));
			}
			else{
				 javaEESecurityExtractor = new DefaultJavaEESecurityExtractor(new WebOrReflectionRoleDefinitionExtractor(reflectionRoleDefinitionExtractor, webXMLRoleDefinitionExtractor));				
			}
			
			userInfoResolver.setJavaEESecurityExtractor(javaEESecurityExtractor);
			
			resolver.setUserInfoResolver(userInfoResolver);
			
			this.resolver = resolver;
			
			appContextDataResolver = new DefaultApplicationContextDataResolver(resolver);									
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}


	public BootstrapData getBootstrapData(String menuInterfaceClass) {
		
		if(menuInterfaceClass==null){
			throw new IllegalArgumentException("menuInterfaceClass cannot be null");
		}
		
		setMenuInterfaceClass(menuInterfaceClass);
		return getBootstrapData();
	}
	
	public BootstrapData getBootstrapData(List<String> roles) {

		if(roles==null){
			throw new IllegalArgumentException("I ruoli sono nulli");
		}
		setRoles(roles);
		
		return getBootstrapData();
	}
	
	public BootstrapData getBootstrapData() {
		return resolver.getBootstrapData(getThreadLocalRequest());
	}
	
	public ApplicationContextData getApplicationContextData(String appContextName) {
		return appContextDataResolver.getAppContextData(getThreadLocalRequest(), appContextName);
	}

	private String getInitParameterOrDefault(String paramName, String defaultValue) {
    	String retVal = getServletConfig().getInitParameter(paramName);
    	
    	return retVal!=null?retVal:defaultValue;
	}

	private boolean getInitParameterOrDefault(String paramName, boolean defaultValue) {
		
		if(getServletConfig().getInitParameter(paramName)!=null){
			return Boolean.valueOf(getServletConfig().getInitParameter(paramName));
		}
		else {
			return defaultValue;
		}
	}
}
