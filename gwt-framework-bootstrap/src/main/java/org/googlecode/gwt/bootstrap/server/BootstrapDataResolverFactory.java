package org.googlecode.gwt.bootstrap.server;

import java.util.Map;

public interface BootstrapDataResolverFactory {
	public BootstrapDataResolver createUserInfoResolver(Map<String, String> params);
}
