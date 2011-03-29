package org.googlecode.gwt.simplegrid.shared.rpc;

import java.io.Serializable;

/**
 * Classe che non dovrebbe stare qui, bensi' in un modulo a parte, da cui simplegrid dipenda.<br>
 * <br>
 * Author: Andrea Baroncelli
 * Date: 26-mag-2010
 * Time: 13.04.55
 */
public final class Sorter implements Serializable {
	private String propertyPath;
	private boolean ascending;

	public Sorter() {}

	public Sorter(String propertyPath, boolean ascending) {
		setPropertyPath(propertyPath);
		setAscending(ascending);
	}

	public static String toOrderByClause(String alias, Sorter ... sorters) {
		StringBuilder orderByClauseSb = new StringBuilder();
		for (Sorter sorter : sorters) {
			orderByClauseSb.append(orderByClauseSb.length() == 0 ? " order by " : ", ").append(sorter.toOrderByClause(alias));
		}
		return orderByClauseSb.toString();
	}

	private String toOrderByClause(String alias) {
		return alias + "." + propertyPath + (ascending ? " asc" : " desc");
	}

	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
}
