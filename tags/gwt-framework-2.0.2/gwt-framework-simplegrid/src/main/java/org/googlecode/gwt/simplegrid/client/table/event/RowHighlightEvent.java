package org.googlecode.gwt.simplegrid.client.table.event;

import com.google.gwt.event.logical.shared.HighlightEvent;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;

/**
 * Logical event fired when a row is highlighted.
 */
public class RowHighlightEvent extends HighlightEvent<Row> {
	/**
	 * Event Key for {@link RowHighlightEvent}.
	 */
	//public static final Type<HighlightHandler<Row>> TYPE = new Type<HighlightHandler<Row>>();
	/*{

		/*@Override
		protected void fire(RowHighlightHandler handler, RowHighlightEvent event) {
		  handler.onRowHighlight(event);
		}
	  };*/

	/**
	 * @param rowIndex the index of the highlighted row
	 */
	public RowHighlightEvent(int rowIndex) {
		this(new Row(rowIndex));
	}

	/**
	 * @param row the row being highlighted
	 */
	public RowHighlightEvent(Row row) {
		super(row);
	}
}
