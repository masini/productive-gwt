package org.googlecode.gwt.bootstrap.server.security;

import javax.servlet.http.HttpServletRequest;

public class PDASecurityExtractor extends DefaultJavaEESecurityExtractor {

	
	public PDASecurityExtractor(RoleDefinitionExtractor extractionStrategy) {
		super(extractionStrategy);
	}

	public String getCurrentUsername(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

}
