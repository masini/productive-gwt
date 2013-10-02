package org.googlecode.gwt.client.rpc;

import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.RemoteCallback;

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
        return new ErrorCallback<Message>() {
            @Override
            public boolean error(Message message, Throwable throwable) {
                onFailure(throwable);
                return false;
            }
        };
    }
}
