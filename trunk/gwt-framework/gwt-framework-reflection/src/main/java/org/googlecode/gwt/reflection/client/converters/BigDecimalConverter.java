package org.googlecode.gwt.reflection.client.converters;

import java.math.BigDecimal;

import org.googlecode.gwt.reflection.client.WrapperFactory.Converter;

public class BigDecimalConverter implements Converter<BigDecimal> {

	public BigDecimal convertFromString(String value) {
		return new BigDecimal(value);
	}

	public String convertToString(BigDecimal value) {
		return value.toString();
	}

}
