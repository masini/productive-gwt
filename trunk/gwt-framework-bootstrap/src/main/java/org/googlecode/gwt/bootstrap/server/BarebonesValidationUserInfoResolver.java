package org.googlecode.gwt.bootstrap.server;

import it.iconmedialab.esselunga.auth.AuthFilter;
import it.iconmedialab.esselunga.auth.AuthServletRequest;
import it.iconmedialab.esselunga.auth.AuthorizationData;
import it.iconmedialab.esselunga.auth.Parameters;
import it.iconmedialab.esselunga.auth.Roles;
import it.iconmedialab.esselunga.auth.validation.ValidationPrincipal;

import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;

/**
 * A class extracting the current user info from a request set by the validation library. Requires that the validation filter is set into the webapp.
 *
 * @@author Davide Baroncelli <davide.baroncelli@@esselunga.it>
 *         Date: 7-giu-2007
 *         Time: 19.07.50
 */
public class BarebonesValidationUserInfoResolver implements UserInfoResolver {

	private static Log log = LogFactory.getLog(BarebonesValidationUserInfoResolver.class);
	
    public String getCurrentUsername( HttpServletRequest request ) {
        Principal userPrincipal = getCurrentPrincipal( request );
        if ( userPrincipal == null ) {
            throw new RuntimeException( "couldn't identify current principal: is JEE security correctly set up for the application?" );
        }
        String username = userPrincipal.getName();
        return username;
    }

    public Principal getCurrentPrincipal( HttpServletRequest request ) {
        Principal userPrincipal = request.getUserPrincipal();
        return userPrincipal;
    }

    public String getCurrentUserRole( HttpServletRequest request ) {
        AuthServletRequest servletRequest = ( AuthServletRequest )request;
        String role = servletRequest.getRole();
        return role;
    }

    public String getCurrentUserParameter( HttpServletRequest request, String parameterName ) {
        AuthServletRequest servletRequest = ( AuthServletRequest )request;
        String parameterValue = servletRequest.getUserParameter( parameterName );
        return parameterValue;
    }

    public UserInfo getCurrentUserInfo( HttpServletRequest request ) {
        String username = request.getRemoteUser();

        String firstName = null;
        String lastName = null;
        try {        
	        ValidationPrincipal validationPrincipal = ( ValidationPrincipal )request.getUserPrincipal();
	        firstName = validationPrincipal.getFirstName();
	        lastName = validationPrincipal.getLastName();
        }
        catch(Exception e)
        {
        	log.error("Exception thrown while getting UserPrincipal: ", e);
        }

        Map roleMap = extractRoles( request );
        Map authorizationParameters = extractAuthorizationParameters( request );

        UserInfo userInfo = newUserInfo( username, firstName, lastName, roleMap, authorizationParameters );
        return userInfo;
    }

    protected UserInfo newUserInfo( String username, String firstName, String lastName, Map roles, Map authorizationParameters ) {
        return new DefaultUserInfo( username, firstName, lastName, roles, authorizationParameters );
    }

    protected Map extractAuthorizationParameters( HttpServletRequest request ) {
        AuthorizationData authData = ( AuthorizationData )request.getSession().getAttribute( AuthFilter.AUTHDATA_SESSION_ATTR );
        Map authorizationParameters = new HashMap();
        Parameters parameters = authData.getParameters();
        Enumeration parameterNames = parameters.getNames();
        while ( parameterNames.hasMoreElements() ) {
            String parameterName = ( String )parameterNames.nextElement();
            String parameterValue = parameters.get( parameterName );
            //noinspection unchecked
            authorizationParameters.put( parameterName, parameterValue );
        }
        return authorizationParameters;
    }

    protected Map extractRoles( HttpServletRequest request ) {
        AuthorizationData authData = ( AuthorizationData )request.getSession().getAttribute( AuthFilter.AUTHDATA_SESSION_ATTR );
        Map<String,String> roleMap = new HashMap<String,String>();
        Roles roles = authData.getRoles();
        for ( Iterator iterator = roles.iterator(); iterator.hasNext() ; ) {
            String roleName = ( String )iterator.next();
            String roleDescription = (String)roles.getRolesDescriptions().get(roleName);
            roleMap.put( roleName, roleDescription != null ? roleDescription : roleName);
        }
        return roleMap;
    }
}