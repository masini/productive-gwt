package org.googlecode.gwt.client.rpc;

import com.google.gwt.http.client.Response;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.enterprise.client.jaxrs.MarshallingWrapper;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseCallback;

/**
 * Tracked response callback, useful for accessing both the response header
 * and the response body.
 * User: ictfr1
 * Date: 09/12/13
 */

public abstract class TrackedResponseCallback<T> extends TrackedAsyncCallback<T> {

    private Class<?> clazz;

    public TrackedResponseCallback(String serviceName, Class<?> clazz) {
        super(serviceName);
        this.clazz = clazz;
    }

    public ResponseCallback asRemoteCallback() {
        return new ResponseCallback() {
            @Override
            public void callback(Response response) {
                String text;
                T body = null;

                if (response != null) {
                    text = response.getText();
                    if (text != null && !text.equals("")) {
                        body = (T)(MarshallingWrapper.fromJSON(response.getText(), clazz));
                    }
                }
                onSuccess(body);
                userOnSuccess(response);
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

    /**
     * Implement this method in order to access both the response header and the
     * response body.
     * @param response rpc response.
     */
    protected abstract void userOnSuccess(Response response);
}
