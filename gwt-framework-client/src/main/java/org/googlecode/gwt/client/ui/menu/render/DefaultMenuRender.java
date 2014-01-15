package org.googlecode.gwt.client.ui.menu.render;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import org.googlecode.gwt.client.ui.menu.model.MenuModel;

public class DefaultMenuRender extends Composite {

	private MenuModel model = null;
	
	private MenuBar menu = null;

	public DefaultMenuRender() {
		menu = new MenuBar();
		menu.setAutoOpen(true);
		initWidget(menu);
	}
	
	protected void onLoad() {
		buildMenu();
	}
	
	private void buildMenu() {
		if (model != null && model.hasChildren()) {
			MenuItem[] menuItem = getMenuItems(model.getChildren());

			menuItem[0].addStyleName("menu-applicativo");
			menu.addItem(menuItem[0]);

			for (int i = 1; i < menuItem.length; i++) {
				menuItem[i].addStyleName("menu-applicativo");
				menuItem[i].addStyleName("menu-applicativo-border");
				menu.addItem(menuItem[i]);
			}
		}
	}

	private MenuItem[] getMenuItems(MenuModel[] menuItemModel) {
		MenuItem[] menuItem = new MenuItem[menuItemModel.length];
		
		for (int i = 0; i < menuItemModel.length; i++) {
			
			if (menuItemModel[i].hasChildren()) {
				MenuBar subMenu = new MenuBar(true);
				MenuItem[] subMenuItem = getMenuItems(menuItemModel[i].getChildren());
				for (int j = 0; j < subMenuItem.length; j++) {
					subMenu.addItem(subMenuItem[j]);
				}
				
				menuItem[i] = new MenuItem(getMenuItemLabel(menuItemModel[i]), true, subMenu);
			} else {
				menuItem[i] = new MenuItem(getMenuItemLabel(menuItemModel[i]), true, menuItemModel[i].getCommand());
			}
		}

		return menuItem;
	}
	
	private String getMenuItemLabel(MenuModel menuItemModel) {
		StringBuffer label = new StringBuffer();
		
//		label.append("<SPAN style='padding-left: 20px; height: 16px;");
//		if (menuItemModel.getIcon() != null && !"null".equalsIgnoreCase(menuItemModel.getIcon())) {
//			label.append("background: url(").append(menuItemModel.getIcon()).append(") no-repeat 0 center;");
//		}
//		label.append("'>");
		label.append(menuItemModel.getLabel());
//		label.append("</SPAN>");
		
		return label.toString();
	}

	public void setMenuModel(MenuModel model) {
		this.model = model;
	}
}
