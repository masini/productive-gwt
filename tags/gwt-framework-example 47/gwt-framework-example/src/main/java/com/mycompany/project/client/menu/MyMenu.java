package com.mycompany.project.client.menu;

import org.googlecode.gwt.menu.client.SMenu;

public interface MyMenu extends SMenu{

	/**
	 * @gwt.item_position 3
	 * @gwt.item_label Primo panel
	 * @gwt.item_icon images/user.png
	 * @gwt.user_role primoPanel
	 */
	SecondoCommand primoPanel();
	
	/**
	 * @gwt.item_position 1
	 * @gwt.item_label Command
	 * @gwt.item_icon images/user.png
	 * @gwt.user_role primoCommand
	 */
	PrimoCommand primoCommand();
	
	/**
	 * @gwt.item_position 2
	 * @gwt.item_label Menu 1
	 * @gwt.item_icon images/user.png
	 */
	interface Menu1 {
		
		/**
		 * @gwt.item_position 1
		 * @gwt.item_label Secondo Panel
		 * @gwt.item_icon images/user.png
		 * @gwt.user_role secondoPanel
		 */
		PrimoCommand secondoPanel();
		
		
		/**
		 * @gwt.item_position 2
		 * @gwt.item_label Terzo Panel Popup
		 * @gwt.item_icon images/user.png
		 * @gwt.user_role terzoPanelPopup
		 */
		PrimoCommand terzoPanelPopup();
		
		
		/**
		 * @gwt.item_position 3
		 * @gwt.item_label Sub Menu 1
		 * @gwt.item_icon images/user.png
		 */
		interface Menu11 {

			/**
			 * @gwt.item_position 1
			 * @gwt.item_label Terzo Panel
			 * @gwt.item_icon images/user.png
			 * @gwt.user_role terzoPanel
			 */
			PrimoCommand terzoPanel();
		}

	}

}
