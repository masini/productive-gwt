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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.mycompany.project.shared.PojoBean;

/**
 * The static set of data used to populate the grid.
 */
public class DataSourceGenerator {
	
	public DataSourceGenerator() {
		super();
	}
	
	public DataSourceGenerator(int numRecords) {
		super();
		this.numRecords = numRecords;
	}

	private int numRecords = 105;
	//private final int NUM_RECORS = new Random().nextInt(100);

	public ArrayList<PojoBean> getListaPojo(){
		
		ArrayList<PojoBean> listaPojo = new ArrayList<PojoBean>();
		for (Integer i = 0; i< numRecords; i++){
			PojoBean tempPojo = new PojoBean();
			
			int num = i+1;
			tempPojo.setCodice("SHOW-"+new Random().nextInt(100));
			tempPojo.setDescrizione("descrizione "+num+"-esima");
			tempPojo.setNumero(i*10);
			tempPojo.setTimestamp_id((new Timestamp(new Date().getTime())).toString());
			tempPojo.setValue(new Double(i));
			
			listaPojo.add(tempPojo);
		}
		return listaPojo;
	}

	public int getNumRecords() {
		return numRecords;
	}

	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}

}
