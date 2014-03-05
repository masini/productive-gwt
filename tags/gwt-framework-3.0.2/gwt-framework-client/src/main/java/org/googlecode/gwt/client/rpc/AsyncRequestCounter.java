package org.googlecode.gwt.client.rpc;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import org.googlecode.gwt.client.rpc.request.RequestsCompletedEvent;
import org.googlecode.gwt.client.rpc.request.RequestsStartedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AsyncRequestCounter {

    private final static Logger LOG = Logger.getLogger(AsyncRequestCounter.class.getName());

    static int counter = 0;

    static EventBus eventBus = new SimpleEventBus();

    private final static Map<EventHandler, HandlerRegistration> registrations = new HashMap<EventHandler, HandlerRegistration>();

    public static void registerCompleted(RequestsCompletedEvent.Handler handler) {
        registrations.put(handler, eventBus.addHandler(RequestsCompletedEvent.TYPE, handler));
    }

    public static void unregisterCompleted(RequestsCompletedEvent.Handler handler) {
        HandlerRegistration completedRegistration = registrations.remove(handler);
        completedRegistration.removeHandler();
    }

    public static void registerStarted(RequestsStartedEvent.Handler handler) {
        registrations.put(handler, eventBus.addHandler(RequestsStartedEvent.TYPE, handler));
    }

    public static void unregisterStarted(RequestsStartedEvent.Handler handler) {
        HandlerRegistration startedRegistration = registrations.remove(handler);
        startedRegistration.removeHandler();
    }

    public static void increment(String serviceName, boolean guiBlocking) {
        if( counter<=0 ) {
            LOG.info("inizio a contare");

            eventBus.fireEvent(new RequestsStartedEvent(guiBlocking));
        }

        counter++;
        LOG.info("incremented by "+serviceName+", counter="+counter);
    }

    public static void increment(String serviceName) {

       increment(serviceName, true);
        
    }

    public static void decrement(String serviceName) {
        counter--;
        LOG.info("decremented by "+serviceName+", counter="+counter);

        if( counter<=0 ) {
            LOG.info("contatore resettato");

            eventBus.fireEvent(new RequestsCompletedEvent());

            counter = 0;
        }
    }
}
