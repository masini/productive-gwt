package org.googlecode.gwt.client.ui.template;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Template implements EntryPoint {
	

	
	public void onModuleLoad() {

        //TODO: sostituire il DockPanel con un vertical che nel mezzo ha un horizontal(per fare west centro est)

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setWidth("100%");

		RootPanel.get().add(verticalPanel);

        // TOP
        HorizontalPanel top = TemplateManager.createTop();
		verticalPanel.add(top);

        // HEADER
		verticalPanel.add(PlaceHolder.HEADER.getPanel());

        // MENU
		verticalPanel.add(PlaceHolder.MENU.getPanel());

        // WEST CENTER EAST
        HorizontalPanel middle = TemplateManager.createMiddle();
		verticalPanel.add(middle);

        // FOOTER
        verticalPanel.add(PlaceHolder.FOOTER.getPanel());

		TemplateManager.setNavigationContent(null,false);
	
	}

}
