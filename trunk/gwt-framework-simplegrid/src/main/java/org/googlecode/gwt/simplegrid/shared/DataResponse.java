package org.googlecode.gwt.simplegrid.shared;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.gen2.table.client.TableModelHelper.Response;


public class DataResponse<ROW extends Serializable> extends Response<ROW> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int totalRowCount;
	private List<ROW> resultRows;

	private UserData<?> userData;

	public void setUserData(UserData<?> userData) {
		this.userData = userData;
	}

	public UserData<?> getUserData() {
		return userData;
	}

	public DataResponse() {}

	public DataResponse(List<ROW> resultRows, int totalRowCount) {
		this.resultRows = resultRows;
		this.totalRowCount = totalRowCount;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public List<ROW> getResultRows() {
		return resultRows;
	}

	public void setResultRows(List<ROW> resultRows) {
		this.resultRows = resultRows;
	}

	//used for data table rendering
	@Override
	public Iterator<ROW> getRowValues() {
		return resultRows.iterator();
	}
}
