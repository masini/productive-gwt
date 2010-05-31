package com.mycompany.project.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.googlecode.gwt.reflection.client.WrapperFactory;
import org.googlecode.gwt.simplegrid.client.table.ListCellEditor;
import org.googlecode.gwt.simplegrid.client.table.SimpleGrid;
import org.googlecode.gwt.simplegrid.client.table.SimpleGridClickHandler;
import org.googlecode.gwt.simplegrid.client.table.SimpleGridDoubleClickHandler;
import org.googlecode.gwt.simplegrid.client.table.SimpleGridFactory;
import org.googlecode.gwt.simplegrid.client.table.SimpleGridPolicy;
import org.googlecode.gwt.simplegrid.client.table.TableConfigurer;
import org.googlecode.gwt.simplegrid.client.table.TableController;
import org.googlecode.gwt.simplegrid.client.table.TextCellEditor;
import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.DataResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.gen2.table.client.CellRenderer;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.RowRenderer;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractRowView;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mycompany.project.shared.MyDataSourceService;
import com.mycompany.project.shared.MyDataSourceServiceAsync;
import com.mycompany.project.shared.PojoBean;
import org.googlecode.gwt.simplegrid.shared.Filter;

public class SimpleGridPanel extends Composite {

	final TextBox txtNumRows1 = new TextBox();
	final TextBox txtNumRows2 = new TextBox();
	
	List<String> valueList = new ArrayList<String>();
	List<String> textList = new ArrayList<String>();
	
	public SimpleGridPanel() {
		final Button btnResize = new Button("RESIZE TABLE");
		final Button btnReload1 = new Button("RELOAD BEANS");
		final Button btnReload2 = new Button("RELOAD BEANS");
		final Button btnRefresh2 = new Button("REFRESH TABLE");
		final Button btnSelRows = new Button("GET ROW VALUES");
		final Button btnUpdateRow = new Button("UPDATE ROW");
		final Label labTab1 = new Label("TABELLA CON ARRAY Serializable[ ] (SINGLE SELECTION)");
		final Label labTab2 = new Label("TABELLA CON LISTA DI BEANS (MULTI SELECTION)");
		labTab1.setStyleName("gwt-SubTitleRed");
		labTab2.setStyleName("gwt-SubTitleRed");
		
		VerticalPanel vp = new VerticalPanel();
		initWidget(vp);
		vp.setWidth("100%");
		vp.setHeight("100%");
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		txtNumRows1.setWidth("50px");
		txtNumRows1.setText("10");
		
		txtNumRows2.setWidth("50px");
		txtNumRows2.setText("10");
		
		// ========================================================================================================= //

		/*
		 * TABLE 1 - Base Constructor ("serviceName", String[] columnsName)
		 */
		final SimpleGrid<Serializable[]> table1 = 
			SimpleGridFactory.
				createSimpleGrid("datasourceSerial", 
								 "COL 1", "COL 2","COL 3","COL 4", "COL 5");
		
		// Table 1 - set filter on dataRequest
		table1.getTableController().setFilter(new Filter<Integer>(Integer.parseInt(txtNumRows1.getText())));
		
		// Table 1  listener for the "OnClick" e "OnDoucleClick" event
		table1.getTableController().addClickHandler(new SimpleGridClickHandler(){

			public void onClick(ClickEvent event, int row, int column) {
				//Serializable[] selectedRow = table1.getRowValue(row);
				//Window.alert("VALORE ARRAY = " + selectedRow[column]);
			}
		});
		
		table1.getTableController().addDoubleClickHandler(new SimpleGridDoubleClickHandler(){
		
			public void onDoubleClick(DoubleClickEvent event, int row,
					int column) {
				Serializable[] selectedRow = table1.getRowValue(row);
				Window.alert("[DOUBLE CLICK] => "+ selectedRow[column]);
			}
			
		});
		
		// Button "RELOAD" listener
		btnReload1.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent arg0) {
				
				//reload data from server
				table1.reloadData(0, true, new Filter<Integer>(Integer.parseInt(txtNumRows1.getText())));
				
			}
		});		
		
		// Button "RESIZE" listener
		btnResize.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				
				//reload data from server
				table1.setHeight("500px");
			}
		});		
		
		// ========================================================================================================= //

		// *** Table 2 - OPTIONAL config parameters (tableController, tableConfigurer, String[] columnsName) ***
		final TableConfigurer<PojoBean> tableConfigurer = new TableConfigurer<PojoBean>(); 
		tableConfigurer.setColumnsWidth(new int[]{50,100,50,50,50,50})
			.setCellpadding(3).setCellspacing(0).setWidth("100%").setHeight("400px")
			.setPageSize(20).setPaginable(true).setSortable(true).setVisible(true).setWithCache(false)
			.setSgResizePolicy(SimpleGridPolicy.ResizePolicy.FILL_WIDTH)
			.setSgScrollPolicy(SimpleGridPolicy.ScrollPolicy.BOTH)
			.setSgSelectionPolicy(SimpleGridPolicy.SelectionPolicy.MULTI_ROW)
			
			/*
			 *  Table 2 - Customizzazione dello stile di riga condizionato
			 *  (vedi file css: 
			 *  .gwt-ScrollTable .dataTable tr.rosso"
			 *  .gwt-ScrollTable .dataTable tr.selected 
			 *  .gwt-ScrollTable .dataTable tr.highlighted ) 
			 */
			.setRowRenderer(new RowRenderer<PojoBean>() {
			public void renderRowValue(PojoBean rowValue, AbstractRowView<PojoBean> view) {
				if(rowValue.getValue() < 1)
					view.setStyleName("rosso");
				}
			});
		
		// Table 2 - OPTIONAL custom Cell Renderers
		tableConfigurer.setCellRenderer(3, new CellRenderer<PojoBean, Object>() {

			public void renderRowValue(PojoBean rowValue,
					ColumnDefinition<PojoBean, Object> columnDef,
					AbstractCellView<PojoBean> view) {

				view.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		          //view.setHTML(columnDef.getCellValue(rowValue).toString());
		    	  TextBox tb = new TextBox();
		    	  tb.setWidth("100%");
		    	  tb.setText(rowValue.getValue().toString());
		    	  view.setWidget(tb);
				
			}
		});
		
		/*
		 * TABLE 2 - Base Constructor (customTableController, tableConfigurer, String[] columnsName)
		 */
		final SimpleGrid<PojoBean> table2 = SimpleGridFactory.createSimpleGrid(
				new MyCustomTableController((WrapperFactory<PojoBean>)GWT.create(PojoBean.class)),
				//new TableController<PojoBean>(Wrapperfactory),
				tableConfigurer,
				"COL 1", "COL 2 (edit combo)","COL 3","COL 4", "COL 5 (edit text)");
		
		// Table 2 - set filter sulla dataRequest
		table2.getTableController().setFilter(
				new Filter<Integer>(Integer.parseInt(txtNumRows2.getText())));
		
		// Table 2  listener for the "OnClick" event
		/* --- MULTI ROW SELECTION IMPLEMENTED ---
		table2.getTableController().addTableListener(new TableListener(){
			public void onCellClicked(SourcesTableEvents source, int row,
					int column) {
				
				PojoBean selectedRow = table2.getPagingScrollTable().getRowValue(row);
				Window.alert("VALORE BEAN = " + tableConfigurer.getWrapper().get(selectedRow, column));
			}
			
		});
		*/
		
		// ========================================================================================================= //
		
		// Button "RELOAD" listener
		btnReload2.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				//reload data from server
				table2.reloadData(0, true, new Filter<Integer>(Integer.parseInt(txtNumRows2.getText())));
			}
		});
		
		// Button "REFRESH" listener
		btnRefresh2.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				//refresh data from server
				table2.reloadPage();
			}
		});
		
		// Button "SELECT ROWS" listener
		btnSelRows.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				String str = "";
				for(PojoBean row : table2.getSelectedRows())
				{
					for(int i = 0; i < 5; i++)
					{
						str += table2.getTableController().getColumnsWrapper().get(row, i) + " | ";
					}
					str += "\n";
				}
				Window.alert(str);
			}
		});
		
		// Button "REFRESH" listener - Nuovo bean di riga lato client
		btnUpdateRow.setTitle("Aggiorna il bean nella prima riga!");
		vp.add(new Label("   "));
		vp.add(btnUpdateRow);
		btnUpdateRow.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent arg0) {
				
				PojoBean pojoBean = table2.getRowValue(0);
				pojoBean.setCodice("*** UPDATED ***");
				pojoBean.setTimestamp_id("*** UPDATED TIMESTAMP ***");
				table2.setRowValue(0, pojoBean);
			}
		});
		
		
		// Table 2 - Visual labels combo-box
		textList.add("Descrizione 1");
		textList.add("Descrizione 2");
		textList.add("Descrizione 3");
		textList.add("Descrizione 4");
		
		// Table 2 - Values combo-box
		valueList.add("descrizione 1-esima");
		valueList.add("descrizione 2-esima");
		valueList.add("descrizione 3-esima");
		valueList.add("descrizione 4-esima");
		
		
		//Table 1
		vp.add(labTab1);
		HorizontalPanel hp1 = new HorizontalPanel();
		hp1.setSpacing(4);
		hp1.add(new Label("FILTRO - NUMERO RIGHE = "));
		hp1.add(txtNumRows1);
		hp1.add(btnReload1);
		vp.add(hp1);
		vp.add(btnResize);
		vp.add(table1);
		
		//Table 2
		vp.add(labTab2);
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setSpacing(4);
		hp2.add(new Label("FILTRO - NUMERO RIGHE = "));
		hp2.add(txtNumRows2);
		hp2.add(btnReload2);
		hp2.add(btnRefresh2);
		hp2.add(btnUpdateRow);
		vp.add(hp2);
		vp.add(table2);
		
		vp.add(btnSelRows);
	}
	
	/*
	 * Table 2 - Custom TableModel definition
	 */
	class MyCustomTableController extends TableController<PojoBean> {
		 
		public MyCustomTableController(WrapperFactory<PojoBean> wrapperFactory) {
			super();
		}
		
		  /**
		   * Override that can optionally throw an error.
		   */
		  @Override
		  public void dataTableRequestRows(final DataRequest request, final Callback<PojoBean> callback) {

			  // Create default data service
			  MyDataSourceServiceAsync<PojoBean> myDataService = GWT.create(MyDataSourceService.class);
			  
			  // Set entry point
			  ServiceDefTarget myService = (ServiceDefTarget) myDataService;
			  myService.setServiceEntryPoint(GWT.getModuleBaseURL() + "newDatasourceBean");
				
			  // Launch request
			  myDataService.requestRows(request, new DefaultAsyncCallback<PojoBean>(request, callback) {
				  
			  // OPTIONAL - Getter response custom userData
			  public void dataTableRequestRowsOnSuccess(final DataResponse<PojoBean> dataResponse){
					  //Window.alert(dataResponse.getUserData().returnUserData().toString());
				  }
				  
			  // OPTIONAL - Getter caught exception client side
			  public void dataTableRequestRowsOnFailure(final Throwable caught) {
				  Window.alert(caught.getMessage());
			  }
			  
			  }); 
		  }
		  
		/*
		 * Table 2 - Bean wrapper for the table model filed/column mapping
		 *  server side like: "public List<PojoBean> requestRows(Request request)"
		 */
		@Override
		public ColumnsWrapper<PojoBean> createColumnsWrapper() {
			return new ColumnsWrapper<PojoBean>() {
				
				public Object get(PojoBean row, int index)
			    {
					switch(index) {
						case 0: return row.getCodice();
						case 1: return new ListCellEditor<String>(row.getDescrizione(), textList, valueList);
						case 2: return row.getTimestamp_id();
						case 3:	
						{
							TextBox tb = new TextBox();
							tb.setWidth("100%");
							tb.setText((String)row.getValue().toString());
							return tb;
						}
						case 4: return new TextCellEditor(row.getNumero().toString());
						default: throw new IllegalArgumentException("Illegal column value: "+index);
					}
			    }

				public void set(PojoBean row, int index, String cellValue) {
					switch(index) {
						case 0: { row.setCodice(cellValue); break; }
						case 1: { row.setDescrizione(cellValue); break; }
						case 2: { row.setTimestamp_id(cellValue); break; }
						case 3: { row.setValue(Double.parseDouble(cellValue)); break; }
						case 4: { row.setNumero(Integer.parseInt(cellValue)); break; }
						default: throw new IllegalArgumentException("Illegal column value: "+index);
					}
				}
				
			};
		}
		
		@Override
		protected boolean onRowInserted(int beforeRow) {
			return false;
		}
		@Override
		protected boolean onRowRemoved(int row) {
			return false;
		}		
	}
}