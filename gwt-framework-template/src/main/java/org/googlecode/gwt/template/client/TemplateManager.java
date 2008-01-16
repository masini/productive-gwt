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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class TemplateManager {

	public static void setApplicationTitle(Widget widget) throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(PlaceHolder.APPLICATION_TITLE);
		if(root==null){
			throw new PlaceHolderException("");
		}
		root.add(widget);
	}
	
	public static void setHeader(Widget widget) throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(PlaceHolder.HEADER);
		if(root==null){
			throw new PlaceHolderException("");
		}
		root.add(widget);
	}
	
	public static void setFooter(Widget widget) throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(PlaceHolder.FOOTER);
		if(root==null){
			throw new PlaceHolderException("");
		}
		root.add(widget);
	}
	
	public static void setApplicationContent(Widget widget)throws PlaceHolderException{
		
		RootPanel root = get(PlaceHolder.CENTER);
		
		if(root==null){
			throw new PlaceHolderException("");
		}
		root.clear();
		root.add(widget);
	}
	
	public static void setNavigationContent(Widget widget,boolean append)throws PlaceHolderException{
		
		RootPanel root = get(PlaceHolder.NAVIGATION);
		
		if(root==null){
			throw new PlaceHolderException("");
		}
		
		if(!append)
			root.clear();
		root.add(new Label(TemplateConstantsFactory.getInstance().PAGE_CONTEXT_SEPARATOR()));
		root.add(widget);
	}
	
	private static Widget firstPanel;
	private static Widget firstNavigation;
	public static void setFirstContent(Widget content,Widget navigation)throws PlaceHolderException{
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
					throw new PlaceHolderException("");
				}
				root.setSize("100%", "100%");
				root.add(render);
			}
		});
	}
	
	public static void setReloadFirstPanel() {
		if(firstPanel!=null){
			setApplicationContent(firstPanel);
		}
		else{
			RootPanel root = get(PlaceHolder.CENTER);
			root.clear();
		}
		
		if(firstNavigation!=null){
			setNavigationContent(firstNavigation,false);
		}
		else{
			RootPanel root = get(PlaceHolder.NAVIGATION);
			root.clear();
		}
		
		
	}	
	
	private static RootPanel get(PlaceHolderConstant constant)throws PlaceHolderException{
		RootPanel root = PlaceHolder.get(constant);
		return root;
	}



}
