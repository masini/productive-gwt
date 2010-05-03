package org.googlecode.gwt.simplegrid.shared;

import java.io.Serializable;

/**
 * @deprecated in favor of {@link org.googlecode.gwt.simplegrid.shared.rpc.StandardDataRequest}
 */
@Deprecated
public class DataRequest extends AbstractDataRequest {
	private static final long serialVersionUID = 1L;

	private Filter<? extends Serializable> filter;


	/**
	 * Default constructor used for RPC.
	 */
	public DataRequest() {}

	/**
	 * Construct a new {@link DataRequest}.
	 *
	 * @param startRow the first row to request
	 * @param numRows  the number of rows to request
	 */
	public DataRequest(int startRow, int numRows) {
		super(startRow, numRows);
	}

	/**
	 * Construct a new {@link DataRequest} with information about the sort order of columns.
	 *
	 * @param startRow	   the first row to request
	 * @param numRows		the number of rows to request
	 * @param columnSortList a list of {@link DataColumnSortInfo}
	 */
	public DataRequest(int startRow, int numRows, DataColumnSortList columnSortList) {
		super(startRow, numRows, columnSortList);
	}

	public DataRequest(int startRow, int numRows, DataColumnSortList columnSortList, Filter<? extends Serializable> filter) {
		super(startRow, numRows, columnSortList);
		this.filter = filter;
	}

	public Serializable getCurrentFilter() {
		return filter.returnCurrentFilter();
	}

	public void setFilter(Filter<? extends Serializable> filter) {
		this.filter = filter;
	}

	public Filter<? extends Serializable> getFilter() {
		return filter;
	}
}
