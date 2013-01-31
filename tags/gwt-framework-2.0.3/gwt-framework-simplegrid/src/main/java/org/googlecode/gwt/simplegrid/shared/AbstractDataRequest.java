package org.googlecode.gwt.simplegrid.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.gen2.table.client.TableModelHelper;

/**
 * Author: Andrea Baroncelli
 * Date: 23-apr-2010
 * Time: 23.16.44
 */
public abstract class AbstractDataRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The first row of table data to request */
	private int startRow;

	/**	The number of rows to request */
	private int numRows;

	/** An ordered list of {@link DataColumnSortInfo} */
	private DataColumnSortList columnSortList;

	/**
	 * Default constructor used for RPC.
	 */
	protected AbstractDataRequest() {
		this(0, 0, null);
	}

	/**
	 * Construct a new {@link AbstractDataRequest}.
	 *
	 * @param startRow the first row to request
	 * @param numRows  the number of rows to request
	 */
	protected AbstractDataRequest(int startRow, int numRows) {
		this(startRow, numRows, null);
	}

	/**
	 * Construct a new {@link AbstractDataRequest} with information about the sort order of columns.
	 *
	 * @param startRow	     the first row to request
	 * @param numRows		 the number of rows to request
	 * @param columnSortList a list of {@link DataColumnSortInfo}
	 */
	protected AbstractDataRequest(int startRow, int numRows, DataColumnSortList columnSortList) {
		this.startRow = startRow;
		this.numRows = numRows;
		this.columnSortList = columnSortList;
	}

	/**
	 * @return the first requested row
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * @return the number of requested rows
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * @param n the number of rows
	 */
	public void setNumRows(int n) {
		this.numRows = n;
	}

	/**
	 * @return the list of {@link DataColumnSortInfo}
	 */
	public DataColumnSortList getColumnSortList() {
		return columnSortList;
	}

	public static abstract class DataColumnSortInfo implements Serializable {
		private static final long serialVersionUID = 1L;

		/** The column index */
		private int column;

		/** True if the sort order is ascending */
		private boolean ascending;

		/**
		 * Default constructor used for RPC.
		 */
		protected DataColumnSortInfo() {
			this(0, true);
		}

		/**
		 * Construct a new {@link DataColumnSortInfo}.
		 *
		 * @param column	the column index
		 * @param ascending true if sorted ascending
		 */
		protected DataColumnSortInfo(int column, boolean ascending) {
			this.column = column;
			this.ascending = ascending;
		}

		protected DataColumnSortInfo(TableModelHelper.ColumnSortList columnSortList) {
			this(columnSortList.getPrimaryColumn(), columnSortList.isPrimaryAscending());
		}

		public abstract boolean exists();

		@Override
		public boolean equals(Object obj) {
			return obj instanceof DataColumnSortInfo && equalsNonTrivially((DataColumnSortInfo) obj);
		}

		/**
		 * Check if this object is equal to another. The objects are equal if the column and ascending values are equal.
		 *
		 * @param csi the other object
		 * @return true if objects are the same
		 */
		protected boolean equalsNonTrivially(DataColumnSortInfo csi) {
			return csi != null && getColumn() == csi.getColumn() && isAscending() == csi.isAscending();
		}

		/**
		 * @return the column index
		 */
		public int getColumn() {
			return column;
		}

		/**
		 * Set the column index.
		 *
		 * @param column the column index
		 */
		public void setColumn(int column) {
			this.column = column;
		}

		/**
		 * @return true if ascending, false if descending
		 */
		public boolean isAscending() {
			return ascending;
		}

		/**
		 * Set whether the sorting is ascending or not.
		 *
		 * @param ascending true if ascending, false if descending
		 */
		public void setAscending(boolean ascending) {
			this.ascending = ascending;
		}
	}

	/**
	 * An ordered list of sorting info where each entry tells us how to sort a single column.
	 * The first entry is the primary sort order, the second entry is the first tie-breaker, and so on.
	 */
	public static abstract class DataColumnSortList implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * A List used to manage the insertion/removal of {@link com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortInfo}.
		 */
		private List<DataColumnSortInfo> infos = new ArrayList<DataColumnSortInfo>();

		/**
		 * Add a {@link DataColumnSortInfo} to this list. If the column already exists,
		 * it will be removed from its current position and placed at the start of
		 * the list, becoming the primary sort info.
		 * <p/>
		 * This add method inserts an entry at the beginning of the list. It does
		 * not append the entry to the end of the list.
		 *
		 * @param sortInfo the {@link DataColumnSortInfo} to add
		 */
		public void add(DataColumnSortInfo sortInfo) {
			add(0, sortInfo);
		}

		/**
		 * Inserts the specified {@link DataColumnSortInfo} at the specified position in
		 * this list. If the column already exists in the sort info, the index will
		 * be adjusted to account for any removed entries.
		 *
		 * @param index the index
		 * @param sortInfo the {@link DataColumnSortInfo} to add
		 */
		public void add(int index, DataColumnSortInfo sortInfo) {
			// Remove sort info for duplicate columns
			int column = sortInfo.getColumn();
			for (int i = 0; i < infos.size(); i++) {
				DataColumnSortInfo curInfo = infos.get(i);
				if (curInfo.getColumn() == column) {
					infos.remove(i);
					i--;
					if (column < index) {
						index--;
					}
				}
			}

			// Insert the new sort info
			infos.add(index, sortInfo);
		}

		/**
		 * Removes all of the elements from this list.
		 */
		public void clear() {
			infos.clear();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof DataColumnSortList && equals((DataColumnSortList) obj);
		}

		/**
		 * Check if this object is equal to another.
		 *
		 * @param csl the other object
		 * @return true if objects are equal
		 */
		public boolean equals(DataColumnSortList csl) {
			// Object is null
			if (csl == null) {
				return false;
			}

			// Check the size of the lists
			int size = size();
			if (size != csl.size()) {
				return false;
			}

			// Compare the entries
			for (int i = 0; i < size; i++) {
				if (!infos.get(i).equals(csl.infos.get(i))) {
					return false;
				}
			}

			// Everything is equal
			return true;
		}

		/**
		 * Get the primary (first) {@link DataColumnSortInfo}.
		 *
		 * @return the primary column sort info
		 */
		public DataColumnSortInfo getPrimaryColumnSortInfoNullSafe() {
			return infos.size() > 0 ? infos.get(0) : getDummyColumnSortInfo();
		}

		protected abstract DataColumnSortInfo getDummyColumnSortInfo();

		/**
		 * Get the primary (first) {@link DataColumnSortInfo}'s column index.
		 *
		 * @return the primary column or -1 if not sorted
		 */
		public int getPrimaryColumn() {
			return getPrimaryColumnSortInfoNullSafe().getColumn();
		}

		/**
		 * Get the primary (first) {@link DataColumnSortInfo}'s sort order.
		 *
		 * @return true if ascending, false if descending
		 */
		public boolean isPrimaryAscending() {
			return getPrimaryColumnSortInfoNullSafe().isAscending();
		}

		/**
		 * Get the primary (first) {@link DataColumnSortInfo}.
		 *
		 * @return the primary column sort info
		 */
		public DataColumnSortInfo getPrimaryColumnSortInfo() {
			return infos.size() > 0 ? infos.get(0) : null;
		}

		/**
		 * Remove a {@link DataColumnSortInfo} from the list.
		 *
		 * @param sortInfo the {@link DataColumnSortInfo} to remove
		 * @return true if infos contained the specified element
		 */
		public boolean remove(Object sortInfo) {
			//noinspection SuspiciousMethodCalls
			return infos.remove(sortInfo);
		}

		/**
		 * @return the number of {@link DataColumnSortInfo} in the list
		 */
		public int size() {
			return infos.size();
		}
	}
}

