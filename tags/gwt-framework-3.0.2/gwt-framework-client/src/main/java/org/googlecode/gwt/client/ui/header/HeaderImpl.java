package org.googlecode.gwt.client.ui.header;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HeaderImpl {

	/**
	 * Classe del pulsante al passaggio del mouse o quando selezionato
	 */
	public static final String INFO_WIDGET_STYLE_NAME = "header-info-widget";
	
	/**
	 * Classe per il titolo dell'applicazione
	 */
	public static final String TITLE_STYLE_NAME = "header-title";

	/**
	 * Pannello principale del componente
	 */
	protected FlowPanel panel = null;

	/**
	 * Pannello con i pulsanti dell'header
	 */
	protected HorizontalPanel infoPanel = null;

	public void moduleLoad() {};
	
	/**
	 * Restituisce il pannello con le informazioni relative all'utente,
	 * all'applicazione, al contesto
	 * 
	 * @return
	 */
	protected Widget createInfoPanel() {return null;};
	
	/**
	 * Aggiunge un pulsante alla pulsantiera dell'header presente nell'area info
	 * 
	 * @param button
	 *            Pulsante da aggiungere
	 */
	public void addInfoWidget(Widget button) {
		infoPanel.add(button);
	}
	
}
