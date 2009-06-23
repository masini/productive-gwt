
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
package org.googlecode.gwt.simplegrid.client.table.handler;

import org.googlecode.gwt.simplegrid.client.table.event.UnhighlightEvent;

import com.google.gwt.event.logical.shared.HighlightEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link HighlightEvent} events.
 * 
 * @param <V> the highlighted value type
 */
public interface UnhighlightHandler<V> extends EventHandler {

  /**
   * Called when {@link HighlightEvent} is fired.
   * 
   * @param event the {@link HighlightEvent} that was fired
   */
  void onUnhighlight(UnhighlightEvent<V> event);
}
