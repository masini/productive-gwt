package org.googlecode.gwt.server.security;

import javax.servlet.http.HttpServletRequest;

public interface JavaEESecurityExtractor {

	String getCurrentUsername(HttpServletRequest request);

    String[] getJavaEERoles(HttpServletRequest request);
}
