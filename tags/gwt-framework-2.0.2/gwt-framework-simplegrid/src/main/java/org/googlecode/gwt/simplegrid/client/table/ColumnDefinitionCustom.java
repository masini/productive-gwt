package org.googlecode.gwt.simplegrid.client.table;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;

/**
 * An {@link AbstractColumnDefinition} applied to {@link ROW} row values.
 *
 * @param <ROW> the data type of the row
 */
public abstract class ColumnDefinitionCustom<ROW> extends AbstractColumnDefinition<ROW, Object> {

	protected final int columnIndex;

	/**
	 * The general grouping of the column definition.
	 */
	public static enum Group {
		DATA("Data");

		private String name;

		private Group(String name) {
			this.name = name;
		}

		/**
		 * @return the name of the group
		 */
		public String getName() {
			return name;
		}
	}

	/**
	 * The name of the column.
	 */
	private String name;

	/**
	 * The {@link Group} that the column is in.
	 */
	private Group group;

	/**
	 * Construct a new {@link ColumnDefinitionCustom}.
	 *
	 * @param name  the name of the column
	 * @param group the column group
	 */
	public ColumnDefinitionCustom(String name, Group group) {
		this(name, group, 0);
	}

	public ColumnDefinitionCustom(String name, int index) {
		this(name, Group.DATA, index);
	}

	public ColumnDefinitionCustom(String name, Group group, int index) {
		this.name = name;
		this.group = group;
		this.columnIndex = index;
	}

	/**
	 * @return the column group, used to create the header row
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @return the name of the column, used to create the header row
	 */
	public String getName() {
		return name;
	}
}
