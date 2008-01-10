package com.mycompany.project.client;

import org.googlecode.gwt.base.client.PlaceHolder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FrameworkExample implements EntryPoint {

	public void onModuleLoad() {

		RootPanel rootTitle = PlaceHolder.get(PlaceHolder.APPLICATION_TITLE);
		rootTitle.add(new Label("PRIMA APP"));

		RootPanel page = PlaceHolder.get(PlaceHolder.CENTER);

		PrimoPanel primoPanel = new PrimoPanel();
		primoPanel.setWidth("100%");
		page.add(primoPanel);

	}
}
