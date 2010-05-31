package org.googlecode.gwt.simplegrid.client.table.handler;

import org.googlecode.gwt.simplegrid.client.table.event.ColumnSortEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link ColumnSortEvent} events.
 */
public interface ColumnSortHandler extends EventHandler {

  /**
   * Called when {@link ColumnSortEvent} is fired.
   *
   * @param event the {@link ColumnSortEvent} that was fired
   */
	void onColumnSorted(ColumnSortEvent event);
}
