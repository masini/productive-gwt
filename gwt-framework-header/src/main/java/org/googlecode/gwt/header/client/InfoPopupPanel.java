package org.googlecode.gwt.header.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Pannello per la realizzazione dei popup di informazione dell'header
 */
public class InfoPopupPanel extends PopupPanel {
	
	/**
	 * Classe principale del componete
	 */
	private static final String INFO_POPUP_PANEL_CLASS = "info-popup";

	/**
	 * Classe associata alla tabella principale 
	 */
	private static final String INFO_POPUP_PANEL_TABLE_CLASS = "table";

	/**
	 * Classe associata alle label
	 */
	private static final String INFO_POPUP_PANEL_LABEL_CLASS = "label";
	
	/**
	 * Classe associata ai valori
	 */
	private static final String INFO_POPUP_PANEL_VALUE_CLASS = "value";
	
	/**
	 * Classe asseciata
	 */
	private static final String INFO_POPUP_PANEL_ICON_CLASS = "icon";
	private static final String INFO_POPUP_PANEL_INFO_CLASS = "info";
	
	/**
	 * Icona associata al popup
	 */
	protected Image icon = null;

	/**
	 * Elenco delle label delle voci presenti nel pannello.
	 */
	protected List labels = new ArrayList();

	/**
	 * Valori associati alle voci del pannello
	 */
	protected List values = new ArrayList();

	/**
	 * Costruttore.
	 * 
	 * @param autoHide
	 *            Nasconde automaticamente il pannello true/false
	 * @param icon
	 *            Icona da mostrare sul lato del pannello.
	 */
	public InfoPopupPanel(boolean autoHide, Image icon) {
		super(true);
		this.icon = icon;
	}

	/**
	 * @see com.google.gwt.user.client.ui.Panel#onLoad()
	 */
	protected void onLoad() {
		this.setStyleName(INFO_POPUP_PANEL_CLASS);
		
		clear();

		FlexTable table = new FlexTable();
		this.add(table);
		table.setStyleName(INFO_POPUP_PANEL_TABLE_CLASS);
		table.setCellPadding(0);
		table.setCellSpacing(0);
		
		table.setWidget(0, 0, icon);
		table.getCellFormatter().setStyleName(0, 0, INFO_POPUP_PANEL_ICON_CLASS);
		table.getCellFormatter().setAlignment(0, 0, HasAlignment.ALIGN_CENTER, HasAlignment.ALIGN_TOP);
		
		VerticalPanel infoPanel = new VerticalPanel();
		table.setWidget(0, 1, infoPanel);
		infoPanel.setStyleName(INFO_POPUP_PANEL_INFO_CLASS);
		
		for (int i = 0; i < labels.size(); i++) {
			Label title = new HTML((String) labels.get(i));
			infoPanel.add(title);
			title.setStyleName(INFO_POPUP_PANEL_LABEL_CLASS);

			Label info = new HTML((String) values.get(i));
			infoPanel.add(info);
			info.setStyleName(INFO_POPUP_PANEL_VALUE_CLASS);
		}
	}

	/**
	 * Aggiunge una coppia etichetta valore al panello corrente.
	 * 
	 * @param label
	 *            Etichetta della voce da aggiungere.
	 * @param value
	 *            Valore della voce da aggiungere.
	 */
	public void add(String label, String value) {
		if (label != null || value != null) {
			labels.add(label != null ? label : "&nbsp;");
			values.add(value != null ? value : "&nbsp;");
		}
	}
}
