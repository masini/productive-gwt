package org.googlecode.gwt.template.client;

import org.googlecode.gwt.base.client.PlaceHolder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Template implements EntryPoint {
	
	private static final String ID = "id";
	
	public void onModuleLoad() {
		DockPanel dockPanel = new DockPanel();
		RootPanel.get().add(dockPanel);
		dockPanel.setWidth("100%");
		
		dockPanel.setBorderWidth(1);
		
		
		SimplePanel header = new SimplePanel();
		SimplePanel navigation = new SimplePanel();
		SimplePanel menu = new SimplePanel();
		
		
		
		//header.setWidth("100%");
		SimplePanel footer = new SimplePanel();
//		footer.setWidth("100%");
		SimplePanel east = new SimplePanel();
		//east.setWidth("100%");
		SimplePanel west = new SimplePanel();
		//west.setWidth("100%");
		SimplePanel center = new SimplePanel();
		center.setWidth("100%");
		
		dockPanel.add(header, DockPanel.NORTH);
		dockPanel.add(navigation, DockPanel.NORTH);
		dockPanel.add(menu, DockPanel.NORTH);
		
		dockPanel.add(footer, DockPanel.SOUTH);
		dockPanel.add(east, DockPanel.EAST);
		dockPanel.add(west, DockPanel.WEST);
		dockPanel.add(center, DockPanel.CENTER);
		
		DOM.setElementAttribute(header.getElement(), ID, PlaceHolder.HEADER.getId());
		DOM.setElementAttribute(navigation.getElement(), ID, PlaceHolder.NAVIGATION.getId());
		DOM.setElementAttribute(menu.getElement(), ID, PlaceHolder.MENU.getId());
		DOM.setElementAttribute(footer.getElement(), ID, PlaceHolder.FOOTER.getId());
		DOM.setElementAttribute(east.getElement(), ID, PlaceHolder.EAST.getId());
		DOM.setElementAttribute(west.getElement(), ID, PlaceHolder.WEST.getId());
		DOM.setElementAttribute(center.getElement(), ID, PlaceHolder.CENTER.getId());
	
	}
}
