package org.googlecode.gwt.menu.client.render;


import org.googlecode.gwt.menu.client.model.MenuModel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Questa classe effettua il render del menu utilzzando un elenco di pulsanti<br>
 * 
 */
public class MenuRenderSimplePDA extends Composite{

	/**
	 * Modello del menu
	 */
	private MenuModel menuModel = null;

	/**
	 * Pannello principale dell'applicazione
	 */
	private VerticalPanel panel = null;

	/**
	 * Costruttore
	 */
	public MenuRenderSimplePDA() {
		panel = new VerticalPanel();
		panel.setWidth("100%");
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
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
		button.setWidth("90%");
		button.setStylePrimaryName("pda-menu-button");
		button.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				model.getCommand().execute();
				panel.setVisible(false);
			}
		});
		return button;
	}

}
