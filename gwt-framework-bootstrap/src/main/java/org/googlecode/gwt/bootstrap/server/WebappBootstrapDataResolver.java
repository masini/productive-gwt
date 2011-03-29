package org.googlecode.gwt.bootstrap.server;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.googlecode.gwt.base.client.BootstrapData;

/**
 * A {@link org.googlecode.gwt.bootstrap.server.BootstrapDataResolver } which takes the application info (version, name,etc.) from parameters
 * in the web.xml.
 * @author Arkady Syamtomov
 * @author Davide Baroncelli
 */
public class WebappBootstrapDataResolver extends DefaultBootstrapDataResolver {
    private String appCodeParam = DEFAULT_APP_CODE_PARAM;
    private String appNameParam = DEFAULT_APP_NAME_PARAM;
    private String appVersionParam = DEFAULT_APP_VERSION_PARAM;
    private String homePageURLString = DEFAULT_HOME_PAGE_URL_PARAM;

    public static final String DEFAULT_APP_CODE_PARAM = "app.code";
    public static final String DEFAULT_APP_NAME_PARAM = "app.name";
    public static final String DEFAULT_APP_VERSION_PARAM = "app.version";
    public static final String DEFAULT_HOME_PAGE_URL_PARAM = "home.page.url";
    
    public BootstrapData getBootstrapData( HttpServletRequest request ) {
        BootstrapData data = super.getBootstrapData( request );
        ServletContext ctx = request.getSession().getServletContext();
        if ( ctx != null ) {
            data.setApplicationCode( ctx.getInitParameter( appCodeParam ) );
            data.setApplicationName( ctx.getInitParameter( appNameParam ) );
            data.setApplicationVersion( ctx.getInitParameter( appVersionParam ) );
            data.setHomePageURLString( ctx.getInitParameter( homePageURLString ) );
        }
        return data;
    }

    public void setAppCodeParam( String appCodeParam ) {
        this.appCodeParam = appCodeParam;
    }

    public void setAppNameParam( String appNameParam ) {
        this.appNameParam = appNameParam;
    }

    public void setAppVersionParam( String appVersionParam ) {
        this.appVersionParam = appVersionParam;
    }
    
    public void setHomePageURLString( String homePageURLString ) {
        this.homePageURLString = homePageURLString;
    }
}
