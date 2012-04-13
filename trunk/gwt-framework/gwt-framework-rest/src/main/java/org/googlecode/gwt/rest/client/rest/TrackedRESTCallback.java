package org.googlecode.gwt.rest.client.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import org.googlecode.gwt.base.rpc.AsyncRequestCounter;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class TrackedRESTCallback<T> extends RESTCallback<T> {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    private final String serviceName;
    private final Integer expectedStatusCode;

    public TrackedRESTCallback(String serviceName) {
        this(serviceName, null);
    }


    protected TrackedRESTCallback(String serviceName, Integer expectedStatusCode) {
        AsyncRequestCounter.increment(serviceName);
        this.serviceName = serviceName;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Override
    public final void onResponseReceived(Response response, T entity) {
        AsyncRequestCounter.decrement(serviceName);
        if( expectedStatusCode==null || expectedStatusCode!=null && expectedStatusCode==response.getStatusCode() ) {
            if(entity!=null) {
                userOnSuccess(response, entity);
            } else {
                userOnSuccess(response);
            }
        } else {
            onError(response.getStatusCode(), null);
        }
    }

    @Override
    public final void onResponseReceived(Response response) {
        onResponseReceived(response, null);
    }

    @Override
    public final void onError(int statusCode, Throwable throwable) {
        AsyncRequestCounter.decrement(serviceName);

        final boolean veto = userOnFailure(statusCode,throwable);

        if( !veto ) {
            if( GWT.getUncaughtExceptionHandler()!=null ) {
                GWT.getUncaughtExceptionHandler().onUncaughtException(throwable);
            } else {
                LOG.log(Level.SEVERE, serviceName, throwable);
                Window.alert("Errore in " + serviceName + ", " + throwable.getMessage());
            }
        }
    }

    protected boolean userOnFailure(int statusCode, Throwable throwable) {return false;}
    protected void userOnSuccess(Response response, T entity) {}
    protected void userOnSuccess(Response response) {}
}
