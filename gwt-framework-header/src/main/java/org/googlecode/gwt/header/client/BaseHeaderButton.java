package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.util.StyleUtil;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * Classe base per la creazione di pulsanti per l'header
 */
public abstract class BaseHeaderButton extends Composite implements MouseListener, ClickListener {

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
	protected HorizontalPanelBorder panel = null;
	
	/**
	 * Tabella che contiene le label
	 */
	protected FlexTable table = null;

	/**
	 * Stato del componente
	 */
	private boolean isClicked = false;

	/**
	 * Costruttore
	 */
	public BaseHeaderButton() {
		panel = new HorizontalPanelBorder();
		initWidget(panel);
	}

	/**
	 * Aggiunge un widget al comonente.<br>
	 * Il widget passato come parametro viene utilizzato come label del
	 * pulsante. E' possibile aggiungere un numero arbitrario di elementi.
	 * 
	 * @param widget
	 *            componente da aggiungere
	 */
	public void add(Widget widget) {
		table.setWidget(0, table.getRowCount() == 0 ? 0 : table.getCellCount(0), widget);
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
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad() {
		StyleUtil.setCursorPointer(this);
		
		/* Creo il pannello */
		panel.setStyleName(BASE_HEADER_BUTTON_CLASS);
		panel.setSize("100%", "100%");
		panel.addMouseListener(this);
		panel.addClickListener(this);
		
		/* Creo la label */
		table = new FlexTable();
		panel.add(table);
		table.setWidth("100%");
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

	/*
	 * ClickListener
	 */
	
	/**
	 * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
	 */
	public void onClick(Widget sender) {
		isClicked = !isClicked;
		setStyle(isClicked);
		onClick(sender, isClicked);
	}
	
	/*
	 * MouseListener
	 */

	/**
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseEnter(com.google.gwt.user.client.ui.Widget)
	 */
	public void onMouseEnter(Widget sender) {
		setStyle(true);
	}

	/**
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseLeave(com.google.gwt.user.client.ui.Widget)
	 */
	public void onMouseLeave(Widget sender) {
		if (!isClicked) {
			setStyle(false);
		}
	}

	/**
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseMove(com.google.gwt.user.client.ui.Widget, int, int)
	 */
	public void onMouseMove(Widget sender, int x, int y) {
	}

	/**
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseDown(com.google.gwt.user.client.ui.Widget, int, int)
	 */
	public void onMouseDown(Widget sender, int x, int y) {
	}

	/**
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseUp(com.google.gwt.user.client.ui.Widget, int, int)
	 */
	public void onMouseUp(Widget sender, int x, int y) {
	}

	/*
	 * Getter and Setter
	 */

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