package org.googlecode.gwt.bootstrap.client;


import java.util.HashMap;
import java.util.Map;

import org.googlecode.gwt.base.client.ExtendedUserInfo;
import org.googlecode.gwt.base.client.UserInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Base class useful to send to a gwt client information about the user.
 *
 */
public class DefaultUserInfo implements ExtendedUserInfo {
    private String username;
    private String firstName;
    private String lastName;
    private Map<String,String> roleDescriptions = new HashMap<String,String>();
    private Map<String,String> userParameters = new HashMap<String,String>();

    private static final long serialVersionUID = 1931201066080419627L;

    public DefaultUserInfo( String username, String firstName, String lastName, String[] roles, Map<String,String> userParameters ) {
        this.username = username;
        this.userParameters = checkNullMap(userParameters);
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleDescriptions = new HashMap<String,String>();
        if(roles != null) {
        	//noinspection ForLoopReplaceableByForEach
	        for ( int i = 0; i < roles.length ; i++ ) {
	            String role = roles[ i ];
	            //noinspection unchecked
	            this.roleDescriptions.put( role, null );
	        }
        }
    }

    public DefaultUserInfo(UserInfo userInfo) {
        this(userInfo.getUsername(), userInfo.getFirstName(),
             userInfo.getLastName(), userInfo.getRoles(), userInfo.getUserParameters());
    }

	private Map<String, String> checkNullMap(Map<String, String> map) {
		return (map == null ? new HashMap<String,String>() : new HashMap<String,String>(map));
	}

    public DefaultUserInfo( String username, String firstName, String lastName, Map<String,String> roleDescriptions, Map<String,String> userParameters ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleDescriptions = checkNullMap(roleDescriptions);
        this.userParameters = checkNullMap(userParameters);
    }

    public DefaultUserInfo() {}

    public String getUsername() {
        return username;
    }

    public String getRole() {
        if ( roleDescriptions != null && roleDescriptions.size() > 0 ) {
            return ( String )roleDescriptions.keySet().iterator().next();
        }
        else {
            return null;
        }
    }

    public String getRoleDescription() {
        String role = getRole();
        return getRoleDescription( role );
    }

    public String getRoleDescription( String role ) {
        return ( String )( roleDescriptions != null ? roleDescriptions.get( role ) : null );
    }

    public String getUserParameter( String parameterName ) {
        return ( String )userParameters.get( parameterName );
    }
    
    public String[] getUserParameterNames() {
    	return ( String[] )userParameters.keySet().toArray( new String[ userParameters.size() ] );
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String[] getRoles() {
        //noinspection unchecked
        return ( String[] )roleDescriptions.keySet().toArray( new String[ roleDescriptions.size() ] );
    }

    public boolean isUserInRole( String role ) {
        return roleDescriptions.keySet().contains( role );
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append( "{" );
        buffer.append( username );
        buffer.append( "}" );


        return buffer.toString();
    }

    public Map<String,String> getRoleDescriptions() {
        return roleDescriptions;
    }

    public Map<String,String> getUserParameters() {
        return userParameters;
    }
}
