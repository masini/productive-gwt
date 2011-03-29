package org.googlecode.gwt.simplegrid.client.table;

import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * An {@link InlineCellEditor} that can be used to edit {@link String Strings}.
 */
public class TextCellEditor extends InlineCellEditor<String> {
	/**
	 * The text field used in this editor.
	 */
	private final TextBoxBase textBox;

	/**
	 * Construct a new {@link TextCellEditor} using a normal {@link TextBox}.
	 * @param value the value
	 */
	public TextCellEditor(String value) {
		this(new TextBox());
		setValue(value);
		this.textBox.addKeyPressHandler(new EditorKeyPressHandler());
	}

	/**
	 * Construct a new {@link TextCellEditor} using the specified {@link TextBox}.
	 *
	 * @param textBox the text box to use
	 */
	private TextCellEditor(TextBoxBase textBox) {
		super(textBox);
		this.textBox = textBox;

	}

	/*
	 * Construct a new {@link TextCellEditor} using the specified {@link TextBox} and images.
	 *
	 * @param textBox the text box to use
	 * @param images  the images to use for the accept/cancel buttons
	 */
	/*
	public TextCellEditor(TextBoxBase textBox, InlineCellEditorImages images) {
		super(textBox, images);
		this.textBox = textBox;
	}
	*/

	@Override
	public void editCell(CellEditInfo cellEditInfo, String cellValue, Callback<String> callback) {
		super.editCell(cellEditInfo, cellValue, callback);
		textBox.setFocus(true);
	}

	/**
	 * @return the text box used in the editor
	 */
	protected TextBoxBase getTextBox() {
		return textBox;
	}

	@Override
	protected String getValue() {
		return textBox.getText();
	}

	@Override
	protected void setValue(String cellValue) {
		textBox.setText(cellValue != null ? cellValue : "");
	}

	private class EditorKeyPressHandler implements KeyPressHandler {
		public void onKeyPress(KeyPressEvent event) {
			if (event.getCharCode() == KeyCodes.KEY_ENTER)
				accept();

			if (event.getCharCode() == KeyCodes.KEY_ESCAPE)
				cancel();
		}
	}
}
