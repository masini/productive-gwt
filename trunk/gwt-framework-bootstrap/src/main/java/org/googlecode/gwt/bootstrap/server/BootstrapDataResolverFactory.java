package org.googlecode.gwt.bootstrap.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.googlecode.gwt.bootstrap.server.dummy.DummyUserInfoResolver;

public interface BootstrapDataResolverFactory {
	public BootstrapDataResolver createUserInfoResolver(Map<String, String> params);
	
	public static class Utils {
		public static BootstrapDataResolverFactory createBootstrapDataResolver() {
			
	        try
	        {
	            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/services/org.apache.commons.logging.LogFactory");
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
	                	return (BootstrapDataResolverFactory) Thread.currentThread().getContextClassLoader().loadClass(factoryClassName).newInstance();
	                }
	            }
	        }
	        catch(Exception ex) { }

	        return  new BootstrapDataResolverFactory() {

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
		}
	}
}
