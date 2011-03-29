package org.googlecode.gwt.simplegrid.shared.rpc;

import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Author: Andrea Baroncelli
 * Date: 27-nov-2009
 * Time: 19.32.37
 */
public abstract class AsyncController<
		SERVICE_ASYNC extends ServiceAsync
	>
{
	protected final SERVICE_ASYNC asynchronousService;

	protected AsyncController(String servletRelativeUrlAsInWebXml) {
		asynchronousService = createAsynchronousService(); //Purtroppo il compilatore GWT non puo' accettare GWT.create(getServiceClass())
		((ServiceDefTarget) asynchronousService).setServiceEntryPoint(servletRelativeUrlAsInWebXml);
	}

	protected abstract SERVICE_ASYNC createAsynchronousService();
}
