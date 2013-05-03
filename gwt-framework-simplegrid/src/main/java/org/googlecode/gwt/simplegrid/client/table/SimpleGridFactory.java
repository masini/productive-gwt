package org.googlecode.gwt.simplegrid.client.table;

import java.io.Serializable;

/**
 * @deprecated because use of {@link SimpleGrid} is discouraged in favor of {@link StandardGrid}
 */
@Deprecated
public final class SimpleGridFactory {

	private SimpleGridFactory() {
	}

	// Public CONSTRUCTOR methods
	public static <SerializableRowType extends Serializable> SimpleGrid<SerializableRowType> createSimpleGrid(String dataSource, String... columnsName) {
		return new SimpleGrid<SerializableRowType>(new DataServiceTableController<SerializableRowType>(dataSource), new TableConfigurer<SerializableRowType>(), columnsName);
	}

	// Public CONSTRUCTOR methods
	public static <SerializableRowType extends Serializable> SimpleGrid<SerializableRowType> createSimpleGrid(String dataSource, TableConfigurer<SerializableRowType> tableConfigurer, String... columnsName) {
		return new SimpleGrid<SerializableRowType>(new DataServiceTableController<SerializableRowType>(dataSource), tableConfigurer, columnsName);
	}

	public static <RowType> SimpleGrid<RowType> createSimpleGrid(TableController<RowType> tableController, TableConfigurer<RowType> tableConfigurer, String... columnsName) {
		return new SimpleGrid<RowType>(tableController, tableConfigurer, columnsName);
	}

}
