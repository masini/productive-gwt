package org.googlecode.gwt.server.jmx;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;


public class JMXServerState {
	
    private final static String RUNTIMESERVICE = "com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
	
    public static MBeanServer getMBeanServer() {
        try {
            InitialContext ctx = new InitialContext();
            MBeanServer server = (MBeanServer) ctx.lookup("java:comp/env/jmx/runtime");
            ctx.close();
            return server;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Object getServerConfigurationAttribute(String...paths) {
        try {
            MBeanServer server = getMBeanServer();

            Object currentPath = new ObjectName(RUNTIMESERVICE);
            for(String nextPath: paths) {
                if( currentPath==null ) {
                    break;
                }
                currentPath = server.getAttribute((ObjectName) currentPath, nextPath);
            }

            return currentPath;
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
		}
		
		return retVal;
	}	
}
