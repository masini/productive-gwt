package org.googlecode.gwt.simplegrid.client.table;


import java.io.Serializable;

import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.DefaultDataSourceService;
import org.googlecode.gwt.simplegrid.shared.DefaultDataSourceServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class DataServiceTableController<RowType extends Serializable> extends TableController<RowType> {

	/**
	 * The RPC service used to generate data.
	 */
	  private DefaultDataSourceServiceAsync<RowType> defaultDataService = GWT.create(DefaultDataSourceService.class);

	public DataServiceTableController(String endPoint) {
		super();

		//create default data service
		ServiceDefTarget service = (ServiceDefTarget) defaultDataService;
		service.setServiceEntryPoint(GWT.getModuleBaseURL() + endPoint);
	}

	  /**
	   * Override that can optionally throw an error.
	   */
	  @Override
	  public void dataTableRequestRows(final DataRequest request, final Callback<RowType> callback) {
		  
		  // Send RPC request for data
		  defaultDataService.requestRows(request, new DefaultAsyncCallback<RowType>(request, callback));
	  }
		
}
