package org.googlecode.gwt.client.ui.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.googlecode.gwt.client.ui.menu.SMenu;
import org.googlecode.gwt.client.ui.exception.PlaceHolderException;
import org.googlecode.gwt.client.util.ApplicationResources;
import org.jboss.errai.ioc.client.container.IOC;


public class TemplateManager {

	private static TemplateManagerImpl impl = GWT.create(TemplateManagerImpl.class);
	
	public static void setApplicationTitle(String applicationTitle) throws PlaceHolderException {
		impl.setApplicationTitle(applicationTitle);
	}
	
	public static void setApplicationTitle(Widget widget) throws PlaceHolderException {
		impl.setApplicationTitle(widget);
	}

	public static void setHeader(Widget widget) throws PlaceHolderException {
		impl.setHeader(widget);	}

	public static void setInfo(Widget widget) throws PlaceHolderException {
		impl.setInfo(widget);
	}

	public static void setFooter(Widget widget) throws PlaceHolderException {
		impl.setFooter(widget);
	}

	public static void setApplicationContent(Widget widget) throws PlaceHolderException {
		impl.setApplicationContent(widget);
	}

	public static void setNavigationContent(Widget widget, boolean append) throws PlaceHolderException {
		impl.setNavigationContent(widget, append);
	}

	public static void setHomePage(Widget content, Widget navigation) throws PlaceHolderException {
		impl.setHomePage(content, navigation);
	}

    public static void setHomePage(Place place) throws PlaceHolderException {
        impl.setHomePage(place);
    }

	public static void setMenu(final SMenu menu) {
		impl.setMenu(menu);
	}

	/**
	 * This method set the value of HistoryToken of homepage. 
	 * @param homePageHistoryToken HomePage token name
	 */
	public static void setHomePageHistoryToken(String homePageHistoryToken) {
		impl.setHomePageHistoryToken(homePageHistoryToken);
	}

	protected static Panel get(PlaceHolder.PlaceHolderConstant constant) {
		return impl.get(constant);
	}

	/*
	 * Path di navigaizone
	 */

	protected static Image getIntranetLogo() {
		return impl.getIntranetLogo();
	}

	protected static HTML getIntranetLink() {
		return impl.getIntranetLink();
	}

	protected static HTML getHomePageLink() {
		return impl.getHomePageLink();
	}

	protected HTML newSeparator() {
		return new HTML(ApplicationResources.getCostants().PAGE_CONTEXT_SEPARATOR());
	}

	/*
	 * Navigaizone
	 */

	protected static void reloadFirstPanel() {
		impl.reloadFirstPanel();
	}

	public static void backToHome() {
		impl.backToHome();
	}
	
	public static void openIntranet() {
		redirect("bvcbnvcbvcb");
	}
	
	public static native void redirect(String url)/*-{
			    $wnd.location = url;
			}-*/;

	public static void setShowIntranetLink(boolean showIntranetLink) {
		impl.setShowIntranetLink(showIntranetLink);
	}

	public static void setHome(HTML home) {
		TemplateManagerImpl.home = home;
	}

	protected HTML createIntranetLink(final String intranetUrl) {
		HTML intranetLink = new HTML("<a href='"+intranetUrl+"'>" + ApplicationResources.getCostants().INTRANET_PAGE_CONTEXT_LABEL() + "</a>", true);
//		intranetLink.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				redirect(intranetUrl);
//			}
//		});
		
		return intranetLink;
	}
	
}
