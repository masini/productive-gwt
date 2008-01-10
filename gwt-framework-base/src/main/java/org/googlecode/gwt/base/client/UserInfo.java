package org.googlecode.gwt.base.client;

import java.io.Serializable;

/**
 * @author Davide Baroncelli <davide.baroncelli@esselunga.it>
 *         Date: 24-ago-2007
 *         Time: 20.17.16
 */
public interface UserInfo extends Serializable {
    String DEFAULT_USER_ROLE = "UTEGEN";
    String DEFAULT_ADMIN_USER_ROLE = "UTEADM";
    String DEFAULT_CODNEG_PARAM_NAME = "CODNEG";
    String DEFAULT_SIGLA_PARAM_NAME = "SIGLA";
    String DEFAULT_LOCATION_PARAM_NAME = "LOCALITA";

    String getUsername();

    String getRole();
    
    String[] getRoles();
    
    boolean isUserInRole(String role);
    
    String getUserParameter( String parameterName );

    String getFirstName();

    String getLastName();

    String getRoleDescription();

    String getRoleDescription( String role );
}
