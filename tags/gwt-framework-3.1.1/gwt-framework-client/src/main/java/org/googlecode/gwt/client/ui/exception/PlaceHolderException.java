package org.googlecode.gwt.client.ui.exception;

import java.io.Serializable;

public class PlaceHolderException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -4312833907716907857L;
	
	public PlaceHolderException() {
		super();
	}

	public PlaceHolderException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlaceHolderException(String message) {
		super(message);
	}

	public PlaceHolderException(Throwable cause) {
		super(cause);
	}

}
