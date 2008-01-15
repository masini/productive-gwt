package org.googlecode.gwt.header.client;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Implementazione di pulsante per l'header per la visualizzazione di un popup
 */
public abstract class BaseInfoPanelHeaderButton extends BaseHeaderButton implements PopupListener {

	/**
	 * Poupup visualizzato al click sul pulsante
	 */
	private InfoPopupPanel popup;

	protected void onLoad() {
		super.onLoad();

		/* Creo il popup */
		popup = new InfoPopupPanel(true, getPopupImage());
		popup.addPopupListener(this);
		
		addLabels();
		add(HeaderImagesFactory.getInstance().getArrowDownIcon().createImage());
	}

	/**
	 * Restituisce l'icona da associare al popup
	 * 
	 * @return L'icona da inserie nel popup
	 */
	protected abstract Image getPopupImage();

	/**
	 * Aggiunge le labels del pulsante al componente
	 */
	protected abstract void addLabels();

	/**
	 * Aggiunge una coppia etichetta valore al panello corrente.
	 * 
	 * @param label
	 *            Etichetta della voce da aggiungere.
	 * @param value
	 *            Valore della voce da aggiungere.
	 */
	public void addInfo(String label, String value) {
		popup.add(label, value);
	}

	protected void onClick(Widget sender, boolean isClicked) {
		popup.show();
		updatePopupSizeAndPosition(sender);
	}

	/**
	 * Aggiorna la posizione e la dimensione del popup.
	 * 
	 * @param sender
	 *            Widget sul quale è stato effettuato il click.
	 */
	private void updatePopupSizeAndPosition(Widget sender) {
		if (popup.getOffsetWidth() < getOffsetWidth()) {
			popup.setWidth(String.valueOf(getOffsetWidth() - 2));
		}

		int left = sender.getAbsoluteLeft() + sender.getOffsetWidth() - popup.getOffsetWidth();
		int top = sender.getAbsoluteTop() + sender.getOffsetHeight() + 1;

		popup.setPopupPosition(left, top);
	}

	/*
	 * PopupListener
	 */

	/**
	 * @see com.google.gwt.user.client.ui.PopupListener#onPopupClosed(com.google.gwt.user.client.ui.PopupPanel,
	 *      boolean)
	 */
	public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
		this.setClicked(false);
	}
}
