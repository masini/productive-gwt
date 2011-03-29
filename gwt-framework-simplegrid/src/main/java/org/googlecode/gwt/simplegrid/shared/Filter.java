package org.googlecode.gwt.simplegrid.shared;

import java.io.Serializable;

public final class Filter<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;

	private T currentFilter;

	public Filter() {}

	public Filter(T cf) {
		currentFilter = cf;
	}

	public T returnCurrentFilter() {
		return currentFilter;
	}
}