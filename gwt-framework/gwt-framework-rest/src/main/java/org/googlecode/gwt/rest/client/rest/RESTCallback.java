package org.googlecode.gwt.rest.client.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public abstract class RESTCallback<T> {
    public void onResponseReceived(Response response, T entity) {}
    public void onResponseReceived(Response response) {}
    public void onError(int statusCode, Throwable exception) {
        GWT.log(exception.getMessage());
        Window.alert(exception.getMessage());
    }
}
