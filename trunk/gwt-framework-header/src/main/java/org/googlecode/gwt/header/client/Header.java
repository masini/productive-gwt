package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.ApplicationContextFactory;
import org.googlecode.gwt.base.client.util.StyleUtil;
import org.googlecode.gwt.template.client.PlaceHolder;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Header implements EntryPoint {
	/**
	 * Pannello principale del componente
	 */
	private VerticalPanel panel = null;

	/**
	 * Pannello con i pulsanti dell'header
	 */
	private HorizontalPanel infoPanel = null;

	/**
	 * Widget che implementa il titolo dell'applicazione
	 */
	private SimplePanel title = new SimplePanel();
	public void onModuleLoad() {
		panel = new VerticalPanel();
		
		TemplateManager.setHeader(panel);
		
		panel.setWidth("100%");

		FlexTable table = new FlexTable();
		panel.add(table);
		table.setWidth("100%");

		/* Logo intranet */
		table.setWidget(0, 0, createIntranetLogo());
		table.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		table.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		table.getCellFormatter().setWidth(0, 0, "29%");

		/* titolo */
		DOM.setElementAttribute(title.getElement(), "id", PlaceHolder.APPLICATION_TITLE.getId());
		table.setWidget(0, 1, this.title);
		table.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		table.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		table.getCellFormatter().setWidth(0, 1, "40%");

		/* Info */
		table.setWidget(0, 2, createInfoPanel());
		table.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
		table.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		table.getCellFormatter().setWidth(0, 2, "30%");


		
	}
	
	/**
	 * Restituisce il logo comune a tutte le applicazioni della intranet
	 * 
	 * @return Il widget con il logo della intranet
	 */
	protected Widget createIntranetLogo() {
		Image logo = HeaderImagesFactory.getInstance().getIntranetIcon().createImage();
		StyleUtil.setCursorPointer(logo);

		logo.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Window.open("http://intra.esselunga.net","","");
			}
		});

		return logo;
	}
	
	/**
	 * Restituisce il pannello con le informazioni relative all'utente,
	 * all'applicazione, al contesto
	 * 
	 * @return
	 */
	protected Widget createInfoPanel() {
		infoPanel = new HorizontalPanel();

		ApplicationContextFactory.getApplicationContext(new AsyncCallback() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			public void onSuccess(Object result) {
				addButton(new UserHeaderButton((ApplicationContext)result));
				addButton(new ApplicationHeaderButton((ApplicationContext)result));
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
	public void addButton(BaseHeaderButton button) {
		infoPanel.add(button);
	}
	
}
