package org.googlecode.gwt.simplegrid.client.table.event;

import org.googlecode.gwt.simplegrid.client.table.handler.ColumnSortHandler;
import org.googlecode.gwt.simplegrid.client.table.handler.HasColumnSortHandlers;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;

/**
 * Logical event fired when a column is sorted.
 */
public class ColumnSortEvent extends GwtEvent<ColumnSortHandler> {

	/**
	 * Handler type.
	 */
	private static Type<ColumnSortHandler> TYPE;

	/**
	 * Fires a selection event on all registered handlers in the handler manager.
	 * If no such handlers exist, this method will do nothing.
	 *
	 * @param <I>        the selected item type (useless)
	 * @param source     the source of the handlers
	 * @param sortList   the column sort list
	 *
	 * @deprecated use {@link #fireEvent(HasColumnSortHandlers, ColumnSortList)}
	 */
	@Deprecated
	public static <I> void fire(HasColumnSortHandlers source, ColumnSortList sortList) {
		fireEvent(source, sortList);
	}

	/**
	 * Fires a selection event on all registered handlers in the handler manager.
	 * If no such handlers exist, this method will do nothing.
	 *
	 * @param source       the source of the handlers
	 * @param sortList     the column sort list
	 */
	public static void fireEvent(HasColumnSortHandlers source, ColumnSortList sortList) {
		if (TYPE != null) {
			ColumnSortEvent event = new ColumnSortEvent(sortList);
			source.fireEvent(event);
		}
	}

	/**
	 * Information about the column sorting.
	 */
	private final ColumnSortList sortList;

	/**
	 * @param sortList information about the sort order of columns
	 */
	public ColumnSortEvent(ColumnSortList sortList) {
		this.sortList = sortList;
	}

	/**
	 * @return information about the sort order of columns
	 */
	public ColumnSortList getColumnSortList() {
		return sortList;
	}

	/**
	 * @return returns the handler type associated to this event.
	 */
	public static Type<ColumnSortHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ColumnSortHandler>();
		}
		return TYPE;
	}

	@Override
	protected void dispatch(ColumnSortHandler handler) {
		handler.onColumnSorted(this);
	}

	@Override
	public final Type<ColumnSortHandler> getAssociatedType() {
		return TYPE;
	}

}
