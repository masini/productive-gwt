package com.mycompany.project.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class TitledPanel extends SimplePanel {

	public static class TitledPanelImpl {
		public void setCaption(Element fieldset, Element legend, String caption) {
			if ((caption != "") && (caption != null)) {
				DOM.setInnerHTML(legend, caption);
				DOM.insertChild(fieldset, legend, 0);
			} else if (DOM.getParent(legend) != null) {
				DOM.removeChild(fieldset, legend);
			}
		}
	}

	/**
	 * * Implementation class for TitledPanel that handles Mozilla rendering
	 * issues.
	 */
	public static class TitledPanelImplMozilla extends TitledPanelImpl {
		@Override
		public void setCaption(final Element fieldset, Element legend,
				String caption) {
			DOM.setStyleAttribute(fieldset, "display", "none");
			super.setCaption(fieldset, legend, caption);
			DOM.setStyleAttribute(fieldset, "display", "");
		}
	}

	/**
	 * * Implementation class for TitledPanel that handles Safari rendering
	 * issues.
	 */
	public static class TitledPanelImplSafari extends TitledPanelImpl {
		@Override
		public void setCaption(final Element fieldset, Element legend,
				String caption) {
			DOM.setStyleAttribute(fieldset, "visibility", "hidden");
			super.setCaption(fieldset, legend, caption);
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					DOM.setStyleAttribute(fieldset, "visibility", "");
				}
			});
		}
	}

	private static TitledPanelImpl impl = GWT.create(TitledPanelImpl.class);
	
	/** * The legend used as the title. */
	private Element legend;
	/** * The title at the top of the border. */
	private String caption;

	/** * Constructor. * * @param caption the title to display */
	public TitledPanel(String caption) {
		super(DOM.createElement("fieldset"));
		legend = DOM.createElement("legend");
		DOM.appendChild(getElement(), legend);
		setCaption(caption);
	}

	/**
	 * * Constructor. * * @param caption the title to display * @param w the
	 * widget to add to the panel
	 */
	public TitledPanel(String caption, Widget w) {
		this(caption);
		setWidget(w);
	}

	/** * @return the title on top of the border */
	public String getCaption() {
		return this.caption;
	}

	/**
	 * * Set the title in the border. Pass in null or an empty string to remove
	 * the * title completely, leaving just a box. * * @param caption the new
	 * title
	 */
	public void setCaption(String caption) {
		this.caption = caption;
		impl.setCaption(getElement(), legend, caption);
	}
}
