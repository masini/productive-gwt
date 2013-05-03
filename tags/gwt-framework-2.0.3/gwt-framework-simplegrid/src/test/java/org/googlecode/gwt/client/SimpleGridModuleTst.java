package org.googlecode.gwt.client;

import com.google.gwt.junit.client.GWTTestCase;
//import com.google.gwt.user.client.Timer;

//import java.io.Serializable;

public class SimpleGridModuleTst extends GWTTestCase{

	public String getModuleName() {
		return "org.googlecode.gwt.simplegrid.SimpleGridEntryPoint";
	}

	/*
 	public void testSimpleGrid() throws Exception {
 		SimplePagingScrollTable<Serializable> table = new SimplePagingScrollTable<Serializable>(
			"datasource",
			 new String[] {"colonna uno", "colonna due", "colonna tre", "colonna quattro", "colonna cinque"}
		 );
		
 		module.getDefinition("pugnacious");
 		
 		Timer timer = new Timer() {
	   		public void run() {
	    		String value = module.getOutputLabel().getText();
				String control = "inclined to quarrel or fight readily;...";   
				assertEquals("should be " + control, control, value);
	    		finishTest();
	   		}
  		};
  		
  		timer.schedule(200);
  		delayTestFinish(500);
		finishTest();
	}
    */
}
