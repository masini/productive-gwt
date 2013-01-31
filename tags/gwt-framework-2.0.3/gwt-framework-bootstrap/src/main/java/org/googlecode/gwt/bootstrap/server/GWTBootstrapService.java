package org.googlecode.gwt.bootstrap.server;

import java.io.InputStream;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.bootstrap.client.BootstrapService;
import org.googlecode.gwt.bootstrap.server.security.RoleDefinitionExtractor;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GWTBootstrapService extends RemoteServiceServlet implements BootstrapService {

	private static final long serialVersionUID = 1L;

	@Resource
	protected transient BootstrapDataResolver resolver;
	@Resource
	protected transient RoleDefinitionExtractor roleDefinitionExtractor;	
	
	public GWTBootstrapService() {
		super();
	}
	
	@Override
	public void init() throws ServletException {
		try {
			if(roleDefinitionExtractor!=null && roleDefinitionExtractor.getClass().isAnnotationPresent(Resource.class) ){
				
				Method setWebXML = roleDefinitionExtractor.getClass().getMethod("setWebXML",  new Class[]{InputStream.class}); 
				setWebXML.invoke(roleDefinitionExtractor, new Object[]{getServletContext().getResourceAsStream("/WEB-INF/web.xml")});
			}
		} 
		catch (Exception e) {
			throw new ServletException(e);
		}

	}	
	
	public ApplicationContextData getApplicationContextData(String appContext) {
		return resolver.getAppContextData(getThreadLocalRequest(), appContext);
	}

	public BootstrapData getBootstrapData() {
		return resolver.getBootstrapData(getThreadLocalRequest());
	}

	public void setResolver(BootstrapDataResolver resolver) {
		this.resolver = resolver;
	}
}