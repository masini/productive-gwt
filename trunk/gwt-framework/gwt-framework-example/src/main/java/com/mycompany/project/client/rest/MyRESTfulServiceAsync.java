package com.mycompany.project.client.rest;

import com.google.gwt.http.client.Request;
import org.googlecode.gwt.rest.client.rest.GWTRESTfulService;
import org.googlecode.gwt.rest.client.rest.RESTCallback;

import javax.ws.rs.*;

@ApplicationPath("services")
@Path("restendpoint")
public interface MyRESTfulServiceAsync extends GWTRESTfulService<MyRESTfulServiceAsync> {
    @Path("method/{id}")
    @GET
    @Produces("application/json")
    Request lista(@PathParam("id") long id, RESTCallback callback);
}
