package org.googlecode.gwt.bootstrap.client;

import com.google.gwt.http.client.Request;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.rest.client.rest.GWTRESTfulService;
import org.googlecode.gwt.rest.client.rest.GwtApplicationPath;
import org.googlecode.gwt.rest.client.rest.GwtPath;
import org.googlecode.gwt.rest.client.rest.RESTCallback;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@GwtApplicationPath("resources")
@GwtPath("bootstrap")
public interface RESTBootstrapServiceAsync extends GWTRESTfulService<RESTBootstrapServiceAsync> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Request bootstrap(RESTCallback<BootstrapData> callback);
}
