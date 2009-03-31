package org.googlecode.gwt.header.client;

import java.util.Date;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.ApplicationContextFactory;
import org.googlecode.gwt.template.client.PlaceHolder;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Header implements EntryPoint {

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
	private FlowPanel panel = null;

	/**
	 * Pannello con i pulsanti dell'header
	 */
	private HorizontalPanel infoPanel = null;

	/**
	 * Widget che implementa il titolo dell'applicazione
	 */
	private SimplePanel title = new SimplePanel();
	
	
	public void onModuleLoad() {
		panel = new FlowPanel();
		TemplateManager.setHeader(panel);
		panel.setStyleName(TITLE_STYLE_NAME);
		
		TemplateManager.setInfo(createInfoPanel());

		VerticalPanel table = new VerticalPanel();
		panel.add(table);

		/* titolo */
		DOM.setElementAttribute(title.getElement(), "id", PlaceHolder.APPLICATION_TITLE.getId());
		table.add(this.title);
		table.setCellVerticalAlignment(this.title, HasVerticalAlignment.ALIGN_TOP);
		table.setCellHorizontalAlignment(this.title, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	/**
	 * Restituisce il pannello con le informazioni relative all'utente,
	 * all'applicazione, al contesto
	 * 
	 * @return
	 */
	protected Widget createInfoPanel() {
		infoPanel = new HorizontalPanel();
		infoPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		ApplicationContextFactory.getApplicationContext(new AsyncCallback<ApplicationContext>() {

			public void onFailure(Throwable caught) {
				//TODO: Gestire?
			}

			public void onSuccess(ApplicationContext result) {
				addInfoWidget(new UserHeaderButton(result));
				addInfoWidget(new ApplicationHeaderButton(result));
				
				Label date = new Label(DateTimeFormat.getFormat("dd/MM/yyyy").format(new Date()));
				date.setStyleName(INFO_WIDGET_STYLE_NAME);
				addInfoWidget(date);
			}
		});
		
		return infoPanel;
	}
	
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
