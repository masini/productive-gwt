package org.googlecode.gwt.simplegrid.client.table.handler;

import org.googlecode.gwt.simplegrid.client.table.event.RowSelectionEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link RowSelectionEvent} events.
 */
public interface RowSelectionHandler extends EventHandler {

  /**
   * Called when {@link RowSelectionEvent} is fired.
   *
   * @param event the {@link RowSelectionEvent} that was fired
   */
	void onRowSelection(RowSelectionEvent event);
}
