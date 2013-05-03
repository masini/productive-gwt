package org.googlecode.gwt.simplegrid.client.table;

/**
 * Author: Andrea Baroncelli
 * Date: 25-mar-2010
 * Time: 12.47.03
 */
public interface Converter<OLD, NEW> {
	NEW convert(OLD old);
}
