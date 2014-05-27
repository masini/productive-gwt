package org.googlecode.gwt.server;

import org.googlecode.gwt.server.provider.NoCache;
import org.googlecode.gwt.shared.client.ApplicationBootstrapData;
import org.googlecode.gwt.shared.client.Bootstrap;
import org.googlecode.gwt.shared.client.BootstrapData;
import org.googlecode.gwt.shared.server.BootstrapDataResolver;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Path("bootstrap")
@RequestScoped
public class RESTBootstrapService {

    @Inject @Bootstrap
    ApplicationBootstrapData applicationBootstrapData;

    protected transient BootstrapDataResolver resolver;

    protected transient ApplicationContextDataResolver appContextDataResolver;

    public void init(ServletConfig servletConfig) throws ServletException {

        Map<String, String> params = extractInitParameters(servletConfig);

        BootstrapDataResolverFactory dataResolverFactory = BootstrapDataResolverFactory.Utils.createBootstrapDataResolverFactory(servletConfig);
        this.resolver = dataResolverFactory.createUserInfoResolver(params);

        appContextDataResolver = new DefaultApplicationContextDataResolver(resolver);
    }

    private Map<String, String> extractInitParameters(ServletConfig servletConfig) {
        Map<String, String> params = new HashMap<String, String>();
        for(Enumeration<String> paramsName = servletConfig.getInitParameterNames(); paramsName.hasMoreElements();) {
            String param = paramsName.nextElement();

            params.put(param, servletConfig.getInitParameter(param));
        }

        return params;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public BootstrapData getBootstrapData(@Context HttpServletRequest request, @Context ServletConfig servletConfig) {
        try {
            init(servletConfig);
            BootstrapData bootstrapData = resolver.getBootstrapData(request);
            bootstrapData.setApplicationBootstrapData(applicationBootstrapData);
            return bootstrapData;
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        }
    }

}
