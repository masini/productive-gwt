package org.googlecode.gwt.simplegrid.client.table;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.TableModelHelper;
import com.google.gwt.gen2.table.client.SortableGrid;
import com.google.gwt.gen2.table.event.client.TableEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.DOM;

import java.util.Collection;

public class FixedWidthGridCustom extends FixedWidthGrid implements HasClickHandlers, HasDoubleClickHandlers {
    private SimpleGridPolicy.SelectionPolicy sgSelectionPolicy;
    public final boolean retrieveDataOnLoad;
    private final String[] propertyPaths;

    FixedWidthGridCustom(SimpleGridPolicy.SelectionPolicy sgSelectionPolicy, boolean retrieveDataOnLoad, String... propertyPaths) {
        this.sgSelectionPolicy = sgSelectionPolicy;
        setSelectionPolicy(sgSelectionPolicy.equivalentStandardOne);

        this.retrieveDataOnLoad = retrieveDataOnLoad;

        this.propertyPaths = propertyPaths;
    }

    @Deprecated
    public FixedWidthGridCustom() {
        this(SimpleGridPolicy.SelectionPolicy.ONE_ROW, true);
    }

    // Metodo che ho dovuto ricostruire andandolo a cercare in una delle versioni di gwt-incubator (peraltro ovvio)

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
        return addDomHandler(handler, DoubleClickEvent.getType());
    }

    @Override
    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        sgSelectionPolicy = SimpleGridPolicy.SelectionPolicy.valueOf(selectionPolicy.toString());
        super.setSelectionPolicy(selectionPolicy);
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK: {
                DomEvent.fireNativeEvent(event, this, this.getElement());
                break;
            }

            case Event.ONDBLCLICK: {
                DomEvent.fireNativeEvent(event, this, this.getElement());
                break;
            }
        }

        super.onBrowserEvent(event);
    }


    // Metodo che ho dovuto ricostruire andandolo a cercare in una delle versioni di gwt-incubator (peraltro meno ovvio)

    TableEvent.Cell getCellForEvent(ClickEvent event) {
        Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
        if (td != null) {
            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            int column = DOM.getChildIndex(tr, td);

            return new TableEvent.Cell(row, column);
        } else {
            return null;
        }
    }

    void configure(FixedWidthFlexTable headerTable, String... columnsName) {
        sgSelectionPolicy.configure(headerTable, this, columnsName);
    }

    int getOffset() {
        return sgSelectionPolicy.getOffset();
    }

    /**
     * Override che, sostanzialmente, riproduce il codice del corrispondente metodo in {@link com.google.gwt.gen2.table.client.SortableGrid},
     * salvo infilare i {@link #propertyPaths} se possibile.<br>
     *
     * @param column    l'indice della colonna da ordinare
     * @param ascending true se l'ordinamento e' crescente
     */
    @Override
    public void sortColumn(int column, boolean ascending) {
        // Verify the column bounds
        if (column < 0) {
            throw new IndexOutOfBoundsException("Cannot access a column with a negative index: " + column);
        } else if (column >= numColumns) {
            throw new IndexOutOfBoundsException("Column index: " + column + ", Column size: " + numColumns);
        }

        // Add the sorting to the list of sorted columns
        getColumnSortList().add(
			propertyPaths != null && propertyPaths.length > column ?
				new ColumnSortInfo(column, ascending, propertyPaths[column])
				:
				new TableModelHelper.ColumnSortInfo(column, ascending)
        );

        // Use the onSort method to actually sort the column
        Collection<Element> elements = getSelectedRowsMap().values();
        Element[] selectedRows = elements.toArray(new Element[elements.size()]);
        deselectAllRows();
        getColumnSorter(true).onSortColumn(this, getColumnSortList(), new ColumnSorterCallback(selectedRows));
    }

    /**
     * Classe resa necessaria dal bizzarro vezzo googleiano
     * consistente nel dichiarare protected il costruttore di {@link ColumnSorterCallback},
     * il che mi obbliga a definirne una sottoclasse (onde avere un costruttore accessibile).
     */
    private class ColumnSorterCallback extends SortableGrid.ColumnSorterCallback {
        private ColumnSorterCallback(Element[] selectedRows) {
            super(selectedRows);
        }
    }

    class ColumnSortInfo extends TableModelHelper.ColumnSortInfo {
        private String propertyPath;

        public ColumnSortInfo(int column, boolean ascending, String propertyPath) {
            super(column, ascending);
            this.propertyPath = propertyPath;
        }

        public ColumnSortInfo() {
        }

        public String getPropertyPath() {
            return propertyPath;
		}
	}
}
