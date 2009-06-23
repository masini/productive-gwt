package org.googlecode.gwt.reflection;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.googlecode.gwt.reflection.client.ReflectionTestCollectionPojo;
import org.googlecode.gwt.reflection.client.ReflectionTestMyOwnPojo;
import org.googlecode.gwt.reflection.client.ReflectionTestNewOwnPojo;
import org.googlecode.gwt.reflection.client.ReflectionTestPubSubscribe;
import org.googlecode.gwt.reflection.client.complexmodel.ReflectionTestComplexModel;

public class GwtReflectionTestSuite extends com.google.gwt.junit.tools.GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for gwt-framework-reflection");
		suite.addTestSuite(ReflectionTestMyOwnPojo.class);
		suite.addTestSuite(ReflectionTestNewOwnPojo.class);
		suite.addTestSuite(ReflectionTestPubSubscribe.class);
		suite.addTestSuite(ReflectionTestComplexModel.class);
		suite.addTestSuite(ReflectionTestCollectionPojo.class);
		return suite;
	}
}
