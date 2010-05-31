package com.mycompany.project.client.menu;

import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class SecondoCommand implements Command {

	public void execute() {
		SimplePanel s = new SimplePanel();
		TemplateManager.setApplicationContent(s);
		s.clear();
		s.add(new Label("SecondoCommand eseguito"));
		
		TemplateManager.setNavigationContent(new Label("SecondoCommand"),true);
	}

}
