package org.googlecode.gwt.simplegrid.shared.rpc;

import org.googlecode.gwt.simplegrid.shared.DataResponse;
import org.googlecode.gwt.simplegrid.shared.Filter;
import org.googlecode.gwt.simplegrid.client.table.ColumnsFormatter;
import org.googlecode.gwt.simplegrid.client.table.StandardGrid;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;

public abstract class RowRequestAsyncController<
		ROW extends Serializable,
		FILTER extends Serializable
	>
	extends AsyncController<RowRequestServiceAsync<ROW, FILTER>>
{
	public final ColumnsFormatter<ROW> columnsFormatter;
	public final StandardGrid.Controller<ROW, FILTER> controller;

	public RowRequestAsyncController(String servletRelativeUrlAsInWebXml, ColumnsFormatter<ROW> columnsFormatter) {
		super(servletRelativeUrlAsInWebXml);

		this.columnsFormatter = columnsFormatter;

		this.controller = new StandardGrid.Controller<ROW, FILTER>() {
			protected void requestRows(StandardDataRequest<FILTER> dataRequest, Callback<ROW> callback) {
				preprocess(dataRequest).invoke(
					asynchronousService,
					new DefaultAsyncCallback<ROW>(callback) {
						protected void beforeOnSuccess(final DataResponse<ROW> dataResponse) {
							afterSuccessfulLoading(dataResponse);
						}

						protected void beforeOnFailure(Throwable t) {
							afterFailedLoading(t);
						}
					}
				);
			}

			@Override
			protected ColumnsWrapper<ROW> createColumnsWrapper() {
				return RowRequestAsyncController.this.createColumnsWrapper();
			}
		};
	}

	protected StandardGrid.Controller.ColumnsWrapper<ROW> createColumnsWrapper() {
		return new StandardGrid.Controller.ReadOnlyColumnsWrapper<ROW>() {
            public Object get(ROW row, int index) {
                return columnsFormatter.get(row, index);
            }
        };
	}

	public final void setFilter(FILTER filter) {
		controller.setFilter(new Filter<FILTER>(filter));
	}

	protected StandardDataRequest<FILTER> preprocess(StandardDataRequest<FILTER> dataRequest) {
		return dataRequest;
	}

	protected void afterSuccessfulLoading(DataResponse<ROW> dataResponse) {}

	protected void afterFailedLoading(Throwable t) {}

	protected RowRequestServiceAsync<ROW, FILTER> createAsynchronousService() {
		return GWT.create(RowRequestService.class);
	}

	protected final StandardGrid.Controller.ColumnsWrapper<ROW> createReadOnlyColumnsWrapper() {
		return new StandardGrid.Controller.ReadOnlyColumnsWrapper<ROW>() {
			public Object get(ROW row, int index) {
				return columnsFormatter.get(row, index);
			}
		};
	}
}