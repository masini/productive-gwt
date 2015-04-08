package org.googlecode.gwt.client.rpc;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.enterprise.client.jaxrs.MarshallingWrapper;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseCallback;

public abstract class UntrackedResponseCallback<T> implements AsyncCallback<T> {

    private Class<?> clazz;

    public UntrackedResponseCallback(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    public UntrackedResponseCallback() {
        super();
    }

    public ResponseCallback asRemoteCallback() {
        return new ResponseCallback() {
            @Override
            public void callback(Response response) {
                String text;
                T body = null;

                if (response != null) {
                    text = (response).getText();
                    if (text != null && !text.equals("")){
                        body = (T)(MarshallingWrapper.fromJSON(
                                response.getText(), clazz));
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
     * <p/>
     * response body.
     *
     * @param response rpc response.
     */

    protected abstract void userOnSuccess(Response response);

}


