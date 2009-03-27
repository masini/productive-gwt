package org.googlecode.gwt.bootstrap.server;

import static org.googlecode.gwt.bootstrap.server.jmx.JMXServerState.getServerConfigurationAttributeAsString;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.BootstrapData;

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
			serverName = getServerConfigurationAttributeAsString("Name", "");
		}
		if(NOTINITIALIZED.equals(clusterName)){
			clusterName = getServerConfigurationAttributeAsString("Domain", "");
		}
		
		bootstrapData.setServerName(serverName);
		bootstrapData.setClusterName(clusterName);
		return bootstrapData;
	}

}
