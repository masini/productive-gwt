package com.mycompany.project.client;

import org.googlecode.gwt.reflection.client.WrapperFactory;

import com.mycompany.project.client.PersonPojo.Stati;

public class StatiConverter implements WrapperFactory.Converter<Stati>{

	public Stati convertFromString(String value) {
		// TODO Auto-generated method stub
		return Stati.valueOf(value);
	}

	public String convertToString(Stati value) {
		// TODO Auto-generated method stub
		return value.name();
	}
	
}

