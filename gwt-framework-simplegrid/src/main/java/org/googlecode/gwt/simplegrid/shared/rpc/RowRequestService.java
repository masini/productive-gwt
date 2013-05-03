package org.googlecode.gwt.simplegrid.shared.rpc;

import java.io.Serializable;

import org.googlecode.gwt.simplegrid.shared.DataResponse;

public interface RowRequestService<ROW extends Serializable, FILTER extends Serializable> extends Service {
	DataResponse<ROW> requestRows(StandardDataRequest<FILTER> request) throws Exception;
}
