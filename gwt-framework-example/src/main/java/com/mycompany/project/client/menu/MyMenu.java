package com.mycompany.project.client.menu;

import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.menu.client.SMenuItem;

@SuppressWarnings({"UnusedDeclaration"})
public interface MyMenu extends SMenu{

    @SMenuItem(position = 3,  label="Primo panel", icon="images/user.png", userRoles="primoPanel")
	PrimoCommand primoPanel();
	
    @SMenuItem(position = 1, label = "Command", icon = "images/user.png", userRoles = "primoCommand")
	PrimoCommand primoCommand();
	
    @SMenuItem(position = 2, label = "Menu 1", icon="images/user.png")
	interface Menu1 {
		
        @SMenuItem(position=1, label = "Secondo Panel", icon="images/user.png", userRoles = "secondoPanel")
		SecondoCommand secondoPanel();
		
        @SMenuItem(position = 2, label = "Terzo Panel Popup", icon="images/user.png", userRoles = "terzoPanelPopup")
		TerzoCommand terzoPanelPopup();
		
        @SMenuItem(position = 3, label="Sub Menu 1", icon="images/user.png")
		interface Menu11 {

            @SMenuItem(position = 1, label = "Terzo Panel", icon = "images/user.png", userRoles = "terzoPanel")
			TerzoCommand terzoPanel();
		}
	}

    @SMenuItem(position = 4, label = "SimpleGrid Demo", icon = "images/user.png", userRoles = "terzoPanelPopup")
	QuartoCommand simpleGridPanel();
}
