package org.googlecode.gwt.reflection.client;

import java.math.BigDecimal;
import java.util.List;

public class NewOwnPojo implements Reflectable {

	private long longField;
	private List<String> listField;
	private BigDecimal bigDecimal;
	
	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

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
