package org.googlecode.gwt.reflection.client.converters;

import org.googlecode.gwt.reflection.client.WrapperFactory.Converter;

public class BooleanConverter implements Converter<Boolean> {

	public Boolean convertFromString(String value) {
		return Boolean.valueOf(value);
	}

	public String convertToString(Boolean value) {
		return Boolean.toString(value);
	}

}
