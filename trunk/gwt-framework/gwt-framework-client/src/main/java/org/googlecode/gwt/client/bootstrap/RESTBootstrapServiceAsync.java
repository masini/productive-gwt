package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.http.client.Request;
import org.googlecode.gwt.client.rest.GWTRESTfulService;
import org.googlecode.gwt.client.rest.GwtApplicationPath;
import org.googlecode.gwt.client.rest.GwtPath;
import org.googlecode.gwt.client.rest.RESTCallback;
import org.googlecode.gwt.shared.client.BootstrapData;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@GwtApplicationPath("resources")
@GwtPath("bootstrap")
public interface RESTBootstrapServiceAsync extends GWTRESTfulService<RESTBootstrapServiceAsync> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Request bootstrap(RESTCallback<BootstrapData> callback);
}
