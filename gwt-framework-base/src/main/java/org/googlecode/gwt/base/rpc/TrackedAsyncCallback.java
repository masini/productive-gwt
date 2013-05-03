package org.googlecode.gwt.base.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class TrackedAsyncCallback<T> implements AsyncCallback<T> {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    private final String serviceName;

    public TrackedAsyncCallback(String serviceName) {
        AsyncRequestCounter.increment(serviceName);
        this.serviceName = serviceName;
    }

    @Override
    public final void onSuccess(T t) {
        AsyncRequestCounter.decrement(serviceName);
        userOnSuccess(t);
    }

    @Override
    public final void onFailure(Throwable throwable) {
        AsyncRequestCounter.decrement(serviceName);

        final boolean veto = userOnFailure(throwable);

        if( !veto ) {
            if( GWT.getUncaughtExceptionHandler()!=null ) {
                GWT.getUncaughtExceptionHandler().onUncaughtException(throwable);
            } else {
                LOG.log(Level.SEVERE, serviceName, throwable);
                Window.alert("Errore in " + serviceName + ", " + throwable.getMessage());
            }
        }
    }

    protected boolean userOnFailure(Throwable throwable) {return false;}
    protected abstract void userOnSuccess(T t);
}
