package org.googlecode.gwt.rebind.menu;

public class MenuGeneratorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuGeneratorException() {
		super("(see previous log entries)");
	}

	public MenuGeneratorException(String string) {
		super(string);
	}
}
