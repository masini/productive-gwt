package org.googlecode.gwt.simplegrid.client.table;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.gen2.table.client.CellRenderer;
import com.google.gwt.gen2.table.client.RowRenderer;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ResizePolicy;
import com.google.gwt.gen2.table.client.AbstractScrollTable.ScrollPolicy;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;

public class TableConfigurer<RowType> {

	private final Factory factory = new Factory();
	
	class Factory {
		
		private Factory() {
		}
		
		TableConfigurer<RowType> createTableConfigurer() {
			return TableConfigurer.this;
		}
	}
	
	Factory getFactory() {
		return factory;
	}
	
	public TableConfigurer() {
	}

	private int cellpadding = 3;
	private int cellspacing = 0;
	private String width = "100%";
	private String height = "400px";
	private boolean visible = true;
	private boolean sortable = true;
	private boolean paginable = true;
	private boolean withCache = true;
	private ResizePolicy resizePolicy = ResizePolicy.FILL_WIDTH;
	private ScrollPolicy scrollPolicy = ScrollPolicy.BOTH;
	private SelectionPolicy selectionPolicy = SelectionPolicy.ONE_ROW; 
	private int pageSize = 50;
	private int[] columnsWidth;
	private Map<Integer, CellRenderer<RowType, Object>> cellRenderers = new HashMap<Integer, CellRenderer<RowType, Object>>();
	private RowRenderer<RowType> rowRenderer;

	public int getPageSize() {
		return pageSize;
	}
	public TableConfigurer<RowType> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		
		return this;
	}
	public int getCellpadding() {
		return cellpadding;
	}
	public TableConfigurer<RowType> setCellpadding(int cellpadding) {
		this.cellpadding = cellpadding;
		
		return this;
	}
	public int getCellspacing() {
		return cellspacing;
	}
	public TableConfigurer<RowType> setCellspacing(int cellspacing) {
		this.cellspacing = cellspacing;
		
		return this;
	}
	public String getWidth() {
		return width;
	}
	public TableConfigurer<RowType> setWidth(String width) {
		this.width = width;
		
		return this;
	}
	public String getHeight() {
		return height;
	}
	public TableConfigurer<RowType> setHeight(String height) {
		this.height = height;
		
		return this;
	}
	public boolean isVisible() {
		return visible;
	}
	public TableConfigurer<RowType> setVisible(boolean visible) {
		this.visible = visible;
		
		return this;
	}
	public boolean isSortable() {
		return sortable;
	}
	public TableConfigurer<RowType> setSortable(boolean sortable) {
		this.sortable = sortable;
		
		return this;
	}
	public int[] getColumnsWidth() {
		return columnsWidth;
	}
	
	public int getColumnWidth(int index) {
		return columnsWidth[index];
	}
	public TableConfigurer<RowType> setColumnsWidth(int[] columnsWidth) {
		this.columnsWidth = columnsWidth;
		
		return this;
	}
	public boolean isPaginable() {
		return paginable;
	}
	public TableConfigurer<RowType> setPaginable(boolean paginable) {
		this.paginable = paginable;
		
		return this;
	}
	public TableConfigurer<RowType> setCellRenderer(int index, CellRenderer<RowType, Object> cellRenderer) {
		this.cellRenderers.put(index, cellRenderer);
		
		return this;
	}
	public CellRenderer<RowType, Object> getCellRenderer(int index) {
		return this.cellRenderers.get(index);
	}

	protected ResizePolicy getResizePolicy() {
		return resizePolicy;
	}

	protected void setResizePolicy(ResizePolicy resizePolicy) {
		this.resizePolicy = resizePolicy;
	}

	protected ScrollPolicy getScrollPolicy() {
		return scrollPolicy;
	}

	protected void setScrollPolicy(ScrollPolicy scrollPolicy) {
		this.scrollPolicy = scrollPolicy;
	}

	protected SelectionPolicy getSelectionPolicy() {
		return selectionPolicy;
	}

	protected void setSelectionPolicy(SelectionPolicy selectionPolicy) {
		this.selectionPolicy = selectionPolicy;
	}

	public TableConfigurer<RowType> setSgResizePolicy(SimpleGridPolicy.ResizePolicy gresizePolicy) {
		//SGresizePolicy = gresizePolicy;
		setResizePolicy(ResizePolicy.valueOf(gresizePolicy.toString()));
		return this;
	}

	public TableConfigurer<RowType> setSgScrollPolicy(SimpleGridPolicy.ScrollPolicy gscrollPolicy) {
		//SGscrollPolicy = gscrollPolicy;
		setScrollPolicy(ScrollPolicy.valueOf(gscrollPolicy.toString()));
		return this;
	}

	public TableConfigurer<RowType> setSgSelectionPolicy(
			SimpleGridPolicy.SelectionPolicy gselectionPolicy) {
		//SGselectionPolicy = gselectionPolicy;
		setSelectionPolicy(SelectionPolicy.valueOf(gselectionPolicy.toString()));
		return this;
	}

	public RowRenderer<RowType> getRowRenderer() {
		return rowRenderer;
	}

	public TableConfigurer<RowType> setRowRenderer(RowRenderer<RowType> rowRenderer) {
		this.rowRenderer = rowRenderer;
		return this;
	}

	public boolean getWithCache() {
		return withCache;
	}

	public TableConfigurer<RowType> setWithCache(boolean withCache) {
		this.withCache = withCache;
		return this;
	}
	
}