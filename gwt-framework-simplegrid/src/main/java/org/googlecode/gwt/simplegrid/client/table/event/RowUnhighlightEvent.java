package org.googlecode.gwt.simplegrid.client.table.event;

import com.google.gwt.gen2.table.event.client.TableEvent.Row;

/**
 * Logical event fired when a row is unhighlighted.
 */
public class RowUnhighlightEvent extends UnhighlightEvent<Row> {
	/**
	 * Event Key for {@link RowUnhighlightEvent}.
	 */
	//public static final Type<UnhighlightHandler<Row>> TYPE = new Type<UnhighlightHandler<Row>>();
	/*{
	  /*@Override
	  protected void fire(RowUnhighlightHandler handler, RowUnhighlightEvent event) {
		handler.onRowUnhighlight(event);
	  }
	};*/

	/**
	 * Construct a new {@link RowUnhighlightEvent}.
	 *
	 * @param rowIndex the index of the row being unhighlighted
	 */
	public RowUnhighlightEvent(int rowIndex) {
		this(new Row(rowIndex));
	}

	/**
	 * Construct a new {@link RowUnhighlightEvent}.
	 *
	 * @param row the row being unhighlighted
	 */
	public RowUnhighlightEvent(Row row) {
		super(row);
	}
}
