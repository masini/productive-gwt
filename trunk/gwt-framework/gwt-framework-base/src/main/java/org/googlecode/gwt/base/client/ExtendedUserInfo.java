package org.googlecode.gwt.base.client;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public interface ExtendedUserInfo extends UserInfo {
    boolean isUserInRole(String role);
    String getUserParameter( String parameterName );
    String getRoleDescription( String role );
}
