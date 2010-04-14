package org.googlecode.gwt.bootstrap.client;

import java.util.ArrayList;
import java.util.List;

import org.googlecode.gwt.base.client.ApplicationContextFactory;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.base.client.LogText;
import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.bootstrap.client.utils.BootstrapConstants;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;

/**
 * An abstract class that can be extended by GWT client application to automatically get integrated with the bootstrap process.
 * Subclasses should implement {@link #onBootstrapCompleted() } instead of {@link #onModuleLoad() }.
 *
 * @author Arkady Syamtomov (cslas2)
 */
public class BootstrapEntryPoint implements EntryPoint {
	protected BootstrapData bootstrapData;	
    protected static final BootstrapConstants bsConstants;
    private List<BootstrapEventListener> bootstrapEventListeners;
    

    static {
    	LogText.writeOnLogText("==> static " + BootstrapEntryPoint.class.getName());
        bsConstants = ( BootstrapConstants )GWT.create( BootstrapConstants.class );
    }

    protected BootstrapEntryPoint() {
    	LogText.writeOnLogText("==> constructor " + BootstrapEntryPoint.class.getName());
        bootstrapEventListeners = new ArrayList<BootstrapEventListener>();
    }

    /**
     * Adds a new AuthenticationEventListener object to the list of listeners
     * and call BootstrapService for authentication
     * @param aeListener - AuthenticationEventListener listener
     */
    public void bootstrap( BootstrapEventListener aeListener ) {
    	addBootstrapEventListener( aeListener );
        BootstrapServiceFactory.getInstance().getBootstrapData( new BootstrapAsyncCallback( bootstrapEventListeners ) );
    }

    /**
     * Adds a new AuthenticationEventListener object to the list of listeners
     * @param aeListener - AuthenticationEventListener listener
     */
    public void addBootstrapEventListener( BootstrapEventListener aeListener ) {
        //noinspection unchecked
        bootstrapEventListeners.add( aeListener );
    }
    
    /**
     * Remove a AuthenticationEventListener object to the list of listeners
     * @param aeListener - AuthenticationEventListener listener
     */
    public void removeBootstrapEventListener( BootstrapEventListener aeListener ) {
        if ( bootstrapEventListeners.contains( aeListener ) ) {
            bootstrapEventListeners.remove( aeListener );
        }
    }

    /**
     * Get list of registered AuthenticationEventListener objects
     * @return List - list of AuthenticationEventListener
     */
    public List<BootstrapEventListener> getBootstrapEventListeners() {
        return bootstrapEventListeners;
    }

    /**
     * The entryPoint of all application
     */
    public void onModuleLoad() {
    	LogText.writeOnLogText("==> onModuleLoad " + BootstrapEntryPoint.class.getName());
        GWT.setUncaughtExceptionHandler( new MyUncaughtExceptionHandler() );

        bootstrap( new BootstrapEventListener() {

            public void onBootstrapFailed( BootstrapEvent bootstrapEvent ) {
                
            	//MessageBox.alert( "Authentication failed", bootstrapEvent.getEventDescription() );
            	Window.alert("Authentication failed " + bootstrapEvent.getEventDescription() );
            }

            public void onBootstrap( BootstrapEvent bootstrapEvent ) {
            	bootstrapData = bootstrapEvent.getBootstrapData();
                ApplicationContextFactory.createApplicationContext(bootstrapData);
            }
        } );
    }

    protected boolean isUserInRole( String role ) {
        UserInfo userInfo = bootstrapData.getUserInfo();
        return userInfo.isUserInRole( role );
    }

    public static class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
        public void onUncaughtException( Throwable e ) {
        	//MessageBox.alert( bsConstants.errore_title() + " generico", "Uncaught Exception\n" + ( e == null ? "null" : e.toString() ) );
        	Window.alert(bsConstants.errore_title() + " generico Uncaught Exception\n" + ( e == null ? "null" : e.toString() ) );
        	GWT.log("errore generico", e);
        }
    }
}
