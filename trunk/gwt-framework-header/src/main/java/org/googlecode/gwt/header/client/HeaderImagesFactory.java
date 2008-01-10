package org.googlecode.gwt.header.client;

import com.google.gwt.core.client.GWT;

/**
 * Factory per l'accesso rapido alle immagini comuni con gestione singleton
 * delle stesse.
 */
public class HeaderImagesFactory {

	/**
	 * Variabile di istanza
	 */
	private static HeaderImages images = null;

	/**
	 * Restituisce l'istanza dell'oggetto che contiene le immagini standard del
	 * framework.
	 * 
	 * @return Una classe che implementa l'interfaccia.
	 */
	public static HeaderImages getInstance() {
		if (images == null) {
			images = (HeaderImages) GWT.create(HeaderImages.class);
		}

		return images;
	}
}
