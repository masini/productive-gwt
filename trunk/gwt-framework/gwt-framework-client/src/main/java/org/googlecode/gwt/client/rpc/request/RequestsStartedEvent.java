package org.googlecode.gwt.client.rpc.request;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RequestsStartedEvent extends GwtEvent<RequestsStartedEvent.Handler> {

    public final static Type<Handler> TYPE =
            new Type<Handler>();
    private final boolean guiBlocking;

    public RequestsStartedEvent() {
        this(true);
    }

    public RequestsStartedEvent(boolean guiBlocking) {
        this.guiBlocking = guiBlocking;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onRequestsStarted(this);
    }

    public interface Handler extends EventHandler {
        void onRequestsStarted(RequestsStartedEvent event);
    }

    public boolean isGuiBlocking() {
        return guiBlocking;
    }
}
