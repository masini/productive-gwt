
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

import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;

/**
 * Logical event fired when a cell is highlighted.
 */
public class RowSelectionEvent  extends SelectionEvent<Set<Row>> {
	
  /**
   * Event Key for {@link RowSelectionEvent}.
   */
  /*public static final Type<RowSelectionEvent, RowSelectionHandler> TYPE = new Type<RowSelectionEvent, RowSelectionHandler>() {
    @Override
    protected void fire(RowSelectionHandler handler, RowSelectionEvent event) {
      handler.onRowSelection(event);
    }
  };
  
*/
	private Set<Row> oldSelectedItems;
    private Set<Row> newSelectedItems;
	  
  /**
   * Construct a new {@link RowSelectionEvent}.
   * 
   * @param oldList the set of rows that were previously selected
   * @param newList the set of rows that are now selected
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
    Set<Row> deselected = new TreeSet<Row>();
    Set<Row> oldList = oldSelectedItems;
    Set<Row> newList = newSelectedItems;
    for (Row row : oldList) {
      if (!newList.contains(row)) {
        deselected.add(row);
      }
    }
    return deselected;
  }

  /**
   * @return the newly selected rows
   */
  public Set<Row> getSelectedRows() {
    Set<Row> selected = new TreeSet<Row>();
    Set<Row> oldList = oldSelectedItems;
    Set<Row> newList = newSelectedItems;
    for (Row row : newList) {
      if (!oldList.contains(row)) {
        selected.add(row);
      }
    }
    return selected;
  }

}
