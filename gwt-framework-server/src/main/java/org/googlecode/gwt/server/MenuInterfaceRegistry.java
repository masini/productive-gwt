package org.googlecode.gwt.server;

import java.util.List;

public class MenuInterfaceRegistry {
	private static String menuInterfaceClass;

	public static String getMenuInterfaceClass() {
		return menuInterfaceClass;
	}

	public static void setMenuInterfaceClass(String menuInterface) {
		MenuInterfaceRegistry.menuInterfaceClass = menuInterface;
	}
	
	private static List<String> roles;

	public static List<String> getRoles() {
		return roles;
	}

	public static void setRoles(List<String> roles) {
		MenuInterfaceRegistry.roles = roles;
	}
	
}
