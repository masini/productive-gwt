package org.googlecode.gwt.bootstrap.server.security;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public abstract class JavaEESecurityExtractorAdapter {
    public String getCurrentUsername( HttpServletRequest request ) {
        Principal userPrincipal = request.getUserPrincipal();
        if ( userPrincipal == null ) {
            throw new RuntimeException( "couldn't identify current principal: is JEE security correctly set up for the application?" );
        }
        return userPrincipal.getName();
    }

    public String[] getJavaEERoles( HttpServletRequest request ) {
        Set<String> authorizedRoles = new HashSet<String>();

        String[] allRoles = getExtractionStrategy().extractAllRoles();
        for ( String role : allRoles ) {
            if ( request.isUserInRole( role ) ) {
                authorizedRoles.add( role );
            }
        }

        return authorizedRoles.toArray( new String[authorizedRoles.size()] );
    }

    public abstract RoleDefinitionExtractor getExtractionStrategy();
}