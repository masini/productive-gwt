package org.googlecode.gwt.bootstrap.server.security;

import javax.servlet.http.HttpServletRequest;

public class PDASecurityExtractor extends DefaultJavaEESecurityExtractor {

	
	public PDASecurityExtractor(RoleDefinitionExtractor extractionStrategy) {
		super(extractionStrategy);
	}

	public String getCurrentUsername(HttpServletRequest request) {
		return request.getLocalAddr();
	}
	
//	public String[] getJavaEERoles(HttpServletRequest request) {
//		return new String[] {"PDAROLE","PDAROLE"};
//	}


}
