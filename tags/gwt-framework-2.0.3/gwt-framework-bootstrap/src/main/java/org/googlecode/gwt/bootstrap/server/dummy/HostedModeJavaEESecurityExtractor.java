package org.googlecode.gwt.bootstrap.server.dummy;


import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.bootstrap.server.security.DefaultJavaEESecurityExtractor;
import org.googlecode.gwt.bootstrap.server.security.RoleDefinitionExtractor;

public class HostedModeJavaEESecurityExtractor extends DefaultJavaEESecurityExtractor {

    public HostedModeJavaEESecurityExtractor() {
        super();
    }

    public HostedModeJavaEESecurityExtractor( RoleDefinitionExtractor extractionStrategy ) {
        super( extractionStrategy );
    }

    public String getCurrentUsername( HttpServletRequest request ) {
        return "ictlm1";
    }

    public String[] getJavaEERoles( HttpServletRequest request ) {
        return getExtractionStrategy().extractAllRoles();
    }

}
