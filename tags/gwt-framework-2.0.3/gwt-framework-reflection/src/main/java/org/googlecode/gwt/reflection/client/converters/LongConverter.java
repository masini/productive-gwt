package org.googlecode.gwt.reflection.client.converters;

import org.googlecode.gwt.reflection.client.WrapperFactory.Converter;

public class LongConverter implements Converter<Long> {

	public Long convertFromString(String value) {
		return Long.parseLong(value);
	}

	public String convertToString(Long value) {
		return Long.toString(value);
	}

}
