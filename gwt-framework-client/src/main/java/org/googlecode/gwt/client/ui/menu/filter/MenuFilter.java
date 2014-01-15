package org.googlecode.gwt.client.ui.menu.filter;

import org.googlecode.gwt.client.ui.menu.model.MenuModel;

/**
 * 
 */
public class MenuFilter {

	/**
	 * Execute the passed FilterAction on passed MenuModel.<br>
	 * A copy of the passed MenuModel is created before apply the filter. If the
	 * passed model is null the method return null.
	 * 
	 * @param model
	 *            MenuModel to be filtered
	 * @param action
	 *            Action to be executed
	 * 
	 * @return A new instance of MenuModel
	 */
	public static MenuModel filter(MenuModel model, FilterAction action) {
		MenuModel copy = null;
		
		copy = copy(model);

		if (action != null) {
			execute(copy, action);
		}

		return copy;
	}

	/**
	 * Execute the passed FilterAction on passed MenuModel.<br>
	 * 
	 * @param model
	 *            MenuModel to be filtered
	 * @param action
	 *            Action to be executed
	 */
	protected static void execute(MenuModel model, FilterAction action) {
		if (model != null) {
			if (!model.hasChildren()) {
				if (action.isToExecuteOnLeaf(model)) {
					action.execute(model);
				}
			} else {
				MenuModel[] children = model.getChildren();
				for (int i = 0; i < children.length; i++) {
					execute(children[i], action);
				}

				if (action.isToExecuteOnNode(model)) {
					action.execute(model);
				}
			}
		}
	}

	/**
	 * Create a copy of the passed MenuModel.<br>
	 * Copy also the child node. If the passed node is null return null.
	 * 
	 * @param model
	 *            MenuModel to be copied
	 * 
	 * @return A copy of the passed MenuModel or null.
	 */
	private static MenuModel copy(MenuModel model)  {
		MenuModel copiedMenuModel = null;

		if (model != null) {
			copiedMenuModel = model.clone();

			copiedMenuModel.removeChildren();
			if (model.hasChildren()) {
				MenuModel[] child = model.getChildren();

				for (int i = 0; i < child.length; i++) {
					copiedMenuModel.addChild(i, copy(child[i]));
				}
			}
		}

		return copiedMenuModel;
	}
}
