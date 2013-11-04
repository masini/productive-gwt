package org.googlecode.gwt.client.ui.menu.render;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.googlecode.gwt.client.ui.menu.model.MenuModel;
import org.googlecode.gwt.client.util.StyleUtil;

public class ShortcutMenuRender extends Composite {
	private MenuModel model = null;
	private MenuBar menu = null;
	
	private class MenuGo extends Composite implements ClickHandler {
		private HorizontalPanel horizontalPanel = new HorizontalPanel();
		private TextBox menuVeloce = new TextBox();
		private Image menuVeloceGo = new Image("./images/action_go.gif");

		public MenuGo() { 
			initWidget(horizontalPanel);
			setStyleName("gwt-MenuBar");
			menuVeloce.setWidth("45px");
			menuVeloce.setHeight("18px");
			menuVeloce.setMaxLength(4);
			menuVeloceGo.setHeight("18px");
			menuVeloceGo.addClickHandler(this);
			StyleUtil.setCursorPointer(menuVeloceGo);
			
			menuVeloce.addKeyDownHandler(new KeyDownHandler() {
				
				public void onKeyDown(KeyDownEvent event) {
					if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER ) {
						imgClick();
					}
					
				}
			});
			
			horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel.add(menuVeloce);
			horizontalPanel.add(menuVeloceGo);
		}
		
		private MenuModel getMenuItems(MenuModel[] menuItemModel) {
			for (int i = 0; i < menuItemModel.length; i++) {
				if (menuItemModel[i].hasChildren()) {
					MenuModel ret = getMenuItems(menuItemModel[i].getChildren());
					if(ret != null)
						return ret;
				} else {
					String shortcut = menuItemModel[i].getShortcut();
					if (shortcut!= null && shortcut.equals(menuVeloce.getText())) {
						return menuItemModel[i];
					}
				}
			}

			return null;
		}
		
		private void imgClick() {
			if(menuVeloce.getText().equals("")){
				Window.alert("Nessun menù specificato");
			}
			MenuModel menuItems = getMenuItems(model.getChildren());
			if(menuItems!=null){
				menuItems.getCommand().execute();
			}
			else{
				Window.alert("Nessuna voce trovata con questo nome");
			}
		}

		public void onClick(ClickEvent event) {
			imgClick();
		}
	}
	
	

	public ShortcutMenuRender() {
		FlexTable menuFlex = new FlexTable();
		initWidget(menuFlex);

		menu = new MenuBar();
		menuFlex.setWidget(0, 0, menu);
		menuFlex.setWidget(0, 1, new MenuGo());
		menuFlex.setCellPadding(0);
		menuFlex.setCellSpacing(0);
		menuFlex.getColumnFormatter().setWidth(0, "99%");
		
		menu.setAutoOpen(true);
		
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

		// label.append("<SPAN style='padding-left: 20px; height: 16px;");
		// if (menuItemModel.getIcon() != null &&
		// !"null".equalsIgnoreCase(menuItemModel.getIcon())) {
		// label.append("background:
		// url(").append(menuItemModel.getIcon()).append(") no-repeat 0
		// center;");
		// }
		// label.append("'>");
		// label.append(menuItemModel.getLabel());
		//		

		if (menuItemModel.existLevelShortcut(menuItemModel.getParent()
				.getChildren())) {
			Element span = DOM.createSpan();
			DOM.setElementAttribute(span, "className", "menu-applicativo-shortcut");

			if (menuItemModel.getShortcut() != null) {
				DOM.setInnerHTML(span, menuItemModel.getShortcut().trim());
			} else {
				DOM.setInnerHTML(span, "&nbsp;");
			}
			label.append(span.toString());
			label.append(menuItemModel.getLabel());
			
			if(menuItemModel.hasChildren()){
				label.append(" <STRONG>...</STRONG>");
			}
			
			
		} else {
			label.append(menuItemModel.getLabel());
		}

		return label.toString();
	}

	public void setMenuModel(MenuModel model) {
		this.model = model;
	}
}
