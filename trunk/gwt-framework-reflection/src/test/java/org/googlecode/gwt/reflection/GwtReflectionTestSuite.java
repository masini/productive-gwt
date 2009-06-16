package org.googlecode.gwt.reflection;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.googlecode.gwt.reflection.client.ReflectionTestMyOwnPojo;
import org.googlecode.gwt.reflection.client.ReflectionTestNewOwnPojo;

public class GwtReflectionTestSuite extends com.google.gwt.junit.tools.GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for gwt-framework-reflection");
		suite.addTestSuite(ReflectionTestMyOwnPojo.class);
		suite.addTestSuite(ReflectionTestNewOwnPojo.class);
		return suite;
	}
}
