package org.googlecode.gwt.template.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Template implements EntryPoint {
	
	public static final String TOP_STYLE_NAME = "placeholder-top";
	public static final String NAVIGATION_STYLE_NAME = "placeholder-navigation";
	public static final String INFO_STYLE_NAME = "placeholder-info";
	
	public void onModuleLoad() {

        //TODO: sostituire il DockPanel con un vertical che nel mezzo ha un horizontal(per fare west centro est)

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setWidth("100%");

		RootPanel.get().add(verticalPanel);

        // TOP
        HorizontalPanel top = createTop();
		verticalPanel.add(top);

        // HEADER
		verticalPanel.add(PlaceHolder.HEADER.getPanel());

        // MENU
		verticalPanel.add(PlaceHolder.MENU.getPanel());

        // WEST CENTER EAST
        HorizontalPanel middle = createMiddle();
		verticalPanel.add(middle);

        // FOOTER
        verticalPanel.add(PlaceHolder.FOOTER.getPanel());

		TemplateManager.setNavigationContent(null,false);
	
	}

    private HorizontalPanel createMiddle() {
        HorizontalPanel middle = new HorizontalPanel();
        middle.add(PlaceHolder.WEST.getPanel());
        PlaceHolder.CONTENT.getPanel().setWidth("100%");
        middle.add(PlaceHolder.CONTENT.getPanel());
        middle.add(PlaceHolder.EAST.getPanel());
        middle.setWidth("100%");
        return middle;
    }

    private HorizontalPanel createTop() {
        HorizontalPanel top = new HorizontalPanel();
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

        return top;
    }
}
