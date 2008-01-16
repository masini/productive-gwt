package org.googlecode.gwt.menu.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.ApplicationContextFactory;
import org.googlecode.gwt.base.client.PlaceHolder;
import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.menu.client.filter.MenuFilter;
import org.googlecode.gwt.menu.client.filter.RoleMenuFilterAction;
import org.googlecode.gwt.menu.client.model.MenuModel;
import org.googlecode.gwt.menu.client.render.DefaultMenuRender;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Menu implements EntryPoint {
	public void onModuleLoad() {
		/* Wait context inizialization */
		ApplicationContextFactory.getApplicationContext(new AsyncCallback() {
			public void onFailure(Throwable caught) {
				//TODO: Mange failure on loading
			}

			public void onSuccess(Object result) {
				/* User info */
				ApplicationContext context = (ApplicationContext) result;
				UserInfo user = context.getBootstrapData().getUserInfo();

				/* Model */
				MarkMenu markMenu = (MarkMenu) GWT.create(MarkMenu.class);
				MenuModel menuModel = markMenu.getMenuModel();

				/* Apply security policy (server mode only)*/
				if (GWT.isScript()) {
					menuModel = MenuFilter.filter(menuModel, new RoleMenuFilterAction(user));
				}

				/* Render */
				DefaultMenuRender render = new DefaultMenuRender();
				render.setMenuModel(menuModel);
				
				/* Show menu */
				RootPanel mainpage = PlaceHolder.get(PlaceHolder.MENU);
				mainpage.setSize("100%", "100%");
				mainpage.add(render);
			}
		});
	}
}
