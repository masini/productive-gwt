package org.googlecode.gwt.simplegrid.client.table;

import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.DefaultDataSourceServiceAsync;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @deprecated in favor of {@link org.googlecode.gwt.simplegrid.shared.rpc.RowRequestAsyncController}
 * @param <ROW> the row type
 */
@Deprecated
public class DataServiceTableController<ROW extends Serializable> extends TableController<ROW> {

	/**
	 * The RPC service used to generate data.
	 */
	private final DefaultDataSourceServiceAsync<ROW> defaultDataService = GWT.create(org.googlecode.gwt.simplegrid.shared.DefaultDataSourceService.class);

	public DataServiceTableController(String endPoint) {
		ServiceDefTarget service = (ServiceDefTarget) defaultDataService;
		service.setServiceEntryPoint(GWT.getModuleBaseURL() + endPoint);
	}

	@Override
	public void dataTableRequestRows(final DataRequest request, final Callback<ROW> callback) {
		defaultDataService.requestRows(request, new DefaultAsyncCallback<ROW>(request, callback));
	}

}
