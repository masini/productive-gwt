package org.googlecode.gwt.client.ui.footer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import org.googlecode.gwt.client.logging.LogText;
import org.googlecode.gwt.client.ui.template.TemplateManager;
import org.googlecode.gwt.client.util.ApplicationResources;

import java.util.Date;

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
		
		Label label = new Label(ApplicationResources.getCostants().COPYRIGHT(getYear()));
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		absolutePanel.add(label);
	}
}
