/**
 *
 */
package org.googlecode.gwt.bootstrap.client;

import java.util.List;

import org.googlecode.gwt.base.client.BootstrapData;

/**
 * CallBack for the bootstrapService
 */
public class BootstrapAsyncCallback extends LoggingChainingCallback {

    private List<BootstrapEventListener> listeners;

    public BootstrapAsyncCallback( List< BootstrapEventListener> listeners ) {
        this.listeners = listeners;
        setAsyncCallback( this );
    }

    /**
     * invoke when the bootstrapService fail
     */
    public void onFailure( final Throwable caught ) {
        //noinspection ForLoopReplaceableByForEach
        for ( int i = 0; i < listeners.size() ; i++ ) {
            Object listener = listeners.get( i );
            BootstrapEventListener beListener = ( BootstrapEventListener )listener;
            if ( beListener != null ) {
                beListener.onBootstrapFailed( new BootstrapEvent() {
                    {
                        setEventDescription( "GWT error caught: " + caught );
                    }
                } );
            }
        }
    }

    /**
     * invoke when the bootstrapService is success
     */
    public void onSuccess( Object result ) {
        //noinspection ForLoopReplaceableByForEach
        for ( int i = 0; i < listeners.size() ; i++ ) {
            Object listener = listeners.get( i );
            BootstrapEventListener beListener = ( BootstrapEventListener )listener;
            if ( beListener != null ) {
                //noinspection InstanceofInterfaces
                if ( result != null && result instanceof BootstrapData ) {
                    beListener.onBootstrap( new BootstrapEvent( ( BootstrapData )result ) );
                }
                else {
                    beListener.onBootstrapFailed( new BootstrapEvent() {
                        {
                            setEventDescription( "No authorization data received or authorization system was not set-up correctly" );
                        }
                    } );
                }
            }
        }
	}
	
}