package com.mycompany.project.client;

import org.googlecode.gwt.reflection.client.WrapperFactory;

import com.mycompany.project.client.PersonPojo.Indirizzo;

public class IndirizzoConverter implements WrapperFactory.Converter<Indirizzo>{

	public Indirizzo convertFromString(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public String convertToString(Indirizzo value) {
		// TODO Auto-generated method stub
		return value.getVia()+", "+Integer.toString(value.getNumero())+ " - "+value.getCitta();
	}
	
}

