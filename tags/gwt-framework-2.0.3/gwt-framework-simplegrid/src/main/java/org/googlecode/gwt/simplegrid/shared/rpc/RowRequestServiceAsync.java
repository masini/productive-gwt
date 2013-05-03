package org.googlecode.gwt.simplegrid.shared.rpc;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.googlecode.gwt.simplegrid.shared.DataResponse;

public interface RowRequestServiceAsync<ROW extends Serializable, FILTER extends Serializable> extends ServiceAsync {
	void requestRows(StandardDataRequest<FILTER> request, AsyncCallback<DataResponse<ROW>> callback);
}
