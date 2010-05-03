package org.googlecode.gwt.simplegrid.client.table.event;

import com.google.gwt.event.logical.shared.HighlightEvent;
import com.google.gwt.gen2.table.event.client.TableEvent.Cell;

/**
 * Logical event fired when a cell is highlighted.
 */
public class CellHighlightEvent extends HighlightEvent<Cell> {
	/**
	 * Event Key for {@link CellHighlightEvent}.
	 */
	//public static final Type<HighlightHandler<Cell>> TYPE = new Type<HighlightHandler<Cell>>();
	/*{
		@Override
		protected void fire(CellHighlightHandler handler, CellHighlightEvent event) {
		  handler.onCellHighlight(event);
		}
	  };*/

	/**
	 * Construct a new {@link CellHighlightEvent}.
	 *
	 * @param rowIndex  the index of the row being highlighted
	 * @param cellIndex the index of the cell being highlighted
	 */
	public CellHighlightEvent(int rowIndex, int cellIndex) {
		this(new Cell(rowIndex, cellIndex));
	}

	/**
	 * Construct a new {@link CellHighlightEvent}.
	 *
	 * @param cell the cell being highlighted
	 */
	public CellHighlightEvent(Cell cell) {
		super(cell);
	}
}
