package org.googlecode.gwt.server.exception;

import java.io.Serializable;

/**
 * Exception throw from client module 
 *
 */
public class BootstrapModelException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -4312833907716907857L;

	public BootstrapModelException() {
		super();
	}

	public BootstrapModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public BootstrapModelException(String message) {
		super(message);
	}

	public BootstrapModelException(Throwable cause) {
		super(cause);
	}

}
