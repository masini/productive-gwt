package org.googlecode.gwt.reflection.client;

import java.util.List;

public class NewOwnPojo implements DataBindable {

	private long longField;
	
	private List<String> listField;

	public long getLongField() {
		return longField;
	}

	public void setLongField(long longField) {
		this.longField = longField;
	}

	
	public List<String> getListField() {
		return listField;
	}

	public void setListField(List<String> listField) {
		this.listField = listField;
	}

}
