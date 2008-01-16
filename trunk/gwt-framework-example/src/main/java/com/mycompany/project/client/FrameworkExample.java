package com.mycompany.project.client;

import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.mycompany.project.client.menu.MyMenu;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FrameworkExample implements EntryPoint {

    public void onModuleLoad() {

        TemplateManager.setApplicationTitle(new Label("PRIMA APP"));

        PrimoPanel primoPanel = new PrimoPanel();

        primoPanel.setWidth("100%");

        TemplateManager.setHomePage(primoPanel,new Label("aaaaaa"));

		TemplateManager.setMenu((SMenu)GWT.create(MyMenu.class));

    }

}
