package org.googlecode.gwt.client.ui.menu.render;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import org.googlecode.gwt.client.ui.menu.model.MenuModel;

/**
 * Questa classe effettua il render del menu utilzzando un elenco di pulsanti<br>
 * 
 */
public class MenuRenderSimple extends Composite {

	/**
	 * Modello del menu
	 */
	private MenuModel menuModel = null;

	/**
	 * Pannello principale dell'applicazione
	 */
	private HorizontalPanel panel = null;

	/**
	 * Costruttore
	 */
	public MenuRenderSimple() {
		panel = new HorizontalPanel();
		panel.setWidth("100%");
		initWidget(panel);

	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public void onResize(int width) {
		panel.setWidth(width + "px");
	}

	/**
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad() {
		if (menuModel != null)
			createMenu(menuModel.getChildren());
	}

	/**
	 * Effettua la creazione del menu
	 * 
	 * @param models
	 */
	private void createMenu(MenuModel[] models) {
		for (int i = 0; i < models.length; i++) {
			MenuModel model = models[i];

			if (model.hasChildren()) {
				createMenu(model.getChildren());
			} else {
				panel.add(getButton(model));
			}
		}
	}

	/**
	 * Effettua la creazione del pulsante del menu
	 * 
	 * @param model
	 * @return
	 */
	private Button getButton(final MenuModel model) {
		Button button = new Button();
		button.setText(model.getLabel());
		button.setWidth("100%");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				model.getCommand().execute();
			}
		});
		
		return button;
	}

}
