package org.googlecode.gwt.client.logging;

public class LogText {
	
	private static StringBuilder logText = new StringBuilder();
	private static int maxLines = 100;
	
	private LogText(){};
	
	public static void writeOnLogText(String message) {
		LogText.logText.append(message).append("\n");
		if(LogText.getNumberOfLines() > LogText.maxLines) {
			LogText.logText.replace(0, LogText.logText.indexOf("\n") + 1, "");
		}
	}
	
	public static String getLogText() {
		return logText.toString();
	}

	public static int getNumberOfLines() {
		String trimmedText = LogText.logText.toString().trim(); 
		if(trimmedText.length() < 1) {
			return 0;
		}
		return trimmedText.split("\n").length;
	}
	
	public static void reset() {
		LogText.logText.setLength(0);
	}

	public static void setMaxLines(int maxLines) {
		if(maxLines < 0)
		{
			throw new IllegalArgumentException("maxLines must be greater than zero");
		}
		LogText.maxLines = maxLines;
	}
}
