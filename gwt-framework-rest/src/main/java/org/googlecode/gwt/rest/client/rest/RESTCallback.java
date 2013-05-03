package org.googlecode.gwt.rest.client.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import org.googlecode.gwt.base.rpc.AsyncRequestCounter;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RESTCallback<T> {

    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    private final String serviceName;
    private final Integer expectedStatusCode;

    public static class CallbackInterceptorDummyImpl {
        protected void preconditionStart(String serviceName) {}
        protected void preconditionStop(String serviceName) {}
    }

    CallbackInterceptorDummyImpl interceptor = GWT.create(CallbackInterceptorDummyImpl.class);

    protected RESTCallback() {
        this(null, null);
    }

    protected RESTCallback(Integer expectedStatusCode) {
        this(null, expectedStatusCode);
    }

    protected RESTCallback(String serviceName) {
        this(serviceName, null);
    }

    protected RESTCallback(String serviceName, Integer expectedStatusCode) {
        this.serviceName = serviceName!=null?serviceName:this.getClass().getName();
        this.expectedStatusCode = expectedStatusCode;

        interceptor.preconditionStart(serviceName);
    }

    public final void onResponseReceived(Response response, T entity) {
        interceptor.preconditionStop(serviceName);

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

    public final void onResponseReceived(Response response) {
        onResponseReceived(response, null);
    }

    public final void onError(int statusCode, Throwable throwable) {
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

    protected void userOnSuccess(Response response, T entity) {
        userOnSuccess(response);
    }

    protected void userOnSuccess(Response response) {}
}
