package org.googlecode.gwt.bootstrap.client.server;

import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;
import org.googlecode.gwt.bootstrap.server.BootstrapDataResolver;
import org.googlecode.gwt.bootstrap.server.BootstrapDataResolverFactory;
import org.googlecode.gwt.bootstrap.server.UserInfoResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public class TestUserInfoResolverSerlvetConfig {

    private ServletConfig servletConfig = mock(ServletConfig.class);
    private HttpServletRequest servletRequest = mock(HttpServletRequest.class);

    @Before
    public void before() {
        reset(servletConfig);
        reset(servletRequest);
    }

    @Test
    public void mustUseDefaultIfNotPassed() throws ServletException {

        Map<String, String> params = new HashMap<String, String>();

        BootstrapDataResolverFactory bootstrapDataResolverFactory = BootstrapDataResolverFactory.Utils.createBootstrapDataResolverFactory(servletConfig);

        BootstrapDataResolver bootstrapDataResolver = bootstrapDataResolverFactory.createUserInfoResolver(params);

        BootstrapData bootstrapData = bootstrapDataResolver.getBootstrapData(servletRequest);
        UserInfo userInfo = bootstrapData.getUserInfo();
        Assert.assertEquals("", userInfo.getFirstName());
        Assert.assertEquals("", userInfo.getLastName());
        Assert.assertEquals("", userInfo.getUsername());
        Assert.assertEquals(0, userInfo.getRoles().size());
    }

    @Test
    public void mustUseDeclaredImplementationIfPassed() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userInfoResolver", "org.googlecode.gwt.bootstrap.client.server.TestUserInfoResolverSerlvetConfig$FakeUserInfoResolver");

        BootstrapDataResolverFactory bootstrapDataResolverFactory = BootstrapDataResolverFactory.Utils.createBootstrapDataResolverFactory(servletConfig);

        BootstrapDataResolver bootstrapDataResolver = bootstrapDataResolverFactory.createUserInfoResolver(params);

        BootstrapData bootstrapData = bootstrapDataResolver.getBootstrapData(servletRequest);
        DefaultUserInfo userInfo = new DefaultUserInfo(bootstrapData.getUserInfo());
        Assert.assertEquals("testfirstname", userInfo.getFirstName());
        Assert.assertEquals("testlastname", userInfo.getLastName());
        Assert.assertEquals("testusername", userInfo.getUsername());
        Assert.assertEquals("testrole", userInfo.getRoles().get(0));
        Assert.assertEquals("value1", userInfo.getUserParameter("param1"));
    }

    public static class FakeUserInfoResolver implements UserInfoResolver {

        @Override
        public UserInfo getCurrentUserInfo(HttpServletRequest request) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("param1", "value1");
            return new DefaultUserInfo("testusername", "testfirstname", "testlastname", Arrays.asList("testrole"), param);
        }
    }

}
