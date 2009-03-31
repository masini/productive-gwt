package org.googlecode.gwt.bootstrap.server;

import it.iconmedialab.esselunga.auth.AuthorizationData;
import it.iconmedialab.esselunga.auth.Parameters;
import it.iconmedialab.esselunga.auth.Roles;
import it.iconmedialab.esselunga.auth.validation.ValidationAccessor;
import it.iconmedialab.esselunga.auth.validation.ValidationData;
import it.iconmedialab.esselunga.auth.validation.ValidationPrincipal;
import it.iconmedialab.esselunga.auth.validation.ValidationProfile;
import it.iconmedialab.esselunga.auth.validation.ValidationRole;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;
import org.googlecode.gwt.bootstrap.server.security.JavaEESecurityExtractor;

/**
 * @author Luca Masini
 */
public class ValidationUserInfoResolver implements UserInfoResolver {

    private ValidationAccessor validationAccessor;
    private boolean j2eeMode;
    private String applicationCode;
    private JavaEESecurityExtractor javaEESecurityExtractor;

    public UserInfo getCurrentUserInfo( final HttpServletRequest request ) {

        String username = javaEESecurityExtractor.getCurrentUsername( request );

        ValidationData data = validationAccessor.getData( username, applicationCode );
        AuthorizationData authData = getAuthorizationData( data );

        ValidationPrincipal validationPrincipal = getValidationPrincipal( username );

        String firstName = validationPrincipal.getFirstName();
        String lastName = validationPrincipal.getLastName();

        String[] validationRoles = extractRoles( authData );
        String[] j2eeRoles = javaEESecurityExtractor.getJavaEERoles( request );

        String[] roles = new String[validationRoles.length + j2eeRoles.length];

        System.arraycopy( validationRoles, 0, roles, 0, validationRoles.length );
        System.arraycopy( j2eeRoles, 0, roles, validationRoles.length, j2eeRoles.length );

        Map<String,String> authorizationParameters = extractAuthorizationParameters( authData );

        UserInfo userInfo = newUserInfo( username, firstName, lastName, roles, authorizationParameters );
        //DefaultUserInfo userInfo = new DefaultUserInfo( username, firstName, lastName, roles, authorizationParameters );
        return userInfo;
    }
    
    protected UserInfo newUserInfo( String username, String firstName, String lastName, String[] roles, Map<String,String> authorizationParameters ) {
        return new DefaultUserInfo( username, firstName, lastName, roles, authorizationParameters );
    }
    

    @SuppressWarnings( "unchecked" )
    private AuthorizationData getAuthorizationData( final ValidationData data ) {
        Set<String> roleSet = new HashSet<String>();
        Map<String, String> paramMap = new HashMap<String, String>();
        if ( j2eeMode ) {
            for ( Object o : data.getRoles() ) {
                ValidationRole validationRole = ( ValidationRole )o;
                roleSet.add( validationRole.getCode() );
            }
            Set flattenedProfiles = data.getFlattenedProfiles();
            if ( !flattenedProfiles.isEmpty() ) {
                ValidationProfile profile = ( ValidationProfile )flattenedProfiles.iterator().next();
                paramMap.putAll( profile.getParameters() );
            }
        }
        else if ( !data.getRoles().isEmpty() ) {
            ValidationRole validationRole = ( ValidationRole )data.getRoles().iterator().next();
            roleSet.add( validationRole.getCode() );
            if ( !validationRole.getProfiles().isEmpty() ) {
                ValidationProfile profile = ( ValidationProfile )validationRole.getProfiles().iterator().next();
                paramMap.putAll( profile.getParameters() );
            }
        }
        return new AuthorizationData( new Roles( roleSet, data.getRolesDescription() ), new Parameters( paramMap ) );
    }

    private ValidationPrincipal getValidationPrincipal( final String username ) {
        ValidationPrincipal validationPrincipal = validationAccessor.getPrincipal( username );
        return validationPrincipal;
    }

    protected Map<String,String> extractAuthorizationParameters( final AuthorizationData authData ) {
        Map<String, String> authorizationParameters = new HashMap<String, String>();
        Parameters parameters = authData.getParameters();
        Enumeration<String> parameterNames = parameters.getNames();
        while ( parameterNames.hasMoreElements() ) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = (String) parameters.get( parameterName );
            //noinspection unchecked
            authorizationParameters.put( parameterName, parameterValue );
        }
        return authorizationParameters;
    }

    private String[] extractRoles( final AuthorizationData authData ) {
        List<String> roleList = new ArrayList<String>();
        Roles roles = authData.getRoles();
        for ( Iterator<String> iterator = roles.iterator(); iterator.hasNext() ; ) {
            String roleName =(String) iterator.next();
            // String roleDescription = (String)roles.getRolesDescriptions().get(roleName);
            roleList.add( roleName );
        }
        String[] rolesArray = roleList.toArray( new String[roleList.size()] );
        return rolesArray;
    }

    public void setApplicationCode( String applicationCode ) {
        this.applicationCode = applicationCode;
    }

    public void setJ2eeMode( boolean mode ) {
        j2eeMode = mode;
    }

    public void setValidationAccessor( ValidationAccessor validationAccessor ) {
        this.validationAccessor = validationAccessor;
    }

    public void setJavaEESecurityExtractor( JavaEESecurityExtractor javaEESecurityExtractor ) {
        this.javaEESecurityExtractor = javaEESecurityExtractor;
    }
}
