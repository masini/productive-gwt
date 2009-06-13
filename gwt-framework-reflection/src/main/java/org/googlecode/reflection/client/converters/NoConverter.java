package org.googlecode.reflection.client.converters;

import org.googlecode.reflection.client.WrapperFactory.Converter;

public class NoConverter implements Converter<Object> {

	public Object convertFromString(String value) {
		return value;
	}

	public String convertToString(Object value) {
		return value!=null?value.toString():null;
	}

}
