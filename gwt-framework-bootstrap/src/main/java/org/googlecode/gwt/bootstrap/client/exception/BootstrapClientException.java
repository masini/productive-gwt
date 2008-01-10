package org.googlecode.gwt.bootstrap.client.exception;

import java.io.Serializable;

/**
 * Exception throw from client module 
 *
 */
public class BootstrapClientException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -4312833907716907857L;
	
	public BootstrapClientException() {
		super();
	}

	public BootstrapClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public BootstrapClientException(String message) {
		super(message);
	}

	public BootstrapClientException(Throwable cause) {
		super(cause);
	}

}
