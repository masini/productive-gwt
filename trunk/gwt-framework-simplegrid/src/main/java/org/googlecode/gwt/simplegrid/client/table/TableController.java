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


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.DataResponse;
import org.googlecode.gwt.simplegrid.shared.DataRequest.DataColumnSortInfo;
import org.googlecode.gwt.simplegrid.shared.DataRequest.DataColumnSortList;
import org.googlecode.gwt.simplegrid.shared.DataRequest.Filter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.gen2.table.client.CellEditor;
import com.google.gwt.gen2.table.client.MutableTableModel;
import com.google.gwt.gen2.table.client.CellEditor.CellEditInfo;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.event.client.PagingFailureEvent;
import com.google.gwt.gen2.table.override.client.HTMLTable.Cell;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * An iterator that serves as the data source for TableOracle requests.
 */
public abstract class TableController<RowType> extends MutableTableModel<RowType> {

		PagingScrollTableCustom<RowType> pagingScrollTable = null;
		int totalRowCount;
		Request lastRequest;
		
		@SuppressWarnings("unchecked")
		private Filter filter = null;
		private SimpleGridClickHandler clickHandler;
		private SimpleGridDoubleClickHandler doubleClickHandler;
		private Cell clickedHTMLcell = null;
		
		//Wrapper interface  
		public interface ColumnsWrapper<E>
			{
				public Object get(E row, int index);
				public void set(E row, int index, String cellValue );
			}
		
		public abstract static class ReadOnlyColumnsWrapper<E> implements ColumnsWrapper<E> {
			public void set(E row, int index, String cellValue) {
			}
		}
		
		private ColumnsWrapper<RowType> columnsWrapper = null; 
		
		//collection of listeners
		//private TableListenerCollectionCustom collection = new TableListenerCollectionCustom();
		
		public TableController() {
			super();
		}

		@SuppressWarnings("unchecked")
		protected <T extends Serializable> Filter<T> createFilter() {
			return filter;
		}
		
		<T extends Serializable> Filter<T> getFilter() {
			return createFilter();		
		}

		public <T extends Serializable> void setFilter(Filter<T> filter) {
			this.filter = filter;
		}

		protected ColumnsWrapper<RowType> createColumnsWrapper() {
			return new ColumnsWrapper<RowType>()
			{
				public Object get(RowType row, int index)
			    {
					if(row instanceof Serializable[])
			  		{
						Serializable[] arrSerializable =(Serializable[]) row;
						List<Serializable> celleRigaServer = Arrays.asList(arrSerializable);

						
						if(celleRigaServer.get(index) != null)
							return celleRigaServer.get(index);
					}
					
					return "";
			    }
				
				public void set(RowType row, int index, String cellValue )
			    {
					if(row instanceof Serializable[])
			  		{
						List<Object> celleRigaServer = Arrays.asList((Object[]) row);
						celleRigaServer.set(index, cellValue);
			  		}
			    }
			};
		}
		
		public void setPagingScrollTable(final PagingScrollTableCustom<RowType>  pagingScrollTable) {
			this.pagingScrollTable = pagingScrollTable;
			
			((FixedWidthGridCustom)pagingScrollTable.getDataTable()).addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					
					//get cell element
					clickedHTMLcell = ((FixedWidthGridCustom)pagingScrollTable.getDataTable()).getCellForEvent(event);
					
					//if more than one cell selected in event click
					if(clickedHTMLcell == null)
						return;
						
					int row = clickedHTMLcell.getRowIndex() - 1; // start from 0
					int column = clickedHTMLcell.getCellIndex();
					
					Object cell = getColumnsWrapper().get(getPagingScrollTable().getRowValue(row), column);
					
					//create cell edit info for positioning
					CellEditInfo cellEditInfo = new CellEditInfo(getPagingScrollTable().getDataTable(), row, column);
					
					//text editor
					if(cell instanceof TextCellEditor)
					{
						TextCellEditor textCellEditor = (TextCellEditor)cell;
						textCellEditor.editCell(cellEditInfo, textCellEditor.getValue(), new DefaultEditorCallback());
					} 
					//list editor
					else if(cell instanceof ListCellEditor)
					{
						@SuppressWarnings("unchecked")
						ListCellEditor<String> listCellEditor = (ListCellEditor<String>)cell;
						listCellEditor.editCell(cellEditInfo, listCellEditor.getValue(), new DefaultEditorCallback());

					}
					else
						if(clickHandler != null)
							clickHandler.onClick(event, row, column);
				}
				
			});
			
			((FixedWidthGridCustom)pagingScrollTable.getDataTable()).addDoubleClickHandler(new DoubleClickHandler(){

				public void onDoubleClick(DoubleClickEvent event) {
					
					//if more than one cell selected in event click
					if(clickedHTMLcell == null)
						return;
					
					int row = clickedHTMLcell.getRowIndex() - 1; //start from 0
					int column = clickedHTMLcell.getCellIndex();
					
					if(doubleClickHandler != null)
						doubleClickHandler.onDoubleClick(event, row , column);
				}
				
			});
			
			//cell clicked event
			/*((FixedWidthGridCustom)this.pagingScrollTable.getDataTable()).addTableListener(new SimpleGridListener(){
				@SuppressWarnings("unchecked")
				public void onCellClicked(final SourcesTableEvents sender,final int row, final int column) {				
					
					Object cell = getColumnsWrapper().get(getPagingScrollTable().getRowValue(row), column);
					
					//create cell edit info for positioning
					CellEditInfo cellEditInfo = new CellEditInfo(getPagingScrollTable().getDataTable(), row, column);
					
					//text editor
					if(cell instanceof TextCellEditor)
					{
						TextCellEditor textCellEditor = (TextCellEditor)cell;
						textCellEditor.editCell(cellEditInfo, textCellEditor.getValue(), new DefaultEditorCallback());
					} 
					//list editor
					else if(cell instanceof ListCellEditor)
					{
						ListCellEditor<String> listCellEditor = (ListCellEditor<String>)cell;
						listCellEditor.editCell(cellEditInfo, listCellEditor.getValue(), new DefaultEditorCallback());

					}
					else
						collection.fireCellClicked(sender, row, column);
					
				}

				public void onCellDblClicked(SourcesTableEvents sender, final int row, final int column) {
					
					collection.fireCellDblClicked(sender, row, column);
				}
*/
				/*public void onDownKey(SourcesTableEvents sender) {
					// TODO Auto-generated method stub
					
				}

				public void onUpKey(SourcesTableEvents sender) {
					// TODO Auto-generated method stub
					
				}
			});*/
			
			
			
			/*this.pagingScrollTable.getDataTable().addTableSelectionListener(new TableSelectionListener(){
				
				public void onRowsSelected(SourceTableSelectionEvents sender,
						int firstRow, int numRows) {
						Window.alert("Row "+firstRow+" selected!");
				}

				public void onAllRowsDeselected(
						SourceTableSelectionEvents sender) {
					// TODO Auto-generated method stub
					
				}

				public void onCellHover(SourceTableSelectionEvents sender,
						int row, int cell) {
					// TODO Auto-generated method stub
					
				}

				public void onCellUnhover(SourceTableSelectionEvents sender,
						int row, int cell) {
					// TODO Auto-generated method stub
					
				}

				public void onRowDeselected(SourceTableSelectionEvents sender,
						int row) {
					// TODO Auto-generated method stub
					
				}

				public void onRowHover(SourceTableSelectionEvents sender,
						int row) {
					// TODO Auto-generated method stub
					
				}

				public void onRowUnhover(SourceTableSelectionEvents sender,
						int row) {
					// TODO Auto-generated method stub
					
				}

				public void onRowsSelected(SourceTableSelectionEvents sender,
						int firstRow, int numRows) {
					// TODO Auto-generated method stub
					
				}
			});*/
		}
	  
	  	private PagingScrollTableCustom<RowType> getPagingScrollTable()
		{
			return pagingScrollTable;
			
		}
		
    /**
	 * Override that can optionally throw an error.
	 */
	@Override
	public final void requestRows(final Request request,
			final Callback<RowType> callback) {
		
		//primary data column sort info 
		DataColumnSortInfo columnSortInfo = new DataColumnSortInfo();
		columnSortInfo.setColumn(request.getColumnSortList().getPrimaryColumn());
		columnSortInfo.setAscending(request.getColumnSortList().isPrimaryAscending());
		
		//data column sort list
		DataColumnSortList columnSortList = new DataColumnSortList();
		columnSortList.add(columnSortInfo);
		
		//save request
		lastRequest = request;
		
		dataTableRequestRows(new DataRequest(request.getStartRow(), request.getNumRows(), columnSortList, filter), callback);
	}
	
	public abstract void dataTableRequestRows(final DataRequest request,
			final Callback<RowType> callback);
		
	@Override
	protected boolean onRowInserted(int beforeRow) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean onRowRemoved(int row) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean onSetRowValue(int row, RowType rowValue) {
		// TODO Auto-generated method stub
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
	
	public class DefaultAsyncCallback<SerializableRowType extends Serializable> implements AsyncCallback<DataResponse<SerializableRowType>> {  
	  	
		 private Callback<SerializableRowType> callback;
		  
		  public DefaultAsyncCallback(DataRequest request, Callback<SerializableRowType> callback){
			  this.callback = callback;
		  }
		  
		  public final void onFailure(Throwable caught) {
			  callback.onFailure(caught);
			  fireEvent(new PagingFailureEvent(caught));
			  
			//custom user onSuccess   
			  dataTableRequestRowsOnFailure(caught);  
		  }

		  public final void onSuccess(DataResponse<SerializableRowType> dataResponse) {
		  		
			  	//total row counts (greater equal to result size)
		  		totalRowCount = dataResponse.getTotalRowCount();
		  		
		  		//set total row count to paginable options
		  		pagingScrollTable.getTableModel().setRowCount(totalRowCount);
		  		//cachedTableModel.setRowCount(totalRowCount);
		  		
		  		// Setting paginable option
		  	    if (!pagingScrollTable.isPaginable() && pagingScrollTable.getPageSize() == 0)
		  	    	pagingScrollTable.setPageSize(Math.max(1, dataResponse.getResultRows().size()));

		  	    //rilancio la requesto di gwt
		  	    callback.onRowsReady(lastRequest, dataResponse);
		  	    
		  	    //custom user onSuccess   
		  	    dataTableRequestRowsOnSuccess(dataResponse);
		  }
		  
		  public void dataTableRequestRowsOnSuccess(final DataResponse<SerializableRowType> dataResponse)
		  {}
		  
		  public void dataTableRequestRowsOnFailure(final Throwable caught)
		  {}
	}
	
	public final class DefaultEditorCallback implements CellEditor.Callback<String>{

		public final void onCancel(CellEditInfo cellEditInfo) {
			// TODO Auto-generated method stub
			
		}

		public final void onComplete(CellEditInfo cellEditInfo,
				String cellValue) {
				
			RowType modified_row = getPagingScrollTable().getRowValue(cellEditInfo.getRowIndex());
			getColumnsWrapper().set(modified_row, cellEditInfo.getCellIndex(), cellValue);
			
			getPagingScrollTable().setRowValue(cellEditInfo.getRowIndex(), modified_row);
		    
			
			// TODO Auto-generated method stub
		}
		
	}

	public ColumnsWrapper<RowType> getColumnsWrapper() {
		
		if( columnsWrapper==null ) {
			columnsWrapper = createColumnsWrapper();
		}
		
		return columnsWrapper;
	}


}
