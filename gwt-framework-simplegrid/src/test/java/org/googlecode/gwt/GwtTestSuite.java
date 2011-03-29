package org.googlecode.gwt;

import org.googlecode.gwt.client.SimpleGridModuleTst;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

public class GwtTestSuite extends GWTTestSuite {
	
    public static Test suite() {
        TestSuite suite = new TestSuite("Simplegrid lib test suite");

        suite.addTestSuite(SimpleGridModuleTst.class); 
        
        return suite;
      }
}