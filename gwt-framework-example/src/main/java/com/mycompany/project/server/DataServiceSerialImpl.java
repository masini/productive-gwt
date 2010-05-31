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

import org.googlecode.gwt.simplegrid.shared.AbstractDataRequest;
import org.googlecode.gwt.simplegrid.shared.DataRequest;
import org.googlecode.gwt.simplegrid.shared.DataResponse;
import org.googlecode.gwt.simplegrid.shared.DefaultDataSourceService;
import com.mycompany.project.shared.PojoBean;

import java.io.Serializable;
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
@SuppressWarnings({"GwtServiceNotRegistered"})
public class DataServiceSerialImpl extends RemoteServiceServlet implements DefaultDataSourceService<Serializable[]> {

	private static final long serialVersionUID = 1L;
	
	private static int sortCol;
	private static boolean sortAsc;

	public DataResponse<Serializable[]> requestRows(DataRequest request) throws Exception{
		
		AbstractDataRequest.DataColumnSortList sortList = request.getColumnSortList();
		sortCol = sortList.getPrimaryColumn()+1;
		sortAsc = sortList.isPrimaryAscending();	
		
		//get filter
		int numRowsFilter = (request.getFilter()!=null)?(Integer) request.getFilter().returnCurrentFilter():20;
		
		ArrayList<PojoBean> listaBean = new DataSourceGenerator(numRowsFilter).getListaPojo();
		
		int totalRowsNum = listaBean.size();
		
		if (sortCol>0) 
			Collections.sort(listaBean, genericBeanComparator);
		
		List<PojoBean> resultOut = new ArrayList<PojoBean>();
		for (int i=request.getStartRow(); i < Math.min(request.getStartRow() + request.getNumRows(), totalRowsNum); i++){
			resultOut.add(listaBean.get(i));
		}

		return new DataResponse<Serializable[]>(getRawDataFromBeans(resultOut), totalRowsNum);
	}
	
	

  	/**
  	 * Ritorna Lista di collezioni da una LISTA di POJO BEANS
  	 * @param listaDati
  	 * @return List<Collection<Serializable>>
  	 */
	private List<Serializable[]> getRawDataFromBeans(List<PojoBean> listaDati){

		List<Serializable[]> listaRighe = new ArrayList<Serializable[]>();
		
	 
		for (int riga=0; riga<listaDati.size(); riga++) {
			int numCols = listaDati.get(0).getClass().getDeclaredFields().length;
			
			List<Serializable> collCampi = new ArrayList<Serializable>();
			
		    Object objRow = listaDati.get(riga);
		    Field[] fields = objRow.getClass().getDeclaredFields();
	    
	  		 for (int col = 0; col < numCols; col++) { 
  				Field field = fields[col];
  			 	String filedName = field.getName();
  			 	String strMethodName = "get"+filedName.substring(0,1).toUpperCase()+filedName.substring(1).toLowerCase();
  			 	
  			   try {		
	  			 	Method objMethod = objRow.getClass().getDeclaredMethod(strMethodName, (Class<?>[])null);
	  			 	collCampi.add((Serializable)objMethod.invoke(objRow, (Object[])null));
  			    } catch (Exception e){
  			    	//System.err.println("Unable to find the method: "+e.getMessage());
  			    }
	  		 }
	  		 
	  		listaRighe.add(collCampi.toArray(new Serializable[]{}));
		}

  		return listaRighe;
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