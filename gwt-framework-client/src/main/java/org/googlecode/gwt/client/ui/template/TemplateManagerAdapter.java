package org.googlecode.gwt.client.ui.template;

import com.google.gwt.user.client.ui.*;

import java.util.Iterator;

public class TemplateManagerAdapter implements HasWidgets, AcceptsOneWidget {

    private Iterator<Widget> iterator;

    @Override
    public void add(final Widget w) {
        TemplateManager.setApplicationContent(w);

        if(w instanceof HasName) {
            TemplateManager.setNavigationContent(new Label(((HasName)w).getName()),false);
        }

        iterator = new Iterator<Widget>() {

            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public Widget next() {
                try {
                    return w;
                } finally {
                    hasNext = false;
                }
            }

            @Override
            public void remove() {
                clear();
            }
        };
    }

    @Override
    public void clear() {
        TemplateManager.setApplicationContent(new Label(""));
    }

    @Override
    public Iterator<Widget> iterator() {
        return iterator;
    }

    @Override
    public boolean remove(Widget w) {
        clear();
        return true;
    }

    @Override
    public void setWidget(IsWidget isWidget) {
        if( isWidget!=null ) {
            add(isWidget.asWidget());
        }
    }
}
