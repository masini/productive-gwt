package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.util.StyleUtil;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Classe base per la creazione di pulsanti per l'header
 */
public abstract class BaseHeaderButton extends Composite {

	/**
	 * Classe del pulsante quando non selezionato
	 */
	public static final String BASE_HEADER_BUTTON_CLASS = "header-button";

	/**
	 * Classe del pulsante al passaggio del mouse o quando selezionato
	 */
	public static final String BASE_HEADER_BUTTON_CLICKED_CLASS = "header-button-clicked";

	/**
	 * Pannello principale del componente
	 */
	protected FocusPanel panel = null;
	
	/**
	 * Pannello contentitore
	 */
	protected FlowPanel main = null;

	/**
	 * Stato del componente
	 */
	private boolean isClicked = false;

	/**
	 * Costruttore
	 */
	public BaseHeaderButton() {
		panel = new FocusPanel();
		initWidget(panel);
	}

	/**
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad() {
		StyleUtil.setCursorPointer(this);
		
		/* Pannello principale con stile per clicked */
		panel.setStyleName(BASE_HEADER_BUTTON_CLASS);
		panel.addMouseOverHandler(new MouseOverHandler() {
			
			public void onMouseOver(MouseOverEvent event) {
				setStyle(true);
			}
		});
		panel.addMouseOutHandler(new MouseOutHandler() {
			
			public void onMouseOut(MouseOutEvent event) {
				if (!isClicked) {
					setStyle(false);
				}
			}
		});
		panel.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				isClicked = !isClicked;
				setStyle(isClicked);
				BaseHeaderButton.this.onClick((Widget) event.getSource(), isClicked);
			}
		});
		
		/* Pannello per la label */
		main = new FlowPanel();
		panel.add(main);
		main.setStyleName(HeaderImpl.INFO_WIDGET_STYLE_NAME);
	}

	/**
	 * Metodo richiamato sul click del pulsante.<br>
	 * Prima di chiamare il metodo viene aggiornato lo stato del pulsante in
	 * modo da sostituire il class.
	 * 
	 * @param sender
	 *            Widget che ha generato l'evento di click
	 * @param isClicked
	 *            Stato del pulsante
	 */
	protected abstract void onClick(Widget sender, boolean isClicked);


	/**
	 * Aggiunge un widget al componente.<br>
	 * Il widget passato come parametro viene utilizzato come label del
	 * pulsante. E' possibile aggiungere un numero arbitrario di elementi.
	 * 
	 * @param widget
	 *            componente da aggiungere
	 */
	public void add(Widget widget) {
		main.add(widget);
	}

	/**
	 * Imposta lo stile del pulsante tra selezionato e non selezionato
	 * 
	 * @param isClicked
	 *            stato da impostare
	 */
	public void setStyle(boolean isClicked) {
		if (isClicked) {
			setStyleName(BASE_HEADER_BUTTON_CLICKED_CLASS);
		} else {
			setStyleName(BASE_HEADER_BUTTON_CLASS);
		}
	}

	/**
	 * Restituisce lo stato del componente
	 * 
	 * @return lo stato del componente
	 */
	public boolean isClicked() {
		return isClicked;
	}

	/**
	 * Imposta lo stato del componente ed aggiorna il css
	 * 
	 * @param isClicked
	 *            Stato del componente
	 */
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
		this.setStyle(isClicked);
	}
}