package org.googlecode.gwt.footer.client;

import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Footer implements EntryPoint {
	public void onModuleLoad() {
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		TemplateManager.setFooter(absolutePanel);
		 
		absolutePanel.setSize("100%", "100%");
		
		
		Label label = new Label("sdf asdf -sd f-sdf- sd-fs- f- sd");
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		absolutePanel.add(label);
	}
}
