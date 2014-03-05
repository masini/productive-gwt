package org.googlecode.gwt.client.ui.menu;

import com.google.gwt.core.client.EntryPoint;
import org.googlecode.gwt.client.logging.LogText;

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
