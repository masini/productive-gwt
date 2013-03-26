package org.googlecode.gwt.base.rpc;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

public abstract class TrackedRemoteCallback<T> extends TrackedAsyncCallback<T> {

    public TrackedRemoteCallback(String serviceName) {
        super(serviceName);
    }

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
            public boolean error(Message message, Throwable throwable) {
                onFailure(throwable);
                return false;
            }
        };
    }
}
