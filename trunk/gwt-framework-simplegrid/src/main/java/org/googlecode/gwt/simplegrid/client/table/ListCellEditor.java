package org.googlecode.gwt.simplegrid.client.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

/**
 * An {@link InlineCellEditor} that allows the user to select a {@link String}
 * from a drop down {@link ListBox}.
 *
 * @param <ColType> the data type of the column
 */
public class ListCellEditor<ColType> extends InlineCellEditor<ColType> {
	/**
	 * The list box of options.
	 */
	private final ListBox listBox;

	/**
	 * A list of item values that cooresponds to the indexes in the
	 * {@link ListBox}.
	 */
	private List<ColType> itemValues = new ArrayList<ColType>();

	public ListCellEditor(ColType value, List<String> items, List<ColType> values) {
		//create
		this(new ListBox());

		//populate
		for (int i = 0; i < items.size(); i++)
			listBox.addItem(items.get(i), values.get(i).toString());

		this.itemValues = values;
		setValue(value);

		this.listBox.addChangeHandler(
			new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					accept();
				}
			}
		);
	}

	private ListCellEditor(ListBox listBox) {
		super(listBox);
		this.listBox = listBox;
	}

	/**
	 * Adds an item to the {@link ListBox} in the editor.
	 *
	 * @param item  the text of the item to be added
	 * @param value the value associated with the item
	 */
	public void addItem(String item, ColType value) {
		listBox.addItem(item);
		itemValues.add(value);
	}

	@Override
	public void editCell(CellEditInfo cellEditInfo, ColType cellValue, Callback<ColType> callback) {
		super.editCell(cellEditInfo, cellValue, callback);
		listBox.setFocus(true);
	}

	/**
	 * Inserts an item into the {@link ListBox} in the editor.
	 *
	 * @param item  the text of the item to be inserted
	 * @param index the index at which to insert it
	 * @param value the value associated with the item
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public void insertItem(String item, int index, ColType value) throws IndexOutOfBoundsException {
		if (index < 0 || index >= listBox.getItemCount()) {
			throw new IndexOutOfBoundsException();
		}
		listBox.insertItem(item, index);
		itemValues.add(index, value);
	}

	/**
	 * Removes the item at the specified index.
	 *
	 * @param index the index of the item to be removed
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public void removeItem(int index) throws IndexOutOfBoundsException {
		listBox.removeItem(index);
		itemValues.remove(index);
	}

	@Override
	protected ColType getValue() {
		int index = listBox.getSelectedIndex();
		return index >= 0 ? itemValues.get(index) : null;
	}

	protected String getItem() {
		int index = listBox.getSelectedIndex();
		return index >= 0 ? listBox.getItemText(index) : null;
	}

	@Override
	protected void setValue(ColType cellValue) {
		listBox.setSelectedIndex(Math.max(0, itemValues.indexOf(cellValue)));
	}
}
