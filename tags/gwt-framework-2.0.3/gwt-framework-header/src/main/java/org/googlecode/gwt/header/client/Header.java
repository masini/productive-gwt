 package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.LogText;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Header implements EntryPoint {

	
	private static HeaderImpl impl = GWT.create(HeaderImpl.class);

	static {
		LogText.writeOnLogText("==> static " + Header.class.getName());
	}
	
	public Header() {
		LogText.writeOnLogText("==> constructor " + Header.class.getName());
	}
	
	public void onModuleLoad() {
		LogText.writeOnLogText("==> onModuleLoad " + Header.class.getName());
		impl.moduleLoad();
	}
	
	/**
	 * Restituisce il pannello con le informazioni relative all'utente,
	 * all'applicazione, al contesto
	 * 
	 * @return
	 */
	protected Widget createInfoPanel() {
		return impl.createInfoPanel();
	}
	
	/**
	 * Aggiunge un pulsante alla pulsantiera dell'header presente nell'area info
	 * 
	 * @param button
	 *            Pulsante da aggiungere
	 */
	public void addInfoWidget(Widget button) {
		impl.addInfoWidget(button);
	}
	
}
