package org.googlecode.gwt.base.client;

import java.io.Serializable;

public interface CicsInfo extends Serializable {

	String getServerName();
	
	String getTransactionGatewayAddress();
}
