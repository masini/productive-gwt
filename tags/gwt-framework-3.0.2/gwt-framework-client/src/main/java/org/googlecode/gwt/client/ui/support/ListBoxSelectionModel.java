package org.googlecode.gwt.client.ui.support;

import com.google.gwt.user.client.ui.ListBox;

public class ListBoxSelectionModel implements SelectionModel {

    private final ListBox listBox;

    public ListBoxSelectionModel(ListBox listBox) {
        this.listBox = listBox;
    }

    @Override
    public int getSelectedIndex() {
        return listBox.getSelectedIndex();
    }

    @Override
    public void setSelectedIndex(int selectedIndex) {
        listBox.setSelectedIndex(selectedIndex);
    }

    @Override
    public String getValue(int index) {
        return listBox.getValue(index);
    }

    @Override
    public void setValue(int index, String value) {
        listBox.setValue(index, value);
    }

    @Override
    public String getItemText(int index) {
        return listBox.getItemText(index);
    }

    @Override
    public void setItemText(int index, String text) {
        listBox.setItemText(index, text);
    }

    @Override
    public void addItem(String item, String value) {
        listBox.addItem(item, value);
    }

    @Override
    public void addItem(String item) {
        listBox.addItem(item);
    }

    @Override
    public void clear() {
        listBox.clear();
    }

    @Override
    public int getItemCount() {
        return listBox.getItemCount();
    }

    @Override
    public void setVisibleItemCount(int count) {
        listBox.setVisibleItemCount(count);
    }
}
