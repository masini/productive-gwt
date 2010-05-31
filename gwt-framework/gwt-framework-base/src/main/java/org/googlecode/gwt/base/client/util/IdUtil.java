package org.googlecode.gwt.base.client.util;

public class IdUtil {
	/**
	 * Id seguenziale per la generazione del token
	 */
	private static int id = 0;

	/**
	 * Prefisso aggiunto alla stringa del token
	 */
	private static String TOKEN_PREFIX = "id_";

	/**
	 * Restituisce l'ultimo token creato
	 * 
	 * @return
	 */
	public static String getLastToken() {
		return TOKEN_PREFIX + id;
	}

	/**
	 * Restituisce un nuovo token univoco.
	 * 
	 * @return
	 */
	public static String getNextToken() {
		id++;
		return getLastToken();
	}

}
