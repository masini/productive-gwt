package org.googlecode.gwt.simplegrid.client.table.event;

import com.google.gwt.gen2.table.event.client.TableEvent.Cell;

/**
 * Logical event fired when a cell is unhighlighted.
 */
public class CellUnhighlightEvent extends UnhighlightEvent<Cell> {
	/**
	 * Event Key for {@link CellUnhighlightEvent}.
	 */
	//public static final Type<UnhighlightHandler<Cell>> TYPE = new Type<UnhighlightHandler<Cell>>();
	/*{

		/*@Override
		protected void fire(CellUnhighlightHandler handler, CellUnhighlightEvent event) {
		  handler.onCellUnhighlight(event);
		}
	  };*/

	/**
	 * Construct a new {@link CellUnhighlightEvent}.
	 *
	 * @param rowIndex  the index of the row being unhighlighted
	 * @param cellIndex the index of the cell being unhighlighted
	 */
	public CellUnhighlightEvent(int rowIndex, int cellIndex) {
		this(new Cell(rowIndex, cellIndex));
	}

	/**
	 * Construct a new {@link CellUnhighlightEvent}.
	 *
	 * @param cell the cell being unhighlighted
	 */
	public CellUnhighlightEvent(Cell cell) {
		super(cell);
	}

}
