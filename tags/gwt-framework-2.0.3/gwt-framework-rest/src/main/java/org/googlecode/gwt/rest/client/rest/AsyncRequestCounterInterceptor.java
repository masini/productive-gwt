package org.googlecode.gwt.rest.client.rest;

import org.googlecode.gwt.base.rpc.AsyncRequestCounter;

public class AsyncRequestCounterInterceptor extends RESTCallback.CallbackInterceptorDummyImpl {
    @Override
    protected void preconditionStart(String serviceName) {
        AsyncRequestCounter.increment(serviceName);
    }

    @Override
    protected void preconditionStop(String serviceName) {
        AsyncRequestCounter.decrement(serviceName);
    }
}
