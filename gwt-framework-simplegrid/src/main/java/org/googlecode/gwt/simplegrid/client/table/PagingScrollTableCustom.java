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

import java.util.Iterator;
import java.util.List;

import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.TableDefinition;
import com.google.gwt.gen2.table.client.TableModel;

/**
 * An customized version of the {@link PagingScrollTable} that updated the
 * header and footer tables to reflect the currently visible rows.
 */
public class PagingScrollTableCustom<RowType> extends PagingScrollTable<RowType> {
  /**
   * The previous list of visible column definitions.
   */
  private List<ColumnDefinition<RowType, ?>> lastColDefs = null;
  boolean paginable = true;
  
  /**
   * The {@link CheckBox} used to select all rows.
   */
  //private CheckBox selectAllCheckBox = new CheckBox();



/**
   * Construct a new {@link PagingScrollTableCustom}.
   * 
   * @param tableModel the underlying table model
   * @param dataTable the table used to display data
   * @param headerTable the header table
   * @param tableDefinition the column definitions
   */
  public PagingScrollTableCustom(TableModel<RowType> tableModel,
      FixedWidthGridCustom dataTable, FixedWidthFlexTable headerTable,
      TableDefinition<RowType> tableDefinition) {
    super(tableModel, dataTable, headerTable, tableDefinition);
  }
  
  @Override
  protected void setData(int firstRow, Iterator<RowType> rows) {
    // Get the visible column definitions
   List<ColumnDefinition<RowType, ?>> colDefs = getTableDefinition().getVisibleColumnDefinitions();
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
  
  public void setRowStyleName(Integer row, String stylename)
  {
	  if (getDataTable().isRowSelected(row)) {
        stylename += " selected";
      }
  
	  getDataTable().getRowFormatter().setStyleName(row, stylename);
  }
  
  public void setCellStyleName(Integer row, Integer column, String stylename)
  {
	  getDataTable().getCellFormatter().setStyleName(row,
	          column, stylename);
  }
}
