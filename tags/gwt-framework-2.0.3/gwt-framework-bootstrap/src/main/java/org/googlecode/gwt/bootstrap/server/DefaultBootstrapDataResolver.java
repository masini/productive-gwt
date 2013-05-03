package org.googlecode.gwt.bootstrap.server;

import org.googlecode.gwt.base.client.ApplicationContextData;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.base.client.DefaultBootstrapData;
import org.googlecode.gwt.base.client.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * A {@link org.googlecode.gwt.bootstrap.server.BootstrapDataResolver } that uses a delegate {@link org.googlecode.gwt.bootstrap.server.UserInfoResolver }
 * in order to resolve meaningful user info.
 *
 *         Date: 24-ago-2007
 *         Time: 20.04.23
 */
public class DefaultBootstrapDataResolver implements UserInfoBootstrapDataResolver {
    protected UserInfoResolver userInfoResolver;
    protected BootstrapDataDecorator bootstrapDataDecorator;
    private String applicationCode = "unknown";
    private String applicationVersion = "unknown";
    private String applicationName = "unknown";
    private String homePageURLString = "_self";

    private String serverNameProperty = DEFAULT_SERVER_NAME_PROPERTY;
    private String clusterNameProperty = DEFAULT_CLUSTER_NAME_PROPERTY;

    public static final String DEFAULT_SERVER_NAME_PROPERTY = "weblogic.Name";
    private static final String DEFAULT_CLUSTER_NAME_PROPERTY = "weblogic.Domain";

    public BootstrapData getBootstrapData( final HttpServletRequest request ) {
        if ( request == null ) {
            throw new IllegalArgumentException( "HttpServletRequest request parameter cannot be null" );
        }

        BootstrapData bootstrapData = newBootstrapData();
        UserInfo userInfo = userInfoResolver.getCurrentUserInfo( request );
        bootstrapData.setUserInfo( userInfo );
        bootstrapData.setApplicationCode( applicationCode );
        bootstrapData.setApplicationName( applicationName );
        bootstrapData.setApplicationVersion( applicationVersion );
        bootstrapData.setHomePageURLString(homePageURLString);
        String serverName = retrieveServerName();
        bootstrapData.setServerName( serverName );
        String clusterName = retrieveClusterName();
        bootstrapData.setClusterName( clusterName );
        String localServerName = retrieveServerHostName( request );
        bootstrapData.setServerHostName( localServerName );
        onBootstrapDataAvailable( bootstrapData );
        return bootstrapData;
    }
    
    public ApplicationContextData getAppContextData(HttpServletRequest request, String appContextName) {
    	if ( request == null ) {
            throw new IllegalArgumentException( "HttpServletRequest request parameter cannot be null" );
        }
				
		ApplicationContextData appContextData = new ApplicationContextData();		
		return appContextData;
	}

	protected BootstrapData newBootstrapData() {
        return new DefaultBootstrapData();
    }

    /**
     * If a {@link #setBootstrapDataDecorator(org.googlecode.gwt.bootstrap.server.BootstrapDataDecorator) decorator} has been set, calls its
     * {@link BootstrapDataDecorator#decorateBootstrapData(org.googlecode.gwt.base.client.BootstrapData) } right after resolving the
     * {@link org.googlecode.gwt.base.client.BootstrapData } object.
     *
     * @param data
     */
    protected void onBootstrapDataAvailable( BootstrapData data ) {
        if ( bootstrapDataDecorator != null ) {
            bootstrapDataDecorator.decorateBootstrapData( data );
        }
    }

    public void setUserInfoResolver( UserInfoResolver userInfoResolver ) {
        this.userInfoResolver = userInfoResolver;
    }

    public void setApplicationCode( String applicationCode ) {
        this.applicationCode = applicationCode;
    }

    public void setApplicationName( String applicationName ) {
        this.applicationName = applicationName;
    }

    public void setApplicationVersion( String applicationVersion ) {
        this.applicationVersion = applicationVersion;
    }

    public void setBootstrapDataDecorator( BootstrapDataDecorator bootstrapDataDecorator ) {
        this.bootstrapDataDecorator = bootstrapDataDecorator;
    }

    /**
     * @return The local server host name.
     */
    protected String retrieveServerHostName( HttpServletRequest httpServletRequest ) {
        String localServerName = null;
        if ( httpServletRequest != null ) {
            localServerName = httpServletRequest.getLocalName();
        }
        return localServerName;
    }

    protected String retrieveClusterName() {
        
    	String cluster = System.getProperty( clusterNameProperty );
    	if(cluster!=null){
    		return cluster;	
    	}
    	return "No cluster";
		
    }

    protected String retrieveServerName() {
        String serverName = System.getProperty( serverNameProperty );
        return serverName;
    }

	public void setHomePageURLString(String homePageURLString) {
		this.homePageURLString = homePageURLString;
	}

    public void useParams(Map<String, String> params) {
        if(params.containsKey("applicationCode")) {
            applicationCode = params.get("applicationCode");
        }
        if(params.containsKey("applicationName")) {
            applicationName = params.get("applicationName");
        }
        if(params.containsKey("applicationVersion")) {
            applicationVersion = params.get("applicationVersion");
        }
        if(params.containsKey("homePageURLString")) {
            homePageURLString = params.get("homePageURLString");
        }
    }
    
}