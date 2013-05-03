package org.googlecode.gwt.bootstrap.server.dummy;

import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;
import org.googlecode.gwt.bootstrap.server.UserInfoResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

public class DummyUserInfoResolver implements UserInfoResolver {

	public UserInfo getCurrentUserInfo(HttpServletRequest request) {
		return new DefaultUserInfo("","","", new ArrayList<String>(), new HashMap<String, String>(0));
	}

}
