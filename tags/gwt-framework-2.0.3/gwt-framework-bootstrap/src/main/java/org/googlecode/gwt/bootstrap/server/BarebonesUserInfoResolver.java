package org.googlecode.gwt.bootstrap.server;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;
import org.googlecode.gwt.bootstrap.server.security.JavaEESecurityExtractor;

import java.util.Arrays;
import java.util.List;

public class BarebonesUserInfoResolver implements UserInfoResolver {

	private JavaEESecurityExtractor javaEESecurityExtractor;
	
	public UserInfo getCurrentUserInfo(HttpServletRequest request) {
				
		String username = javaEESecurityExtractor.getCurrentUsername(request);		
		List<String> roles = Arrays.asList(javaEESecurityExtractor.getJavaEERoles(request));
				
		return new DefaultUserInfo(username,null,null,roles,null);		
	}

	public void setJavaEESecurityExtractor(
			JavaEESecurityExtractor javaEESecurityExtractor) {
		this.javaEESecurityExtractor = javaEESecurityExtractor;
	}			
	
}
