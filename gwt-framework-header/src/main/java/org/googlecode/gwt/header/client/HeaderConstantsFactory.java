package org.googlecode.gwt.header.client;

import com.google.gwt.core.client.GWT;

/**
 * Factory per l'accesso rapido alle costanti comuni con gestione singleton
 * delle stesse.
 */
public class HeaderConstantsFactory {
	
	/**
	 * Variabile di istanza
	 */
	private static HeaderConstans constans = null;
	
	/**
	 * Restituisce l'istanza dell'oggetto che contiene le costanti standard del framework
	 * @return
	 */
	public static HeaderConstans getInstance() {
		if (constans == null) {
			constans = (HeaderConstans) GWT.create(HeaderConstans.class);
		}
		
		return constans;
	}
}
