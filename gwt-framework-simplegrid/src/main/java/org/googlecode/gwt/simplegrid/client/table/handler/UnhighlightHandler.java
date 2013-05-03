package org.googlecode.gwt.simplegrid.client.table.handler;

import com.google.gwt.event.shared.EventHandler;
import org.googlecode.gwt.simplegrid.client.table.event.UnhighlightEvent;

/**
 * Handler interface for {@link UnhighlightEvent} events.
 *
 * @param <V> the unhighlighted value type
 */
public interface UnhighlightHandler<V> extends EventHandler {

  /**
   * Called when {@link UnhighlightEvent} is fired.
   *
   * @param event the {@link UnhighlightEvent} that was fired
   */
  void onUnhighlight(UnhighlightEvent<V> event);
}
