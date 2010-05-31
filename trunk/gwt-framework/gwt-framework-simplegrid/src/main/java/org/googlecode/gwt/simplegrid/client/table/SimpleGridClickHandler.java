package org.googlecode.gwt.simplegrid.client.table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for {@link ClickEvent} events.
 */
public interface SimpleGridClickHandler extends EventHandler {
	/**
	 * Called when a native click event is fired.
	 *
	 * @param event the {@link ClickEvent} that was fired
	 */
	void onClick(ClickEvent event, int row, int column);

}