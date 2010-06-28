package org.googlecode.gwt.simplegrid.shared.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.io.Serializable;
import java.util.Collections;

import org.googlecode.gwt.simplegrid.shared.AbstractDataRequest;
import org.googlecode.gwt.simplegrid.shared.Filter;
import org.googlecode.gwt.simplegrid.shared.DataResponse;

/**
 * Author: Andrea Baroncelli
 * Date: 23-apr-2010
 * Time: 23.19.40
 */
public class StandardDataRequest<FILTER extends Serializable> extends AbstractDataRequest {
	private static final long serialVersionUID = 1L;

	private Filter<FILTER> filter;
	private Sorter[] sorters;

	public final void setFilter(Filter<FILTER> filter) {
		this.filter = filter;
	}

	public final Filter<FILTER> getFilter() {
		return filter;
	}

	public Sorter[] getSorters() {
		return sorters;
	}

	public void setSorters(Sorter[] sorters) {
		this.sorters = sorters;
	}

	/**
	 * Default constructor used for RPC.
	 */
	public StandardDataRequest() {}

	public StandardDataRequest(int startRow, int numRows, StandardDataRequest.DataColumnSortList columnSortList, Filter<FILTER> filter) {
		super(startRow, numRows, columnSortList);
		this.filter = filter;
	}

	public final FILTER getCurrentFilter() {
		return filter.returnCurrentFilter();
	}

	public <ROW extends Serializable> void invoke(
		RowRequestServiceAsync<ROW, FILTER> asynchronousService,
		AsyncCallback<DataResponse<ROW>> asyncCallback
	) {
		asynchronousService.requestRows(this, asyncCallback);
	}

	private static final StandardDataRequest<Serializable> DUMMY = new StandardDataRequest<Serializable>() {
		private static final long serialVersionUID = 1L;

		@Override
		public <ROW extends Serializable> void invoke(
			RowRequestServiceAsync<ROW, Serializable> asynchronousService,
			AsyncCallback<DataResponse<ROW>> asyncCallback
		) {
			asyncCallback.onSuccess(
				new DataResponse<ROW>(Collections.<ROW>emptyList(), 0)
			);
		}
	};

	@SuppressWarnings("unchecked")
	public static <FILTER extends Serializable> StandardDataRequest<FILTER> getDummy() {
		return (StandardDataRequest<FILTER>) DUMMY;
	}

	@Override
	public DataColumnSortList getColumnSortList() {
		return (DataColumnSortList) super.getColumnSortList();
	}

	public static class DataColumnSortInfo extends AbstractDataRequest.DataColumnSortInfo {
		private static final long serialVersionUID = 1L;

		private String propertyPath;

		private static final DataColumnSortInfo DUMMY = new DataColumnSortInfo(-1, true, null);

		/**
		 * Default constructor used for RPC.
		 */
		public DataColumnSortInfo() {}

		/**
		 * Construct a new {@link DataColumnSortInfo}.
		 *
		 * @param column	the column index
		 * @param ascending true if sorted ascending
		 */
		public DataColumnSortInfo(int column, boolean ascending) {
			super(column, ascending);
		}

		public DataColumnSortInfo(int column, boolean ascending, String propertyPath) {
			super(column, ascending);
			this.propertyPath = propertyPath;
		}

		public void setPropertyPath(String propertyPath) {
			this.propertyPath = propertyPath;
		}

		public String getPropertyPath() {
			return propertyPath;
		}

		public boolean exists() {
			return !this.equals(DUMMY); // O forse this != DUMMY?
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof DataColumnSortInfo && equalsNonTrivially((DataColumnSortInfo) obj);
		}

		private boolean equalsNonTrivially(DataColumnSortInfo csi) {
			return
				super.equalsNonTrivially(csi)
				&&
				(propertyPath != null ? propertyPath.equals(csi.getPropertyPath()) : csi.getPropertyPath() == null);
		}

		Sorter asSorter() {
			return new Sorter(getPropertyPath(), isAscending());
		}
	}

	/**
	 * An ordered list of sorting info where each entry tells us how to sort a single column.
	 * The first entry is the primary sort order, the second entry is the first tie-breaker, and so on.
	 */
	public static class DataColumnSortList extends AbstractDataRequest.DataColumnSortList {
		private static final long serialVersionUID = 1L;

		public String getPrimaryPropertyPath() {
			return getPrimaryColumnSortInfoNullSafe().getPropertyPath();
		}

		protected DataColumnSortInfo getDummyColumnSortInfo() {
			return DataColumnSortInfo.DUMMY;
		}

		@Override
		public DataColumnSortInfo getPrimaryColumnSortInfoNullSafe() {
			return (DataColumnSortInfo) super.getPrimaryColumnSortInfoNullSafe();
		}
	}
}
