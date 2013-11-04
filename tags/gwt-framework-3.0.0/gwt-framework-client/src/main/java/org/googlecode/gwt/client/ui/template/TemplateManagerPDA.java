package org.googlecode.gwt.client.ui.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.googlecode.gwt.client.ApplicationContextFactory;
import org.googlecode.gwt.client.ui.exception.PlaceHolderException;
import org.googlecode.gwt.client.ui.menu.SMenu;
import org.googlecode.gwt.client.ui.menu.filter.MenuFilter;
import org.googlecode.gwt.client.ui.menu.filter.RoleMenuFilterAction;
import org.googlecode.gwt.client.ui.menu.model.MenuModel;
import org.googlecode.gwt.client.ui.menu.render.MenuRenderSimplePDA;
import org.googlecode.gwt.client.util.ApplicationResources;
import org.googlecode.gwt.shared.client.ApplicationContext;
import org.googlecode.gwt.shared.client.DefaultUserInfo;
import org.jboss.errai.ioc.client.container.IOC;

public class TemplateManagerPDA extends TemplateManagerImpl{
	
	private String applicationTitle;
	/* Render */
	private Widget render;
	
	public void setApplicationTitle(String applicationTitle) throws PlaceHolderException {
		Panel root = PlaceHolder.get(PlaceHolder.NAVIGATION);
		if (root == null) {
			throw new PlaceHolderException(ERR_MSG + "APPLICATION_TITLE");
		}
		this.applicationTitle=applicationTitle;
		root.add(getHomePageLink());
	}

	public void setHeader(Widget widget) throws PlaceHolderException {
		headerWidget = widget;
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
//			if (showIntranetLink) {
//				root.add(getIntranetLogo());
//				root.add(getIntranetLink());
//				root.add(newSeparator());
//			}
//			root.add(getHomePageLink());
		}

		if (widget != null) {
//			root.add(newSeparator());
			root.add(widget);
		}
	}
	


	public  void setHomePage(Widget content, Widget navigation) throws PlaceHolderException {
		firstPanel = content;
		firstNavigation = navigation;
		setApplicationContent(firstPanel);
		setNavigationContent(firstNavigation, false);
	}

    public void setHomePage(Place place) throws PlaceHolderException {
        if (place == null) {
            throw new PlaceHolderException(ERR_MSG + "PLACE");
        }
        firstPlace = place;
    }

	public  void setMenu(final SMenu menu) {
		ApplicationContextFactory.getApplicationContext(new AsyncCallback<ApplicationContext>() {
            public void onFailure(Throwable caught) {
                // TODO: Mange failure on loading
            }

            public void onSuccess(ApplicationContext result) {
				/* User info */
                ApplicationContext context = result;
                DefaultUserInfo user = new DefaultUserInfo(context.getBootstrapData().getUserInfo());

				/* Model */
                MenuModel menuModel = menu.getMenuModel();

				/* Apply security policy (server mode only) */
                if (GWT.isScript()) {
                    menuModel = MenuFilter.filter(menuModel, new RoleMenuFilterAction(user));
                }

                render = new MenuRenderSimplePDA();
                ((MenuRenderSimplePDA) render).setMenuModel(menuModel);

				
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

//	protected  Image getIntranetLogo() {
//		if (intranetLogo == null) {
//			intranetLogo = images.getHomeIcon().createImage();
//			intranetLogo.setStyleName(INTRANET_LOGO_STYLE_NAME);
//			intranetLogo.addClickListener(new ClickListener() {
//				public void onClick(Widget sender) {
//					openIntranet();
//				}
//			});
//		}
//
//		return intranetLogo;
//	}
//
//	protected  HTML getIntranetLink() {
//		if (intranet == null) {
//			intranet = new HTML("<a href='#'>" + TemplateConstantsFactory.getInstance().INTRANET_PAGE_CONTEXT_LABEL() + "</a>", true);
//			intranet.addClickListener(new ClickListener() {
//				public void onClick(Widget sender) {
//					openIntranet();
//				}
//			});
//		}
//		return intranet;
//	}
//
	
	public void backToHome() {
		TemplateManager.reloadFirstPanel();
		render.setVisible(true);
	}

	
	protected HTML getHomePageLink() {
		if (home == null) {
//			home = new HTML(" <a href='javascript:;'>" + applicationTitle + "</a> ", true);
//			home.addClickListener(new ClickListener() {
//				public void onClick(Widget sender) {
//					TemplateManager.reloadFirstPanel();
//					render.setVisible(true);
//				}
//			});
			home=new HTML(applicationTitle);
			home.setStylePrimaryName("pda-label");
		}
		return home;
	}
//	
//	protected  HTML newSeparator() {
//		return new HTML(TemplateConstantsFactory.getInstance().PAGE_CONTEXT_SEPARATOR());
//	}
	
	
	
	/*
	 * Navigaizone
	 */
	
	protected  void reloadFirstPanel() {
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
			root.add(getHomePageLink());
		}

        if(firstPlace != null) {
            IOC.getBeanManager().lookupBean(PlaceController.class).getInstance().goTo(firstPlace);
        }
	}
	

//	public  void openIntranet() {
//		redirect(intranetUrl);
//	}

	public  native void redirect(String url)/*-{
			    $wnd.location = url;
			}-*/;

	public  void setShowIntranetLink(boolean showIntranetLink) {
		TemplateManagerPDA.showIntranetLink = showIntranetLink;
	}

	public  void setHome(HTML home) {
		TemplateManagerPDA.home = home;
	}
	
	public void hideRender(){
		render.setVisible(false);
	}

    public Widget getHeaderWidget() {
        return headerWidget;
    }

}
