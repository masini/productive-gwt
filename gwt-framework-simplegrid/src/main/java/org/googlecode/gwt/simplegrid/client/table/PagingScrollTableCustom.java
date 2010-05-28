package org.googlecode.gwt.simplegrid.client.table;

import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

import com.google.gwt.gen2.table.client.*;
import static com.google.gwt.gen2.table.client.TableModel.UNKNOWN_ROW_COUNT;
import com.google.gwt.gen2.table.client.property.PreferredWidthProperty;
import org.googlecode.gwt.simplegrid.shared.DataResponse;

/**
 * A customized version of the {@link PagingScrollTable} that updates
 * the header and footer tables to reflect the currently visible rows.
 */
public class PagingScrollTableCustom<ROW> extends PagingScrollTable<ROW> {
	/**
	 * The previous list of visible column definitions.
	 */
	private List<ColumnDefinition<ROW, ?>> lastColDefs;
	private boolean paginable = true;

	/**
	 * Construct a new {@link PagingScrollTableCustom}.
	 *
	 * @param tableModel	  the underlying table model
	 * @param dataTable	   the table used to display data
	 * @param headerTable	 the header table
	 * @param tableDefinition the column definitions
	 */
	public PagingScrollTableCustom(
		TableModel<ROW> tableModel,
		FixedWidthGridCustom dataTable,
		FixedWidthFlexTable headerTable,
		TableDefinition<ROW> tableDefinition
	) {
		super(tableModel, dataTable, headerTable, tableDefinition);

		// Patch che ho trovato su internet per far coesistere i due comportamenti
		//
		// 1) colonne indefinitamente allargabili (ResizePolicy.UNCONSTRAINED)
		// 2) colonne dotate di una larghezza predefinita
		//
		// che altrimenti sarebbero mutuamente esclusivi.

		refreshVisibleColumnDefinitions();

		List<ColumnDefinition<ROW, ?>> columns = getVisibleColumnDefinitions();
		for (int index = 0 ; index < columns.size() ; index++) {
			setColumnWidth(index, columns.get(index).getColumnProperty(PreferredWidthProperty.TYPE).getPreferredColumnWidth());
		}
	}

	@Override
	protected void setData(int firstRow, Iterator<ROW> rows) {
		// Get the visible column definitions
		List<ColumnDefinition<ROW, ?>> colDefs = getTableDefinition().getVisibleColumnDefinitions();
		if (!colDefs.equals(lastColDefs)) {
			lastColDefs = colDefs;
			//updateHeaderTable(colDefs);
		}

		// Set the actual data
		super.setData(firstRow, rows);
	}

	public boolean isPaginable() {
		return paginable;
	}

	public void setPaginable(boolean paginable) {
		this.paginable = paginable;
	}

	public void setRowStyleName(Integer row, String stylename) {
		if (getDataTable().isRowSelected(row)) {
			stylename += " selected";
		}

		getDataTable().getRowFormatter().setStyleName(row, stylename);
	}

	public void setCellStyleName(Integer row, Integer column, String stylename) {
		getDataTable().getCellFormatter().setStyleName(row, column, stylename);
	}

	@Override
	public FixedWidthGridCustom getDataTable() {
		return (FixedWidthGridCustom) super.getDataTable();
	}

	@Override
	public List<ROW> getRowValues() {
		return super.getRowValues();
	}

	void reloadData(int page, boolean forced) {
		getTableModel().setRowCount(UNKNOWN_ROW_COUNT);
		gotoPage(page, forced);
	}

	<R extends Serializable> void setPageSize(DataResponse<R> dataResponse) {
		// Setting paginable option
		if (!isPaginable() && getPageSize() == 0) {
			setPageSize(Math.max(1, dataResponse.getResultRows().size()));
		}
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		if (getDataTable().retrieveDataOnLoad) {
			gotoPage(0, true);
		}
	}

	void addRow(int beforeIndex, ROW row) {
		getRowValues().add(null);
		for(int i = getRowValues().size() - 1; i > beforeIndex; i--) {
			getRowValues().set(i, getRowValues().get(i - 1));
		}

		setRowValue(beforeIndex, row);

		incrementRowCount(1);
	}

	void removeRow(int rowIndex) {
		getRowValues().remove(rowIndex);
		incrementRowCount(-1);
	}

	private void incrementRowCount(int delta) {
		int count = getTableModel().getRowCount();
		if (count == UNKNOWN_ROW_COUNT) {
			if (delta > 0) {
				count = delta;
			}
		} else {
			count += delta;
			if (count < 0) {
				count = 0;
			}
		}
		getTableModel().setRowCount(count);
	}
}
