package org.googlecode.gwt.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.googlecode.gwt.server.dummy.DummyUserInfoResolver;
import org.googlecode.gwt.shared.BootstrapDataResolver;
import org.googlecode.gwt.shared.DefaultBootstrapDataResolver;
import org.googlecode.gwt.shared.UserInfoBootstrapDataResolver;
import org.googlecode.gwt.shared.UserInfoResolver;

import javax.servlet.ServletConfig;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface BootstrapDataResolverFactory {

    String INIT_PARAM_RESOLVER = "resolver";
    String INIT_PARAM_USER_INFO_RESOLVER = "userInfoResolver";

    public BootstrapDataResolver createUserInfoResolver(Map<String, String> params);
	public ServletConfig getServletConfig();
	
	public static class Utils {
		private final static Log log = LogFactory.getLog(Utils.class);
		
		public static BootstrapDataResolverFactory createBootstrapDataResolverFactory(ServletConfig servletConfig) {
			
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

	        return new AbstractBootstrapDataResolverFactory(servletConfig) {

				public BootstrapDataResolver createUserInfoResolver(Map<String, String> params) {
					try {
                        UserInfoBootstrapDataResolver resolver = null;

                        resolver = new InstanceFromParamFactory<UserInfoBootstrapDataResolver>() {
                            @Override
                            UserInfoBootstrapDataResolver defaultInstance(Map<String, String> params) {
                                DefaultBootstrapDataResolver resolver = new DefaultBootstrapDataResolver();
                                resolver.useParams(params);
                                return resolver;
                            }
                        }.newInstance(params, INIT_PARAM_RESOLVER);

                        UserInfoResolver userInfoResolver = new InstanceFromParamFactory<UserInfoResolver>() {
                            @Override
                            UserInfoResolver defaultInstance(Map<String, String> params) {
                                return new DummyUserInfoResolver();
                            }
                        }.newInstance(params, INIT_PARAM_USER_INFO_RESOLVER);

						resolver.setUserInfoResolver(userInfoResolver);
						
						return resolver;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			};
		}
	}

    static abstract class InstanceFromParamFactory<T> {

        T newInstance(Map<String, String> params, String paramName) throws Exception {
            T instance = null;
            if(params.containsKey(paramName)) {
                instance = (T) Class.forName(params.get(paramName)).newInstance();
            } else {
                instance = defaultInstance(params);
            }

            return instance;
        }

        abstract T defaultInstance(Map<String, String> params);

    }

}
