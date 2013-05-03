package org.googlecode.gwt.client.ui.menu.filter;

import org.googlecode.gwt.client.ui.menu.model.MenuModel;
import org.googlecode.gwt.shared.DefaultUserInfo;
import org.googlecode.gwt.shared.UserInfo;

/**
 * Implements Security policy role based on MenuModel.
 */
public class RoleMenuFilterAction implements FilterAction {

	private DefaultUserInfo user = null;

	public RoleMenuFilterAction(UserInfo user) {
		this.user = new DefaultUserInfo(user);
	}

	/**
	 * @see org.googlecode.gwt.client.ui.menu.filter.FilterAction#isToExecuteOnLeaf(org.googlecode.gwt.client.ui.menu.model.MenuModel)
	 */
	public boolean isToExecuteOnLeaf(MenuModel leaf) {
		String[] role = leaf.getRole();
		
		for(int i = 0; i < role.length; i++) {
			if (user != null && user.getIsUserInRole(role[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @see org.googlecode.gwt.client.ui.menu.filter.FilterAction#isToExecuteOnNode(org.googlecode.gwt.client.ui.menu.model.MenuModel)
	 */
	public boolean isToExecuteOnNode(MenuModel node) {
		if (node.hasChildren()) {
			return false;
		}

		return true;
	}

	/**
	 * @see org.googlecode.gwt.client.ui.menu.filter.FilterAction#execute(org.googlecode.gwt.client.ui.menu.model.MenuModel)
	 */
	public void execute(MenuModel model) {
		if (model.getParent() != null) {
			model.getParent().removeChild(model);
		}
	}

}
