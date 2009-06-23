/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
	   * Fires a selection event on all registered handlers in the handler
	   * manager.If no such handlers exist, this method will do nothing.
	   * 
	   * @param <I> the selected item type
	   * @param source the source of the handlers
	   * @param selectedItem the selected item
	   */
	  public static <I> void fire(HasColumnSortHandlers source, ColumnSortList sortList) {
	    if (TYPE != null) {
	    	ColumnSortEvent event = new ColumnSortEvent(sortList);
	      source.fireEvent(event);
	    }
	  }

  /**
   * Information about the column sorting.
   */
  private ColumnSortList sortList;

  /**
   * Construct a new {@link ColumnSortEvent}.
   * 
   * @param sortList information about the sort order
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
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
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

	// The instance knows its BeforeSelectionHandler is of type I, but the TYPE
	  // field itself does not, so we have to do an unsafe cast here.
	  @Override
	  public final Type<ColumnSortHandler> getAssociatedType() {
	    return TYPE;
	  }
	
}
