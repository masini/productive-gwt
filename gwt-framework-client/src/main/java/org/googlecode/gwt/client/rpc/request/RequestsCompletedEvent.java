package org.googlecode.gwt.client.rpc.request;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RequestsCompletedEvent extends GwtEvent<RequestsCompletedEvent.Handler> {

    public final static Type<Handler> TYPE =
            new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onRequestsCompleted(this);
    }

    public interface Handler extends EventHandler {
        void onRequestsCompleted(RequestsCompletedEvent event);
    }
    
}
