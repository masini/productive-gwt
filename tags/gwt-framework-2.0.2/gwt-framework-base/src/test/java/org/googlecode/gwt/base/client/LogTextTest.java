package org.googlecode.gwt.base.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogTextTest {

	@Before
	public void setup() {
		LogText.reset();
	}
	
	@Test
	public void testInitialBlank() {
		Assert.assertEquals("", LogText.getLogText());
	}
	
	@Test
	public void testAddLog() {
		String messagge = "Roba loggata";
		LogText.writeOnLogText(messagge);
		Assert.assertEquals(messagge + "\n", LogText.getLogText());
	}
	
	@Test
	public void testHowManyLinesNone() {
		Assert.assertEquals(0, LogText.getNumberOfLines());
	}
	
	@Test
	public void testHowManyLinesOne() {
		LogText.writeOnLogText("the message");
		Assert.assertEquals(1, LogText.getNumberOfLines());
	}
	
	@Test
	public void testHowManyLinesThree() {
		LogText.writeOnLogText("the three");
		LogText.writeOnLogText("lines");
		LogText.writeOnLogText("message");
		Assert.assertEquals(3, LogText.getNumberOfLines());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalMaxLines() {
		LogText.setMaxLines(-1);
	}
	
	@Test
	public void testCutLongLogText() {
		LogText.setMaxLines(3);
		
		LogText.writeOnLogText("the");
		LogText.writeOnLogText("four");
		LogText.writeOnLogText("lines");
		LogText.writeOnLogText("message");
		
		Assert.assertEquals(3, LogText.getNumberOfLines());
		Assert.assertEquals("four\nlines\nmessage\n", LogText.getLogText());
	}
	
}
