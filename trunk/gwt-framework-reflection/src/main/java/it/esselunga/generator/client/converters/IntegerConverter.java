package it.esselunga.generator.client.converters;

import it.esselunga.generator.client.WrapperFactory.Converter;

public class IntegerConverter implements Converter<Integer> {

	public Integer convertFromString(String value) {
		return Integer.parseInt(value);
	}

	public String convertToString(Integer value) {
		return Integer.toString(value);
	}
}
