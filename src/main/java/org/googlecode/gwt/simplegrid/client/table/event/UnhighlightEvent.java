

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

import org.googlecode.gwt.simplegrid.client.table.handler.HasUnhighlightHandlers;
import org.googlecode.gwt.simplegrid.client.table.handler.UnhighlightHandler;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Represents a highlight event.
 * 
 * @param <V> the highlighted value type
 */
public class UnhighlightEvent<V> extends GwtEvent<UnhighlightHandler<V>> {

  /**
   * Handler type.
   */
  private static Type<UnhighlightHandler<?>> TYPE;

  /**
   * Fires a highlight event on all registered handlers in the handler manager.
   * 
   * @param <V> the highlighted value type
   * @param <S> The event source
   * @param source the source of the handlers
   * @param highlighted the value highlighted
   */
  public static <V, S extends HasUnhighlightHandlers<V> & HasHandlers> void fire(
      S source, V unhighlighted) {
    if (TYPE != null) {
      UnhighlightEvent<V> event = new UnhighlightEvent<V>(unhighlighted);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<UnhighlightHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<UnhighlightHandler<?>>();
    }
    return TYPE;
  }

  private final V unhighlighted;

  /**
   * Creates a new highlight event.
   * 
   * @param highlighted value highlighted
   */
  protected UnhighlightEvent(V unhighlighted) {
    this.unhighlighted = unhighlighted;
  }

  // Because of type erasure, our static type is
  // wild carded, yet the "real" type should use our I param.
  @SuppressWarnings("unchecked")
  @Override
  public final Type<UnhighlightHandler<V>> getAssociatedType() {
    return (Type) TYPE;
  }

  /**
   * Gets the value highlighted.
   * 
   * @return value highlighted
   */
  public V getUnhighlighted() {
    return unhighlighted;
  }

  @Override
  protected void dispatch(UnhighlightHandler<V> handler) {
    handler.onUnhighlight(this);
  }
}
