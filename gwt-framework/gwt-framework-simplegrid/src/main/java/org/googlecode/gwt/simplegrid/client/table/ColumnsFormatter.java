package org.googlecode.gwt.simplegrid.client.table;

import java.util.List;
import java.util.ArrayList;

/**
 * Author: Andrea Baroncelli
* Date: 17-mar-2010
* Time: 17.21.31
*/
public final class ColumnsFormatter<ROW> {
	private final List<Definition<?>> definitions;

	public ColumnsFormatter() {
		this.definitions = new ArrayList<Definition<?>>();
	}

	protected String[] titles() {
		String[] titles = new String[definitions.size()];
		int i = 0;
		for (Definition<?> definition : definitions) {
			titles[i++] = definition.title;
		}
		return titles;
	}

	protected int[] widths() {
		int[] sizes = new int[definitions.size()];
		int i = 0;
		for (Definition<?> definition : definitions) {
			sizes[i++] = definition.width;
		}
		return sizes;
	}

	protected String[] propertyPaths() {
		String[] propertyPaths = new String[definitions.size()];
		int i = 0;
		for (Definition<?> definition : definitions) {
			propertyPaths[i++] = definition.propertyPath;
		}
		return propertyPaths;
	}

	public Object get(ROW row, int index) {
		return definitions.get(index).getValue(row);
	}

	public abstract class Definition<VALUE> {
		private final String title;
		private final int width;
		private final String propertyPath;

		protected Definition(String title, int width, String propertyPath) {
			this.title = title;
			this.width = width;
			this.propertyPath = propertyPath;
		}

		protected abstract VALUE getValue(ROW row);

		public final ColumnsFormatter<ROW> add() {
			definitions.add(this);
			return ColumnsFormatter.this;
		}
	}
}
