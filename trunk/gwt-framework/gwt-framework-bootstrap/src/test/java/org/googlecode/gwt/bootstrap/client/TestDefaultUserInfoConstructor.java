package org.googlecode.gwt.bootstrap.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;
import org.junit.Assert;
import org.junit.Test;

public class TestDefaultUserInfoConstructor {

	@Test
	public void testConstructorNullParameters()
	{
		DefaultUserInfo info = new DefaultUserInfo("username","Nome","Cognome", Arrays.asList("Utente"), null );
		Assert.assertNotNull("User parameter names must be not null", info.getUserParameterNames());
		
		DefaultUserInfo info2 = new DefaultUserInfo("username","Nome","Cognome", new HashMap<String, String>(), null );
		Assert.assertNotNull("User parameter names must be not null", info2.getUserParameterNames());
	}
	
	@Test
	public void testConstructorNullRoles()
	{
		DefaultUserInfo info = new DefaultUserInfo("username","Nome","Cognome",(List<String>)null, new HashMap<String, String>() );
		Assert.assertNotNull("User roles must be not null", info.getRoles());
		
		DefaultUserInfo info2 = new DefaultUserInfo("username","Nome","Cognome",(Map<String, String>) null, new HashMap<String, String>() );
		Assert.assertNotNull("User roles must be not null", info2.getRoles());
	}
	
}
