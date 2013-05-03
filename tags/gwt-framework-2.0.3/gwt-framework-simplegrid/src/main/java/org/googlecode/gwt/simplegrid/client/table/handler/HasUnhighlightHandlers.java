package org.googlecode.gwt.simplegrid.client.table.handler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of {@link org.googlecode.gwt.simplegrid.client.table.event.UnhighlightEvent} events.
 *
 * @param <V> the unhighlighted value type
 */
public interface HasUnhighlightHandlers<V> extends HasHandlers {
  /**
   * Adds a {@link UnhighlightHandler} handler.
   *
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addUnhighlightHandler(UnhighlightHandler<V> handler);
}
