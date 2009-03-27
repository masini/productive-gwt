package org.googlecode.gwt.template.client;

import com.google.gwt.core.client.GWT;

public class TemplateConstantsFactory {

	/**
	 * Variabile di istanza
	 */
	private static TemplateConstants constans = null;
	
	/**
	 * Restituisce l'istanza dell'oggetto che contiene le costanti standard del framework
	 * @return
	 */
	public static TemplateConstants getInstance() {
		if (constans == null) {
			constans = (TemplateConstants) GWT.create(TemplateConstants.class);
		}
		
		return constans;
	}
}
