package org.googlecode.gwt.simplegrid.client.table.event;

import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;

/**
 * Logical event fired when some rows are selected.
 */
public class RowSelectionEvent extends SelectionEvent<Set<Row>> {

	/**
	 * Event Key for {@link RowSelectionEvent}.
	 */
	/*
	public static final Type<RowSelectionEvent, RowSelectionHandler> TYPE = new Type<RowSelectionEvent, RowSelectionHandler>() {
		@Override
		protected void fire(RowSelectionHandler handler, RowSelectionEvent event) {
			handler.onRowSelection(event);
		}
	};
    */

	private final Set<Row> oldSelectedItems;
	private final Set<Row> newSelectedItems;

	/**
	 * Construct a new {@link RowSelectionEvent}.
	 *
	 * @param oldSelectedItems the set of rows that were previously selected
	 * @param newSelectedItems the set of rows that are now selected
	 */
	public RowSelectionEvent(Set<Row> oldSelectedItems, Set<Row> newSelectedItems) {
		super(newSelectedItems);
		this.oldSelectedItems = oldSelectedItems;
		this.newSelectedItems = newSelectedItems;
	}

	/**
	 * @return the newly deselected rows
	 */
	public Set<Row> getDeselectedRows() {
		return extractFirstOnesNotAmongSecondOnes(oldSelectedItems, newSelectedItems);
	}

	/**
	 * @return the newly selected rows
	 */
	public Set<Row> getSelectedRows() {
		return extractFirstOnesNotAmongSecondOnes(newSelectedItems, oldSelectedItems);
	}

	private static <ITEM extends Comparable<ITEM>> Set<ITEM> extractFirstOnesNotAmongSecondOnes(Set<ITEM> items, Set<ITEM> itemsToExclude) {
		Set<ITEM> extractedItems = new TreeSet<ITEM>();
		for (ITEM item : items) {
			if (!itemsToExclude.contains(item)) {
				extractedItems.add(item);
			}
		}
		return extractedItems;
	}

}
