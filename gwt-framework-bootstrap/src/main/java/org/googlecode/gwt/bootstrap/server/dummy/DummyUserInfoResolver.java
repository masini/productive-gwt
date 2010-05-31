package org.googlecode.gwt.bootstrap.server.dummy;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;
import org.googlecode.gwt.bootstrap.server.UserInfoResolver;

public class DummyUserInfoResolver implements UserInfoResolver {

	public UserInfo getCurrentUserInfo(HttpServletRequest request) {
		DefaultUserInfo userInfo = new DefaultUserInfo();
		userInfo.setUsername("");
		return userInfo;
	}

}
