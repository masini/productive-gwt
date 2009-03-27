package org.googlecode.gwt.bootstrap.server.jmx;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JMXServerState {
	
	private final static Log log = LogFactory.getLog(JMXServerState.class); 
    private final static String RUNTIMESERVICE = "com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
	
	public static Object getServerConfigurationAttribute(String attribute) throws AttributeNotFoundException {
		try {
			InitialContext ctx = new InitialContext();
			MBeanServer server = (MBeanServer) ctx.lookup("java:comp/env/jmx/runtime");
			
			ObjectName serverConfiguration = (ObjectName) server.getAttribute(
					new ObjectName(
							RUNTIMESERVICE), "ServerConfiguration");

			Object name = server.getAttribute(serverConfiguration, attribute);
			return name;
		} catch (AttributeNotFoundException e) {
			throw e;
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}  	  	  	    

	public static String getServerConfigurationAttributeAsString(final String attribute, final String defaultValue) {
		
		String retVal = defaultValue;
		
		try {
			retVal = getServerConfigurationAttribute(attribute).toString();
		} catch (Exception e) {
			log.error("error getting JMX server configuration", e);
		}
		
		return retVal;
	}	
}
