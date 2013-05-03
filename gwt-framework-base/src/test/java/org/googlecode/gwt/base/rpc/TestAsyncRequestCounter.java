package org.googlecode.gwt.base.rpc;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import org.googlecode.gwt.base.rpc.AsyncRequestCounter;
import org.googlecode.gwt.base.rpc.request.RequestsCompletedEvent;
import org.googlecode.gwt.base.rpc.request.RequestsStartedEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestAsyncRequestCounter {

    EventBus originalEventBus = AsyncRequestCounter.eventBus;
    SimpleEventBus simpleEventBus = mock(SimpleEventBus.class);

    @Before
    public void before() {
        reset(simpleEventBus);
        AsyncRequestCounter.eventBus = simpleEventBus;
    }

    @After
    public void after() {
        // Dopo il test rimetto tutto come prima
        // visto che sono risorse statiche e possono compromettere altri test
        AsyncRequestCounter.eventBus = originalEventBus;
        AsyncRequestCounter.counter = 0;
    }

    @Test
    public void incrementWithZeroCountFireRequestsStartedEvent() {

        AsyncRequestCounter.counter = 0;

        AsyncRequestCounter.increment("servizio");

        verify(simpleEventBus).fireEvent(any(RequestsStartedEvent.class));

    }

    @Test
    public void incrementWithOneCountCannotFireRequestsStartedEvent() {

        AsyncRequestCounter.counter = 1;

        AsyncRequestCounter.increment("servizio");

        verify(simpleEventBus, never()).fireEvent(any(RequestsStartedEvent.class));

    }

    @Test
     public void decrementToZeroFireRequestsCompletedEvent() {
        AsyncRequestCounter.counter = 1;

        AsyncRequestCounter.decrement("servizio");

        verify(simpleEventBus).fireEvent(any(RequestsCompletedEvent.class));
    }

    @Test
    public void decrementToOneCannotFireRequestsCompletedEvent() {
        AsyncRequestCounter.counter = 2;

        AsyncRequestCounter.decrement("servizio");

        verify(simpleEventBus, never()).fireEvent(any(RequestsCompletedEvent.class));
    }

}
