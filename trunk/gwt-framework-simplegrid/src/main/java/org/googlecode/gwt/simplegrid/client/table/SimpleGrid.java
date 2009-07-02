/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.googlecode.gwt.simplegrid.client.table;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.googlecode.gwt.simplegrid.client.table.ColumnDefinitionCustom.Group;
import org.googlecode.gwt.simplegrid.shared.DataRequest.Filter;

import com.google.gwt.gen2.table.client.CachedTableModel;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGridBulkRenderer;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.TableDefinition;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleGrid<RowType> extends Composite {
	
	//table container
	private FlexTable container = new FlexTable();
	
	private String[] columnsName;
	private TableConfigurer<RowType> tableConfigurer = null;
	
	//constructor
	SimpleGrid(TableController<RowType> tableModel, TableConfigurer<RowType> tableConfigurer, String... columnsName) {
		
		this.tableController = tableModel;
		this.columnsName = columnsName;
		this.tableConfigurer = tableConfigurer.getFactory().createTableConfigurer();
		
		//init table container
		initWidget(container);
	}
	
  /**
   * The {@link CachedTableModel} around the main table model.
   */
  private CachedTableModel<RowType> cachedTableModel = null;

  /**
   * The {@link PagingScrollTableCustom}.
   */
  private PagingScrollTableCustom<RowType> pagingScrollTable = null;

  /**
   * The {@link TableController}.
   */
  private TableController<RowType> tableController = null;

  /**
   * The {@link DefaultTableDefinition}.
   */
  private DefaultTableDefinition<RowType> tableDefinition = null;

  /**
   * @return the cached table model
   */
  public CachedTableModel<RowType> getCachedTableModel() {
    return cachedTableModel;
  }

  /**
   * @return the table definition of columns
   */
  public DefaultTableDefinition<RowType> getTableDefinition() {
    return tableDefinition;
  }

  /**
   * @return the table model
   */
  public TableController<RowType> getTableController() {
    return tableController;
  }

  public void insertDataRow(int beforeRow) {
    getCachedTableModel().insertRow(beforeRow);
  }

	
  /**
   * This is the entry point method.
   */
  @Override
  public void onLoad() {

	container.setVisible(tableConfigurer.isVisible());
	container.setWidth(tableConfigurer.getWidth());
	container.setHeight(tableConfigurer.getHeight());
	container.setCellPadding(3);
	container.setCellSpacing(0);

    // Get formatter for container object
    final FlexCellFormatter formatter = container.getFlexCellFormatter();

    // Create the tables
	FixedWidthFlexTable headerTable = createHeaderTable();
	FixedWidthFlexTable footerTable = createFooterTable();
	FixedWidthGridCustom dataTable = createDataTable();
	createPagingScrollTable(headerTable, dataTable, footerTable);
	  
	//add scrollTable to layout element
	container.setWidget(0, 0, pagingScrollTable);
	formatter.setHeight(0, 0, "100%");
	formatter.setWidth(0, 0, "100%");
	formatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
    	  
    // If paginable create an paging options
    if(tableConfigurer.isPaginable())
    {
	    PagingOptions pagingOptions = new PagingOptions(pagingScrollTable);
	    
	    //Avoid insert more than one row for paging options
	    if (container.getRowCount()==1)
	    	container.insertRow(1);
	    container.setWidget(1, 0, pagingOptions);
    }
    
    onModuleLoaded();
  }

  protected final void createPagingScrollTable(
      FixedWidthFlexTable headerTable, FixedWidthGridCustom dataTable,
      FixedWidthFlexTable footerTable) {
	  
	// Setup the controller
    cachedTableModel = new CachedTableModel<RowType>(tableController);
    
    if(tableConfigurer.getWithCache())
    {
	    cachedTableModel.setPreCachedRowCount(tableConfigurer.getPageSize());
	    cachedTableModel.setPostCachedRowCount(tableConfigurer.getPageSize());
    } else {
	    cachedTableModel.setPreCachedRowCount(0);
	    cachedTableModel.setPostCachedRowCount(0);    	
    }
    
    // Create a TableCellRenderer
    TableDefinition<RowType> tableDef = createTableDefinition();
    
    // Create the scroll table
    pagingScrollTable = new PagingScrollTableCustom<RowType>(cachedTableModel, dataTable, headerTable, tableDef);
    pagingScrollTable.setFooterTable(footerTable);
    pagingScrollTable.setEmptyTableWidget(new HTML("Nessun dato da visualizzare"));
    pagingScrollTable.setPaginable(tableConfigurer.isPaginable());
    
    // Allow setting paging size to pagingScrollTable object
    tableController.setPagingScrollTable(pagingScrollTable);
    
    // Setup the bulk renderer
    FixedWidthGridBulkRenderer<RowType> bulkRenderer = new FixedWidthGridBulkRenderer<RowType>(dataTable, pagingScrollTable);
    pagingScrollTable.setBulkRenderer(bulkRenderer);

    // Formatting table
    pagingScrollTable.setCellPadding(tableConfigurer.getCellpadding());
    pagingScrollTable.setCellSpacing(tableConfigurer.getCellspacing());
    pagingScrollTable.setResizePolicy(tableConfigurer.getResizePolicy());
    pagingScrollTable.setScrollPolicy(tableConfigurer.getScrollPolicy());
    pagingScrollTable.setPageSize(tableConfigurer.getPageSize());
    pagingScrollTable.setHeight("100%");
    
    // Setting sortable option
    if (tableConfigurer.isSortable())
    	pagingScrollTable.setSortPolicy(SortPolicy.SINGLE_CELL);
    else
    	pagingScrollTable.setSortPolicy(SortPolicy.DISABLED);
    
    
  }

  protected void onModuleLoaded() {
	  //nothing
  }
  
  
  /**
   * @return the {@link TableDefinition} with all ColumnDefinitions defined.
   */
  private final TableDefinition<RowType> createTableDefinition() {
    
    // Create the table definition
    tableDefinition = new DefaultTableDefinition<RowType>();
    
    if(tableConfigurer.getRowRenderer() != null)
    	tableDefinition.setRowRenderer(tableConfigurer.getRowRenderer());

    //add column definition for each column setted by user 
    
	for (int i = 0; i < columnsName.length; i++) {
		
		// Column definition
		ColumnDefinitionCustom<RowType> columnDef = new ColumnDefinitionCustom<RowType>(columnsName[i], Group.DATA, i) {
	  
	        @SuppressWarnings("unchecked")
			@Override
	        public Object getCellValue(RowType rowValue) {    	
	        	
	        	
	        	Object cellValue = tableController.getColumnsWrapper().get(rowValue, columnIndex);
	        	
	        	if(cellValue instanceof TextCellEditor)
	        	{
	        		return ((TextCellEditor)cellValue).getValue();
	        	}
	        	else if(cellValue instanceof ListCellEditor)
	        	{
	        		return ((ListCellEditor)cellValue).getItem();
	        	}
	        	
	        	return cellValue;
	        }
	
	        @Override
	        public void setCellValue(RowType rowValue, Object cellValue) {
	        	tableController.getColumnsWrapper().set(rowValue, columnIndex, cellValue.toString());
	        }
		};
  
		//column cell renderer
		if (tableConfigurer.getCellRenderer(i) != null)
		{
			columnDef.setCellRenderer(tableConfigurer.getCellRenderer(i));  
		}
      
		//column width
		if (tableConfigurer.getColumnsWidth() != null && tableConfigurer.getColumnsWidth().length > i)
		{  
			//columnDef.setMinimumColumnWidth(columnsWidth[i]);
			columnDef.setPreferredColumnWidth(tableConfigurer.getColumnWidth(i));
			//columnDef.setMaximumColumnWidth(columnsWidth[i]);
		}
  
		// enable sorting for all columns
		if (tableConfigurer.isSortable())
			columnDef.setColumnSortable(true);
		
		tableDefinition.addColumnDefinition(columnDef);
    }
    
	return tableDefinition;
  
  }
  
	/**
	   * @return the newly created data table.
	   */
	  protected FixedWidthGridCustom createDataTable() {
	    FixedWidthGridCustom dataTable = new FixedWidthGridCustom();
	    dataTable.setSelectionPolicy(tableConfigurer.getSelectionPolicy());
	    dataTable.setSelectionEnabled(true);
	    return dataTable;
	  }
	  
	  /**
	   * @return the new footer table
	   */
	  protected FixedWidthFlexTable createFooterTable() {
	    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
	    return footerTable;
	 }
	  
	  /**
	   * @return the new header table
	   */
	  protected FixedWidthFlexTable createHeaderTable() {
	    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();

		if (columnsName != null && columnsName.length > 0) {
			for (int i = 0; i < columnsName.length; i++) {
				headerTable.setHTML(0, i, columnsName[i]);
			}
		}
		
	    return headerTable;
	  }
	  
	  public String[] getColumnsName() {
			return columnsName;
		}

	  public List<RowType> getSelectedRows(){
		  
		  Set<Integer> row_indexs = pagingScrollTable.getDataTable().getSelectedRows();
		  List<RowType> rows = new ArrayList<RowType>();
		  
		  for(int row_index : row_indexs)
			  rows.add(pagingScrollTable.getRowValue(row_index));
		  
		  return rows;
	  }
	  
	  public void reloadData(int page, boolean forced) {
		  
		    ((CachedTableModel<RowType>)pagingScrollTable.getTableModel()).clearCache();
			
			//reset row count
			pagingScrollTable.getTableModel().setRowCount(CachedTableModel.UNKNOWN_ROW_COUNT);
			
			//launch reload
			pagingScrollTable.gotoPage(page, forced);	
	  }
	  
	  @SuppressWarnings("unchecked")
	  public void reloadData(int page, boolean forced, Filter filter) {
			
		  	//set filter
			tableController.setFilter(filter);
			
			reloadData(page, forced);
			
	  }
	  
	  public RowType getRowValue(int row)
	  {
		  return pagingScrollTable.getRowValue(row);
		  
	  }
	  
	  public int getAbsoluteFirstRowIndex()
	  {
		  return pagingScrollTable.getAbsoluteFirstRowIndex();  
	  }
	  
	  public int getAbsoluteLastRowIndex()
	  {
		  return pagingScrollTable.getAbsoluteLastRowIndex();  
	  }
	  
	  public int getRowCount()
	  {
		  return pagingScrollTable.getTableModel().getRowCount();  
	  }
	  
	  public void setHeight(String height)
	  {
		  container.setHeight(height);
	  }
	  
	  public void setRowValue(int rowIndex, RowType row){
		  pagingScrollTable.setRowValue(rowIndex, row);
	  }
	  
	  public void reloadPage(){
		  pagingScrollTable.reloadPage();
	  }
	  
	  //online change of stylename
	  public void setRowStyleName(Integer row, String stylename)
	  {
		  pagingScrollTable.setRowStyleName(row, stylename);
	  }
	  
	  public void setCellStyleName(Integer row, Integer column, String stylename)
	  {
		  pagingScrollTable.setCellStyleName(row, column, stylename);
	  }
}