package org.googlecode.gwt.template.client;

import com.google.gwt.i18n.client.Constants;

public interface TemplateCostants  extends Constants {

	/**
	 * Label della intranet 
	 */
	@DefaultStringValue("Torna alla Intranet")
	String INTRANET_PAGE_CONTEXT_LABEL();
	
	/**
	 * Label della homepage
	 */
	@DefaultStringValue("Home")
	String HOME_PAGE_CONTEXT_LABEL();

	/**
	 * Separatore del path
	 */
	@DefaultStringValue("&nbsp;&raquo;&nbsp;")
	String PAGE_CONTEXT_SEPARATOR();

	/**
	 * Intranet URL 
	 */
	@DefaultStringValue("javascript:;")
	String INTRANET_URL();
	
	@DefaultStringValue("My Copyright (c)")
	String COPYRIGHT();
	
}
