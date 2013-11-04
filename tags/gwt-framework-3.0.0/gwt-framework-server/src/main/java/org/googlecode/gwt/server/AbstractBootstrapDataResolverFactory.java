package org.googlecode.gwt.server;

import javax.servlet.ServletConfig;

public abstract class AbstractBootstrapDataResolverFactory implements BootstrapDataResolverFactory {

	private final ServletConfig servletConfig;

	public AbstractBootstrapDataResolverFactory(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
	}
	public ServletConfig getServletConfig() {
		return servletConfig;
	}

}
