package org.googlecode.gwt.template.client;

import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.template.client.PlaceHolder.PlaceHolderConstant;
import org.googlecode.gwt.template.client.exception.PlaceHolderException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

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

	public static void setMenu(final SMenu menu) {
		impl.setMenu(menu);
	}

	/**
	 * This method set the value of HistoryToken of homepage. 
	 * @param tokenName HomePage token name
	 */
	public static void setHomePageHistoryToken(String homePageHistoryToken) {
		impl.setHomePageHistoryToken(homePageHistoryToken);
	}

	protected static RootPanel get(PlaceHolderConstant constant) {
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

	protected static HTML newSeparator() {
		return new HTML(TemplateConstantsFactory.getInstance().PAGE_CONTEXT_SEPARATOR());
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
		redirect("http://intra.esselunga.net/validationnet/home.aspx");
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
}
