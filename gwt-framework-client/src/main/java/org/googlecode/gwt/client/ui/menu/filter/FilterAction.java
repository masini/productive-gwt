package org.googlecode.gwt.client.ui.menu.filter;

import org.googlecode.gwt.client.ui.menu.model.MenuModel;

/**
 * Define action can be execute on MenuModel
 */
public interface FilterAction {

	/**
	 * Test if the action is to execute on the passed leaf node.
	 * 
	 * @param leaf
	 *            Node to test
	 * @return true if this action is to execute on the node, false otherwise.
	 */
	public abstract boolean isToExecuteOnLeaf(MenuModel leaf);

	/**
	 * Test if the action is to execute on the passed not-leaf node.
	 * 
	 * @param node
	 *            Node to test
	 * @return true if this action is to execute on the node, false otherwise.
	 */
	public abstract boolean isToExecuteOnNode(MenuModel node);

	/**
	 * Execute the code of the action on the passed node (leaf or not-leaf).<br>
	 * It's call only if isToExecuteOnLeaf() or isToExecuteOnNode() was returned
	 * true on passed node. On the not-leaf node the method is called after his
	 * child node.
	 * 
	 * @param node
	 *            Current node
	 */
	public abstract void execute(MenuModel node);

}