package org.googlecode.gwt.menu.client.filter;

import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.menu.client.model.MenuModel;

/**
 * Implements Security policy role based on MenuModel.
 */
public class RoleMenuFilterAction implements FilterAction {

	private UserInfo user = null;

	public RoleMenuFilterAction(UserInfo user) {
		this.user = user;
	}

	/**
	 * @see org.googlecode.gwt.menu.client.filter.FilterAction#isToExecuteOnLeaf(org.googlecode.gwt.menu.client.model.MenuModel)
	 */
	public boolean isToExecuteOnLeaf(MenuModel leaf) {
		if (user != null && user.isUserInRole(leaf.getRole())) {
			return false;
		}

		return true;
	}

	/**
	 * @see org.googlecode.gwt.menu.client.filter.FilterAction#isToExecuteOnNode(org.googlecode.gwt.menu.client.model.MenuModel)
	 */
	public boolean isToExecuteOnNode(MenuModel node) {
		if (node.hasChildren()) {
			return false;
		}

		return true;
	}

	/**
	 * @see org.googlecode.gwt.menu.client.filter.FilterAction#execute(org.googlecode.gwt.menu.client.model.MenuModel)
	 */
	public void execute(MenuModel model) {
		if (model.getParent() != null) {
			model.getParent().removeChild(model);
		}
	}

}
