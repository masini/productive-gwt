package com.mycompany.project.client.menu;

import org.googlecode.gwt.base.client.PlaceHolder;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SecondoCommand implements Command {

	public void execute() {
		RootPanel page = PlaceHolder.get(PlaceHolder.CENTER);

		page.clear();
		page.add(new Label("SecondoCommand eseguito"));
	}

}
