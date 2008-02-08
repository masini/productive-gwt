package org.googlecode.gwt.header.client;

import com.google.gwt.i18n.client.Constants;


/**
 * Costanti utilizzate dalla DefaultHomePage.
 */
public interface HeaderConstans extends Constants {

	/**
	 * Label del link alla intranet
	 */
	String INTRANET_PAGE_CONTEXT_LABEL();

	/**
	 * Stringa di benvenuto
	 */
	String WELCOME_LABEL();

	/**
	 * Label per l'utente corrente
	 */
	String CURRENT_USER_NAME_LABEL();

	/**
	 * Label per l'utente corrente
	 */
	String CURRENT_USER_ID_LABEL();

	/**
	 * Label per i ruoli utente
	 */
	String ROLES_LABEL();

	/**
	 * Label per i parametri di validation
	 */
	String PARAMETERS_LABEL();

	/**
	 * Label per il nome del cluster
	 */
	String CLUSTER_NAME_LABEL();

	/**
	 * Label per il nome del cluster
	 */
	String SERVER_NAME_LABEL();

	/**
	 * Label per il nome dell'applicazione
	 */
	String APPLICATION_NAME_LABEL();

	/**
	 * Label per il codice dell'applciazione
	 */
	String APPLICATION_CODE_LABEL();

	/**
	 * Label per la versione dell'applicazione
	 */
	String APPLICATION_VERSION_LABEL();

	/**
	 * Label per il server CICS
	 */
	String CICS_SERVER_NAME();
	
	/**
	 * LAbel per il nome del gateway delle connessioni a Cics 
	 */
	String CICS_TRANSACTION_GATEWAY_ADDRESS();
}
