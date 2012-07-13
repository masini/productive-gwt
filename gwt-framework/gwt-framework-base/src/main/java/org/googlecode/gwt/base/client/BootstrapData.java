package org.googlecode.gwt.base.client;

public interface BootstrapData {
    long getSystemTime();

    void setSystemTime(long systemTime);

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
}
