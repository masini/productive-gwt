package org.googlecode.gwt.template.client;

import com.google.gwt.core.client.GWT;

public class TemplateConstantsFactory {

	/**
	 * Variabile di istanza
	 */
	private static TemplateCostants constans = null;
	
	/**
	 * Restituisce l'istanza dell'oggetto che contiene le costanti standard del framework
	 * @return
	 */
	public static TemplateCostants getInstance() {
		if (constans == null) {
			constans = (TemplateCostants) GWT.create(TemplateCostants.class);
		}
		
		return constans;
	}
}
