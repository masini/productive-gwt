package org.googlecode.gwt.simplegrid.shared;

import java.io.Serializable;

public final class UserData<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;

	private T currentUserData;

	public UserData () {}

	public UserData (T ca) {
		currentUserData = ca;
	}

	public T returnUserData() {
		return currentUserData;
	}
}

