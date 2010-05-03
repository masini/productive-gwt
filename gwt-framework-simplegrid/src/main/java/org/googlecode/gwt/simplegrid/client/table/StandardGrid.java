package org.googlecode.gwt.simplegrid.client.table;

import org.googlecode.gwt.simplegrid.shared.Filter;
import org.googlecode.gwt.simplegrid.shared.rpc.StandardDataRequest;
import org.googlecode.gwt.simplegrid.shared.rpc.RowRequestAsyncController;
import static org.googlecode.gwt.simplegrid.client.table.SimpleGridPolicy.SelectionPolicy.CHECKBOX_IN_HEADER_TOO;

import java.util.*;
import java.io.Serializable;

import com.google.gwt.gen2.table.client.*;
import com.google.gwt.user.client.ui.*;

public class StandardGrid<ROW extends Serializable, FILTER extends Serializable>
	extends AbstractSimpleGrid<
		ROW,
		StandardGrid.Controller<ROW, FILTER>,
		StandardGrid.Configurer<ROW, FILTER>
	>
{
	// Attributi ridondati, perché, per esigenze di retrocompatibilita', preferisco non renderli protected nella superclasse
	private final Configurer<ROW, FILTER> configurer;
	private final PagingScrollTableCustom<ROW> pagingScrollTable;

	private StandardGrid(Controller<ROW, FILTER> tableController, Configurer<ROW, FILTER> tableConfigurer, String... columnsName) {
		super(tableController, tableConfigurer, columnsName);
		this.configurer = tableConfigurer;

		// Create the tables
		FixedWidthGridCustom dataTable = createDataTable();
		FixedWidthFlexTable headerTable = createHeaderTable(dataTable);
		FixedWidthFlexTable footerTable = createFooterTable();

		pagingScrollTable = createPagingScrollTable(headerTable, dataTable, footerTable);
	}

	/**
	 * @return the newly created data table.
	 */
	private FixedWidthGridCustom createDataTable() {
		FixedWidthGridCustom dataTable = new FixedWidthGridCustom(
			configurer.sgSelectionPolicy,
			configurer.isRetrieveDataOnLoad(),
			configurer.columnsFormatter.propertyPaths()
		) {
			@Override
			protected int getInputColumnWidth() {
				// Dimensione campo di selezione con checkbox
				return 25;
			}
		};
		dataTable.setSelectionEnabled(true);

		return dataTable;
	}

	/**
	 * @param dataTable the data grid
	 * @return the new header table
	 */
	private FixedWidthFlexTable createHeaderTable(final FixedWidthGridCustom dataTable) {
		FixedWidthFlexTable headerTable = new FixedWidthFlexTable();

		if (columnsName != null && columnsName.length > 0) {
			dataTable.configure(headerTable, columnsName);
		}

		return headerTable;
	}

	@Override
	public void reloadData(int page, boolean forced) {
		super.reloadData(page, forced);

		if (configurer.sgSelectionPolicy == CHECKBOX_IN_HEADER_TOO) {
			((CheckBox) pagingScrollTable.getHeaderTable().getWidget(0, 0)).setValue(false);
		}
	}

	public void reloadData(int page, boolean forced, Filter<FILTER> filter) {
		tableController.setFilter(filter);

		reloadData(page, forced);
	}

	public void createFilterAndReloadData(int page, boolean forced, FILTER filter) {
		reloadData(page, forced, new Filter<FILTER>(filter));
	}

	public FixedWidthGridCustom getDataTable() {
		return pagingScrollTable.getDataTable();
	}

	public void setData(Collection<ROW> rows) {
		pagingScrollTable.setData(0, rows.iterator());
	}

	public void insertRow(int beforeIndex, ROW row) {
		getDataTable().insertRow(beforeIndex);
		setRowValue(beforeIndex, row);
	}

	public void insert(Collection<ROW> rows) {
		int i = 0;
		for (Iterator<ROW> rowIt = rows.iterator() ; rowIt.hasNext() ; i++) {
			insertRow(i, rowIt.next());
		}
	}

	public <OLD_ROW> List<ROW> insert(Collection<OLD_ROW> oldRows, Converter<OLD_ROW, ROW> converter) {
		List<ROW> rows = new LinkedList<ROW>();
		int i = 0;
		for (Iterator<OLD_ROW> oldRowsIt = oldRows.iterator() ; oldRowsIt.hasNext() ; i++) {
			ROW row = converter.convert(oldRowsIt.next());
			rows.add(row);
			insertRow(i, row);
		}
		return rows;
	}

	public List<ROW> remove(Set<Integer> rowIndexes) {
		Integer[] sortedRowsIndexes = rowIndexes.toArray(new Integer[rowIndexes.size()]);
		java.util.Arrays.sort(sortedRowsIndexes);

		return remove(sortedRowsIndexes);
	}

	public List<ROW> remove(Integer... sortedRowsIndexes) {
		List<ROW> removedRows = new LinkedList<ROW>();

		FixedWidthGridCustom dataTable = getDataTable();
		for (int index = sortedRowsIndexes.length - 1 ; index >= 0 ; index--) {
			removedRows.add(getRowValue(sortedRowsIndexes[index]));
			dataTable.removeRow(sortedRowsIndexes[index]);
		}

		return removedRows;
	}

	public List<ROW> removeAllRows() {
		Integer[] sortedRowIndexes = new Integer[getRows().size()];
		for (int i = 0 ; i < sortedRowIndexes.length ; i++) {
			sortedRowIndexes[i] = i;
		}
		return remove(sortedRowIndexes);
	}

	public final boolean populateLike(StandardGrid<ROW, FILTER> simpleGrid) {
		if (!hasSameRowsAs(simpleGrid)) {
			removeAllRows();
			insert(simpleGrid.getRows());
			return true;
		}
		else {
			return false;
		}
	}

	public static abstract class Controller<ROW extends Serializable, FILTER extends Serializable> extends AbstractSimpleGrid.Controller<ROW, StandardDataRequest.DataColumnSortList> {
		private Filter<FILTER> filter;

		public static abstract class ReadOnlyColumnsWrapper<E> implements ColumnsWrapper<E> {
			public final void set(E row, int index, String cellValue) {}
		}

		public void setFilter(Filter<FILTER> filter) {
			this.filter = filter;
		}

		protected final StandardDataRequest.DataColumnSortInfo extractDataColumnSortInfo(TableModelHelper.Request request) {
			FixedWidthGridCustom.ColumnSortInfo primaryColumnSortInfo = (FixedWidthGridCustom.ColumnSortInfo) request.getColumnSortList().getPrimaryColumnSortInfo();

			return new StandardDataRequest.DataColumnSortInfo(
				primaryColumnSortInfo != null ? primaryColumnSortInfo.getColumn() : -1,
				primaryColumnSortInfo == null || primaryColumnSortInfo.isAscending(),
				primaryColumnSortInfo != null ? primaryColumnSortInfo.getPropertyPath() : null
			);
		}

		protected final void requestRows(TableModelHelper.Request request, Callback<ROW> callback, StandardDataRequest.DataColumnSortList columnSortList) {
			requestRows(new StandardDataRequest<FILTER>(request.getStartRow(), request.getNumRows(), columnSortList, filter), callback);
		}

		protected abstract void requestRows(final StandardDataRequest<FILTER> request, final Callback<ROW> callback);

		protected StandardDataRequest.DataColumnSortList newColumnSortList() {
			return new StandardDataRequest.DataColumnSortList();
		}
	}

	public static class Configurer<ROW extends Serializable, FILTER extends Serializable> extends AbstractSimpleGrid.Configurer<Configurer<ROW, FILTER>, ROW> {
		private final ColumnsFormatter<ROW> columnsFormatter;
		private final Controller<ROW, FILTER> controller;

		private SimpleGridPolicy.SelectionPolicy sgSelectionPolicy = SimpleGridPolicy.SelectionPolicy.ONE_ROW;

		public Configurer(RowRequestAsyncController<ROW, FILTER> asyncController) {
			columnsFormatter = asyncController.columnsFormatter;
			controller = asyncController.controller;
		}

		public int[] getColumnsWidth() {
			return columnsFormatter.widths();
		}

		public Configurer<ROW, FILTER> setSgSelectionPolicy(SimpleGridPolicy.SelectionPolicy sgSelectionPolicy) {
			this.sgSelectionPolicy = sgSelectionPolicy;
			return getThis();
		}

		public StandardGrid<ROW, FILTER> build() {
			return new StandardGrid<ROW, FILTER>(controller, this, columnsFormatter.titles());
		}
	}
}

