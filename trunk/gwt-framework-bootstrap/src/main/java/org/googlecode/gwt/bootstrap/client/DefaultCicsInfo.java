package org.googlecode.gwt.bootstrap.client;

import org.googlecode.gwt.base.client.CicsInfo;

public class DefaultCicsInfo implements CicsInfo {

	private static final long serialVersionUID = 1L;
	
	String serverName;
	String transactionGatewayAddress;

	public DefaultCicsInfo() {
		super();
	}
	
	public DefaultCicsInfo(String serverName, String transactionGatewayAddress) {
		super();
		this.serverName = serverName;
		this.transactionGatewayAddress = transactionGatewayAddress;
	}
	
	public String getServerName() {
		return serverName;
	}

	public String getTransactionGatewayAddress() {
		return transactionGatewayAddress;
	}


}
