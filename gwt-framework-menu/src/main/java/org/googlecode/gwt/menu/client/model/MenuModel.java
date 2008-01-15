package org.googlecode.gwt.menu.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Command;

/**
 * Modello di una voce di menu
 */
public abstract class MenuModel {

	/**
	 * Label della voce di menu
	 */
	private String label = null;

	/**
	 * Icona associata alla voce di menu
	 */
	private String icon = null;

	/**
	 * Ruolo necessario alla visualizzazione / utilizzo della voce di menu
	 */
	private String role = null;


	/**
	 * Elenco dei nodi figli
	 * 
	 * @gwt.typeArgs <org.googlecode.gwt.menu.client.model.MenuModel>
	 */
	private List/* <MenuModel> */children = null;

	/**
	 * Nodo padre
	 */
	private MenuModel parent = null;

	/**
	 * Costruttore
	 */
	public MenuModel() {
		children = new ArrayList/* <MenuModel> */();
	}

	/*
	 * Click
	 */

	public abstract Command getCommand() ;


	/*
	 * Gestione struttura
	 */

	/**
	 * Verifica se il nodo corrente ha dei nodi figli
	 * 
	 * @return true o false.
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/**
	 * Aggiunge un nodo al nodo corrente.<br>
	 * Aggiunge il nodo pasato come parametro ai figli del nodo corrente e gli
	 * imposta come padre il nodo corrente.
	 * 
	 * @param position
	 *            posizione in cui inseire il figlio
	 * @param child
	 *            nodo figlio
	 */
	public void addChild(int position, MenuModel child) {
		child.setParent(this);
		children.add(position, child);
	}

	/**
	 * Rimuove il nodo passato come parametro dai figli del nodo.
	 * 
	 * @param child
	 *            Nodo da rimuovere
	 */
	public void removeChild(MenuModel child) {
		children.remove(child);
	}

	/**
	 * Restituisce l'elenco completo dei figli del nodo.
	 * 
	 * @return Restituisce un vettore con i figli del nodo. Se il nodo non ha
	 *         figli restituisce un vettore vuoto.
	 */
	public MenuModel[] getChildren() {
		MenuModel[] child = new MenuModel[children.size()];
		for (int i = 0; i < child.length; i++) {
			child[i] = (MenuModel) children.get(i);
		}

		return child;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public MenuModel getParent() {
		return parent;
	}

	protected void setParent(MenuModel parent) {
		this.parent = parent;
	}
}
