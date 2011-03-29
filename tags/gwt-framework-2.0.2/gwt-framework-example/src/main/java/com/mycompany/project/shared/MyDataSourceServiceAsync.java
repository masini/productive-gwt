/*
 * Copyright 2007 Google Inc.
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
package com.mycompany.project.shared;

import org.googlecode.gwt.simplegrid.client.table.SimpleGrid;
import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.DataResponse;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;


/**
 * A {@link RemoteService} to retrieve row data for the
 * {@link SimpleGrid}.
 */
public interface MyDataSourceServiceAsync<RowType extends Serializable> {
	void requestRows(DataRequest request, AsyncCallback<DataResponse<RowType>> callback);
}
