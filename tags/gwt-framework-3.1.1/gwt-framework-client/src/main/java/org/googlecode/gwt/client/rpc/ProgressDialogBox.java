package org.googlecode.gwt.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import org.googlecode.gwt.client.rpc.request.RequestsCompletedEvent;
import org.googlecode.gwt.client.rpc.request.RequestsStartedEvent;

public class ProgressDialogBox implements RequestsStartedEvent.Handler, RequestsCompletedEvent.Handler{
    interface ProgressDialogBoxUiBinder extends UiBinder<DialogBox, ProgressDialogBox> {

    }
    private static ProgressDialogBoxUiBinder ourUiBinder = GWT.create(ProgressDialogBoxUiBinder.class);

    @UiField
    DialogBox dialog;

    public ProgressDialogBox() {
        ourUiBinder.createAndBindUi(this);
    }

    public void onRequestsCompleted(RequestsCompletedEvent event) {
        dialog.hide();
    }

    public void onRequestsStarted(RequestsStartedEvent event) {
        if(event.isGuiBlocking()) {
            dialog.show();
            dialog.center();
        }
    }
}