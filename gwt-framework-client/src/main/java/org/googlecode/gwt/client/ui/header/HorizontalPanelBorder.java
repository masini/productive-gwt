package org.googlecode.gwt.client.ui.header;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

public class HorizontalPanelBorder  extends HorizontalPanel implements SourcesMouseEvents, SourcesClickEvents {

	private MouseListenerCollection mouseListenerCollection;
	
	private ClickListenerCollection clickListenerCollection;
			 
	public HorizontalPanelBorder() {
		sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONMOUSEMOVE |
				   Event.ONMOUSEOVER | Event.ONMOUSEOUT |
				   Event.ONCLICK);
	}

	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEDOWN:
			case Event.ONMOUSEUP:
			case Event.ONMOUSEMOVE:
			case Event.ONMOUSEOVER:
			case Event.ONMOUSEOUT:
				if (mouseListenerCollection != null) {
					mouseListenerCollection.fireMouseEvent(this, event);
				}
				break;
			case Event.ONCLICK:
				if (clickListenerCollection != null) {
					clickListenerCollection.fireClick(this);
				}
				break;
			default: {
			}
		}
	}

	public void addMouseListener(MouseListener listener) {
		if(mouseListenerCollection == null) {
			mouseListenerCollection = new MouseListenerCollection();
	    }
		mouseListenerCollection.add(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		if(mouseListenerCollection != null) {
			mouseListenerCollection.remove(listener);
		}
	}

	public void addClickListener(ClickListener listener) {
		if(clickListenerCollection == null) {
			clickListenerCollection = new ClickListenerCollection();
	    }
		clickListenerCollection.add(listener);
	}

	public void removeClickListener(ClickListener listener) {
		if(clickListenerCollection != null) {
			clickListenerCollection.remove(listener);
		}
	}
}
