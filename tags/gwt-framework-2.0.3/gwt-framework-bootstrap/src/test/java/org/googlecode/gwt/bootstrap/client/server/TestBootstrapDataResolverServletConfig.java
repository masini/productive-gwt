package org.googlecode.gwt.bootstrap.client.server;

import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.bootstrap.server.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;


public class TestBootstrapDataResolverServletConfig {

    ServletConfig servletConfig = mock(ServletConfig.class);
    HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    Map<String, String> params = new HashMap<String, String>();

    @Before
    public void before() {
        reset(servletConfig);
        reset(servletRequest);
        params.clear();
    }

    @Test
    public void mustUseDefaultIfNotPassed() {

        BootstrapDataResolverFactory bootstrapDataResolverFactory = BootstrapDataResolverFactory.Utils.createBootstrapDataResolverFactory(servletConfig);
        BootstrapDataResolver userInfoResolver = bootstrapDataResolverFactory.createUserInfoResolver(params);

        Assert.assertTrue(userInfoResolver instanceof DefaultBootstrapDataResolver);

    }

    @Test
    public void mustUsePassedImplementation() {

        params.put("resolver", "org.googlecode.gwt.bootstrap.client.server.TestBootstrapDataResolverServletConfig$FakeRisolver");

        BootstrapDataResolverFactory bootstrapDataResolverFactory = BootstrapDataResolverFactory.Utils.createBootstrapDataResolverFactory(servletConfig);
        BootstrapDataResolver userInfoResolver = bootstrapDataResolverFactory.createUserInfoResolver(params);

        Assert.assertTrue(userInfoResolver instanceof FakeRisolver);
        Assert.assertFalse(userInfoResolver instanceof DefaultBootstrapDataResolver);

    }

    public static class FakeRisolver implements UserInfoBootstrapDataResolver {

        @Override
        public void setUserInfoResolver(UserInfoResolver userInfoResolver) {
        }

        @Override
        public BootstrapData getBootstrapData(HttpServletRequest request) {
            return null;
        }

        @Override
        public ApplicationContextData getAppContextData(HttpServletRequest request, String appContextName) {
            return null;
        }
    }


}
