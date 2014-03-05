package org.googlecode.gwt.client.rest;


import org.googlecode.gwt.client.rpc.AsyncRequestCounter;

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
