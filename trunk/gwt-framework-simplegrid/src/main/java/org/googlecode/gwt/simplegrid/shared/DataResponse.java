package org.googlecode.gwt.simplegrid.shared;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.gen2.table.client.TableModelHelper.Response;


public class DataResponse<RowType extends Serializable> extends Response<RowType>
	implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int totalRowCount;
	private List<RowType> resultRows;
	
	@SuppressWarnings("unchecked")
	private UserData  userData;
	
	public <T extends Serializable> void setUserData(UserData <T> userData) {
		this.userData = userData;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> UserData <T> getUserData() {
		return userData;
	}
	
	public final static class UserData<T extends Serializable> implements Serializable {
		
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
	
	public DataResponse() {}
	
	public DataResponse(List<RowType> resultRows, int totalRowCount) {
		this.resultRows = resultRows;
		this.totalRowCount = totalRowCount;
	}
	
	public int getTotalRowCount() {
		return totalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	public List<RowType> getResultRows() {
		return resultRows;
	}
	public void setResultRows(List<RowType> resultRows) {
		this.resultRows = resultRows;
	}
	
	//used for data table rendering
	@Override
	public Iterator<RowType> getRowValues() {
	  return resultRows.iterator();
	}
}
