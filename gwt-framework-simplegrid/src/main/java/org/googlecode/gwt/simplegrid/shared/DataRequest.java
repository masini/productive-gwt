package org.googlecode.gwt.simplegrid.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortInfo;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;

public class DataRequest implements Serializable {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	private Filter filter;
	
	public <T extends Serializable> void setFilter(Filter<T> filter) {
		this.filter = filter;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> Filter<T> getFilter() {
		return filter;
	}
	
	public final static class Filter<T extends Serializable> implements Serializable {
		
		private static final long serialVersionUID = 1L;
		private T currentFilter;
		
		public Filter() {}
		public Filter(T cf) {
			currentFilter = cf;
		}

		public T returnCurrentFilter() {
			return currentFilter;
		}
		
	}

		/**
	     * The number of rows to request.
	     */
	    private int numRows;

	    /**
	     * An ordered list of {@link DataColumnSortInfo}.
	     */
	    private DataColumnSortList columnSortList;

	    /**
	     * The first row of table data to request.
	     */
	    private int startRow;

	    /**
	     * Default constructor used for RPC.
	     */
	    public DataRequest() {
	      this(0, 0, null);
	    }

	    /**
	     * Construct a new {@link Request}.
	     * 
	     * @param startRow the first row to request
	     * @param numRows the number of rows to request
	     */
	    public DataRequest(int startRow, int numRows) {
	      this(startRow, numRows, null);
	    }

	    /**
	     * Construct a new {@link Request} with information about the sort order of
	     * columns.
	     * 
	     * @param startRow the first row to request
	     * @param numRows the number of rows to request
	     * @param columnSortList a list of {@link DataColumnSortInfo}
	     */
	    public DataRequest(int startRow, int numRows, DataColumnSortList columnSortList) {
	      this.startRow = startRow;
	      this.numRows = numRows;
	      this.columnSortList = columnSortList;
	    }
	    
	    @SuppressWarnings("unchecked")
		public <T extends Serializable> DataRequest(int startRow, int numRows, DataColumnSortList columnSortList, Filter filter) {
		      this.startRow = startRow;
		      this.numRows = numRows;
		      this.columnSortList = columnSortList;
		      this.filter = filter;
		    }

	    /**
	     * @return the list of {@link DataColumnSortInfo}
	     */
	    public DataColumnSortList getColumnSortList() {
	      return columnSortList;
	    }

	    /**
	     * @return the number of requested rows
	     */
	    public int getNumRows() {
	      return numRows;
	    }

	    /**
	     * @return the first requested row
	     */
	    public int getStartRow() {
	      return startRow;
	    }
	    
	    public static class DataColumnSortInfo implements Serializable {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
	         * True if the sort order is ascending.
	         */
	        private boolean ascending;

	        /**
	         * The column index.
	         */
	        private int column;

	        /**
	         * Default constructor used for RPC.
	         */
	        public DataColumnSortInfo() {
	          this(0, true);
	        }

	        /**
	         * Construct a new {@link DataColumnSortInfo}.
	         * 
	         * @param column the column index
	         * @param ascending true if sorted ascending
	         */
	        public DataColumnSortInfo(int column, boolean ascending) {
	          this.column = column;
	          this.ascending = ascending;
	        }

	        @Override
	        public boolean equals(Object obj) {
	          if (obj instanceof DataColumnSortInfo) {
	            return equals((DataColumnSortInfo) obj);
	          }
	          return false;
	        }

	        /**
	         * Check if this object is equal to another. The objects are equal if the
	         * column and ascending values are equal.
	         * 
	         * @param csi the other object
	         * @return true if objects are the same
	         */
	        public boolean equals(DataColumnSortInfo csi) {
	          if (csi == null) {
	            return false;
	          }
	          return getColumn() == csi.getColumn()
	              && isAscending() == csi.isAscending();
	        }

	        /**
	         * @return the column index
	         */
	        public int getColumn() {
	          return column;
	        }

	        @Override
	        public int hashCode() {
	          return super.hashCode();
	        }

	        /**
	         * @return true if ascending, false if descending
	         */
	        public boolean isAscending() {
	          return ascending;
	        }

	        /**
	         * Set whether or not the sorting is ascending or descending.
	         * 
	         * @param ascending true if ascending, false if descending
	         */
	        public void setAscending(boolean ascending) {
	          this.ascending = ascending;
	        }

	        /**
	         * Set the column index.
	         * 
	         * @param column the column index
	         */
	        public void setColumn(int column) {
	          this.column = column;
	        }
	      }
	    
	    /**
	     * An ordered list of sorting info where each entry tells us how to sort a
	     * single column. The first entry is the primary sort order, the second entry
	     * is the first tie-breaker, and so on.
	     */
	    public static class DataColumnSortList implements Serializable {
	    
		/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
		/**
	       * A List used to manage the insertion/removal of {@link ColumnSortInfo}.
	       */
	      private List<DataColumnSortInfo> infos = new ArrayList<DataColumnSortInfo>();

	      /**
	       * Add a {@link DataColumnSortInfo} to this list. If the column already exists,
	       * it will be removed from its current position and placed at the start of
	       * the list, becoming the primary sort info.
	       * 
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
	        if (obj instanceof DataColumnSortList) {
	          return equals((DataColumnSortList) obj);
	        }
	        return false;
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
	       * Get the primary (first) {@link DataColumnSortInfo}'s column index.
	       * 
	       * @return the primary column or -1 if not sorted
	       */
	      public int getPrimaryColumn() {
	        DataColumnSortInfo primaryInfo = getPrimaryColumnSortInfo();
	        if (primaryInfo == null) {
	          return -1;
	        }
	        return primaryInfo.getColumn();
	      }

	      /**
	       * Get the primary (first) {@link DataColumnSortInfo}.
	       * 
	       * @return the primary column sort info
	       */
	      public DataColumnSortInfo getPrimaryColumnSortInfo() {
	        if (infos.size() > 0) {
	          return infos.get(0);
	        }
	        return null;
	      }

	      @Override
	      public int hashCode() {
	        return super.hashCode();
	      }

	      /**
	       * Get the primary (first) {@link DataColumnSortInfo}'s sort order.
	       * 
	       * @return true if ascending, false if descending
	       */
	      public boolean isPrimaryAscending() {
	        DataColumnSortInfo primaryInfo = getPrimaryColumnSortInfo();
	        if (primaryInfo == null) {
	          return true;
	        }
	        return primaryInfo.isAscending();
	      }

	      	      /**
	       * Remove a {@link DataColumnSortInfo} from the list.
	       * 
	       * @param sortInfo the {@link DataColumnSortInfo} to remove
	       */
	      public boolean remove(Object sortInfo) {
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
