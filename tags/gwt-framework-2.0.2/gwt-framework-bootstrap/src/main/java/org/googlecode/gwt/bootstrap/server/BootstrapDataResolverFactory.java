package org.googlecode.gwt.bootstrap.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.googlecode.gwt.bootstrap.server.dummy.DummyUserInfoResolver;

public interface BootstrapDataResolverFactory {
	public BootstrapDataResolver createUserInfoResolver(Map<String, String> params);
	public ServletConfig getServletConfig();
	
	public static class Utils {
		private final static Log log = LogFactory.getLog(Utils.class);
		
		public static BootstrapDataResolverFactory createBootstrapDataResolver(ServletConfig servletConfig) {
			
	        try
	        {
	            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/services/org.googlecode.gwt.bootstrap.server.BootstrapDataResolverFactory");
	            if(is != null)
	            {
	                BufferedReader rd;
	                try
	                {
	                    rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	                }
	                catch(UnsupportedEncodingException e)
	                {
	                    rd = new BufferedReader(new InputStreamReader(is));
	                }
	                String factoryClassName = rd.readLine();
	                rd.close();
	                if(factoryClassName != null && !"".equals(factoryClassName)) {
	                	Class<?> factory =  Thread.currentThread().getContextClassLoader().loadClass(factoryClassName);
	                	return (BootstrapDataResolverFactory)factory.getConstructor(ServletConfig.class).newInstance(servletConfig);
	                }
	            }
	        }
	        catch(Exception ex) { 
	        	log.error("error creating BootstrapDataResolver", ex);
	        }

	        return  new AbstractBootstrapDataResolverFactory(servletConfig) {

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
		}
	}
}
