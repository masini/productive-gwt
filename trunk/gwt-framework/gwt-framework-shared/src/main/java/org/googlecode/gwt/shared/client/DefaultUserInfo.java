package org.googlecode.gwt.shared.client;


import org.googlecode.gwt.shared.client.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class useful to send to a gwt client information about the user.
 *
 */
public class DefaultUserInfo implements UserInfo {
    private String username;
    private String firstName;
    private String lastName;
    private String codaz;
    private String soc;
    private HashMap<String,String> roleDescriptions = new HashMap<String,String>();
    private HashMap<String,String> userParameters = new HashMap<String,String>();

    private static final long serialVersionUID = 1931201066080419627L;

    public DefaultUserInfo( String username, String firstName, String lastName, String codaz, String soc, List<String> roles, Map<String,String> userParameters) {
        this.username = username;
        this.userParameters = checkNullMap(userParameters);
        this.firstName = firstName;
        this.lastName = lastName;
        this.codaz = codaz;
        this.soc = soc;
        this.roleDescriptions = new HashMap<String,String>();
        if(roles != null) {
            for(String role: roles) {
                this.roleDescriptions.put( role, null );
            }
        }
    }

    public DefaultUserInfo(UserInfo userInfo) {
        this(userInfo.getUsername(), userInfo.getFirstName(),
                userInfo.getLastName(),  userInfo.getCodaz(), userInfo.getSoc(),
                userInfo.getRoles(), userInfo.getUserParameters());
    }

    private HashMap<String, String> checkNullMap(Map<String, String> map) {
        return (map == null ? new HashMap<String,String>() : new HashMap<String,String>(map));
    }

    public DefaultUserInfo( String username, String firstName, String lastName, String codaz, String soc, Map<String,String> roleDescriptions, Map<String,String> userParameters ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.codaz = codaz;
        this.soc = soc;
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

    public List<String> getUserParameterNames() {
        return new ArrayList<String>(userParameters.keySet());
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

    public List<String> getRoles() {
        return new ArrayList<String>(roleDescriptions.keySet());
    }

    public boolean getIsUserInRole(String role) {
        return roleDescriptions.keySet().contains( role );
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append( "{" );
        buffer.append( username );
        buffer.append( "}" );


        return buffer.toString();
    }

    public HashMap<String,String> getRoleDescriptions() {
        return roleDescriptions;
    }

    public HashMap<String,String> getUserParameters() {
        return userParameters;
    }

    public String getCodaz() {
        return codaz;
    }

    @Override
    public String getSoc() {
        return soc;
    }
}
