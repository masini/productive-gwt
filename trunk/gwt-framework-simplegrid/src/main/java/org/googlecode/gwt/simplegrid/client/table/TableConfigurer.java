package org.googlecode.gwt.simplegrid.client.table;

import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;

/**
 * @deprecated use {@link StandardGrid.Configurer}
 * @param <ROW> the row type
 */
@Deprecated
public class TableConfigurer<ROW> extends AbstractSimpleGrid.Configurer<TableConfigurer<ROW>, ROW> {
	private SelectionPolicy selectionPolicy = SelectionPolicy.ONE_ROW;
	private int[] columnsWidth;

	public int[] getColumnsWidth() {
		return columnsWidth;
	}

	public TableConfigurer<ROW> setColumnsWidth(int[] columnsWidth) {
		this.columnsWidth = columnsWidth;
		return getThis();
	}

	public TableConfigurer<ROW> setSgSelectionPolicy(SimpleGridPolicy.SelectionPolicy sgSelectionPolicy) {
		setSelectionPolicy(sgSelectionPolicy.equivalentStandardOne);
		return getThis();
	}

	protected SelectionPolicy getSelectionPolicy() {
		return selectionPolicy;
	}

	protected void setSelectionPolicy(SelectionPolicy selectionPolicy) {
		this.selectionPolicy = selectionPolicy;
	}
}
