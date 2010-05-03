package org.googlecode.gwt.simplegrid.client.table.handler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasColumnSortHandlers extends HasHandlers{
  /**
   * Adds a {@link ColumnSortHandler}.
   *
   * @param handler the handler
   * @return the handler registration
   */
  HandlerRegistration addColumnSortHandler(ColumnSortHandler handler);
}
