package org.googlecode.gwt.simplegrid.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import java.io.Serializable;

/**
 * @deprecated in favor of {@link org.googlecode.gwt.simplegrid.shared.rpc.RowRequestService}
 * @param <ROW> the row type
 */
@Deprecated
public interface DefaultDataSourceService<ROW extends Serializable> extends RemoteService {
	DataResponse<ROW> requestRows(DataRequest request) throws Exception;
}
