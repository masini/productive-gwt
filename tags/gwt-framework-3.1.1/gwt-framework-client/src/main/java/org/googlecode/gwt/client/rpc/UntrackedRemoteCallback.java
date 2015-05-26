package org.googlecode.gwt.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;

public abstract class UntrackedRemoteCallback<T> implements AsyncCallback<T> {

    public RemoteCallback<T> asRemoteCallback() {
        return new RemoteCallback<T>() {
            @Override
            public void callback(T response) {
                onSuccess(response);
            }
        };
    }

    public ErrorCallback asErrorCallback() {
        return new ErrorCallback() {
            @Override
            public boolean error(Object message, Throwable throwable) {
                onFailure(throwable);
                return false;
            }
        };
    }
}