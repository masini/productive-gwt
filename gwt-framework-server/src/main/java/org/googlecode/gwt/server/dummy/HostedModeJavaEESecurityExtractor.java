package org.googlecode.gwt.server.dummy;


import org.googlecode.gwt.server.security.DefaultJavaEESecurityExtractor;
import org.googlecode.gwt.server.security.RoleDefinitionExtractor;

import javax.servlet.http.HttpServletRequest;

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
