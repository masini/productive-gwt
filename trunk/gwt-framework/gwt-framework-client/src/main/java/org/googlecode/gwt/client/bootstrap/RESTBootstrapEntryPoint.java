package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Response;
import org.googlecode.gwt.client.rest.RESTCallback;
import org.googlecode.gwt.shared.BootstrapData;

public class RESTBootstrapEntryPoint extends BootstrapEntryPoint{
    private final RESTBootstrapServiceAsync serviceAsync = GWT.create(RESTBootstrapServiceAsync.class);
    @Override
    public void bootstrap(BootstrapEventListener aeListener) {
        addBootstrapEventListener( aeListener );

        serviceAsync.bootstrap(new RESTCallback<BootstrapData>() {
            private final BootstrapAsyncCallback ac = new BootstrapAsyncCallback( bootstrapEventListeners );

            @Override
            protected void userOnSuccess(Response response, BootstrapData entity) {
                ac.onSuccess(entity);
            }

            @Override
            protected boolean userOnFailure(int statusCode, Throwable throwable) {
                ac.onFailure(throwable);

                return false;
            }
        });


    }
}
