package org.googlecode.gwt.menu.client;

import org.googlecode.gwt.base.client.PlaceHolder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Menu implements EntryPoint {
	public void onModuleLoad() {
		final RootPanel page = PlaceHolder.get(PlaceHolder.CENTER);
		final RootPanel navigation = PlaceHolder.get(PlaceHolder.NAVIGATION);
		
		final MenuBar menuBar = new MenuBar();
		
		RootPanel mainpage = PlaceHolder.get(PlaceHolder.MENU);
		
		mainpage.add(menuBar);
		mainpage.setSize("100%", "100%");
		
		// rootPanel.add(menuBar, 0, 0);
		menuBar.setWidth("100%");

		final MenuBar fileMenuBar = new MenuBar(true);

		// final MenuBar fileMenuFileNew = new MenuBar(true);

		final MenuItem fileMenuFileNew2 = fileMenuBar.addItem("New",
				new Command() {
					public void execute() {
						
						VerticalPanel panel = new VerticalPanel();
						page.clear();
						page.add(panel);
						
						panel.add(new Label("pannell 1"));
						navigation.clear();
						navigation.add(new Label(" navigation pannell 1"));

						
					}
				});

		final MenuItem fileMenuFileOpen = fileMenuBar.addItem("Open",
				new Command() {
					public void execute() {
						
						VerticalPanel panel = new VerticalPanel();
						page.clear();
						page.add(panel);
						
						panel.add(new Label("pannell 2"));
						navigation.clear();
						navigation.add(new Label(" navigation pannell 2"));

						
					}
				});

		fileMenuBar.addItem("<hr>", true, (Command) null);

		final MenuItem fileMenu = menuBar.addItem("File", fileMenuBar);

		final MenuBar menuBar_1 = new MenuBar(true);

		menuBar_1.addItem("Application", new Command() {
			public void execute() {
				
				VerticalPanel panel = new VerticalPanel();
				page.clear();
				page.add(panel);
				
				panel.add(new Label("pannell 3"));
				navigation.clear();
				navigation.add(new Label(" navigation pannell 3"));

				
			}
		});

		menuBar_1.addItem("Computer", (Command) null);

		fileMenuBar.addItem("Exit", menuBar_1);

		menuBar.addItem("Panello", new Command() {
			public void execute() {
				
				VerticalPanel panel = new VerticalPanel();
				page.clear();
				page.add(panel);
				
				panel.add(new Label("pannell 4"));
				navigation.clear();
				navigation.add(new Label(" navigation pannell 4"));
				
			}
		});
	
	}
}
