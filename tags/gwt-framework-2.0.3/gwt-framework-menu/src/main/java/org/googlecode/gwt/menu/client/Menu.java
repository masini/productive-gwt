package org.googlecode.gwt.menu.client;

import org.googlecode.gwt.base.client.LogText;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Menu implements EntryPoint {
	
	static {
		LogText.writeOnLogText("==> static " + Menu.class.getName());
	}
	
	public Menu() {
		LogText.writeOnLogText("==> constructor " + Menu.class.getName());
	}
	
	public void onModuleLoad() {
		LogText.writeOnLogText("==> onModuleLoad " + Menu.class.getName());
//		MarkMenu markMenu = (MarkMenu)GWT.create(MarkMenu.class);
//		MenuModel menuModel = markMenu.getMenuModel();
//		
////		MenuRenderSimple renderSimple = new MenuRenderSimple();
//		DefaultMenuRender renderSimple = new DefaultMenuRender();
//		renderSimple.setMenuModel(menuModel);
//		
//		RootPanel mainpage = PlaceHolder.get(PlaceHolder.MENU);
//		mainpage.setSize("100%", "100%");
//		mainpage.add(renderSimple);

	
	}
}
