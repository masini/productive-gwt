package org.googlecode.gwt.client.bootstrap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import org.googlecode.gwt.client.ApplicationContextFactory;
import org.googlecode.gwt.client.logging.LogText;
import org.googlecode.gwt.client.util.BootstrapConstants;
import org.googlecode.gwt.shared.client.BootstrapData;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that can be extended by GWT client application to automatically get integrated with the bootstrap process.
 * Subclasses should implement  instead of {@link #onModuleLoad() }.
 *
 * @author Arkady Syamtomov (cslas2)
 */
public class BootstrapEntryPoint implements EntryPoint {
	protected BootstrapData bootstrapData;
    protected static final BootstrapConstants bsConstants;
    protected List<BootstrapEventListener> bootstrapEventListeners;
    

    static {
    	LogText.writeOnLogText("==> static " + BootstrapEntryPoint.class.getName());
        bsConstants = ( BootstrapConstants ) GWT.create(BootstrapConstants.class);
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
        GWT.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        bootstrap( new BootstrapEventListener() {

            public void onBootstrapFailed( BootstrapEvent bootstrapEvent ) {

                ApplicationContextFactory.failedCreatingApplicationContext();
            }

            public void onBootstrap( BootstrapEvent bootstrapEvent ) {
            	bootstrapData = bootstrapEvent.getBootstrapData();
                ApplicationContextFactory.createApplicationContext(bootstrapData);
            }
        } );
    }

    public static class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
        public void onUncaughtException( Throwable e ) {
        	//MessageBox.alert( bsConstants.errore_title() + " generico", "Uncaught Exception\n" + ( e == null ? "null" : e.toString() ) );
        	Window.alert(bsConstants.errore_title() + " generico Uncaught Exception\n" + (e == null ? "null" : e.toString()));
        	GWT.log("errore generico", e);
        }
    }
}
