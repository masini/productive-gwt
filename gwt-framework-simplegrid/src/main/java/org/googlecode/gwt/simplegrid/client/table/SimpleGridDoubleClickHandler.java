package org.googlecode.gwt.simplegrid.client.table;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for {@link DoubleClickEvent} events.
 */
public interface SimpleGridDoubleClickHandler extends EventHandler {
	/**
	 * Called when a native double click event is fired.
	 *
	 * @param event the {@link DoubleClickEvent} that was fired
	 */
	void onDoubleClick(DoubleClickEvent event, int row, int column);

}