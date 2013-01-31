package org.googlecode.gwt.bootstrap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Implements the bootstrap service
 *
 */
public class BootstrapServiceFactory {
    private static BootstrapServiceAsync proxy;
    private static HostedModeBootstrapServiceAsync hostedModeProxy;

    private BootstrapServiceFactory() {}

    /**
     * Implements the bootstrap service
     */    
    public static BootstrapServiceAsync getInstance() {
        if ( proxy == null ) {
            // since this is client code, DCL and synchronization issues do not apply
            // noinspection NonThreadSafeLazyInitialization
            proxy = ( BootstrapServiceAsync )GWT.create( BootstrapService.class );
        	initializeProxy(proxy);
        }
        return proxy;
    }

    public static HostedModeBootstrapServiceAsync getHostedModeInstance() {
        if ( hostedModeProxy == null ) {
            // since this is client code, DCL and synchronization issues do not apply
            // noinspection NonThreadSafeLazyInitialization
        	hostedModeProxy = ( HostedModeBootstrapServiceAsync )GWT.create( HostedModeBootstrapService.class );
        	initializeProxy(hostedModeProxy);
        }
        return hostedModeProxy;
    }

    private static void initializeProxy(Object proxy) {
        ServiceDefTarget target = ( ServiceDefTarget )proxy;
        target.setServiceEntryPoint( GWT.getModuleBaseURL()+"bootstrap" );
    }
}
