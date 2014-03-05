package org.googlecode.gwt.shared.client;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public interface BootstrapData extends Serializable {
    String getApplicationCode();

    void setApplicationCode(String applicationCode);

    String getApplicationName();

    void setApplicationName(String applicationName);

    String getApplicationVersion();

    void setApplicationVersion(String applicationVersion);

    String getServerName();

    void setServerName(String serverName);

    void setUserInfo(UserInfo userInfo);

    UserInfo getUserInfo();

    void setClusterName(String clusterName);

    String getClusterName();

    void setServerHostName(String serverHostName);

    String getServerHostName();

    String getHomePageURLString();

    void setHomePageURLString(String homePageURLString);

    ApplicationBootstrapData getApplicationBootstrapData();

    void setApplicationBootstrapData(ApplicationBootstrapData applicationBootstrapData);

}
