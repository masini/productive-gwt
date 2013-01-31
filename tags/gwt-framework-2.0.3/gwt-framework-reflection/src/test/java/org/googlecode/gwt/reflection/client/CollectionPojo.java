package org.googlecode.gwt.reflection.client;

import java.util.ArrayList;
import java.util.List;



public class CollectionPojo implements Reflectable {
	
	List<MyOwnPojo> myOwnPojioList = new ArrayList<MyOwnPojo>();

	public List<MyOwnPojo> getMyOwnPojioList() {
		return myOwnPojioList;
	}

	public void setMyOwnPojioList(List<MyOwnPojo> myOwnPojioList) {
		this.myOwnPojioList = myOwnPojioList;
	}
	
}
