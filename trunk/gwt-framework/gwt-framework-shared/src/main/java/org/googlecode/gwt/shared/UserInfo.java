package org.googlecode.gwt.shared;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public interface UserInfo extends Serializable {
    String DEFAULT_USER_ROLE = "UTEGEN";
    String DEFAULT_ADMIN_USER_ROLE = "UTEADM";
    String DEFAULT_CODNEG_PARAM_NAME = "CODNEG";
    String DEFAULT_SIGLA_PARAM_NAME = "SIGLA";
    String DEFAULT_LOCATION_PARAM_NAME = "LOCALITA";

    String getUsername();

    String getRole();

    List<String> getRoles();

    List<String> getUserParameterNames();

    String getFirstName();

    String getLastName();

    String getRoleDescription();

    Map<String, String> getUserParameters();
}
