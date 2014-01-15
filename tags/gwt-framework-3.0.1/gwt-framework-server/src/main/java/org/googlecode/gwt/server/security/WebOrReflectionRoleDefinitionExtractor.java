package org.googlecode.gwt.server.security;


import org.googlecode.gwt.server.MenuInterfaceRegistry;

import java.util.List;

public class WebOrReflectionRoleDefinitionExtractor implements
		RoleDefinitionExtractor {

	private ReflectionRoleDefinitionExtractor reflectionRoleDefinitionExtractor;
	private WebXMLRoleDefinitionExtractor webXMLRoleDefinitionExtractor;
	
	public WebOrReflectionRoleDefinitionExtractor() {
		super();
	}

	public WebOrReflectionRoleDefinitionExtractor(ReflectionRoleDefinitionExtractor reflectionRoleDefinitionExtractor, WebXMLRoleDefinitionExtractor webXMLRoleDefinitionExtractor) {
		super();
		
		this.reflectionRoleDefinitionExtractor = reflectionRoleDefinitionExtractor;
		this.webXMLRoleDefinitionExtractor = webXMLRoleDefinitionExtractor;
	}

	public String[] extractAllRoles() {
		/* Leggo dal web.xml */
		String[] allRoles = webXMLRoleDefinitionExtractor.extractAllRoles();
		if(allRoles.length>0){
			return allRoles;
		}
		
		/* Leggo dal menu */
		allRoles = reflectionRoleDefinitionExtractor.extractAllRoles();
		if(allRoles.length>0){
			return allRoles;
		}

		/* Leggo dalla lista statica dei ruoli */
		List<String> roles = MenuInterfaceRegistry.getRoles();
		if (roles != null) {
			return roles.toArray(new String[0]);
		}
		
		return new String[0];
	}

	public void setReflectionRoleDefinitionExtractor(
			ReflectionRoleDefinitionExtractor reflectionRoleDefinitionExtractor) {
		this.reflectionRoleDefinitionExtractor = reflectionRoleDefinitionExtractor;
	}

	public void setWebXMLRoleDefinitionExtractor(
			WebXMLRoleDefinitionExtractor webXMLRoleDefinitionExtractor) {
		this.webXMLRoleDefinitionExtractor = webXMLRoleDefinitionExtractor;
	}

}
