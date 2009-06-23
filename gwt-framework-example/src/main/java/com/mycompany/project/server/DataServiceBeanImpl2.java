/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mycompany.project.server;

import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.DataResponse;
import org.googlecode.gwt.simplegrid.shared.DefaultDataSourceService;
import org.googlecode.gwt.simplegrid.shared.DataRequest.DataColumnSortList;
import com.mycompany.project.shared.MyDataSourceService;
import com.mycompany.project.shared.PojoBean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of {@link DefaultDataSourceService}.
 */
public class DataServiceBeanImpl2 extends RemoteServiceServlet implements MyDataSourceService<PojoBean> {
 
	private static final long serialVersionUID = 1L;
	
	private static int sortCol;
	private static boolean sortAsc;

	public DataResponse<PojoBean> requestRows(DataRequest request) throws Exception {
		
		DataColumnSortList sortList = request.getColumnSortList();
		sortCol = sortList.getPrimaryColumn()+1;
		sortAsc = sortList.isPrimaryAscending();

		//REQUEST get filter
		int numRowsFilter = (Integer) request.getFilter().returnCurrentFilter();
		
		ArrayList<PojoBean> listaBean = new DataSourceGenerator(numRowsFilter).getListaPojo();
		
		int totalRowsNum = listaBean.size();
		
		if (sortCol>0) 
			Collections.sort(listaBean, genericBeanComparator);
		
		List<PojoBean> resultOut = new ArrayList<PojoBean>();
		for (int i=request.getStartRow(); i < Math.min(request.getStartRow() + request.getNumRows(), totalRowsNum); i++){
			resultOut.add(listaBean.get(i));
		}
		
		/*
		 * Simulazione eccezione server-side
		 */
		//if(resultOut.size() > 0)
		//	throw new Exception("Simulazione eccezione");
		
		/*
		 * Setter messaggio custom in dataResponse (userData)
		 */
		DataResponse<PojoBean> dataResponse = new DataResponse<PojoBean>(resultOut, totalRowsNum);
		dataResponse.setUserData(new DataResponse.UserData<String>("Table 2 - Response userData custom message!"));
				
		return dataResponse;
	}

	/**
	 * Generic Bean Comparator
	 *  
	 */
	public static Comparator<Object> genericBeanComparator = new Comparator<Object>(){
		public int compare(Object obj1, Object obj2){ 
		    try {	
				Field filed1 = (obj1.getClass().getDeclaredFields())[sortCol];
				Field filed2 = (obj2.getClass().getDeclaredFields())[sortCol];
				
				Method method1 = obj1.getClass().getDeclaredMethod("get"+filed1.getName().substring(0,1).toUpperCase()+filed1.getName().substring(1).toLowerCase(), (Class<?>[])null);
				Method method2 = obj2.getClass().getDeclaredMethod("get"+filed2.getName().substring(0,1).toUpperCase()+filed2.getName().substring(1).toLowerCase(), (Class<?>[])null);
				
				int compareValue = 0;
				

				if (method1.invoke(obj1, (Object[])null) instanceof String) {
					String value1 = ""+method1.invoke(obj1, (Object[])null);
					String value2 = ""+method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);

				} else if (method1.invoke(obj1, (Object[])null) instanceof Character) {
					Character value1 = (Character)method1.invoke(obj1, (Object[])null);
					Character value2 = (Character)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);
					
				} else if (method1.invoke(obj1, (Object[])null) instanceof Boolean) {
					Boolean value1 = (Boolean)method1.invoke(obj1, (Object[])null);
					Boolean value2 = (Boolean)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);

				} else if (method1.invoke(obj1, (Object[])null) instanceof Short) {
					Short value1 = (Short)method1.invoke(obj1, (Object[])null);
					Short value2 = (Short)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);
					
				} else if (method1.invoke(obj1, (Object[])null) instanceof Integer) {
					Integer value1 = (Integer)method1.invoke(obj1, (Object[])null);
					Integer value2 = (Integer)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);

				} else if (method1.invoke(obj1, (Object[])null) instanceof Double) {
					Double value1 = (Double)method1.invoke(obj1, (Object[])null);
					Double value2 = (Double)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);
					
				} else if (method1.invoke(obj1, (Object[])null) instanceof Long) {
					Long value1 = (Long)method1.invoke(obj1, (Object[])null);
					Long value2 = (Long)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);

				} else if (method1.invoke(obj1, (Object[])null) instanceof BigDecimal) {
					BigDecimal value1 = (BigDecimal)method1.invoke(obj1, (Object[])null);
					BigDecimal value2 = (BigDecimal)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);
					
				} else if (method1.invoke(obj1, (Object[])null) instanceof Float) {
					Float value1 = (Float)method1.invoke(obj1, (Object[])null);
					Float value2 = (Float)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);
					
				} else if (method1.invoke(obj1, (Object[])null) instanceof Timestamp) {
					Timestamp value1 = (Timestamp)method1.invoke(obj1, (Object[])null);
					Timestamp value2 = (Timestamp)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);

				} else if (method1.invoke(obj1, (Object[])null) instanceof Date) {
					Date value1 = (Date)method1.invoke(obj1, (Object[])null);
					Date value2 = (Date)method2.invoke(obj2, (Object[])null);
					compareValue = value1.compareTo(value2);
				}

				
				if (!sortAsc) compareValue=compareValue*-1;
				return compareValue;

		    } catch (Exception e){
		    	e.printStackTrace();
		    	//System.out.println(""+e.getMessage());
		    	return 0;
		    } 

		}
	};

}