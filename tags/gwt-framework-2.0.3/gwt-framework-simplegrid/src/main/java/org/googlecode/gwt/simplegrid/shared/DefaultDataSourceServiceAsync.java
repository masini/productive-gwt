package org.googlecode.gwt.simplegrid.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.io.Serializable;

/**
 * @deprecated in favor of {@link org.googlecode.gwt.simplegrid.shared.rpc.RowRequestServiceAsync}
 * @param <ROW> the row type
 */
@Deprecated
public interface DefaultDataSourceServiceAsync<ROW extends Serializable> {
	void requestRows(DataRequest request, AsyncCallback<DataResponse<ROW>> async);
}
