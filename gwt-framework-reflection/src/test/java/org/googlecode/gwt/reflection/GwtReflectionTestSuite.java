package org.googlecode.gwt.reflection;


import org.googlecode.gwt.reflection.client.ReflectionTestMyOwnPojo;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GwtReflectionTestSuite extends com.google.gwt.junit.tools.GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for gwt-framework-reflection");
		suite.addTestSuite(ReflectionTestMyOwnPojo.class);
		return suite;
	}
}
