package com.mycompany.project.client.rest;

import com.google.gwt.http.client.Request;
import org.googlecode.gwt.rest.client.rest.GWTRESTfulService;
import org.googlecode.gwt.rest.client.rest.RESTCallback;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationPath("services")
@Path("restendpoint")
public interface MyRESTfulServiceAsync extends GWTRESTfulService<MyRESTfulServiceAsync> {
    @Path("method")
    @GET
    @Produces("application/json")
    Request lista(RESTCallback callback);
}
