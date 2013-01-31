package org.googlecode.gwt.base.client;

public interface ExtendedUserInfo extends UserInfo {
    boolean isUserInRole(String role);
    String getUserParameter( String parameterName );
    String getRoleDescription( String role );
}
