package com.mycompany.project.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.DeferredCommand;
import com.mycompany.project.client.rest.MyRESTfulServiceAsync;
import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.rest.client.rest.RESTCallback;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.mycompany.project.client.menu.MyMenu;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FrameworkExample implements EntryPoint {

    MyRESTfulServiceAsync myRESTfulServiceAsync = GWT.create(MyRESTfulServiceAsync.class);

    public void onModuleLoad() {

        TemplateManager.setApplicationTitle(new Label("PRIMA APP"));

        PrimoPanel primoPanel = new PrimoPanel();

        primoPanel.setWidth("100%");

        TemplateManager.setHomePage(new ReflectionPanel(), new Label("Reflection Object"));

		TemplateManager.setMenu((SMenu)GWT.create(MyMenu.class));

        myRESTfulServiceAsync.lista(1, "pippo", new RESTCallback() {});
    }

}
