package org.googlecode.gwt.simplegrid.client.table;

import java.util.*;
import static java.util.Collections.sort;
import java.io.Serializable;

import com.google.gwt.gen2.table.client.*;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.TableEvent;
import com.google.gwt.gen2.table.event.client.PagingFailureEvent;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import org.googlecode.gwt.simplegrid.shared.AbstractDataRequest;
import org.googlecode.gwt.simplegrid.shared.DataResponse;

/**
 * Author: Andrea Baroncelli
 * Date: 23-apr-2010
 * Time: 18.15.40
 */
abstract class AbstractSimpleGrid<
		ROW,
		CONTROLLER extends AbstractSimpleGrid.Controller<ROW, ?>,
		CONFIGURER extends AbstractSimpleGrid.Configurer<CONFIGURER, ROW>
	>
	extends Composite
{
	private final FlexTable container = new FlexTable();

	protected final String[] columnsName;
	protected final CONTROLLER tableController;
	private final CONFIGURER tableConfigurer;

	private CachedTableModel<ROW> cachedTableModel;
	private DefaultTableDefinition<ROW> tableDefinition;
	private PagingScrollTableCustom<ROW> pagingScrollTable;
	private HorizontalPanel topWidgetPanel;

	protected AbstractSimpleGrid(CONTROLLER tableController, CONFIGURER tableConfigurer, String... columnsName) {
		this.tableController = tableController;
		this.tableConfigurer = tableConfigurer;
		this.columnsName = columnsName;

		//init table container
		initWidget(container);
	}

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onLoad() {
		super.onLoad();

		container.setVisible(tableConfigurer.isVisible());
		container.setWidth(tableConfigurer.getWidth());
		container.setHeight(tableConfigurer.getHeight());
		container.setCellPadding(3);
		container.setCellSpacing(0);

		if (tableConfigurer.getTopWidget() == null) {
			// Get formatter for container object
			final FlexCellFormatter formatter = container.getFlexCellFormatter();
			formatter.setHeight(0, 0, "100%");
			formatter.setWidth(0, 0, "100%");
			formatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

			//add scrollTable to layout element
			container.setWidget(0, 0, pagingScrollTable);

			// If paginable create a paging options element
			if (tableConfigurer.isPaginable()) {
				container.setWidget(1, 0, new PagingOptions(pagingScrollTable));
			}
		}
		else {
			final FlexCellFormatter formatter = container.getFlexCellFormatter();

			formatter.setWidth(1, 0, "100%");
			formatter.setHeight(1, 0, "100%");
			formatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);

			Widget widget;
			if (tableConfigurer.isPaginable()) {
				// Per guadagnare spazio, metto la barra di paginazione accanto al titolo
				DockPanel header = new DockPanel();
				header.setWidth(tableConfigurer.getWidth());

				topWidgetPanel = new HorizontalPanel();
				topWidgetPanel.add(tableConfigurer.getTopWidget());
				header.add(topWidgetPanel, DockPanel.WEST);
				header.setCellHorizontalAlignment(topWidgetPanel, HasHorizontalAlignment.ALIGN_LEFT);

				PagingOptions pagingOptions = new PagingOptions(pagingScrollTable);
				header.add(pagingOptions, DockPanel.EAST);
				header.setCellHorizontalAlignment(pagingOptions, HasHorizontalAlignment.ALIGN_RIGHT);

				widget = header;
			}
			else {
				widget = tableConfigurer.getTopWidget();
			}

			container.setWidget(0, 0, widget);
			container.setWidget(1, 0, pagingScrollTable); // Qui avviene il caricamento dei dati, in PagingScrollTableCustom.onLoad
		}

		onModuleLoaded();
	}

	protected final PagingScrollTableCustom<ROW> createPagingScrollTable(
		FixedWidthFlexTable headerTable,
		FixedWidthGridCustom dataTable,
		FixedWidthFlexTable footerTable
	) {
		// Setup the controller
		cachedTableModel = new CachedTableModel<ROW>(tableController);

		int pageSize = (tableConfigurer.getWithCache() ? tableConfigurer.getPageSize() : 0);
		cachedTableModel.setPreCachedRowCount(pageSize);
		cachedTableModel.setPostCachedRowCount(pageSize);

		// Create a TableCellRenderer
		createTableDefinition();

		// Create the scroll table
		pagingScrollTable = new PagingScrollTableCustom<ROW>(cachedTableModel, dataTable, headerTable, tableDefinition);
		pagingScrollTable.setFooterTable(footerTable);
		pagingScrollTable.setEmptyTableWidget(new HTML("Nessun dato da visualizzare"));
		pagingScrollTable.setPaginable(tableConfigurer.isPaginable());

		// Allow setting paging size to pagingScrollTable object
		tableController.setPagingScrollTable(pagingScrollTable);

		// Setup the bulk renderer
		FixedWidthGridBulkRenderer<ROW> bulkRenderer = new FixedWidthGridBulkRenderer<ROW>(dataTable, pagingScrollTable);
		pagingScrollTable.setBulkRenderer(bulkRenderer);

		// Formatting table
		pagingScrollTable.setCellPadding(tableConfigurer.getCellpadding());
		pagingScrollTable.setCellSpacing(tableConfigurer.getCellspacing());
		pagingScrollTable.setResizePolicy(tableConfigurer.getResizePolicy());
		pagingScrollTable.setScrollPolicy(tableConfigurer.getScrollPolicy());
		pagingScrollTable.setPageSize(tableConfigurer.getPageSize());
		pagingScrollTable.setHeight("100%");

		// Setting sortable option
		pagingScrollTable.setSortPolicy(tableConfigurer.isSortable() ? SortPolicy.SINGLE_CELL : SortPolicy.DISABLED);

		return pagingScrollTable;
	}

	protected void onModuleLoaded() {
		//nothing
	}

	private void createTableDefinition() {
		// Create the table definition
		tableDefinition = new DefaultTableDefinition<ROW>();

		if (tableConfigurer.getRowRenderer() != null) {
			tableDefinition.setRowRenderer(tableConfigurer.getRowRenderer());
		}

		//add column definition for each column set by the user
		final StandardGrid.Controller.ColumnsWrapper<ROW> columnsWrapper = tableController.getColumnsWrapper();

		for (int i = 0; i < columnsName.length; i++) {
			ColumnDefinitionCustom<ROW> columnDef = new ColumnDefinitionCustom<ROW>(columnsName[i], i) {
				@Override
				public Object getCellValue(ROW rowValue) {
					Object cellValue = columnsWrapper.get(rowValue, columnIndex);

					if (cellValue instanceof TextCellEditor) {
						return ((TextCellEditor) cellValue).getValue();
					} else if (cellValue instanceof ListCellEditor<?>) {
						return ((ListCellEditor<?>) cellValue).getItem();
					}

					return cellValue;
				}

				@Override
				public void setCellValue(ROW rowValue, Object cellValue) {
					columnsWrapper.set(rowValue, columnIndex, cellValue.toString());
				}
			};

			//column cell renderer
			if (tableConfigurer.getCellRenderer(i) != null) {
				columnDef.setCellRenderer(tableConfigurer.getCellRenderer(i));
			}

			//column width
			int[] columnWidths = tableConfigurer.getColumnsWidth();
			if (columnWidths != null && columnWidths.length > i) {
				//columnDef.setMinimumColumnWidth(columnWidths[i]);
				columnDef.setPreferredColumnWidth(columnWidths[i]);
				//columnDef.setMaximumColumnWidth(columnWidths[i]);
			}

			// enable sorting for all columns
			if (tableConfigurer.isSortable())
				columnDef.setColumnSortable(true);

			tableDefinition.addColumnDefinition(columnDef);
		}
	}

	/**
	 * @return the new footer table
	 */
	protected FixedWidthFlexTable createFooterTable() {
		return new FixedWidthFlexTable();
	}

	public String[] getColumnsName() {
		return columnsName;
	}

	public Set<Integer> getSelectedRowsIndexes() {
		return getDataTable().getSelectedRows();
	}

	public List<Integer> getSortedSelectedRowsIndexes() {
		List<Integer> sortedSelectedRowsIndexes = new ArrayList<Integer>(getSelectedRowsIndexes());
		sort(sortedSelectedRowsIndexes);
		return sortedSelectedRowsIndexes;
	}

	public List<ROW> getSelectedRows() {
		return getRows(getSelectedRowsIndexes());
	}

	public List<ROW> getRows(Iterable<Integer> rowIndexes) {
		List<ROW> rows = new LinkedList<ROW>();

		for (int rowIndex : rowIndexes) {
			rows.add(pagingScrollTable.getRowValue(rowIndex));
		}

		return rows;
	}

	public void reloadData(int page, boolean forced) {
		cachedTableModel.clearCache();

		pagingScrollTable.reloadData(page, forced);
	}

	/**
	 * @return the cached table model
	 */
	public CachedTableModel<ROW> getCachedTableModel() {
		return cachedTableModel;
	}

	/**
	 * @return the table definition of columns
	 */
	public DefaultTableDefinition<ROW> getTableDefinition() {
		return tableDefinition;
	}

	/**
	 * @return the table model
	 */
	public CONTROLLER getTableController() {
		return tableController;
	}

	public void insertDataRow(int beforeRow) {
		getCachedTableModel().insertRow(beforeRow);
	}

	public List<ROW> getRows() {
		return pagingScrollTable.getRowValues();
	}
	
	public ROW getRowValue(int row) {
		return pagingScrollTable.getRowValue(row);
	}

	public int getAbsoluteFirstRowIndex() {
		return pagingScrollTable.getAbsoluteFirstRowIndex();
	}

	public int getAbsoluteLastRowIndex() {
		return pagingScrollTable.getAbsoluteLastRowIndex();
	}

	public int getRowCount() {
		return pagingScrollTable.getTableModel().getRowCount();
	}

	public void setHeight(String height) {
		container.setHeight(height);
	}

	public void setRowValue(int rowIndex, ROW row) {
		pagingScrollTable.setRowValue(rowIndex, row);
	}

	public void reloadPage() {
		pagingScrollTable.reloadPage();
	}

	public void setRowStyleName(Integer row, String stylename) {
		pagingScrollTable.setRowStyleName(row, stylename);
	}

	public void setCellStyleName(Integer row, Integer column, String stylename) {
		pagingScrollTable.setCellStyleName(row, column, stylename);
	}

	public void selectAllRows() {
		getDataTable().selectAllRows();
	}

	public void deselectAllRows() {
		getDataTable().deselectAllRows();
	}

	private FixedWidthGridCustom getDataTable() {
		return pagingScrollTable.getDataTable();
	}

	public void insertIntoTopWidget(int beforeIndex, Widget... widgets) {
		if (topWidgetPanel != null) {
			for (Widget widget : widgets) {
				topWidgetPanel.insert(widget, beforeIndex);
			}
		}
	}

	public void removeFromTopWidget(Widget... widgets) {
		if (topWidgetPanel != null) {
			for (Widget widget : widgets) {
				topWidgetPanel.remove(widget);
			}
		}
	}
	public Set<ROW> getNewlySelected(RowSelectionEvent rowSelectionEvent) {
		Set<ROW> newlySelectedRows = new HashSet<ROW>();
		List<ROW> rows = getRows();
		for (TableEvent.Row tableEventRow : rowSelectionEvent.getSelectedRows()) {
			ROW newlySelectedRow = rows.get(tableEventRow.getRowIndex());
			newlySelectedRows.add(newlySelectedRow);
		}
		return newlySelectedRows;
	}

	public boolean hasSameRowsAs(AbstractSimpleGrid<ROW, CONTROLLER, CONFIGURER> simpleGrid) {
		return getRows().equals(simpleGrid.getRows());
	}

	public void setData(Collection<ROW> rows) {
		pagingScrollTable.setData(0, rows.iterator());
	}

	public void insertRow(int beforeIndex, ROW row) {
		getDataTable().insertRow(beforeIndex);
		pagingScrollTable.addRow(beforeIndex, row);
	}

	public void insert(Collection<ROW> rows) {
		int i = 0;
		for (Iterator<ROW> rowIt = rows.iterator() ; rowIt.hasNext() ; i++) {
			insertRow(i, rowIt.next());
		}
	}

	public List<ROW> remove(Set<Integer> rowIndexes) {
		Integer[] sortedRowsIndexes = rowIndexes.toArray(new Integer[rowIndexes.size()]);
		java.util.Arrays.sort(sortedRowsIndexes);

		return remove(sortedRowsIndexes);
	}

	public List<ROW> remove(Integer... sortedRowsIndexes) {
		List<ROW> removedRows = new LinkedList<ROW>();

		for (int index = sortedRowsIndexes.length - 1 ; index >= 0 ; index--) {
			remove(removedRows, sortedRowsIndexes[index]);
		}

		return removedRows;
	}

	private void remove(List<ROW> removedRows, int rowIndex) {
		removedRows.add(getRowValue(rowIndex));
		getDataTable().removeRow(rowIndex);
		pagingScrollTable.removeRow(rowIndex);
	}

	public List<ROW> removeAllRows() {
		Integer[] sortedRowIndexes = new Integer[getRows().size()];
		for (int i = 0 ; i < sortedRowIndexes.length ; i++) {
			sortedRowIndexes[i] = i;
		}
		return remove(sortedRowIndexes);
	}

	protected static abstract class Configurer<THIS extends Configurer<THIS, ROW>, ROW> {
		private int cellpadding = 3;
		private int cellspacing = 0;
		private String width = "100%";
		private String height = "400px";
		private boolean visible = true;
		private boolean sortable = true;
		private boolean paginable = true;
		private boolean withCache = true;
		private AbstractScrollTable.ResizePolicy resizePolicy = AbstractScrollTable.ResizePolicy.FILL_WIDTH;
		private AbstractScrollTable.ScrollPolicy scrollPolicy = AbstractScrollTable.ScrollPolicy.BOTH;
		private int pageSize = 50;
		private Map<Integer, CellRenderer<ROW, Object>> cellRenderers = new HashMap<Integer, CellRenderer<ROW, Object>>();
		private RowRenderer<ROW> rowRenderer;
		private boolean retrieveDataOnLoad = true;
		private Widget topWidget;

		protected Configurer() {
		}

		public int getPageSize() {
			return pageSize;
		}

		public THIS setPageSize(int pageSize) {
			this.pageSize = pageSize;

			return getThis();
		}

		public int getCellpadding() {
			return cellpadding;
		}

		public THIS setCellpadding(int cellpadding) {
			this.cellpadding = cellpadding;

			return getThis();
		}

		public int getCellspacing() {
			return cellspacing;
		}

		public THIS setCellspacing(int cellspacing) {
			this.cellspacing = cellspacing;

			return getThis();
		}

		public String getWidth() {
			return width;
		}

		public THIS setWidth(String width) {
			this.width = width;

			return getThis();
		}

		public String getHeight() {
			return height;
		}

		public THIS setHeight(String height) {
			this.height = height;

			return getThis();
		}

		public boolean isVisible() {
			return visible;
		}

		public THIS setVisible(boolean visible) {
			this.visible = visible;

			return getThis();
		}

		public boolean isSortable() {
			return sortable;
		}

		public THIS setSortable(boolean sortable) {
			this.sortable = sortable;

			return getThis();
		}

		public abstract int[] getColumnsWidth();

		public int getColumnWidth(int index) {
			return getColumnsWidth()[index];
		}

		public boolean isPaginable() {
			return paginable;
		}

		public THIS setPaginable(boolean paginable) {
			this.paginable = paginable;

			return getThis();
		}

		public THIS setCellRenderer(int index, CellRenderer<ROW, Object> cellRenderer) {
			this.cellRenderers.put(index, cellRenderer);

			return getThis();
		}

		public CellRenderer<ROW, Object> getCellRenderer(int index) {
			return this.cellRenderers.get(index);
		}

		protected AbstractScrollTable.ResizePolicy getResizePolicy() {
			return resizePolicy;
		}

		protected void setResizePolicy(AbstractScrollTable.ResizePolicy resizePolicy) {
			this.resizePolicy = resizePolicy;
		}

		protected AbstractScrollTable.ScrollPolicy getScrollPolicy() {
			return scrollPolicy;
		}

		protected void setScrollPolicy(AbstractScrollTable.ScrollPolicy scrollPolicy) {
			this.scrollPolicy = scrollPolicy;
		}

		public THIS setSgResizePolicy(SimpleGridPolicy.ResizePolicy sgResizePolicy) {
			setResizePolicy(AbstractScrollTable.ResizePolicy.valueOf(sgResizePolicy.toString())); // TODO: rimuovere questo orrore
			return getThis();
		}

		public THIS setSgScrollPolicy(SimpleGridPolicy.ScrollPolicy sgScrollPolicy) {
			setScrollPolicy(AbstractScrollTable.ScrollPolicy.valueOf(sgScrollPolicy.toString())); // TODO: rimuovere questo orrore
			return getThis();
		}

		public abstract THIS setSgSelectionPolicy(SimpleGridPolicy.SelectionPolicy gselectionPolicy);

		public RowRenderer<ROW> getRowRenderer() {
			return rowRenderer;
		}

		public THIS setRowRenderer(RowRenderer<ROW> rowRenderer) {
			this.rowRenderer = rowRenderer;
			return getThis();
		}

		public boolean getWithCache() {
			return withCache;
		}

		public THIS setWithCache(boolean withCache) {
			this.withCache = withCache;
			return getThis();
		}

		public boolean isRetrieveDataOnLoad() {
			return retrieveDataOnLoad;
		}

		public THIS setRetrieveDataOnLoad(boolean retrieveDataOnLoad) {
			this.retrieveDataOnLoad = retrieveDataOnLoad;
			return getThis();
		}

		public THIS setTopWidget(Widget topWidget) {
			this.topWidget = topWidget;
			return getThis();
		}

		public Widget getTopWidget() {
			return topWidget;
		}

		@SuppressWarnings("unchecked")
		protected final THIS getThis() {
			return (THIS) this;
		}
	}

	protected static abstract class Controller<ROW, COLUMN_SORT_LIST extends AbstractDataRequest.DataColumnSortList> extends MutableTableModel<ROW>  {
		private PagingScrollTableCustom<ROW> pagingScrollTable;
		private int totalRowCount;
		private TableModelHelper.Request lastRequest;

		private SimpleGridClickHandler clickHandler;
		private SimpleGridDoubleClickHandler doubleClickHandler;
		private TableEvent.Cell clickedHTMLcell;

		public interface ColumnsWrapper<E> {
			Object get(E row, int index);
			void set(E row, int index, String cellValue);
		}

		private ColumnsWrapper<ROW> columnsWrapper;

		//collection of listeners
		//private TableListenerCollectionCustom collection = new TableListenerCollectionCustom();

		protected Controller() {
			super();
		}

		protected ColumnsWrapper<ROW> createColumnsWrapper() {
			return new ColumnsWrapper<ROW>() {
				public Object get(ROW row, int index) {
					if (row instanceof Serializable[]) {
						Serializable[] columnsWrappers = (Serializable[]) row;

						if (columnsWrappers[index] != null) {
							return columnsWrappers[index];
						}
					}

					return "";
				}

				public void set(ROW row, int index, String cellValue) {
					if (row instanceof Serializable[]) {
						Object[] celleRigaServer = (Object[]) row;
						celleRigaServer[index] = cellValue;
					}
				}
			};
		}

		public void setPagingScrollTable(final PagingScrollTableCustom<ROW> pagingScrollTable) {
			this.pagingScrollTable = pagingScrollTable;

			addHandlersTo(pagingScrollTable);
		}

		private void addHandlersTo(final PagingScrollTableCustom<ROW> pagingScrollTable) {
			final FixedWidthGridCustom dataTable = pagingScrollTable.getDataTable();

			dataTable.addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						clickedHTMLcell = dataTable.getCellForEvent(event); // Attenzione al tipo restituito qui!

						if (clickedHTMLcell != null) {
							int row = clickedHTMLcell.getRowIndex() - 1; // start from 0
							int column = clickedHTMLcell.getCellIndex() - dataTable.getOffset();

							if (column >= 0) {
								Object cell = getColumnsWrapper().get(pagingScrollTable.getRowValue(row), column);

								if (cell instanceof InlineCellEditor<?>) {
									@SuppressWarnings("unchecked") InlineCellEditor<String> inlineCellEditor = (InlineCellEditor<String>) cell;

									//create cell edit info for positioning
									CellEditor.CellEditInfo cellEditInfo = new CellEditor.CellEditInfo(dataTable, row, column);
									inlineCellEditor.editCell(cellEditInfo, inlineCellEditor.getValue(), new DefaultEditorCallback());
								}
								else if (clickHandler != null) {
									clickHandler.onClick(event, row, column);
								}
							}
						}
						else {
							// More than one cell was selected in click event
						}
					}
				}
			);

			dataTable.addDoubleClickHandler(
				new DoubleClickHandler() {
					public void onDoubleClick(DoubleClickEvent event) {
						if (clickedHTMLcell != null) {
							int row = clickedHTMLcell.getRowIndex() - 1; //start from 0
							int column = clickedHTMLcell.getCellIndex() - dataTable.getOffset();

							if (doubleClickHandler != null)
								doubleClickHandler.onDoubleClick(event, row, column);
						}
						else {
							// More than one cell was selected in click event
						}
					}
				}
			);
		}

		@Override
		public final void requestRows(final TableModelHelper.Request request, final Callback<ROW> callback) {
			AbstractDataRequest.DataColumnSortInfo columnSortInfo = extractDataColumnSortInfo(request);

			COLUMN_SORT_LIST columnSortList = newColumnSortList();
			columnSortList.add(columnSortInfo);

			lastRequest = request;

			requestRows(request, callback, columnSortList);
		}

		protected abstract COLUMN_SORT_LIST newColumnSortList();

		protected abstract AbstractDataRequest.DataColumnSortInfo extractDataColumnSortInfo(TableModelHelper.Request request);
		protected abstract void requestRows(TableModelHelper.Request request, Callback<ROW> callback, COLUMN_SORT_LIST columnSortList);

		@Override
		protected boolean onRowInserted(int beforeRow) {
			return false;
		}

		@Override
		protected boolean onRowRemoved(int row) {
			return false;
		}

		@Override
		protected boolean onSetRowValue(int row, ROW rowValue) {
			return false;
		}

		public int getTotalRowCount() {
			return totalRowCount;
		}

		public void addClickHandler(SimpleGridClickHandler clickHandler) {
			this.clickHandler = clickHandler;
		}

		public void addDoubleClickHandler(SimpleGridDoubleClickHandler doubleClickHandler) {
			this.doubleClickHandler = doubleClickHandler;
		}

		public class DefaultAsyncCallback<SerializableRowType extends Serializable> implements com.google.gwt.user.client.rpc.AsyncCallback<DataResponse<SerializableRowType>> {
			private final Callback<SerializableRowType> callback;

			/**
			 * @param request useless
			 * @param callback the callback
			 * @deprecated use {@link #DefaultAsyncCallback(com.google.gwt.gen2.table.client.TableModel.Callback)}
			 */
			@Deprecated
			public DefaultAsyncCallback(AbstractDataRequest request, Callback<SerializableRowType> callback) {
				this(callback);
			}

			public DefaultAsyncCallback(Callback<SerializableRowType> callback) {
				this.callback = callback;
			}

			public final void onSuccess(DataResponse<SerializableRowType> dataResponse) {
				beforeOnSuccess(dataResponse);

				//total row counts (greater equal to result size)
				totalRowCount = dataResponse.getTotalRowCount();

				//set total row count to paginable options
				pagingScrollTable.getTableModel().setRowCount(totalRowCount);
				//cachedTableModel.setRowCount(totalRowCount);

				pagingScrollTable.setPageSize(dataResponse);

				//rilancio la request di gwt
				callback.onRowsReady(lastRequest, dataResponse);

				afterOnSuccess(dataResponse);
			}
			
			public final void onFailure(Throwable caught) {
				beforeOnFailure(caught);

				callback.onFailure(caught);
				fireEvent(new PagingFailureEvent(caught));

				afterOnFailure(caught);
			}

			protected void beforeOnSuccess(final DataResponse<SerializableRowType> dataResponse) {}
			protected void beforeOnFailure(final Throwable caught) {}
			
			protected void afterOnSuccess(final DataResponse<SerializableRowType> dataResponse) {
				dataTableRequestRowsOnSuccess(dataResponse);
			}
			
			protected void afterOnFailure(final Throwable caught) {
				dataTableRequestRowsOnFailure(caught);
			}

			/**
			 * @deprecated in favor of {@link #afterOnSuccess(DataResponse)}
			 * @param dataResponse the data response
			 */
			@Deprecated
			public void dataTableRequestRowsOnSuccess(final DataResponse<SerializableRowType> dataResponse) {}
			
			
			/**
			 * @deprecated in favor of {@link #afterOnFailure(Throwable)} 
			 * @param caught any Throwable
			 */
			@Deprecated
			public void dataTableRequestRowsOnFailure(final Throwable caught) {}
		}

		public final class DefaultEditorCallback implements CellEditor.Callback<String> {
			public void onCancel(CellEditor.CellEditInfo cellEditInfo) {}

			public void onComplete(CellEditor.CellEditInfo cellEditInfo, String cellValue) {
				ROW modifiedRow = pagingScrollTable.getRowValue(cellEditInfo.getRowIndex());
				getColumnsWrapper().set(modifiedRow, cellEditInfo.getCellIndex(), cellValue);

				pagingScrollTable.setRowValue(cellEditInfo.getRowIndex(), modifiedRow);
			}
		}

		public ColumnsWrapper<ROW> getColumnsWrapper() {
			if (columnsWrapper == null) {
				columnsWrapper = createColumnsWrapper();
			}

			return columnsWrapper;
		}
	}
}
