package org.googlecode.gwt.template.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Template implements EntryPoint {
	
	private static final String ID = "id";
	public static final String TOP_STYLE_NAME = "placeholder-top";
	public static final String NAVIGATION_STYLE_NAME = "placeholder-navigation";
	public static final String INFO_STYLE_NAME = "placeholder-info";
	
	public void onModuleLoad() {
		DockPanel dockPanel = new DockPanel();
		RootPanel.get().add(dockPanel);
		dockPanel.setWidth("100%");
		
		HorizontalPanel top = new HorizontalPanel();
		SimplePanel navigation = new SimplePanel();
		SimplePanel info = new SimplePanel();
		SimplePanel header = new SimplePanel();
		SimplePanel menu = new SimplePanel();
		SimplePanel footer = new SimplePanel();
		SimplePanel east = new SimplePanel();
		SimplePanel west = new SimplePanel();
		SimplePanel center = new SimplePanel();
		
		dockPanel.add(top, DockPanel.NORTH);
		dockPanel.add(header, DockPanel.NORTH);
		dockPanel.add(menu, DockPanel.NORTH);
		dockPanel.add(footer, DockPanel.SOUTH);
		dockPanel.add(east, DockPanel.EAST);
		dockPanel.add(west, DockPanel.WEST);
		dockPanel.add(center, DockPanel.CENTER);

		top.setWidth("100%");
		top.setStyleName(TOP_STYLE_NAME);

		top.add(navigation);
		top.setCellVerticalAlignment(navigation, HasVerticalAlignment.ALIGN_MIDDLE);
		top.setCellHorizontalAlignment(navigation, HasHorizontalAlignment.ALIGN_LEFT);
		top.setCellWidth(navigation, "50%");
		navigation.setStyleName(NAVIGATION_STYLE_NAME);

		top.add(info);
		top.setCellVerticalAlignment(info, HasVerticalAlignment.ALIGN_MIDDLE);
		top.setCellHorizontalAlignment(info, HasHorizontalAlignment.ALIGN_RIGHT);
		top.setCellWidth(info, "50%");
		info.setStyleName(INFO_STYLE_NAME);

		center.setWidth("100%");
		
		DOM.setElementAttribute(header.getElement(), ID, PlaceHolder.HEADER.getId());
		DOM.setElementAttribute(navigation.getElement(), ID, PlaceHolder.NAVIGATION.getId());
		DOM.setElementAttribute(info.getElement(), ID, PlaceHolder.INFO.getId());
		DOM.setElementAttribute(menu.getElement(), ID, PlaceHolder.MENU.getId());
		DOM.setElementAttribute(footer.getElement(), ID, PlaceHolder.FOOTER.getId());
		DOM.setElementAttribute(east.getElement(), ID, PlaceHolder.EAST.getId());
		DOM.setElementAttribute(west.getElement(), ID, PlaceHolder.WEST.getId());
		DOM.setElementAttribute(center.getElement(), ID, PlaceHolder.CONTENT.getId());
		
		TemplateManager.setNavigationContent(null,false);
	
	}
}
