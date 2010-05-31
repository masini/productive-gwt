package org.googlecode.gwt.reflection.client.complexmodel;

import org.googlecode.gwt.reflection.client.WrapperFactory.Converter;

public class StatoArticoloConverter implements Converter<StatoArticolo>{

	public StatoArticolo convertFromString(String value) {
		StatoArticolo statoArticolo = new StatoArticolo();
		statoArticolo.setCodice(value);
		return statoArticolo;
	}

	public String convertToString(StatoArticolo value) {
		return value!=null?value.getCodice():null;
	}

}
