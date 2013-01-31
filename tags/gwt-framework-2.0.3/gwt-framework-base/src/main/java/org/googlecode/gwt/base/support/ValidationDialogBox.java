package org.googlecode.gwt.base.support;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;

public class ValidationDialogBox {
    interface ProgressDialogBoxUiBinder extends UiBinder<DialogBox, ValidationDialogBox> {

    }
    private static ProgressDialogBoxUiBinder ourUiBinder = GWT.create(ProgressDialogBoxUiBinder.class);

    @UiField
    DialogBox dialog;
    @UiField
    HTML message;
    @UiField
    Button okButton;

    private final static ValidationDialogBox instance = new ValidationDialogBox();

    private ValidationDialogBox() {
        ourUiBinder.createAndBindUi(this);
    }

    public static void showMessage(String[] messages) {

        StringBuilder messageToShow = new StringBuilder();
        messageToShow.append("<ul>");
        for(String message: messages) {
            messageToShow.append("<li>");
            messageToShow.append(message);
            messageToShow.append("</li>");
        }
        messageToShow.append("</ul>");

        instance.message.setHTML(messageToShow.toString());

        instance.dialog.center();
        instance.dialog.show();
    }

    @UiHandler("okButton")
    void onOKClicked(ClickEvent event) {
        dialog.hide();
    }
}