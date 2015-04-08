package org.googlecode.gwt.server;

import org.googlecode.gwt.server.security.JavaEESecurityExtractor;
import org.googlecode.gwt.shared.client.DefaultUserInfo;
import org.googlecode.gwt.shared.client.UserInfo;
import org.googlecode.gwt.shared.server.UserInfoResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class BarebonesUserInfoResolver implements UserInfoResolver {

	private JavaEESecurityExtractor javaEESecurityExtractor;
	
	public UserInfo getCurrentUserInfo(HttpServletRequest request) {
				
		String username = javaEESecurityExtractor.getCurrentUsername(request);		
		List<String> roles = Arrays.asList(javaEESecurityExtractor.getJavaEERoles(request));
				
		return new DefaultUserInfo(username, null, null, null, null, roles, null);
	}

	public void setJavaEESecurityExtractor(
			JavaEESecurityExtractor javaEESecurityExtractor) {
		this.javaEESecurityExtractor = javaEESecurityExtractor;
	}			
	
}
