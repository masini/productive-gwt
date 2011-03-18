package org.googlecode.gwt.bootstrap.server;

import org.googlecode.gwt.base.client.BootstrapData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

public class TestParametersInDefaultBootstrapDataResolver {

    private static final String UNKNOWN = "unknown";
    private Map<String, String> params = new HashMap<String, String>();
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private UserInfoResolver userInfoResolver = mock(UserInfoResolver.class);
    private DefaultBootstrapDataResolver resolver;

    @Before
    public void before() {
        params.clear();
        reset(request, userInfoResolver);

        resolver = new DefaultBootstrapDataResolver();
        resolver.setUserInfoResolver(userInfoResolver);
    }

    @Test
    public void testNoParameters() {

        resolver.useParams(params);
        BootstrapData bootstrapData = resolver.getBootstrapData(request);

        Assert.assertEquals(UNKNOWN, bootstrapData.getApplicationCode());
        Assert.assertEquals(UNKNOWN, bootstrapData.getApplicationName());
        Assert.assertEquals(UNKNOWN, bootstrapData.getApplicationVersion());
    }

    @Test
    public void testParameters() {
        params.put("applicationCode","EX000");
        params.put("applicationName","Example");
        params.put("applicationVersion","2.0.x");
        params.put("homePageURLString","http://www.google.com");


        resolver.useParams(params);
        BootstrapData bootstrapData = resolver.getBootstrapData(request);

        Assert.assertEquals("EX000", bootstrapData.getApplicationCode());
        Assert.assertEquals("Example", bootstrapData.getApplicationName());
        Assert.assertEquals("2.0.x", bootstrapData.getApplicationVersion());
        Assert.assertEquals("http://www.google.com", bootstrapData.getHomePageURLString());
    }

}
