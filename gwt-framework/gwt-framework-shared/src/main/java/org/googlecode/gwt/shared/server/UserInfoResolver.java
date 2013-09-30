package org.googlecode.gwt.shared.server;

import org.googlecode.gwt.shared.client.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * An interface useful to abstract code that needs to resolve user related information in a web application by the actual implementation of the
 * storage.
 *
 *         Date: 7-giu-2007
 *         Time: 19.06.44
 */
public interface UserInfoResolver {
    UserInfo getCurrentUserInfo(final HttpServletRequest request);
}
