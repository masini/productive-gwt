package org.googlecode.gwt.simplegrid.client.table.handler;

import com.google.gwt.event.logical.shared.HighlightHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.gen2.table.event.client.TableEvent.Cell;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;

public interface HasTableSelectionHandlers extends HasHandlers {
	HandlerRegistration addCellHighlightHandler(HighlightHandler<Cell> handler);
	HandlerRegistration addCellUnhighlightHandler(UnhighlightHandler<Cell> handler);
	HandlerRegistration addRowHighlightHandler(HighlightHandler<Row> handler);
	HandlerRegistration addRowUnhighlightHandler(UnhighlightHandler<Row> handler);
	HandlerRegistration addRowSelectionHandler(SelectionHandler<Row> handler);
}

