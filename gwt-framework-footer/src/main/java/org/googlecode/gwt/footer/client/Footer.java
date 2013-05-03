package org.googlecode.gwt.footer.client;

import java.util.Date;

import org.googlecode.gwt.base.client.ApplicationResources;
import org.googlecode.gwt.base.client.LogText;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Footer implements EntryPoint {
	
	static {
		LogText.writeOnLogText("==> static " + Footer.class.getName());
	}
	
	public Footer() {
		LogText.writeOnLogText("==> constructor " + Footer.class.getName());
	}
	
	private String getYear() {
		return DateTimeFormat.getFormat("yyyy").format(new Date());
	}
	
	public void onModuleLoad() {
		LogText.writeOnLogText("==> onModuleLoad " + Footer.class.getName());
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		TemplateManager.setFooter(absolutePanel);
		 
		absolutePanel.setSize("100%", "100%");
		
		Label label = new Label(ApplicationResources.getCostants().COPYRIGHT() +getYear());
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		absolutePanel.add(label);
	}
}
