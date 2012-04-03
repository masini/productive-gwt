package org.googlecode.gwt.base.support;

public interface SelectionModel {

    public int getSelectedIndex();
    public void setSelectedIndex(int selectedIndex);

    public String getValue(int index);
    public void setValue(int index, String value);

    public String getItemText(int index);
    public void setItemText(int index, String text);

    public void addItem(String item, String value);
    public void addItem(String item);

    public void clear();

    public int getItemCount();
    public void setVisibleItemCount(int count);    
}
