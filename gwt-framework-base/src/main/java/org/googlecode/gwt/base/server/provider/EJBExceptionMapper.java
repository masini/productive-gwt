package org.googlecode.gwt.base.server.provider;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<Exception> {

    private final static Log LOG = LogFactory.getLog(EJBExceptionMapper.class);

    public Response toResponse(Exception exception) {

        /*
        if (exception instanceof WebApplicationException || exception.getCause() instanceof WebApplicationException) {
            WebApplicationException wae = exception.getCause() instanceof WebApplicationException?(WebApplicationException) exception.getCause():(WebApplicationException) exception;
            return wae.getResponse();
        }
        */

        String uuid = UUID.randomUUID().toString();

        String errorMessage = "Errore nell'esecuzione del Bean, UUID: "+uuid;

        LOG.error(errorMessage, exception);
        System.err.println("ERROR " + errorMessage);
        exception.printStackTrace(System.err);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("UUID", uuid).build();
    }
}
