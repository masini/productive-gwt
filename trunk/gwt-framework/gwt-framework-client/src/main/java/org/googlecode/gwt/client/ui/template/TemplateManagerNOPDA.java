package org.googlecode.gwt.client.ui.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.googlecode.gwt.client.ApplicationContextFactory;
import org.googlecode.gwt.client.ui.exception.PlaceHolderException;
import org.googlecode.gwt.client.ui.menu.SMenu;
import org.googlecode.gwt.client.ui.menu.filter.MenuFilter;
import org.googlecode.gwt.client.ui.menu.filter.RoleMenuFilterAction;
import org.googlecode.gwt.client.ui.menu.model.MenuModel;
import org.googlecode.gwt.client.ui.menu.render.DefaultMenuRender;
import org.googlecode.gwt.client.ui.menu.render.ShortcutMenuRender;
import org.googlecode.gwt.client.util.ApplicationResources;
import org.googlecode.gwt.shared.ApplicationContext;
import org.googlecode.gwt.shared.DefaultUserInfo;
import org.googlecode.gwt.shared.UserInfo;

public class TemplateManagerNOPDA extends TemplateManagerImpl{


	public void setApplicationTitle(Widget widget) throws PlaceHolderException {
		Panel root = PlaceHolder.get(PlaceHolder.APPLICATION_TITLE);
		if (root == null) {
			throw new PlaceHolderException(ERR_MSG + "APPLICATION_TITLE");
		}
		root.add(widget);
	}

	public  void setHeader(Widget widget) throws PlaceHolderException {
		Panel root = PlaceHolder.get(PlaceHolder.HEADER);
		if (root == null) {
			throw new PlaceHolderException(ERR_MSG + "HEADER");
		}
		root.add(widget);
	}

	public  void setInfo(Widget widget) throws PlaceHolderException {
		Panel root = PlaceHolder.get(PlaceHolder.INFO);
		if (root == null) {
			throw new PlaceHolderException(ERR_MSG + "INFO");
		}
		root.add(widget);
	}

//	public  void setFooter(Widget widget) throws PlaceHolderException {
//		Panel root = PlaceHolder.get(PlaceHolder.FOOTER);
//		if (root == null) {
//			throw new PlaceHolderException(ERR_MSG + "FOOTER");
//		}
//		root.clear();
//		root.add(widget);
//	}

	public  void setApplicationContent(Widget widget) throws PlaceHolderException {
		Panel root = get(PlaceHolder.CONTENT);

		if (root == null) {
			throw new PlaceHolderException(ERR_MSG + "CONTENT");
		}
		root.clear();
		root.add(widget);
	}

	public  void setNavigationContent(Widget widget, boolean append) throws PlaceHolderException {
		Panel root = get(PlaceHolder.NAVIGATION);
		if (root == null) {
			throw new PlaceHolderException(ERR_MSG + "NAVIGATION");
		}

		if (!append) {
			root.clear();
			if (showIntranetLink) {
				root.add(getIntranetLogo());
				root.add(getIntranetLink());
				root.add(newSeparator());
			}
			root.add(getHomePageLink());
		}

		if (widget != null) {
			root.add(newSeparator());
			root.add(widget);
		}
	}

	public void setHomePage(Widget content, Widget navigation) throws PlaceHolderException {
		firstPanel = content;
		firstNavigation = navigation;
		setApplicationContent(firstPanel);
		setNavigationContent(firstNavigation, false);
	}

	public  void setMenu(final SMenu menu) {
		ApplicationContextFactory.getApplicationContext(new AsyncCallback<ApplicationContext>() {
            public void onFailure(Throwable caught) {
                // TODO: Mange failure on loading
            }

            public void onSuccess(ApplicationContext result) {
				/* User info */
                ApplicationContext context = result;
                UserInfo userInfo = context.getBootstrapData().getUserInfo();
                DefaultUserInfo user = new DefaultUserInfo(userInfo);

				/* Model */
                MenuModel menuModel = menu.getMenuModel();

				/* Apply security policy (server mode only) */
                if (GWT.isScript()) {
                    menuModel = MenuFilter.filter(menuModel, new RoleMenuFilterAction(user));
                }

				/* Render */
                Widget render;
                if (menuModel.existShortcut()) {
                    render = new ShortcutMenuRender();
                    ((ShortcutMenuRender) render).setMenuModel(menuModel);

                } else {
                    render = new DefaultMenuRender();
                    ((DefaultMenuRender) render).setMenuModel(menuModel);
                }

				/* Show menu */
                Panel root = get(PlaceHolder.MENU);
                if (root == null) {
                    throw new PlaceHolderException(ERR_MSG + "MENU");
                }
                root.setSize("100%", "100%");
                root.add(render);
            }
        });
	}

	/**
	 * This method set the value of HistoryToken of homepage.
	 * @param homePageHistoryToken HomePage token name
	 */
	public  void setHomePageHistoryToken(String homePageHistoryToken) {
		if (home == null) getHomePageLink();

		if (homePageHistoryToken == null) {
			home.setHTML("<a href='javascript:;'>" + ApplicationResources.getCostants().HOME_PAGE_CONTEXT_LABEL() + "</a>");
		} else {
			home.setHTML("<a href='#" + homePageHistoryToken + "'>" + ApplicationResources.getCostants().HOME_PAGE_CONTEXT_LABEL() + "</a>");
		}
	}

	protected Panel get(PlaceHolder.PlaceHolderConstant constant) {
		Panel root = PlaceHolder.get(constant);
		return root;
	}

	/*
	 * Path di navigaizone
	 */

	protected Image getIntranetLogo() {
		if (intranetLogo == null) {
			intranetLogo = new Image(ApplicationResources.getImageBundle().HOME());
			intranetLogo.setStyleName(INTRANET_LOGO_STYLE_NAME);
			intranetLogo.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					openIntranet();
				}
			});
		}

		return intranetLogo;
	}

	protected HTML getIntranetLink() {
		if (intranet == null) {
			// Il link di default non fa nulla
			intranet = new HTML("<a href='#'>" + ApplicationResources.getCostants().INTRANET_PAGE_CONTEXT_LABEL() + "</a>", true);
			intranet.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					openIntranet();
				}
			});
		}
		return intranet;
	}

	protected HTML getHomePageLink() {
		if (home == null) {
			home = new HTML("<a href='javascript:;'>" + ApplicationResources.getCostants().HOME_PAGE_CONTEXT_LABEL() + "</a>", true);
			home.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					TemplateManager.reloadFirstPanel();
				}
			});
		}
		return home;
	}

	protected HTML newSeparator() {
		return new HTML(ApplicationResources.getCostants().PAGE_CONTEXT_SEPARATOR());
	}

	/*
	 * Navigaizone
	 */

	protected void reloadFirstPanel() {
		if (firstPanel != null) {
			setApplicationContent(firstPanel);
		} else {
			Panel root = get(PlaceHolder.CONTENT);
			root.clear();
		}

		if (firstNavigation != null) {
			setNavigationContent(firstNavigation, false);
		} else {
			Panel root = get(PlaceHolder.NAVIGATION);
			root.clear();
			setNavigationContent(null, false);
		}
	}

	public  native void redirect(String url)/*-{
			    $wnd.location = url;
			}-*/;

	public  void setShowIntranetLink(boolean showIntranetLink) {
		TemplateManagerImpl.showIntranetLink = showIntranetLink;
	}

	public  void setHome(HTML home) {
		TemplateManagerImpl.home = home;
	}
	
	public void openIntranet() {
		redirect(ApplicationResources.getCostants().INTRANET_URL());
	}
	
}
