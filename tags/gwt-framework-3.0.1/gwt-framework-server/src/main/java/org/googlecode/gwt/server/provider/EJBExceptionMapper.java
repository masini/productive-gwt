package org.googlecode.gwt.server.provider;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<Exception> {

    private final static Log LOG = LogFactory.getLog(EJBExceptionMapper.class);

    public Response toResponse(Exception exception) {

        String uuid = UUID.randomUUID().toString();

        String errorMessage = "Errore nell'esecuzione del Bean, UUID: "+uuid;

        LOG.error(errorMessage, exception);
        System.err.println("ERROR " + errorMessage);
        exception.printStackTrace(System.err);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("UUID", uuid).build();
    }
}
