package org.googlecode.gwt.base.client;

import java.io.Serializable;

/**
 * This class contains various information useful at bootstrap, both regarding
 * the application (name, code, version, location, etc.) and the connected user
 * (username, roles, parameters, etc.)
 */
public class DefaultBootstrapData implements Serializable, BootstrapData {
	private static final long serialVersionUID = 1L;
	private long systemTime;
	private String applicationName;
	private String applicationCode;
	private String applicationVersion;
	private String serverName;
	private String clusterName;
	private String serverHostName;
	private String homePageURLString;
	private UserInfo userInfo;
    private ApplicationBootstrapData applicationBootstrapData;

    /**
	 * Get the systemTime
	 * 
	 * @return long - the systemTime
	 */
    public long getSystemTime() {
		return systemTime;
	}

	/**
	 * Set systemTime
	 * 
	 * @param systemTime -
	 *            The systemTime to set
	 */
    public void setSystemTime(long systemTime) {
		this.systemTime = systemTime;
	}

	/**
	 * Get the applicationCode
	 * 
	 * @return String - The applicationCode
	 */
    public String getApplicationCode() {
		return applicationCode;
	}

	/**
	 * Set the applicationCode
	 * 
	 * @param applicationCode -
	 *            The applicationCode
	 */
	
    public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	/**
	 * Get the applicationName
	 * 
	 * @return String - The applicationCode
	 */
	
    public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Set the applicationName
	 * 
	 * @param applicationName -
	 *            The applicationName
	 */
	
    public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * Get the applicationVersion
	 * 
	 * @return String - The applicationVersion
	 */
	
    public String getApplicationVersion() {
		return applicationVersion;
	}

	/**
	 * Set the applicationVersion
	 * 
	 * @param applicationVersion -
	 *            The applicationVersion
	 */
	
    public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	/**
	 * Get the serverName
	 * 
	 * @return String - The serverName
	 */
	
    public String getServerName() {
		return serverName;
	}

	/**
	 * Set the serverName
	 * 
	 * @param serverName -
	 *            The serverName
	 */
	
    public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * Set the userInfo
	 * 
	 * @param userInfo -
	 *            The userInfo
	 */
	
    public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Get the UserInfo
	 * 
	 * @return UserInfo - The userInfo
	 */
	
    public UserInfo getUserInfo() {
		return userInfo;
	}

	
    public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	
    public String getClusterName() {
		return clusterName;
	}

	
    public void setServerHostName(String serverHostName) {
		this.serverHostName = serverHostName;
	}

	
    public String getServerHostName() {
		return serverHostName;
	}

	public String toString() {
		StringBuffer l_bsStringBuffer = new StringBuffer();
		l_bsStringBuffer.append("[");
		l_bsStringBuffer.append(applicationName);
		l_bsStringBuffer.append("]");
		l_bsStringBuffer.append("[");
		l_bsStringBuffer.append(applicationCode);
		l_bsStringBuffer.append("]");
		l_bsStringBuffer.append("[");
		l_bsStringBuffer.append(applicationVersion);
		l_bsStringBuffer.append("]");
		l_bsStringBuffer.append("[");
		l_bsStringBuffer.append(serverName);
		l_bsStringBuffer.append("]");
		l_bsStringBuffer.append("[");
		l_bsStringBuffer.append(clusterName);
		l_bsStringBuffer.append("]");
		l_bsStringBuffer.append("[");
		l_bsStringBuffer.append(serverHostName);
		l_bsStringBuffer.append("]");
		if (userInfo != null) {
			l_bsStringBuffer.append("[");
			l_bsStringBuffer.append(userInfo.toString());
			l_bsStringBuffer.append("]");
		}

		return l_bsStringBuffer.toString();
	}

	
    public String getHomePageURLString() {
		return homePageURLString;
	}

	
    public void setHomePageURLString(String homePageURLString) {
		this.homePageURLString = homePageURLString;
	}

    public ApplicationBootstrapData getApplicationBootstrapData() {
        return this.applicationBootstrapData;
    }

        public void setApplicationBootstrapData(ApplicationBootstrapData applicationBootstrapData) {
        this.applicationBootstrapData = applicationBootstrapData;
    }

}
