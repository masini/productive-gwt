package org.googlecode.gwt.template.client;

import org.googlecode.gwt.template.client.img.TemplateImageBundle;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Template implements EntryPoint {
	
	public static final String TOP_STYLE_NAME = "placeholder-top";
	public static final String NAVIGATION_STYLE_NAME = "placeholder-navigation";
	public static final String INFO_STYLE_NAME = "placeholder-info";
	
	public void onModuleLoad() {
		
		TemplateCostants constants = GWT.create(TemplateCostants.class);
		TemplateManager.setTemplateCostants(constants);
		
		TemplateImageBundle imageBundle = GWT.create(TemplateImageBundle.class);
		TemplateManager.setImageBundle(imageBundle);
		
		DockPanel dockPanel = new DockPanel();
		RootPanel.get().add(dockPanel);
		dockPanel.setWidth("100%");
		
		HorizontalPanel top = new HorizontalPanel();
		
		dockPanel.add(top, DockPanel.NORTH);
		dockPanel.add(PlaceHolder.HEADER.getPanel(), DockPanel.NORTH);
		dockPanel.add(PlaceHolder.MENU.getPanel(), DockPanel.NORTH);
		dockPanel.add(PlaceHolder.FOOTER.getPanel(), DockPanel.SOUTH);
		dockPanel.add(PlaceHolder.EAST.getPanel(), DockPanel.EAST);
		dockPanel.add(PlaceHolder.WEST.getPanel(), DockPanel.WEST);
		dockPanel.add(PlaceHolder.CONTENT.getPanel(), DockPanel.CENTER);

		top.setWidth("100%");
		top.setStyleName(TOP_STYLE_NAME);

		top.add(PlaceHolder.NAVIGATION.getPanel());
		top.setCellVerticalAlignment(PlaceHolder.NAVIGATION.getPanel(), HasVerticalAlignment.ALIGN_MIDDLE);
		top.setCellHorizontalAlignment(PlaceHolder.NAVIGATION.getPanel(), HasHorizontalAlignment.ALIGN_LEFT);
		top.setCellWidth(PlaceHolder.NAVIGATION.getPanel(), "50%");
		PlaceHolder.NAVIGATION.getPanel().setStyleName(NAVIGATION_STYLE_NAME);

		top.add(PlaceHolder.INFO.getPanel());
		top.setCellVerticalAlignment(PlaceHolder.INFO.getPanel(), HasVerticalAlignment.ALIGN_MIDDLE);
		top.setCellHorizontalAlignment(PlaceHolder.INFO.getPanel(), HasHorizontalAlignment.ALIGN_RIGHT);
		top.setCellWidth(PlaceHolder.INFO.getPanel(), "50%");
		PlaceHolder.INFO.getPanel().setStyleName(INFO_STYLE_NAME);

		PlaceHolder.CONTENT.getPanel().setWidth("100%");

		TemplateManager.setNavigationContent(null,false);
	
	}
}
