package org.googlecode.gwt.footer.client;

import org.googlecode.gwt.base.client.PlaceHolder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Footer implements EntryPoint {
	public void onModuleLoad() {
		RootPanel footer = PlaceHolder.get(PlaceHolder.FOOTER);
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		footer.add(absolutePanel);
		 
		absolutePanel.setSize("100%", "100%");
		
		
		Label label = new Label("sdf asdf -sd f-sdf- sd-fs- f- sd");
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		absolutePanel.add(label);
	}
}
