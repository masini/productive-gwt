package org.googlecode.gwt.server;

public class ServerUtility {

	public static boolean isHostedMode(){
		boolean retVal = false;
		
		try {
			Class.forName("com.google.gwt.dev.GWTShell");
			retVal = true;
		} catch (ClassNotFoundException e) {
		}
		
		return retVal;
	}
}
