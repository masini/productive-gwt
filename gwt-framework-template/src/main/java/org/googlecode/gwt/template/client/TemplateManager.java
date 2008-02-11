package org.googlecode.gwt.template.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.ApplicationContextFactory;
import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.menu.client.filter.MenuFilter;
import org.googlecode.gwt.menu.client.filter.RoleMenuFilterAction;
import org.googlecode.gwt.menu.client.model.MenuModel;
import org.googlecode.gwt.menu.client.render.DefaultMenuRender;
import org.googlecode.gwt.template.client.PlaceHolder.PlaceHolderConstant;
import org.googlecode.gwt.template.client.exception.PlaceHolderException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class TemplateManager {
	private static final String ERR_MSG = "Non trovata la zona ";
	private static final String SPACE = "&nbsp;";

	private static HTML home = null;
	private static Widget firstPanel = null;
	private static Widget firstNavigation = null;
	
	public static void setApplicationTitle(Widget widget) throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(PlaceHolder.APPLICATION_TITLE);
		if(root==null){
			throw new PlaceHolderException(ERR_MSG+"APPLICATION_TITLE");
		}
		root.add(widget);
	}
	
	public static void setHeader(Widget widget) throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(PlaceHolder.HEADER);
		if(root==null){
			throw new PlaceHolderException(ERR_MSG+"HEADER");
		}
		root.add(widget);
	}
	
	public static void setInfo(Widget widget) throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(PlaceHolder.INFO);
		if(root==null){
			throw new PlaceHolderException(ERR_MSG+"INFO");
		}
		root.add(widget);
	}
	
	public static void setFooter(Widget widget) throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(PlaceHolder.FOOTER);
		if(root==null){
			throw new PlaceHolderException(ERR_MSG+"FOOTER");
		}
		root.add(widget);
	}
	
	public static void setApplicationContent(Widget widget)throws PlaceHolderException{
		RootPanel root = get(PlaceHolder.CONTENT);
		
		if(root==null){
			throw new PlaceHolderException(ERR_MSG+"CONTENT");
		}
		root.clear();
		root.add(widget);
	}
	
	public static void setNavigationContent(Widget widget,boolean append)throws PlaceHolderException{
		RootPanel root = get(PlaceHolder.NAVIGATION);
		
		if(root==null){
			throw new PlaceHolderException(ERR_MSG+"NAVIGATION");
		}
		
		if(!append){
			root.clear();
			root.add(homePage());
		}
		
		if(widget!=null) {
			root.add(new HTML(SPACE+TemplateConstantsFactory.getInstance().PAGE_CONTEXT_SEPARATOR()+SPACE));
			root.add(widget);
		}
	}
	
	public static void setHomePage(Widget content,Widget navigation)throws PlaceHolderException{
		firstPanel = content;
		firstNavigation = navigation;
		setApplicationContent(firstPanel);
		setNavigationContent(firstNavigation,false);
	}

	public static void setMenu(final SMenu menu) {
		ApplicationContextFactory.getApplicationContext(new AsyncCallback() {
			public void onFailure(Throwable caught) {
				//TODO: Mange failure on loading
			}

			public void onSuccess(Object result) {
				/* User info */
				ApplicationContext context = (ApplicationContext) result;
				UserInfo user = context.getBootstrapData().getUserInfo();

				/* Model */
				MenuModel menuModel = menu.getMenuModel();

				/* Apply security policy (server mode only)*/
				if (GWT.isScript()) {
					menuModel = MenuFilter.filter(menuModel, new RoleMenuFilterAction(user));
				}

				/* Render */
				DefaultMenuRender render = new DefaultMenuRender();
				render.setMenuModel(menuModel);
				
				/* Show menu */
				RootPanel root = get(PlaceHolder.MENU);
				if(root==null){
					throw new PlaceHolderException(ERR_MSG+"MENU");
				}
				root.setSize("100%", "100%");
				root.add(render);
			}
		});
	}
	
	private static void setReloadFirstPanel() {
		if(firstPanel!=null){
			setApplicationContent(firstPanel);
		}
		else{
			RootPanel root = get(PlaceHolder.CONTENT);
			root.clear();
		}
		
		if(firstNavigation!=null){
			setNavigationContent(firstNavigation,false);
		}
		else{
			RootPanel root = get(PlaceHolder.NAVIGATION);
			root.clear();
			setNavigationContent(null,false);
		}
	}	
	
	private static RootPanel get(PlaceHolderConstant constant){
		RootPanel root = PlaceHolder.get(constant);
		return root;
	}

	private static HTML homePage() {
		if(home==null){
			home = new HTML("<a href='javascript:;'>"+TemplateConstantsFactory.getInstance().HOME_PAGE_CONTEXT_LABEL()+"</a>",true);
			home.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					TemplateManager.setReloadFirstPanel();
				}
			});
		}
		return home;		
	}

}
