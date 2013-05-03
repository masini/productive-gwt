package org.googlecode.gwt.header.client;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Implementazione di pulsante per l'header per la visualizzazione di un popup
 */
public abstract class BaseInfoPanelHeaderButton extends BaseHeaderButton {

	/**
	 * Poupup visualizzato al click sul pulsante
	 */
	//private InfoPopupPanel popup;
	
	// PASSATO A PROTECTED DA ANDREA TODESCHINI
	protected InfoPopupPanel popup;

	/**
	 * @see org.googlecode.gwt.header.client.BaseHeaderButton#onLoad()
	 */
	protected void onLoad() {
		super.onLoad();

		/* Creo il popup */
		popup = new InfoPopupPanel(true, getPopupImage());
		popup.addCloseHandler(new CloseHandler<PopupPanel>() {
			
			public void onClose(CloseEvent<PopupPanel> event) {
				BaseInfoPanelHeaderButton.this.setClicked(false);
			}
		});
		
		addLabels();
		//add(HeaderImagesFactory.getInstance().getArrowDownIcon().createImage());
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

	/**
	 * @see org.googlecode.gwt.header.client.BaseHeaderButton#onClick(com.google.gwt.user.client.ui.Widget, boolean)
	 */
	protected void onClick(Widget sender, boolean isClicked) {
		popup.show();
		updatePopupSizeAndPosition(sender);
	}

	/**
	 * Aggiorna la posizione e la dimensione del popup.
	 * 
	 * @param sender
	 *            Widget sul quale e' stato effettuato il click.
	 */
	private void updatePopupSizeAndPosition(Widget sender) {
		if (popup.getOffsetWidth() < getOffsetWidth()) {
			popup.setWidth(String.valueOf(getOffsetWidth() - 2));
		}

		int left = sender.getAbsoluteLeft() + sender.getOffsetWidth() - popup.getOffsetWidth();
		int top = sender.getAbsoluteTop() + sender.getOffsetHeight() + 1;

		popup.setPopupPosition(left, top);
	}
}
