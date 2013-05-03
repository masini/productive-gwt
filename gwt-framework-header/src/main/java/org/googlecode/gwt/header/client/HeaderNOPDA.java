package org.googlecode.gwt.header.client;

import java.util.Date;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.ApplicationContextFactory;
import org.googlecode.gwt.base.client.ApplicationImageBundle;
import org.googlecode.gwt.base.client.ApplicationResources;
import org.googlecode.gwt.template.client.PlaceHolder;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HeaderNOPDA extends HeaderImpl {
	
	public void moduleLoad() {
		
		
		panel = new FlowPanel();
//		panel.setStyleName(TITLE_STYLE_NAME);
		
		// Info panel
		TemplateManager.setInfo(createInfoPanel());

		// Logo and title
		panel.add(logoAndTitlePanel());
		
		TemplateManager.setHeader(panel);
	}

	private HorizontalPanel logoAndTitlePanel() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setWidth("100%");
		
		/* Logo */
		ApplicationImageBundle imageBundle = ApplicationResources.getImageBundle();
		Image logo = new Image(imageBundle.HEADER_LOGO());
		horizontalPanel.add(logo);
		horizontalPanel.setCellHorizontalAlignment(logo, HorizontalPanel.ALIGN_LEFT);
		horizontalPanel.setCellWidth(logo, "33%");
		
		/* App title */
		Panel title = PlaceHolder.get(PlaceHolder.APPLICATION_TITLE);
		horizontalPanel.add(title);
		horizontalPanel.setCellHorizontalAlignment(title, HorizontalPanel.ALIGN_CENTER);
		
		/* Empty cell */
		Label emptyLabel = new Label();
		horizontalPanel.add(emptyLabel);
		horizontalPanel.setCellWidth(emptyLabel, "33%");
		return horizontalPanel;
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
				
				Label date = new Label(DateTimeFormat.getFormat(ApplicationResources.getCostants().DATE_FORMAT()).format(new Date()));
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
