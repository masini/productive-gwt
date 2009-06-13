package it.esselunga.generator.client.converters;

import it.esselunga.generator.client.WrapperFactory.Converter;

public class NoConverter implements Converter<Object> {

	public Object convertFromString(String value) {
		return value;
	}

	public String convertToString(Object value) {
		return value!=null?value.toString():null;
	}

}
