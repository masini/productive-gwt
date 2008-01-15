package org.googlecode.gwt.menu.client;

import org.googlecode.gwt.base.client.PlaceHolder;
import org.googlecode.gwt.menu.client.model.MenuModel;
import org.googlecode.gwt.menu.client.render.DefaultMenuRender;
import org.googlecode.gwt.menu.client.render.MenuRenderSimple;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Menu implements EntryPoint {
	public void onModuleLoad() {
		
		MarkMenu markMenu = (MarkMenu)GWT.create(MarkMenu.class);
		MenuModel menuModel = markMenu.getMenuModel();
		
//		MenuRenderSimple renderSimple = new MenuRenderSimple();
		DefaultMenuRender renderSimple = new DefaultMenuRender();
		renderSimple.setMenuModel(menuModel);
		
		RootPanel mainpage = PlaceHolder.get(PlaceHolder.MENU);
		mainpage.setSize("100%", "100%");
		mainpage.add(renderSimple);

	
	}
}
