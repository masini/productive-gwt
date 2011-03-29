package org.googlecode.gwt.bootstrap.server;

import org.googlecode.gwt.base.client.BootstrapData;

import javax.servlet.http.HttpServletRequest;

import static org.googlecode.gwt.bootstrap.server.jmx.JMXServerState.getServerConfigurationAttribute;

public class WebLogicBootstrapDataResolver extends DefaultBootstrapDataResolver{

	private static final String NOTINITIALIZED = "<NOTINITIALIZED>";
	private String serverName = NOTINITIALIZED;
	private String clusterName = NOTINITIALIZED;

	public WebLogicBootstrapDataResolver() {
		super();
	}

	@Override
	public BootstrapData getBootstrapData(HttpServletRequest request) {
		BootstrapData bootstrapData = super.getBootstrapData(request);

		if(NOTINITIALIZED.equals(serverName)){
            Object serverNameO = getServerConfigurationAttribute("ServerConfiguration","Name");
			serverName = (serverNameO==null)?"":serverNameO.toString();
		}
		if(NOTINITIALIZED.equals(clusterName)){
			Object clusterNameO = getServerConfigurationAttribute("ServerConfiguration","Cluster", "Name");
            clusterName = (clusterNameO==null)?"":clusterNameO.toString();
		}
		
		bootstrapData.setServerName(serverName);
		bootstrapData.setClusterName(clusterName);
		return bootstrapData;
	}

}
