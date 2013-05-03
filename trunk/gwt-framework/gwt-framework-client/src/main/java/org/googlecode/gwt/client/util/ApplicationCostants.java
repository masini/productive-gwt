package org.googlecode.gwt.client.util;

import com.google.gwt.i18n.client.Constants;

public interface ApplicationCostants  extends Constants {

	/**
	 * Intranet Label 
	 */
	@DefaultStringValue("Back to Intranet")
	String INTRANET_PAGE_CONTEXT_LABEL();
	
	/**
	 * Homepage Label
	 */
	@DefaultStringValue("Home")
	String HOME_PAGE_CONTEXT_LABEL();

	/**
	 * Path separator
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
	
	@DefaultStringValue("Welcome")
	String WELCOME_LABEL();
	
	@DefaultStringValue("User")
	String CURRENT_USER_NAME_LABEL();
	
	@DefaultStringValue("Userid")
	String CURRENT_USER_ID_LABEL();
	
	@DefaultStringValue("Cluster name")
	String CLUSTER_NAME_LABEL();
	
	@DefaultStringValue("Server name")
	String SERVER_NAME_LABEL();
	
	@DefaultStringValue("Application name")
	String APPLICATION_NAME_LABEL();
	
	@DefaultStringValue("Application code")
	String APPLICATION_CODE_LABEL();
	
	@DefaultStringValue("Application version")
	String APPLICATION_VERSION_LABEL();
	
	@DefaultStringValue("Roles")
	String ROLES_LABEL();
	
	@DefaultStringValue("Parameters")
	String PARAMETERS_LABEL();
	
	@DefaultStringValue("EEE, dd MMM yyyy") // Ex. Tue, 20 Apr 2010
	String DATE_FORMAT();
	
}
