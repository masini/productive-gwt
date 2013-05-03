package org.googlecode.gwt.server.dummy;


import org.googlecode.gwt.shared.DefaultUserInfo;
import org.googlecode.gwt.shared.UserInfo;
import org.googlecode.gwt.shared.UserInfoResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

public class DummyUserInfoResolver implements UserInfoResolver {

	public UserInfo getCurrentUserInfo(HttpServletRequest request) {
		return new DefaultUserInfo("","","", new ArrayList<String>(), new HashMap<String, String>(0));
	}

}
