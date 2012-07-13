package org.googlecode.gwt.bootstrap.server;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.googlecode.gwt.base.client.BootstrapData;

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
public class RESTBootstrapService {

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
    public String getBootstrapData(@Context HttpServletRequest request, @Context ServletConfig servletConfig) {
        try {
            init(servletConfig);
            BootstrapData bootstrapData = resolver.getBootstrapData(request);

            ObjectMapper m = new ObjectMapper();
            m.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
            return m.writeValueAsString(bootstrapData);
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        }
    }

}
