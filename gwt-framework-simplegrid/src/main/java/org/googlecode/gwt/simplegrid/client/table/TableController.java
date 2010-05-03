package org.googlecode.gwt.simplegrid.client.table;

import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.Filter;
import org.googlecode.gwt.simplegrid.shared.AbstractDataRequest;

import java.io.Serializable;

import com.google.gwt.gen2.table.client.TableModelHelper.Request;

/**
 * @deprecated use {@link StandardGrid.Controller}
 */
public abstract class TableController<ROW> extends AbstractSimpleGrid.Controller<ROW, AbstractDataRequest.DataColumnSortList> {
	private Filter<?> filter;

	public static abstract class ReadOnlyColumnsWrapper<E> implements ColumnsWrapper<E> {
		public void set(E row, int index, String cellValue) {}
	}

	@SuppressWarnings("unchecked")
	protected <T extends Serializable> Filter<T> createFilter() {
		return (Filter<T>) filter;
	}

	<T extends Serializable> Filter<T> getFilter() {
		return createFilter();
	}

	public <T extends Serializable> void setFilter(Filter<T> filter) {
		this.filter = filter;
	}

	protected final AbstractDataRequest.DataColumnSortInfo extractDataColumnSortInfo(Request request) {
		return new AbstractDataRequest.DataColumnSortInfo(request.getColumnSortList());
	}

	protected final void requestRows(Request request, Callback<ROW> callback, AbstractDataRequest.DataColumnSortList columnSortList) {
		dataTableRequestRows(new DataRequest(request.getStartRow(), request.getNumRows(), columnSortList, filter), callback);
	}

	protected abstract void dataTableRequestRows(final DataRequest request, final Callback<ROW> callback);

	protected AbstractDataRequest.DataColumnSortList newColumnSortList() {
		return new AbstractDataRequest.DataColumnSortList();
	}
}
