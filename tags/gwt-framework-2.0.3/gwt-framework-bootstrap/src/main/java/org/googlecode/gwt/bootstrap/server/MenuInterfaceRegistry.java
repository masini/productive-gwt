package org.googlecode.gwt.bootstrap.server;

import java.util.List;

public class MenuInterfaceRegistry {
	private static String menuInterfaceClass;

	public static String getMenuInterfaceClass() {
		return menuInterfaceClass;
	}

	static void setMenuInterfaceClass(String menuInterface) {
		MenuInterfaceRegistry.menuInterfaceClass = menuInterface;
	}
	
	private static List<String> roles;

	public static List<String> getRoles() {
		return roles;
	}

	static void setRoles(List<String> roles) {
		MenuInterfaceRegistry.roles = roles;
	}
	
}
