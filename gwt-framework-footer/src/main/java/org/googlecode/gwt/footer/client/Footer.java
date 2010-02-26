package org.googlecode.gwt.footer.client;

import java.util.Date;

import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Footer implements EntryPoint {
	
	@SuppressWarnings({ "deprecation" })
	private int getYear() {
		return new Date().getYear()+1900;
	}
	
	public void onModuleLoad() {
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		TemplateManager.setFooter(absolutePanel);
		 
		absolutePanel.setSize("100%", "100%");
		
		Label label = new Label("Esselunga (c) "+getYear());
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		absolutePanel.add(label);
	}
}
